package org.controlaltdel.sample.controllers.api.v1;

import java.io.IOException;
import java.util.List;
import org.controlaltdel.sample.repository.IpRepository;
import org.controlaltdel.sample.service.IpLookupService;
import org.controlaltdel.sample.service.IpUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/v1")
public class IpLookupController {

  private final IpUpdateService ipUpdateService;
  private final IpLookupService ipLookupService;
  private final IpRepository ipRepository;

  /**
   * Constructor.
   * @param ipRepository The IP Repository.
   * @param ipUpdateService The update service.
   * @param ipLookupService The IP lookup service.
   */
  @Autowired
  public IpLookupController(final IpRepository ipRepository,
      final IpUpdateService ipUpdateService,
      final IpLookupService ipLookupService) {
    this.ipUpdateService = ipUpdateService;
    this.ipLookupService = ipLookupService;
    this.ipRepository = ipRepository;
  }

  /**
   * Retrieves all unmapped IPs.
   *
   * @return A list of ips as strings.
   * @throws IOException IO exception.
   */
  @RequestMapping(value = "ips", method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<List<String>> listIps() throws IOException {
    return new ResponseEntity<List<String>>(this.ipRepository.listUnmappedIps(), HttpStatus.OK);
  }

  /**
   * Fetches country code for a given IP.
   *
   * @param ip The ip.
   * @return A string containing country code.
   */
  @RequestMapping(value = "lookup/{ip}", method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<String> lookupIp(final @PathVariable("ip") String ip) {
    return new ResponseEntity<String>(this.ipLookupService.lookupIp(ip), HttpStatus.OK);
  }

  /**
   * Updates all ips that are unmapped.
   *
   * @return HTTP status code accepted.
   */
  @RequestMapping(value = "update", method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity updateAllIps() {
    this.ipUpdateService.updateIps();
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }
}
