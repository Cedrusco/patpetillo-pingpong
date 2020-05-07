package com.cedrus.patpetillo.springkafkapingpong.repository;

import com.cedrus.patpetillo.springkafkapingpong.postgres.PingPongBallEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PingPongBallEventRepository extends
    CrudRepository<PingPongBallEntity, Integer> {

}
