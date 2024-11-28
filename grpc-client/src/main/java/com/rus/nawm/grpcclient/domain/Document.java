package com.rus.nawm.grpcclient.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Document {
  private String id;
  private String number;
  private User owner;
  private Integer expirationMonth;
  private Integer expirationYear;
  private Boolean isValid;
}
