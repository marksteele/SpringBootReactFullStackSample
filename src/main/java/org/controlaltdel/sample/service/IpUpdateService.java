package org.controlaltdel.sample.service;

import static com.pivovarit.collectors.ParallelCollectors.parallelToMap;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.controlaltdel.sample.repository.IpRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IpUpdateService {

  private static final Logger log = LoggerFactory.getLogger(IpUpdateService.class);
  private static final Integer PARALLEL_REQUESTS = 10;

  private IpRepository ipRepository;
  private IpLookupService ipLookupService;
  private ExecutorService executor;

  /**
   * Constructor.
   * @param ipRepository The IP repository.
   * @param ipLookupService The IP lookup service.
   */
  @Autowired
  public IpUpdateService(
      final IpRepository ipRepository,
      final IpLookupService ipLookupService) {
    this.ipRepository = ipRepository;
    this.ipLookupService = ipLookupService;
    this.executor = Executors.newFixedThreadPool(PARALLEL_REQUESTS);
  }

  /**
   * Updates all country codes that aren't set.
   */
  public void updateIps() {
    log.debug("Updating all ips");
    this.ipRepository
        .listUnmappedIps()
        .stream()
        .collect(parallelToMap(i -> i, i -> this.ipLookupService.lookupIp(i), this.executor,
            PARALLEL_REQUESTS))
        .join()
        .forEach((ip, countryCode) -> {
          if (!"unknown".equals(countryCode)) {
            this.ipRepository.updateIp(ip, countryCode);
          }
        });
  }
}
