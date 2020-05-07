package com.cedrus.patpetillo.springkafkapingpong.postgres;

import com.cedrus.patpetillo.springkafkapingpong.model.Action;
import com.cedrus.patpetillo.springkafkapingpong.model.Server;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class PingPongBallEntity {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Integer id;

  private String key;
  private String action;
  private String server;
  private String timeStamp;

  public PingPongBallEntity(String key, Action action, Server server, String timestamp) {
    this.key = key;
    this.action = action.name();
    this.server = server.name();
    this.timeStamp = timestamp;
  }
}
