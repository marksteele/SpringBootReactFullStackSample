package org.controlaltdel.sample.controllers.api.v1;

import java.io.IOException;
import java.util.List;
import org.controlaltdel.sample.model.Visitor;
import org.controlaltdel.sample.repository.VisitorRepository;
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
public class VisitorController {
  private VisitorRepository visitorRepository;

  @Autowired
  public VisitorController(final VisitorRepository visitorRepository) {
    this.visitorRepository = visitorRepository;
  }

  /**
   * Retrieves all visitors.
   * @return The list of visitors
   */
  @RequestMapping(value = "visitor", method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<List<Visitor>> listVisitors() throws IOException {
    return new ResponseEntity<List<Visitor>>(this.visitorRepository.listVisitors(), HttpStatus.OK);
  }

  /**
   * Add a visitor.
   * @param ip An IP.
   * @return An HTTP status code.
   */
  @RequestMapping(value = "visitor/{ip}", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity addVisitor(final @PathVariable("ip") String ip) throws IOException {
    this.visitorRepository.addVisitor(ip);
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }

}
