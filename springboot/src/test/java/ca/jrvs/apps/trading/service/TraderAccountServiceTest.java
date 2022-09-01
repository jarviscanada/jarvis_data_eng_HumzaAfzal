package ca.jrvs.apps.trading.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.domain.TraderAccountView;
import java.sql.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class TraderAccountServiceTest {

  private TraderAccountView savedView;
  @Autowired
  private TraderAccountService traderAccountService;
  @Autowired
  private TraderDao traderDao;
  @Autowired
  private AccountDao accountDao;

  public Trader createTrader() {
    Trader savedTrader = new Trader();
    savedTrader.setId(1);
    savedTrader.setCountry("Canada");
    savedTrader.setDob(Date.valueOf("2003-04-17"));
    savedTrader.setFirstName("Test");
    savedTrader.setLastName("Test");
    savedTrader.setEmail("sadasdasdas");
    return savedTrader;
  }

  @Test
  public void createTraderAndAccount() {

    Trader savedTrader = createTrader();

    traderAccountService.createTraderAndAccount(savedTrader);

    assertTrue(traderDao.findById(savedTrader.getId()).isPresent());

    Trader resultTrader = traderDao.findById(savedTrader.getId()).get();
    assertEquals(resultTrader.getCountry(), savedTrader.getCountry());
    assertEquals(resultTrader.getId(), savedTrader.getId());

    assertTrue(accountDao.findById(savedTrader.getId()).isPresent());
    Account resultAccount = accountDao.findById(savedTrader.getId()).get();

    assertEquals(resultAccount.getAmount(), 0.00, 0.00001);

    accountDao.deleteById(1);
    traderDao.deleteById(1);
  }

  @Test
  public void deleteTraderById() {
    Trader savedTrader = createTrader();
    traderAccountService.createTraderAndAccount(savedTrader);
    traderAccountService.deleteTraderById(savedTrader.getId());
    assertFalse(traderDao.existsById(savedTrader.getId()));
    accountDao.deleteById(1);
    traderDao.deleteById(1);
  }

  @Test
  public void deposit() {
    Trader savedTrader = createTrader();
    traderAccountService.createTraderAndAccount(savedTrader);
    traderAccountService.deposit(savedTrader.getId(), 40.00);
    try {
      assertEquals(40.00, accountDao.findById(savedTrader.getId()).get().getAmount(), 0.00001);
    } catch (Exception e) {
      fail();
    }
    accountDao.deleteById(1);
    traderDao.deleteById(1);
  }

  @Test
  public void withdraw() {
    Trader savedTrader = createTrader();
    traderAccountService.createTraderAndAccount(savedTrader);
    traderAccountService.deposit(savedTrader.getId(), 40.00);
    traderAccountService.withdraw(savedTrader.getId(), 20.00);
    try {
      assertEquals(20.00, accountDao.findById(savedTrader.getId()).get().getAmount(), 0.00001);
    } catch (Exception e) {
      fail();
    }
    accountDao.deleteById(1);
    traderDao.deleteById(1);
  }
}