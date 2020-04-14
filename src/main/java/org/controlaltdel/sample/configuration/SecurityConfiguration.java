package org.controlaltdel.sample.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.cors().disable();
    http
        .antMatcher("/**")
        .authorizeRequests()
        .antMatchers("/login**", "/webjars/**", "/error**", "/actuator/**")
        .permitAll()
        .anyRequest()
        .authenticated().and()
        .oauth2Login();
  }
}
