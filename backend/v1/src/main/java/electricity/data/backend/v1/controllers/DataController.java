package electricity.data.backend.v1.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import electricity.data.backend.v1.entities.DailyTotals;
import electricity.data.backend.v1.interfaces.DailyTotalsServiceInterface;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class DataController {

    @Autowired
    private DailyTotalsServiceInterface dailyTotalsServiceInterface;

    /*
     * 
     * Get the daily totals
     * 
     * @param page the page to be returned
     * @param size the amount of elements per page
     * @param sortBy the field to sort the values with
     * @param sortOrder sort the elements in asc/desc order
     * @param search a search string to query the elements by
     * 
     * @return ResponseEntity of ok if the daily totals was received succesfully, otherwise a bad request
     * 
     */

    @GetMapping("/dailyTotals")
    public ResponseEntity<?> getDailyData(
            @RequestParam(defaultValue="1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue= "asc") String sortOrder,
            @RequestParam(required=false) String search){

        try {

            Page<DailyTotals> dailyTotals = dailyTotalsServiceInterface.getDailyTotals(page, size, sortBy, sortOrder, search);
            return ResponseEntity.ok(dailyTotals);  

        } catch (Exception e) {

            return ResponseEntity.badRequest().body("An exception occurred. Please try again later.");
        }

    }
    
}
