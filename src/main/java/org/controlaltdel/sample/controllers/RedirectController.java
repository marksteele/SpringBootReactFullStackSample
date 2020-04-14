package org.controlaltdel.sample.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

  @GetMapping(value = {"/{regex:\\w+}", "/**/{regex:\\w+}"})
  public String forward404() {
    return "forward:/";
  }

}
