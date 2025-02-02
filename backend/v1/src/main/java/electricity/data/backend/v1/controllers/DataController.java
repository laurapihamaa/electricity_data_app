package electricity.data.backend.v1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import electricity.data.backend.v1.interfaces.DailyTotalsServiceInterface;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class DataController {

    @Autowired
    private DailyTotalsServiceInterface dailyTotalsServiceInterface;

    @GetMapping("/dailyTotals")
    public ResponseEntity<?> getDailyData(
            @RequestParam(defaultValue="1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue= "asc") String sortOrder,
            @RequestParam(required=false) String search){
        return ResponseEntity.ok(dailyTotalsServiceInterface.getDailyTotals(page, size, sortBy, sortOrder, search));
    }
    
}
