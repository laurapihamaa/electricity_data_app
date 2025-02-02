package electricity.data.backend.v1.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import electricity.data.backend.v1.entities.DailyTotals;
import electricity.data.backend.v1.entities.ElectricityData;
import electricity.data.backend.v1.entities.NegativeHours;

@Repository
public interface ElectricityDataRepository extends JpaRepository<ElectricityData, Long>{

    @NativeQuery(value="SELECT date, SUM(productionamount) AS productionamount, SUM(consumptionamount) AS consumptionamount, AVG(hourlyprice) AS hourlyprice FROM electricitydata GROUP BY date")
    Page<DailyTotals> findDailyTotals(Pageable pageable);

    @Query(value="SELECT date, SUM(productionamount) AS productionamount, SUM(consumptionamount) AS consumptionamount, AVG(hourlyprice) AS hourlyprice FROM electricitydata" + 
        " WHERE CAST(date AS text) LIKE CONCAT('%', ?1, '%') OR" +
        " CAST(productionamount AS text) LIKE CONCAT('%', ?1, '%') OR" +
        " CAST(consumptionamount AS text) LIKE CONCAT('%', ?1, '%') OR" +
        " CAST(hourlyprice AS text) LIKE CONCAT('%', ?1, '%') GROUP BY date", nativeQuery=true)
    Page<DailyTotals> findDailyTotalsSearchQuery(String search, Pageable pageable);

    @NativeQuery(value="SELECT starttime, hourlyprice FROM electricitydata WHERE hourlyprice < 0 ORDER BY starttime")
    List<NegativeHours> findNegtaivePricePeriod();
    
}
