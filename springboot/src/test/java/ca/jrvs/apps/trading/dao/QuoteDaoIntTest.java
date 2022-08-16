package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertEquals;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Quote;
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

  @Test
  public void save() {
    Quote quoteOne = new Quote("test", 10d, 10d, 10, 10d, 10);
    Quote resultOne = quoteDao.save(quoteOne);
    assertEquals(quoteOne, resultOne);

    Quote quoteTwo = new Quote("aapl", 10d, 10d, 10, 10d, 10);
    Quote resultTwo = quoteDao.save(quoteOne);
    assertEquals(quoteTwo, resultTwo);
  }

  @Test
  public void saveAll() {
  }

  @Test
  public void findById() {
  }

  @Test
  public void existsById() {
  }

  @Test
  public void findAll() {
  }

  @Test
  public void count() {
  }

  @Test
  public void deleteById() {
  }

  @Test
  public void deleteAll() {
  }

  @Test
  public void delete() {
  }

  @Test
  public void testDeleteAll() {
  }

  @Test
  public void findAllById() {
  }
}