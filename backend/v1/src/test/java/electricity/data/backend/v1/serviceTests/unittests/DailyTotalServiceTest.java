package electricity.data.backend.v1.serviceTests.unittests;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import electricity.data.backend.v1.entities.DailyTotals;
import electricity.data.backend.v1.entities.NegativeHours;
import electricity.data.backend.v1.services.DailyTotalsService;


public class DailyTotalServiceTest {

    public DailyTotalsService dailyTotalsService;


    /*
     * create a subclass to test the protected methods
     */

    public class DailyTotalsServiceSubClass extends DailyTotalsService {
        public Map<Date, Long> testFindConsecutiveHours(List<NegativeHours> negativePricePeriods) {
            return findConcecutiveHours(negativePricePeriods); 
        }
        @Override
        public void combineTotalsAndNegatives(List<DailyTotals> dailyTotals, Map<Date, Long> negativePricePeriods) {
            super.combineTotalsAndNegatives(dailyTotals, negativePricePeriods);
        }
        private List<DailyTotals> testRetrieveSortedNegativeHours(int page, int size, String sortOrder, List<DailyTotals> dailyTotals) {
            return retrieveSortedNegativeHours(page, size, sortOrder, dailyTotals); 
        }
    }

    // Create the mock data
    DailyTotals dailyTotal1 = new DailyTotals(Date.valueOf("2024-10-16"), BigDecimal.valueOf(639946.64), BigDecimal.valueOf(112600901.841), BigDecimal.valueOf(22.0940000000000000));
    DailyTotals dailyTotal2 = new DailyTotals(Date.valueOf("2024-10-17"), BigDecimal.valueOf(139946.64), BigDecimal.valueOf(212600901.841), BigDecimal.valueOf(18.0940000000000000));
    DailyTotals dailyTotal3 = new DailyTotals(Date.valueOf("2024-10-18"), BigDecimal.valueOf(139946.64), BigDecimal.valueOf(212600901.841), BigDecimal.valueOf(18.0940000000000000));
    DailyTotals dailyTotal4 = new DailyTotals(Date.valueOf("2024-11-18"), BigDecimal.valueOf(139946.64), BigDecimal.valueOf(212600901.841), BigDecimal.valueOf(18.0940000000000000));
    DailyTotals dailyTotal5 = new DailyTotals(Date.valueOf("2024-11-19"), BigDecimal.valueOf(139946.64), BigDecimal.valueOf(212600901.841), BigDecimal.valueOf(18.0940000000000000));
    DailyTotals dailyTotal6 = new DailyTotals(Date.valueOf("2024-11-20"), BigDecimal.valueOf(139946.64), BigDecimal.valueOf(212600901.841), BigDecimal.valueOf(18.0940000000000000));

    List<DailyTotals> dailyTotalsList = Arrays.asList(dailyTotal1, dailyTotal2, dailyTotal3, dailyTotal4, dailyTotal5, dailyTotal6);

    NegativeHours negativeHour1 = new NegativeHours(Timestamp.valueOf("2024-10-16 10:00:00"), BigDecimal.valueOf(-4.567373));
    NegativeHours negativeHour2 = new NegativeHours(Timestamp.valueOf("2024-10-16 11:00:00"), BigDecimal.valueOf(-1.567373));
    NegativeHours negativeHour3 = new NegativeHours(Timestamp.valueOf("2024-10-16 12:00:00"), BigDecimal.valueOf(-4.567373));
    NegativeHours negativeHour4 = new NegativeHours(Timestamp.valueOf("2024-10-17 11:00:00"), BigDecimal.valueOf(-4.567373));
    NegativeHours negativeHour5 = new NegativeHours(Timestamp.valueOf("2024-11-18 12:00:00"), BigDecimal.valueOf(-1.567373));
    NegativeHours negativeHour6 = new NegativeHours(Timestamp.valueOf("2024-11-18 13:00:00"), BigDecimal.valueOf(-1.567373));

    List<NegativeHours> negativeHours = Arrays.asList(negativeHour1, negativeHour2, negativeHour3, negativeHour4, negativeHour5, negativeHour6);

    /*
     * Test finding the concecutive negative hours provided in the mock data.
     * The test should return three items with correct hours and dates.
     */

    @Test
    public void testFindConcecutiveHours () {

        DailyTotalsServiceSubClass dailyTotalsServiceSubClass= new DailyTotalsServiceSubClass();

        Map<Date, Long> result = dailyTotalsServiceSubClass.testFindConsecutiveHours(negativeHours);

        assertNotNull(result);
        assertEquals(2L, result.get(Date.valueOf("2024-10-16")));
        assertEquals(0L, result.get(Date.valueOf("2024-10-17")));
        assertEquals(1L, result.get(Date.valueOf("2024-11-18")));

    }

    /*
     * Test combining the daily totals and negative hours.
     * The test should have the daily totals objects with added negative hours
     */

    @Test
    public void combineTotalsAndNegatives () {

        DailyTotalsServiceSubClass dailyTotalsServiceSubClass= new DailyTotalsServiceSubClass();
        Map<Date, Long> result = dailyTotalsServiceSubClass.testFindConsecutiveHours(negativeHours);

        dailyTotalsServiceSubClass.combineTotalsAndNegatives(dailyTotalsList, result);

        assertEquals(2L, dailyTotalsList.get(0).getNegativeHours());
        assertEquals(0L, dailyTotalsList.get(2).getNegativeHours());
        assertEquals(0L, dailyTotalsList.get(2).getNegativeHours());


    }

    /*
     * Test sorting the list by negative hours in ascending order
     * The test should return a list where the first entity has more concecutive negative hours than the second one
     */

    @Test
    public void retrieveSortedNegativeHoursAscending () {

        DailyTotalsServiceSubClass dailyTotalsServiceSubClass= new DailyTotalsServiceSubClass();
        Map<Date, Long> result = dailyTotalsServiceSubClass.testFindConsecutiveHours(negativeHours);

        dailyTotalsServiceSubClass.combineTotalsAndNegatives(dailyTotalsList, result);

        List<DailyTotals> sortedList = dailyTotalsServiceSubClass.testRetrieveSortedNegativeHours(1, 3, "asc", dailyTotalsList);

        assertTrue(sortedList.get(0).getNegativeHours()<sortedList.get(1).getNegativeHours());


    }

    /*
     * Test sorting the list by negative hours in descending order
     * The test should return a list where the first entity has less concecutive negative hours than the second one
     */

     @Test
     public void retrieveSortedNegativeHoursDescending () {
 
         DailyTotalsServiceSubClass dailyTotalsServiceSubClass= new DailyTotalsServiceSubClass();
         Map<Date, Long> result = dailyTotalsServiceSubClass.testFindConsecutiveHours(negativeHours);
 
         dailyTotalsServiceSubClass.combineTotalsAndNegatives(dailyTotalsList, result);
 
         List<DailyTotals> sortedList = dailyTotalsServiceSubClass.testRetrieveSortedNegativeHours(1, 3, "desc", dailyTotalsList);
 
         assertTrue(sortedList.get(0).getNegativeHours()>=sortedList.get(1).getNegativeHours());
 
 
     }

    
}
