package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class QuoteDao implements CrudRepository<Quote, String> {


  private static final String TABLE_NAME = "quote";
  private static final String ID_COLUMN_NAME = "ticker";

  private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);
  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public QuoteDao(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
    simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);
  }


  @Override
  public Quote save(Quote quote) {
    if (existsById(quote.getTicker())) {
      int updatedRowNo = updateOne(quote);
      if (updatedRowNo != 1) {
        throw new DataRetrievalFailureException("Unable to update quote");
      }
    } else {
      addOne(quote);
    }
    return quote;
  }

  private void addOne(Quote quote) {
    SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(quote);
    int row = simpleJdbcInsert.execute(parameterSource);
    if (row != 1) {
      throw new IncorrectResultSizeDataAccessException("Failed to insert", 1, row);
    }
  }

  private int updateOne(Quote quote) {
    String update_sql = "UPDATE quote SET last_price=?, bid_price=?, bid_size=?, ask_price=?, ask_size=? WHERE ticker=?";
    return jdbcTemplate.update(update_sql, makeUpdateValues(quote));
  }

  private Object[] makeUpdateValues(Quote quote) {
    Object[] elements = new Object[]{quote.getLastPrice(), quote.getBidPrice(), quote.getBidSize(),
        quote.getAskPrice(), quote.getAskSize(), quote.getTicker()};
    return elements;
  }

  @Override
  public <S extends Quote> List<S> saveAll(Iterable<S> quotes) {
    List<S> result = new ArrayList<S>();

    for (Quote quote : quotes) {
      result.add((S) save(quote));
    }

    return result;
  }

  @Override
  public Optional<Quote> findById(String ticker) {
    String find_sql = "SELECT * FROM " + TABLE_NAME + " WHERE ticker=?";
    String[] tickers = new String[]{ticker};

    List<Quote> quotes = jdbcTemplate.query(find_sql, tickers,
        new BeanPropertyRowMapper<Quote>(Quote.class));
    if (quotes.size() == 1) {
      return Optional.of(quotes.get(0));
    } else if (quotes.size() == 0) {
      return Optional.empty();
    } else {
      throw new RuntimeException("Duplicate tickers!!");
    }


  }

  @Override
  public boolean existsById(String ticker) {
    Optional<Quote> quote = findById(ticker);
    return quote.isPresent();
  }

  @Override
  public List<Quote> findAll() {
    String find_sql = "SELECT * FROM " + TABLE_NAME;
    List<Quote> quotes = jdbcTemplate.query(find_sql,
        new BeanPropertyRowMapper<Quote>(Quote.class));
    return quotes;
  }

  @Override
  public long count() {
    String count_sql = "SELECT COUNT(*) FROM " + TABLE_NAME;
    return jdbcTemplate.queryForObject(count_sql, Long.class);
  }

  @Override
  public void deleteById(String ticker) {
    String delete_sql = "DELETE FROM " + TABLE_NAME + " WHERE ticker=?";
    Object[] tickers = new Object[]{ticker};
    jdbcTemplate.update(delete_sql, tickers);
  }

  @Override
  public void deleteAll() {
    String delete_sql = "DELETE FROM " + TABLE_NAME;
    jdbcTemplate.update(delete_sql);
  }

  @Override
  public void delete(Quote quote) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteAll(Iterable<? extends Quote> iterable) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public Iterable<Quote> findAllById(Iterable<String> iterable) {
    throw new UnsupportedOperationException("Not implemented");
  }
}
