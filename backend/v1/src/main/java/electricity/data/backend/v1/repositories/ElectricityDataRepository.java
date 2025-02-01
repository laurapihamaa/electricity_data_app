package electricity.data.backend.v1.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import electricity.data.backend.v1.entities.DailyTotals;
import electricity.data.backend.v1.entities.ElectricityData;
import electricity.data.backend.v1.entities.NegativeHours;

@Repository
public interface ElectricityDataRepository extends JpaRepository<ElectricityData, Long>{

    @NativeQuery(value="SELECT date, SUM(productionamount), SUM(consumptionamount), AVG(hourlyprice) FROM electricitydata GROUP BY date")
    List<DailyTotals> findDailyTotals();

    @NativeQuery(value="SELECT starttime, hourlyprice FROM electricitydata WHERE hourlyprice < 0 ORDER BY starttime")
    List<NegativeHours> findNegtaivePricePeriod();
    
}
