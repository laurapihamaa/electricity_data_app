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

    /*
     * 
     * Retrieve the daily totals of electricity production and consumption and the average hourly price
     * 
     * @param pageable pagination information to retrieve the data
     * @return Page<DailyTotals> a page of daily totals objects with the data retrieved
     * 
     */

    @NativeQuery(value="SELECT date, SUM(productionamount) AS productionamount, SUM(consumptionamount) AS consumptionamount, AVG(hourlyprice) AS hourlyprice FROM electricitydata GROUP BY date")
    Page<DailyTotals> findDailyTotals(Pageable pageable);

    /*
     * 
     * Retrieve the daily totals of electricity production and consumption and the average hourly price
     * containing also a search query
     * 
     * @param the string to search by, can match any column data
     * @param pageable the pagination information to retrieve the data
     * @return Page<DailyTotals> a page of daily totals objects with the data retrieved
     * 
     */

    @Query(value="SELECT date, SUM(productionamount) AS productionamount, SUM(consumptionamount) AS consumptionamount, AVG(hourlyprice) AS hourlyprice FROM electricitydata" + 
        " WHERE CAST(date AS text) LIKE CONCAT('%', ?1, '%') OR" +
        " CAST(productionamount AS text) LIKE CONCAT('%', ?1, '%') OR" +
        " CAST(consumptionamount AS text) LIKE CONCAT('%', ?1, '%') OR" +
        " CAST(hourlyprice AS text) LIKE CONCAT('%', ?1, '%') GROUP BY date", nativeQuery=true)
    Page<DailyTotals> findDailyTotalsSearchQuery(String search, Pageable pageable);

    /*
     * 
     * Retrieve periods with negative hours
     * 
     * @return List<NegativeHours> a list with negative hours object from the data retrieved
     * 
     */

    @NativeQuery(value="SELECT starttime, hourlyprice FROM electricitydata WHERE hourlyprice < 0 ORDER BY date")
    List<NegativeHours> findNegtaivePricePeriod();

    
}
