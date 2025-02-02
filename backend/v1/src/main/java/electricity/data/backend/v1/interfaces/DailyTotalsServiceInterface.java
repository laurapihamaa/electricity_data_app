package electricity.data.backend.v1.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import electricity.data.backend.v1.entities.DailyTotals;

@Service
public interface DailyTotalsServiceInterface {

    Page<DailyTotals> getDailyTotals(int page, int size, String sortBy, String sortOrder, String search);
    
}
