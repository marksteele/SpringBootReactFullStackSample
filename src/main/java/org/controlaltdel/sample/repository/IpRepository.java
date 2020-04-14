package org.controlaltdel.sample.repository;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class IpRepository {

  /**
   * Query to retrieve all unmapped IP addresses.
   */
  private static final String GRAB_UNMAPPED_IPS =
      "SELECT ip FROM tblVisitors WHERE countryCode IS NULL";

  /**
   * Query to update a IP's information.
   */
  private static final String UPDATE_SQL = "UPDATE tblVisitors SET countryCode = ? WHERE ip = ?";

  private JdbcTemplate mysqlJdbcTemplate;

  @Autowired
  public IpRepository(final JdbcTemplate mysqlJdbcTemplate) {
    this.mysqlJdbcTemplate = mysqlJdbcTemplate;
  }

  /**
   * Retrieves all ips that don't have country mapping.
   *
   * @return A list of ips.
   */
  public List<String> listUnmappedIps() {
    return this.mysqlJdbcTemplate.queryForList(String.format(GRAB_UNMAPPED_IPS), String.class);
  }

  /**
   * Updates the country code for a given IP address.
   *
   * @param ip          An IP address, as a string.
   * @param countryCode The country code.
   */
  public void updateIp(final String ip, final String countryCode) {
    this.mysqlJdbcTemplate.update(
        UPDATE_SQL,
        countryCode,
        ip
    );
  }
}
