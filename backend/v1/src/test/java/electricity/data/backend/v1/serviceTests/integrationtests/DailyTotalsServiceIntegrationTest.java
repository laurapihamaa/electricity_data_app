package electricity.data.backend.v1.serviceTests.integrationtests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import electricity.data.backend.v1.V1Application;
import electricity.data.backend.v1.entities.DailyTotals;
import electricity.data.backend.v1.repositories.ElectricityDataRepository;
import electricity.data.backend.v1.services.DailyTotalsService;

@SpringBootTest(classes = V1Application.class)
public class DailyTotalsServiceIntegrationTest {

    @Autowired
    private DailyTotalsService dailyTotalsService;

    @Autowired
    private ElectricityDataRepository electricityDataRepository;

    /*
     * Test the sorting of the data with productionampunt column in descending order and also the pagination
     * The test should return the page 0 with the page size 3 and the first value should be smaller than the second one
     */

    @Test
    public void testGetDailyTotals_ProductionAmountSorting(){

        Page<DailyTotals> result = dailyTotalsService.getDailyTotals(1, 3, "productionamount", "desc", "");

        assertEquals(3, result.getSize(), "Page size should be 3");
        assertEquals(0, result.getNumber(), "Page number should be 0");
        assertTrue(result.getContent().get(0).getDailyProduction().floatValue() < result.getContent().get(1).getDailyProduction().floatValue());

        
    }

    /*
     * Test retrieving data with a search string.
     * The test should sanitize the search string and return a result which includes only rows with
     * at least one element containing the sanitized string
     */

    @Test
    public void testGetDailyTotals_WithSearchString(){

        String rawSearch = "abc20"; 
        String sanitizedSearch = "20";
       
        Page<DailyTotals> result = dailyTotalsService.getDailyTotals(1, 3, "date", "asc", rawSearch);
        
        assertTrue(result.getContent().stream().anyMatch(dailyTotal ->
        (dailyTotal.getDate().toString().contains(sanitizedSearch)) ||  
        (dailyTotal.getDailyProduction().toString().contains(sanitizedSearch)) ||  
        (dailyTotal.getDailyConsumption().toString().contains(sanitizedSearch)) ||  
        (dailyTotal.getDailyPrice().toString().contains(sanitizedSearch))
        ));
        
    }

    /*
     * Test the sorting of the data with productionampunt column in descending order and also the pagination
     * The test should return a first value that is smaller than the second one
     */

     @Test
     public void testGetDailyTotals_NegativeHoursSorting(){
 
         Page<DailyTotals> result = dailyTotalsService.getDailyTotals(1, 3, "negativehours", "desc", "");
 
         assertEquals(3, result.getSize(), "Page size should be 3");
         assertEquals(0, result.getNumber(), "Page number should be 0");
         assertTrue(result.getContent().get(0).getNegativeHours().floatValue() <= result.getContent().get(1).getNegativeHours().floatValue());
 
         
     }
    
}
