package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertEquals;

import ca.jrvs.apps.trading.TestConfig;
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
public class TraderDaoIntTest {

  @Autowired
  private TraderDao traderDao;

  private Trader savedTrader = new Trader();

  @Before
  public void insertOne() {
    savedTrader.setId(1);
    savedTrader.setCountry("Canada");
    savedTrader.setDob(Date.valueOf("2003-04-17"));
    savedTrader.setFirstName("Test");
    savedTrader.setLastName("Test");
    savedTrader.setEmail("sadasdasdas");
    traderDao.save(savedTrader);
  }

  @After
  public void deleteOne() {
    traderDao.deleteById(savedTrader.getId());
  }

  @Test
  public void findAllById() {
    List<Trader> traders = Lists.newArrayList(
        traderDao.findAllById(Arrays.asList(savedTrader.getId())));
    assertEquals(1, traders.size());
    assertEquals(savedTrader.getCountry(), traders.get(0).getCountry());
  }
}