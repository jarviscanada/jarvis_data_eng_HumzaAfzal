package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.domain.TraderAccountView;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

@Service
public class TraderAccountService {

  private TraderDao traderDao;
  private AccountDao accountDao;
  private PositionDao positionDao;
  private SecurityOrderDao securityOrderDao;

  @Autowired
  public TraderAccountService(TraderDao traderDao, AccountDao accountDao, PositionDao positionDao,
      SecurityOrderDao securityOrderDao) {
    this.traderDao = traderDao;
    this.accountDao = accountDao;
    this.positionDao = positionDao;
    this.securityOrderDao = securityOrderDao;
  }

  public TraderAccountView createTraderAndAccount(Trader trader) {
    if (trader == null) {
      throw new IllegalArgumentException("trader is null!");
    }
    if (trader.getDob() == null || trader.getCountry() == null
        || trader.getEmail() == null || trader.getFirstName() == null
        || trader.getLastName() == null) {
      throw new IllegalArgumentException("trader attributes are null!");
    }
    traderDao.save(trader);
    Account newAccount = new Account();
    newAccount.setId(trader.getId());
    newAccount.setTraderId(trader.getId());
    newAccount.setAmount(0.00);
    accountDao.save(newAccount);
    TraderAccountView newTraderAccountView = new TraderAccountView();
    newTraderAccountView.setAccount(newAccount);
    newTraderAccountView.setTrader(trader);
    return newTraderAccountView;
  }

  public void deleteTraderById(Integer traderId) {
    if (!traderDao.existsById(traderId)) {
      throw new IllegalArgumentException("Trader ID does not exist!");
    }
    Optional<Account> accountOptional = accountDao.findById(traderId);
    if (accountOptional.isPresent()) {

      Account account = accountOptional.get();
      if (account.getAmount() != 0) {
        throw new IllegalArgumentException("Trader does not have 0 cash balance!");
      }
      List<Position> positions = positionDao.findAllById(account.getId());
      if (positions.size() > 0) {
        for (Position position : positions) {
          if (position.getPosition() != 0) {
            throw new IllegalArgumentException("Trader has active positions!");
          }
        }
      }
      securityOrderDao.deleteByAccountId(account.getId());
      accountDao.deleteById(account.getId());
    }
    traderDao.deleteById(traderId);
  }

  public Account deposit(Integer traderId, Double fund) {
    if (!traderDao.existsById(traderId)) {
      throw new IllegalArgumentException("Trader ID does not exist!");
    }
    if (fund <= 0) {
      throw new IllegalArgumentException("Fund must be greater than 0!");
    }
    Optional<Account> accountOptional = accountDao.findById(traderId);
    if (!accountOptional.isPresent()) {
      throw new DataRetrievalFailureException("Account does not exist!");
    }

    accountDao.updateAmountById(traderId, fund);

    return accountDao.findById(traderId).get();

  }

  public Account withdraw(Integer traderId, Double fund) {
    if (!traderDao.existsById(traderId)) {
      throw new IllegalArgumentException("Trader ID does not exist!");
    }
    if (fund <= 0) {
      throw new IllegalArgumentException("Fund must be greater than 0!");
    }
    Optional<Account> accountOptional = accountDao.findById(traderId);
    if (!accountOptional.isPresent()) {
      throw new DataRetrievalFailureException("Account does not exist!");
    }

    accountDao.updateAmountById(traderId, -fund);

    return accountDao.findById(traderId).get();
  }

}
