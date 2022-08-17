package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertEquals;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.After;
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
public class QuoteDaoIntTest {

  @Autowired
  private QuoteDao quoteDao;

  private Quote savedQuote = new Quote();

  @Before
  public void insertOne() {
    savedQuote.setAskPrice(10d);
    savedQuote.setAskSize(10);
    savedQuote.setBidPrice(10.2d);
    savedQuote.setBidSize(10);
    savedQuote.setId("aapl");
    savedQuote.setLastPrice(10.10d);
    quoteDao.save(savedQuote);
  }

  @After
  public void deleteOne() {
    quoteDao.deleteById(savedQuote.getId());
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
  public void save() {
    Quote quoteOne = helper("test");
    Quote resultOne = quoteDao.save(quoteOne);
    assertEquals(quoteOne.getId(), resultOne.getId());
    quoteDao.deleteById(quoteOne.getTicker());

    Quote quoteTwo = helper("aapl");
    Quote resultTwo = quoteDao.save(quoteTwo);
    assertEquals(quoteTwo.getId(), resultTwo.getId());
  }

  @Test
  public void saveAll() {
    Quote quoteOne = helper("test");
    Quote quoteTwo = helper("aapl");
    List<Quote> queryQuotes = new ArrayList<Quote>();
    queryQuotes.add(quoteOne);
    queryQuotes.add(quoteTwo);
    List<Quote> resultQuotes = quoteDao.saveAll(queryQuotes);
    assertEquals(queryQuotes.get(0).getId(), resultQuotes.get(0).getId());
    assertEquals(queryQuotes.get(1).getId(), resultQuotes.get(1).getId());
    quoteDao.deleteById(quoteOne.getTicker());
  }

  @Test
  public void findById() {
    // empty path
    String ticker1 = "fb";
    Optional<Quote> result = quoteDao.findById(ticker1);
    assertEquals(false, result.isPresent());
    // good path
    String ticker2 = "aapl";
    result = quoteDao.findById(ticker2);
    assertEquals(true, result.isPresent());
    assertEquals(ticker2, result.get().getId());

  }

  @Test
  public void existsById() {
    // empty path
    String ticker1 = "fb";
    boolean result = quoteDao.existsById(ticker1);
    assertEquals(false, result);

    // good path
    String ticker2 = "aapl";
    result = quoteDao.existsById(ticker2);
    assertEquals(true, result);
  }

  @Test
  public void findAll() {
    List<Quote> resultQuotes = quoteDao.findAll();
    assertEquals(1, resultQuotes.size());
    assertEquals("aapl", resultQuotes.get(0).getTicker());
  }

  @Test
  public void count() {
    long result = quoteDao.count();
    assertEquals(1, result);

    Quote quoteOne = helper("test");
    Quote quoteTwo = helper("fb");
    quoteDao.save(quoteOne);
    quoteDao.save(quoteTwo);
    result = quoteDao.count();
    assertEquals(3, result);
    quoteDao.deleteById(quoteOne.getTicker());
    quoteDao.deleteById(quoteTwo.getTicker());
  }

  @Test
  public void deleteById() {
    Quote quoteOne = helper("test");
    quoteDao.save(quoteOne);
    assertEquals(true, quoteDao.existsById(quoteOne.getTicker()));
    quoteDao.deleteById(quoteOne.getTicker());

    assertEquals(false, quoteDao.existsById(quoteOne.getTicker()));
  }

  @Test
  public void deleteAll() {
    Quote quoteOne = helper("test");
    quoteDao.save(quoteOne);
    assertEquals(true, quoteDao.existsById(quoteOne.getTicker()));
    quoteDao.deleteAll();
    List<Quote> result = quoteDao.findAll();
    assertEquals(0, result.size());
    insertOne();
  }

  @Test
  public void delete() {
    // Not implemented
  }

  @Test
  public void testDeleteAll() {
    // Not implemented
  }

  @Test
  public void findAllById() {
    // Not implemented
  }
}