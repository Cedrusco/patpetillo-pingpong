package com.cedrus.patpetillo.springkafkapingpong.controller;

import com.cedrus.patpetillo.springkafkapingpong.model.*;
import com.cedrus.patpetillo.springkafkapingpong.stream.PingPongBallService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
public class PingPongBallController {

  private final PingPongBallService pingPongBallService;

  @RequestMapping("/")
  public String index() {
    return "Ping Pong application running...";
  }

  @PostMapping("/serve")
  public ResponseEntity<ServeBallResponse> serveBall(
      @RequestBody ServeBallRequest serveBallRequest) {
    {
      log.debug("Serve ball request: {}", serveBallRequest);
    }

    return createBall(serveBallRequest);
  }

  private ResponseEntity<ServeBallResponse> createBall(ServeBallRequest serveBallRequest) {
    log.info("Creating ball: {}", serveBallRequest);
    try {
      final Integer id = serveBallRequest.getId();
      final Color color = Color.valueOf(serveBallRequest.getColor());
      final PingPongTeam currentTeam = PingPongTeam.BLUE_TEAM;
      final PingPongTeam receivingTeam = PingPongTeam.RED_TEAM;
      final Server server = Server.PLAYER_ONE_SERVICE;

      try {
        final PingPongBall pingPongBall =
            new PingPongBall(id, currentTeam, receivingTeam, server, color);
        pingPongBallService.serveBall(pingPongBall);
        ServeBallResponse serveBallResponse = new ServeBallResponse(true);

        return new ResponseEntity<>(serveBallResponse, HttpStatus.OK);
      } catch (RuntimeException e) {
        log.error("Runtime except when trying to create ball", e);
        ServeBallResponse serveBallResponse =
            new ServeBallResponse(false, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(serveBallResponse, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    } catch (IllegalArgumentException e) {
      log.error("Invalid ping pong ball color: {}", serveBallRequest.getColor());

      ServeBallResponse serveBallResponse =
          new ServeBallResponse(
              false,
              String.format("Invalid ping pong ball color: %s", serveBallRequest.getColor()),
              HttpStatus.BAD_REQUEST);
      return new ResponseEntity<>(serveBallResponse, HttpStatus.BAD_REQUEST);
    }
  }
}
