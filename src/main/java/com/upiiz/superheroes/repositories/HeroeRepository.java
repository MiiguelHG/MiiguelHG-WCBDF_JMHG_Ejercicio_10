package com.upiiz.superheroes.repositories;

import com.upiiz.superheroes.entities.HeroeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroeRepository extends JpaRepository<HeroeEntity, Long> {
}
