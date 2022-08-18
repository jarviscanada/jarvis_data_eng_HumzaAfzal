package ca.jrvs.apps.trading.service;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.ResourceNotFoundException;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import com.sun.org.apache.xpath.internal.operations.Quo;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteServiceIntTest {

  @Autowired
  private QuoteService quoteService;

  @Autowired
  private QuoteDao quoteDao;

  @Before
  public void setup() {
    quoteDao.deleteAll();
  }

  private Quote helper(String ticker) {
    Quote newQuote = new Quote();
    newQuote.setAskPrice(10d);
    newQuote.setAskSize(10);
    newQuote.setBidPrice(10d);
    newQuote.setBidSize(10);
    newQuote.setId(ticker);
    newQuote.setLastPrice(10d);
    return newQuote;
  }

  @Test
  public void updateMarketData() {
    // fail path
    Quote quoteOne = helper("test");
    quoteDao.save(quoteOne);
    try {
      quoteService.updateMarketData();
    } catch (ResourceNotFoundException e) {
      assertTrue(true);
    }
    quoteDao.deleteAll();

    // success path
    Quote quoteTwo = helper("aapl");
    Quote resultTwo = quoteDao.save(quoteTwo);
    try {
      quoteService.updateMarketData();
      IexQuote iexQuote = quoteService.findIexQuoteByTicker("aapl");
      assertEquals(iexQuote.getIexAskPrice(), resultTwo.getAskPrice());
    } catch (ResourceNotFoundException e) {

    }
    quoteDao.deleteAll();
  }

  @Test
  public void saveQuotes() {
    ArrayList<String> tickers = new ArrayList<String>();
    tickers.add("AAPL");
    tickers.add("TSLA");
    tickers.add("META");
    List<Quote> resultQuotes = quoteService.saveQuotes(tickers);
    assertNotNull(resultQuotes);
    assertEquals(resultQuotes.get(0).getTicker(), quoteDao.findById("AAPL").get().getTicker());
    assertEquals(resultQuotes.get(1).getTicker(), quoteDao.findById("TSLA").get().getTicker());
    assertEquals(resultQuotes.get(2).getTicker(), quoteDao.findById("META").get().getTicker());
    quoteDao.deleteAll();
  }

  @Test
  public void saveQuote() {
    String ticker = "META";
    Quote quote = quoteService.saveQuote(ticker);
    assertNotNull(quote);
    assertEquals("META", quoteDao.findById("META").get().getTicker());
    quoteDao.deleteAll();
  }

  @Test
  public void findIexQuoteByTicker() {
    String ticker = "AAPL";
    IexQuote iexQuote = quoteService.findIexQuoteByTicker(ticker);
    assertNotNull(iexQuote);
    assertEquals(ticker, iexQuote.getSymbol());
  }

  @Test
  public void findAllQuotes() {
    ArrayList<String> tickers = new ArrayList<String>();
    tickers.add("AAPL");
    tickers.add("TSLA");
    tickers.add("META");
    List<Quote> quotes = quoteService.saveQuotes(tickers);
    List<Quote> result = quoteService.findAllQuotes();
    assertNotNull(result);
    assertEquals(quotes.size(), result.size());
    quoteDao.deleteAll();
  }
}