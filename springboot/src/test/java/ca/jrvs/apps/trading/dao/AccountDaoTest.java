package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
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
public class AccountDaoTest {

  @Autowired
  private AccountDao accountDao;
  @Autowired
  private TraderDao traderDao;

  private Account savedAccount = new Account();

  private Trader savedTrader = new Trader();

  @Before
  public void insertOne() {
    savedTrader.setId(1);
    savedTrader.setCountry("Canada");
    savedTrader.setDob(Date.valueOf("1999-10-27"));
    savedTrader.setFirstName("Humza");
    savedTrader.setLastName("Afzal");
    savedTrader.setEmail("humza.afzal77@gmail.com");
    traderDao.save(savedTrader);
    savedAccount.setId(1);
    savedAccount.setAmount(323.00);
    savedAccount.setTraderId(1);
    accountDao.save(savedAccount);
  }

  @After
  public void deleteOne() {
    accountDao.deleteById(savedAccount.getId());
    traderDao.deleteById(savedTrader.getId());
  }

  @Test
  public void findAllById() {
    List<Account> accounts = Lists.newArrayList(accountDao.findAllById(Arrays.asList(savedAccount.getId())));
    assertEquals(1, accounts.size());
    assertEquals(savedAccount.getAmount(), accounts.get(0).getAmount());
  }
}