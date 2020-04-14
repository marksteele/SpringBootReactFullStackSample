package org.controlaltdel.sample.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Visitor {

  @NonNull
  private Integer id;
  @NonNull
  private String ip;
  private String countryCode;
}
