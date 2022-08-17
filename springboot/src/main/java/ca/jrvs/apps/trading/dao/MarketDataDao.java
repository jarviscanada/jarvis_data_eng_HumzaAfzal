package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.dao.utils.TradingUtils;
import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MarketDataDao implements CrudRepository<IexQuote, String> {

  private static final String IEX_BATCH_PATH = "/stock/market/batch?symbols=%s&types=quote&token=";
  private final String IEX_BATCH_URL;

  private Logger logger = LoggerFactory.getLogger(MarketDataDao.class);
  private HttpClientConnectionManager httpClientConnectionManager;

  @Autowired
  public MarketDataDao(HttpClientConnectionManager httpClientConnectionManager,
      MarketDataConfig marketDataConfig) {
    this.httpClientConnectionManager = httpClientConnectionManager;
    IEX_BATCH_URL = marketDataConfig.getHost() + IEX_BATCH_PATH + marketDataConfig.getToken();
  }

  @Override
  public Optional<IexQuote> findById(String ticker) {
    Optional<IexQuote> iexQuote;
    List<IexQuote> quotes = findAllById(Collections.singletonList(ticker));

    if (quotes.size() == 0) {
      return Optional.empty();
    } else if (quotes.size() == 1) {
      iexQuote = Optional.of(quotes.get(0));
    } else {
      throw new DataRetrievalFailureException("Unexpected number of quotes!");
    }
    return iexQuote;
  }

  @Override
  public List<IexQuote> findAllById(Iterable<String> tickers) {

    String input = "";
    for (String ticker : tickers) {
      input += ',' + ticker;
    }
    input = input.replaceFirst(",", "");
    String url = IEX_BATCH_URL.replace("%s", input);
    Optional<String> result = executeHttpGet(url);

    if (!result.isPresent()) {
      throw new DataRetrievalFailureException("No Result!");
    }

    JSONObject iexQuotesJson = new JSONObject(result.get());

    List<IexQuote> iexQuotes = new ArrayList<IexQuote>();

    for (String ticker : tickers) {
      try {
        String quoteStr = ((JSONObject) iexQuotesJson.get(ticker)).get("quote").toString();
        IexQuote iexQuote = TradingUtils.toObjectFromJson(quoteStr, IexQuote.class);
        iexQuotes.add(iexQuote);
      } catch (Exception e) {
        throw new IllegalArgumentException("Response has no entity! ", e);
      }
    }

    return iexQuotes;
  }

  private Optional<String> executeHttpGet(String url) {
    HttpGet httpGet = new HttpGet(url);
    CloseableHttpClient client = getHttpClient();

    try {
      HttpResponse response = client.execute(httpGet);
      //TODO : HERE
      String result = "{" + EntityUtils.toString(response.getEntity()) + "}";
      return Optional.of(result);
    } catch (Exception e) {
      throw new RuntimeException("Failed to execute", e);
    }
  }

  private CloseableHttpClient getHttpClient() {
    return HttpClients.custom().setConnectionManager(httpClientConnectionManager)
        .setConnectionManagerShared(true).build();
  }

  @Override
  public boolean existsById(String s) {
    throw new UnsupportedOperationException("Not implemented");
  }


  @Override
  public <S extends IexQuote> S save(S s) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public <S extends IexQuote> Iterable<S> saveAll(Iterable<S> iterable) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public Iterable<IexQuote> findAll() {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public long count() {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteById(String s) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void delete(IexQuote iexQuote) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteAll(Iterable<? extends IexQuote> iterable) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteAll() {
    throw new UnsupportedOperationException("Not implemented");
  }
}
