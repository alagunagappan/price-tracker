package com.pricetracker.repository;

import com.pricetracker.model.Frequency;
import com.pricetracker.model.PriceAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceAlertRepository extends JpaRepository<PriceAlert, Long> {

    @Query("SELECT pa FROM PriceAlert pa WHERE pa.active = true AND pa.checkFrequency = :frequency")
    List<PriceAlert> findActiveAlertsByFrequency(@Param("frequency") Frequency frequency);
}
