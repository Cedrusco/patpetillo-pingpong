package com.cedrus.patpetillo.springkafkapingpong.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PingPongBall {
  private Integer id;
  private PingPongTeam currentTeamWithBall;
  private PingPongTeam receivingTeamForBall;
  private Server server;
  private Color color;
}
