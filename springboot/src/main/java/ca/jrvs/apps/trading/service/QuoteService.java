package ca.jrvs.apps.trading.service;


import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.ResourceNotFoundException;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import com.sun.org.apache.xpath.internal.operations.Quo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class QuoteService {

  private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);

  private QuoteDao quoteDao;
  private MarketDataDao marketDataDao;

  @Autowired
  public QuoteService(QuoteDao quoteDao, MarketDataDao marketDataDao) {
    this.quoteDao = quoteDao;
    this.marketDataDao = marketDataDao;
  }

  public void updateMarketData() throws ResourceNotFoundException {
    List<Quote> allQuotes = quoteDao.findAll();
    ArrayList<String> tickers = new ArrayList<String>();
    for (Quote quote : allQuotes) {
      tickers.add(quote.getTicker());
    }
    try {
      List<IexQuote> iexQuotes = marketDataDao.findAllById(tickers);
      ArrayList<Quote> quotes = new ArrayList<Quote>();
      for (IexQuote iexQuote : iexQuotes) {
        quotes.add(buildQuoteFromIexQuote(iexQuote));
      }
      quoteDao.saveAll(quotes);
    } catch (Exception e) {
      throw new ResourceNotFoundException("Cannot Find Ticker", e);
    }
  }

  protected static Quote buildQuoteFromIexQuote(IexQuote iexQuote) {
    Quote quote = new Quote();

    quote.setId(iexQuote.getSymbol());
    quote.setAskPrice(Double.valueOf(iexQuote.getIexAskPrice()));
    quote.setAskSize(iexQuote.getIexAskSize());
    quote.setBidPrice(Double.valueOf(iexQuote.getIexBidPrice()));
    quote.setBidSize(iexQuote.getIexBidSize());
    quote.setLastPrice(iexQuote.getLatestPrice());
    return quote;
  }

  public List<Quote> saveQuotes(List<String> tickers) {
    ArrayList<Quote> quotes = new ArrayList<Quote>();
    for (String ticker : tickers) {
      quotes.add(saveQuote(ticker));
    }
    return quotes;
  }

  public Quote saveQuote(String ticker) {
    Optional<IexQuote> iexQuoteOption = marketDataDao.findById(ticker);
    Quote quote;
    if (iexQuoteOption.isPresent()) {
      quote = buildQuoteFromIexQuote(iexQuoteOption.get());
      return quoteDao.save(quote);
    }
    else {
      throw new IllegalArgumentException("Ticker not found");
    }
  }

  public IexQuote findIexQuoteByTicker(String ticker) {
    return marketDataDao.findById(ticker)
        .orElseThrow(() -> new IllegalArgumentException(ticker + " is invalid"));
  }

  public Quote saveQuote(Quote quote) {
    return quoteDao.save(quote);
  }

  public List<Quote> findAllQuotes() {
    return quoteDao.findAll();
  }
}
