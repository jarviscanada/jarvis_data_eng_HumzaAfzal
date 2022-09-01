package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertEquals;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import com.google.common.collect.Lists;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
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
public class SecurityOrderDaoTest {

  @Autowired
  private SecurityOrderDao securityOrderDao;
  @Autowired
  private AccountDao accountDao;
  @Autowired
  private TraderDao traderDao;
  @Autowired
  private QuoteDao quoteDao;

  private Quote savedQuote = new Quote();
  private SecurityOrder savedSecurityOrder = new SecurityOrder();
  private Account savedAccount = new Account();
  private Trader savedTrader = new Trader();

  @Before
  public void insertOne() {
    savedQuote.setAskPrice(10d);
    savedQuote.setAskSize(10);
    savedQuote.setBidPrice(10.2d);
    savedQuote.setBidSize(10);
    savedQuote.setId("AAPL");
    savedQuote.setLastPrice(10.10d);
    quoteDao.save(savedQuote);
    savedTrader.setId(1);
    savedTrader.setCountry("Canada");
    savedTrader.setDob(Date.valueOf("2003-04-17"));
    savedTrader.setFirstName("Test");
    savedTrader.setLastName("Test");
    savedTrader.setEmail("sadasdasdas");
    traderDao.save(savedTrader);
    savedAccount.setId(1);
    savedAccount.setAmount(323.00);
    savedAccount.setTraderId(1);
    accountDao.save(savedAccount);

    savedSecurityOrder.setNotes("");
    savedSecurityOrder.setId(1);
    savedSecurityOrder.setAccountId(1);
    savedSecurityOrder.setSize(3);
    savedSecurityOrder.setPrice(43.22);
    savedSecurityOrder.setTicker("AAPL");
    savedSecurityOrder.setStatus(1);
    securityOrderDao.save(savedSecurityOrder);
  }

  @After
  public void deleteOne() {
    securityOrderDao.deleteById(savedSecurityOrder.getId());
    accountDao.deleteById(savedAccount.getId());
    traderDao.deleteById(savedTrader.getId());
    quoteDao.deleteById(savedQuote.getId());
  }

  @Test
  public void findAllById() {
    List<SecurityOrder> orders = Lists.newArrayList(
        securityOrderDao.findAllById(Arrays.asList(savedSecurityOrder.getId())));
    assertEquals(1, orders.size());
    assertEquals(savedSecurityOrder.getTicker(), orders.get(0).getTicker());
  }

}