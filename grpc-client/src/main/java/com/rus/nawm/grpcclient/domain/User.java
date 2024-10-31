package com.rus.nawm.grpcclient.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
  //  private String id;
  private String username;
  private String email;
  private String phoneNumber;
}
