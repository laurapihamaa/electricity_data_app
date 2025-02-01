package electricity.data.backend.v1.services;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import electricity.data.backend.v1.entities.DailyTotals;
import electricity.data.backend.v1.entities.NegativeHours;
import electricity.data.backend.v1.interfaces.DailyTotalsServiceInterface;
import electricity.data.backend.v1.repositories.ElectricityDataRepository;

@Service
public class DailyTotalsService implements DailyTotalsServiceInterface{

    @Autowired
    private ElectricityDataRepository electricityDataRepository;

    @Override
    public List<DailyTotals> getDailyTotals(){
            
            //retrieve daily totals
            List<DailyTotals> dailyTotals = electricityDataRepository.findDailyTotals();

            //retrieve the negative time period
            Map<Date, Long> negativePricePeriods = findConcecutiveHours(electricityDataRepository.findNegtaivePricePeriod());

            //add into daily totals
            dailyTotals=combineTotalsAndNegatives(dailyTotals, negativePricePeriods);
            
            //return
            return dailyTotals;
    }

    private Map<Date, Long> findConcecutiveHours (List<NegativeHours> negativePricePeriods){

        Map<Date, Long> longestNegativePeriods = new HashMap<>();

        System.out.println(negativePricePeriods.size());

        long duration=0;
        long longestDuration=0;

        LocalDateTime lastTimeStamp = negativePricePeriods.get(0).getDateTime().toLocalDateTime();
        LocalDate currentDate=negativePricePeriods.get(0).getDateTime().toLocalDateTime().toLocalDate();

        for(NegativeHours negativeHour:negativePricePeriods){
            LocalDateTime recordTime = negativeHour.getDateTime().toLocalDateTime();

            if(recordTime.minusHours(1).equals(lastTimeStamp) && recordTime.toLocalDate().equals(currentDate)){
                duration++;
                lastTimeStamp=recordTime;
            }else if(recordTime.toLocalDate().equals(currentDate)){
                longestDuration=Math.max(duration, longestDuration);
                duration=0L;
                lastTimeStamp=recordTime;
            }else{
                Date date = Date.valueOf(currentDate);
                longestNegativePeriods.put(date, Math.max(duration, longestDuration));
                longestDuration=0L;
                duration=0L;
                lastTimeStamp=recordTime;
                currentDate=recordTime.toLocalDate();
            }
        }

        
        System.out.println(longestNegativePeriods.size());
        return longestNegativePeriods;
    }

    private List<DailyTotals> combineTotalsAndNegatives (List<DailyTotals> dailyTotals, Map<Date, Long> negativePricePeriods){
        for (DailyTotals dailyTotal : dailyTotals){
            if(negativePricePeriods.containsKey(dailyTotal.getDate())){
                Long negativeHour=negativePricePeriods.get(dailyTotal.getDate());
                dailyTotal.setNegativeHours(negativeHour);
            }else{
                dailyTotal.setNegativeHours(0L);
            }
        }

        return dailyTotals;

    }
    
}

