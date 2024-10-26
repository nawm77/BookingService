package com.rus.nawm.mainservice.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Document {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  private String number;
  @OneToOne
  private User owner;
  private Integer expirationMonth;
  private Integer expirationYear;
  //todo можно уведомлять что срок действия документа закончился. для этого сделать отдельную очередь documentExpirationNotifier и туда класть сообщения а нотифаер будет вычитывать и отправлять далее
}
