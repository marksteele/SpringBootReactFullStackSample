package org.controlaltdel.sample.task;

import org.controlaltdel.sample.service.IpUpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailyIpCountryUpdate {

  private static final Logger log = LoggerFactory.getLogger(DailyIpCountryUpdate.class);
  private final IpUpdateService ipUpdateService;

  @Autowired
  public DailyIpCountryUpdate(final IpUpdateService ipUpdateService) {
    this.ipUpdateService = ipUpdateService;
  }

  /**
   * Scheduled task to update all ips that are unmapped. second, minute, hour, day of month, month,
   * day(s) of week
   */
  @Scheduled(cron = "0 0 2 * * ?")
  public void updateAllIps() {
    log.info("Running daily number update");
    this.ipUpdateService.updateIps();
  }
}
