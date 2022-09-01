package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PositionDao {


  private static final Logger logger = LoggerFactory.getLogger(TraderDao.class);

  private final String TABLE_NAME = "position";
  private final String ID_COLUMN = "account_id";
  private final String TICKER_COLUMN = "ticker";

  private JdbcTemplate jdbcTemplate;


  @Autowired
  public PositionDao(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public Optional<Position> findByIdAndTicker(Integer id, String ticker) {
    Optional<Position> position = Optional.empty();
    String selectSql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN + " =? AND "
        + TICKER_COLUMN + " =?";
    try {
      position = Optional.ofNullable((Position) jdbcTemplate.queryForObject(selectSql,
          BeanPropertyRowMapper.newInstance(Position.class), id, ticker));
    } catch (IncorrectResultSizeDataAccessException e) {
      logger.debug("Can't find id and ticker: " + id + ", " + ticker, e);
    }
    return position;
  }

  public boolean existsByIdAndTicker(Integer id, String ticker) {
    Optional<Position> result = findByIdAndTicker(id, ticker);
    return result.isPresent();
  }

  public List<Position> findAllById(Integer id) {
    List<Position> positions = new ArrayList<Position>();
    String selectSql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN + " =?";
    try {
      positions = jdbcTemplate.queryForList(selectSql, Position.class, id);
    } catch (IncorrectResultSizeDataAccessException e) {
      logger.debug("Can't find id: " + id, e);
    }
    return positions;
  }

  public boolean existsById(Integer id) {
    return findAllById(id).size() > 0;
  }

}
