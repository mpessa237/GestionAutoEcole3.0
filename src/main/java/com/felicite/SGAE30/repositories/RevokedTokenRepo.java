package com.felicite.SGAE30.repositories;

import com.felicite.SGAE30.models.RevokedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RevokedTokenRepo extends JpaRepository<RevokedToken,Long> {
    Optional<RevokedToken> findByToken(String token);

}
