package electricity.data.backend.v1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import electricity.data.backend.v1.interfaces.DailyTotalsServiceInterface;

@RestController
public class DataController {

    @Autowired
    private DailyTotalsServiceInterface dailyTotalsServiceInterface;

    @GetMapping("/dailyTotals")
    public ResponseEntity<?> getDailyData(){
        return ResponseEntity.ok(dailyTotalsServiceInterface.getDailyTotals());
    }
    
}
