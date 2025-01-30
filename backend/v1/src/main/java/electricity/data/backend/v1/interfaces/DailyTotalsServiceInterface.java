package electricity.data.backend.v1.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import electricity.data.backend.v1.entities.DailyTotals;

@Service
public interface DailyTotalsServiceInterface {

    List<DailyTotals> getDailyTotals();
    
}
