package org.controlaltdel.sample.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IpLookupResponse {

  private String countryCode;
  private String countryCode3;
  private String countryName;
  private String countryEmoji;
}
