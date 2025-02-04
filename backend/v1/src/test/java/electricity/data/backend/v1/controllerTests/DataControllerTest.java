package electricity.data.backend.v1.controllerTests;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import electricity.data.backend.v1.controllers.DataController;
import electricity.data.backend.v1.entities.DailyTotals;
import electricity.data.backend.v1.interfaces.DailyTotalsServiceInterface;

@WebMvcTest(DataController.class)
public class DataControllerTest {

    @MockBean
    private DailyTotalsServiceInterface dailyTotalsServiceInterface;

    @Autowired
    private MockMvc mockMvc;

    // Create the mock data
    DailyTotals dailyTotal1 = new DailyTotals(Date.valueOf("2024-09-16"), BigDecimal.valueOf(639946.64), BigDecimal.valueOf(112600901.841), BigDecimal.valueOf(22.0940000000000000));
    DailyTotals dailyTotal2 = new DailyTotals(Date.valueOf("2024-10-16"), BigDecimal.valueOf(139946.64), BigDecimal.valueOf(212600901.841), BigDecimal.valueOf(18.0940000000000000));

    /*
     * Test getting the daily totals with the default values and no search string
     * The test should return status ok
     */

    @Test
    public void testGetDailyTotals_DailyTotalsFound() throws Exception {

    List<DailyTotals> dailyTotalsList = Arrays.asList(dailyTotal1, dailyTotal2);
    Pageable pageable = PageRequest.of(0, 2);
    Page<DailyTotals> page = new PageImpl<>(dailyTotalsList, pageable, 2);

    when(dailyTotalsServiceInterface.getDailyTotals(0, 2, "date", "asc", "")).thenReturn(page);

    mockMvc.perform(get("/dailyTotals?page=0&size=2&sortBy=date&sortOrder=asc"))
        .andDo(print())
        .andExpect(status().isOk());
    }

    /*
     * Test getting daily totals with default values and no search string with expection thrown
     * The test should return client error and an exception message
     */

    @Test
    public void testGetDailyTotals_WithException() throws Exception {

    when(dailyTotalsServiceInterface.getDailyTotals(0, 2, "date", "asc", "")).thenThrow(new RuntimeException());

    mockMvc.perform(get("/dailyTotals?page=0&size=2&sortBy=date&sortOrder=asc&search="))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(content().string("An exception occurred. Please try again later."));
    }
    
}
