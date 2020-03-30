package com.digitalmaps.desafio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.digitalmaps.desafio.model.PointOfInterest;

public interface PointOfInterestRepository extends JpaRepository<PointOfInterest, Long>{
	
	@Query("select p from PointOfInterest p where abs(p.x - :x) <= :maximumDistance and abs(p.y - :y) <= :maximumDistance")
	List<PointOfInterest> findByPossiblePointsInMaxRange(@Param("x") int x, @Param("y") int y, @Param("maximumDistance") int maximumDistance);
}
