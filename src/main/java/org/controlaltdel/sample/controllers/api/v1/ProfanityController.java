package org.controlaltdel.sample.controllers.api.v1;

import static com.github.pemistahl.lingua.api.Language.ENGLISH;
import static com.github.pemistahl.lingua.api.Language.FRENCH;
import static com.github.pemistahl.lingua.api.Language.SPANISH;
import static com.github.pemistahl.lingua.api.Language.UNKNOWN;

// https://github.com/Twinkle942910/CheckMateFilter
import com.filter.textcorrector.Filter;
import com.filter.textcorrector.TextFilter;
import com.filter.textcorrector.profanity_filtering.model.Censored;

// Compare with using https://github.com/Zabuzard/Grawlox/wiki


import com.github.pemistahl.lingua.api.Language;
import com.github.pemistahl.lingua.api.LanguageDetector;
import com.github.pemistahl.lingua.api.LanguageDetectorBuilder;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.controlaltdel.sample.model.LanguageDetectionRequest;
import org.controlaltdel.sample.model.ProfanityFilterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/v1")
@Slf4j
public class ProfanityController {
  private LanguageDetector detector;
  private Map<Language, com.filter.textcorrector.spellchecking.Language> languageMap = new HashMap<>();

  ProfanityController() {
    this.detector = LanguageDetectorBuilder.fromLanguages(ENGLISH, FRENCH, SPANISH).build();
    languageMap.put(ENGLISH, com.filter.textcorrector.spellchecking.Language.ENGLISH);
//    languageMap.put(FRENCH, com.filter.textcorrector.spellchecking.Language.FRENCH);
//    languageMap.put(SPANISH, com.filter.textcorrector.spellchecking.Language.SPANISH);
    languageMap.put(UNKNOWN, com.filter.textcorrector.spellchecking.Language.ENGLISH);
  }

  @RequestMapping(value = "language", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity detectLanguage(final @RequestBody LanguageDetectionRequest request) {
    return new ResponseEntity<>(this.detector.detectLanguageOf(request.getContent()),HttpStatus.OK);
  }

  @RequestMapping(value = "profanity", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity cleanText(final @RequestBody ProfanityFilterRequest request) {
    Language lang = this.detector.detectLanguageOf(request.getContent());
    log.info("Detected language: {}", lang);
    Filter textFilter = new TextFilter(this.languageMap.getOrDefault(lang, com.filter.textcorrector.spellchecking.Language.ENGLISH));
    textFilter.setProfanityReplacement("[****]");
    textFilter.doCheckCompounds(true);
    textFilter.doPreproccessing(true);
    return new ResponseEntity<Censored>(textFilter.searchForProfanity(request.getContent()),HttpStatus.OK);
  }

}
