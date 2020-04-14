package org.controlaltdel.sample.repository;

import java.util.List;
import org.controlaltdel.sample.model.Visitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class VisitorRepository {

  /**
   * Query to retrieve all visitors.
   */
  private static final String LIST_VISITORS_SQL = "SELECT * FROM tblVisitors";

  /**
   * Query to add a visitor.
   */
  private static final String INSERT_SQL = "INSERT INTO tblVisitors VALUES(NULL,?,NULL)";

  private JdbcTemplate mysqlJdbcTemplate;

  @Autowired
  public VisitorRepository(final JdbcTemplate mysqlJdbcTemplate) {
    this.mysqlJdbcTemplate = mysqlJdbcTemplate;
  }

  /**
   * Retrieves all visitors.
   *
   * @return A list of visitors.
   */
  public List<Visitor> listVisitors() {
    return this.mysqlJdbcTemplate
        .query(LIST_VISITORS_SQL, new BeanPropertyRowMapper(Visitor.class));
  }

  /**
   * Updates the country code for a given IP address.
   *
   * @param ip An IP address, as a string.
   */
  public void addVisitor(final String ip) {
    this.mysqlJdbcTemplate.update(
        INSERT_SQL,
        ip
    );
  }
}
