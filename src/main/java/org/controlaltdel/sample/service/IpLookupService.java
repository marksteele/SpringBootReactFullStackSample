package org.controlaltdel.sample.service;

import java.util.Optional;
import org.controlaltdel.sample.model.IpLookupResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class IpLookupService {

  private static final Logger log = LoggerFactory.getLogger(IpLookupService.class);
  private static final String API_URL = "https://api.ip2country.info/ip?%s";

  /**
   * Query the IP2Country API.
   *
   * @param ip The IP address to lookup.
   * @return A string that represents the country code, or null.
   */
  public String lookupIp(final String ip) {
    String countryCode = "unknown";
    RestTemplate restTemplate = new RestTemplate();
    try {
      IpLookupResponse res = restTemplate
          .getForObject(String.format(API_URL, ip), IpLookupResponse.class);
      if (res != null) {
        countryCode = Optional.ofNullable(res.getCountryCode()).orElse("unknown");
      }
    } catch (RestClientException e) {
      log.debug(e.getMessage());
    }
    return countryCode;
  }
}
