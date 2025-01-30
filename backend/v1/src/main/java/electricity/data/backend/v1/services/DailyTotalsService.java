package electricity.data.backend.v1.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import electricity.data.backend.v1.entities.DailyTotals;
import electricity.data.backend.v1.interfaces.DailyTotalsServiceInterface;
import electricity.data.backend.v1.repositories.ElectricityDataRepository;

@Service
public class DailyTotalsService implements DailyTotalsServiceInterface{

    @Autowired
    private ElectricityDataRepository electricityDataRepository;

    @Override
    public List<DailyTotals> getDailyTotals(){
            return null;
    }
    
}
