package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Entity;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public abstract class JdbcCrudDao<T extends Entity<Integer>> implements CrudRepository<T, Integer> {

  private static final Logger logger = LoggerFactory.getLogger(JdbcCrudDao.class);

  abstract public JdbcTemplate getJdbcTemplate();

  abstract public SimpleJdbcInsert getSimpleJdbcInsert();

  abstract  public String getTableName();

  abstract public String getIdColumnName();

  abstract Class<T> getEntityClass();

  @Override
  public <S extends T> S save(S entity) {
    if (existsById(entity.getId())) {
      if (updateOne(entity) != 1) {
        throw new DataRetrievalFailureException("Unable to update quote");
      }
    } else {
      addOne(entity);
    }
    return entity;
  }

  private <S extends T> void addOne(S entity) {
    SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);

    Number newId = getSimpleJdbcInsert().executeAndReturnKey(parameterSource);
    entity.setId(newId.intValue());
  }

  abstract public int updateOne(T entity);

  @Override
  public Optional<T> findById(Integer id) {
    Optional<T> entity = Optional.empty();
    String selectSql = "SELECT * FROM " + getTableName() + " WHERE " + getIdColumnName() + " =?";

    try {
      entity = Optional.ofNullable((T) getJdbcTemplate()
          .queryForObject(selectSql,
              BeanPropertyRowMapper.newInstance(getEntityClass()), id));
    } catch (IncorrectResultSizeDataAccessException e) {
      logger.debug("Can't find trader id:" + id, e);
    }
    return entity;
  }

  @Override
  public boolean existsById(Integer id){

    Optional<T> result = findById(id);
    return result.isPresent();
  }

  @Override
  public List<T> findAll() {

    String selectSql = "SELECT * FROM " + getTableName();

    List<T> entities = getJdbcTemplate().query(selectSql,
        BeanPropertyRowMapper.newInstance(getEntityClass()));
    return entities;
  }

  @Override
  public List<T> findAllById(Iterable<Integer> ids) {
    List<T> objects = new ArrayList<>();
    for (int id : ids) {
      Optional<T> result = findById(id);
      if (result.isPresent()) {
        objects.add(result.get());
      }
      else {
        throw new DataRetrievalFailureException("Could not find id " + id);
      }
    }
    return objects;
  }

  @Override
  public void deleteById(Integer id) {
    String delete_sql = "DELETE FROM " + getTableName() + " WHERE " + getIdColumnName() +" =?";
    Object[] ids = new Object[]{id};
    getJdbcTemplate().update(delete_sql, ids);
  }

  @Override
  public long count() {
    String count_sql = "SELECT COUNT(*) FROM " + getTableName();
    return getJdbcTemplate().queryForObject(count_sql, Long.class);
  }

  @Override
  public void deleteAll() {
    String delete_sql = "DELETE FROM " + getTableName();
    getJdbcTemplate().update(delete_sql);
  }

}
