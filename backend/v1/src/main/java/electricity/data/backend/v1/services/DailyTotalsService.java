package electricity.data.backend.v1.services;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import electricity.data.backend.v1.entities.DailyTotals;
import electricity.data.backend.v1.entities.NegativeHours;
import electricity.data.backend.v1.interfaces.DailyTotalsServiceInterface;
import electricity.data.backend.v1.repositories.ElectricityDataRepository;

@Service
public class DailyTotalsService implements DailyTotalsServiceInterface{

    @Autowired
    private ElectricityDataRepository electricityDataRepository;

    /*
     * 
     * Get all of the daily totals
     * 
     * @param page the page which to receive
     * @param size the size of the page
     * @param sortBy the element which to use for ordering
     * @param sortOrder sort the elements in asc/desc
     * @param search a search string to query the elements by
     * 
     * @return Page<DailyTotals> return the page with the daily totals
     * 
     */

    @Override
    public Page<DailyTotals> getDailyTotals(int page, int size, String sortBy, String sortOrder, String search){

        try{

        //implemented sorting for the negative hours. Retrieve the whole dataset and sort it and only after that implement pagination.
        //other fields are sorted by quering the datatable

        if (sortBy.equalsIgnoreCase("negativehours")){

            Page<DailyTotals> dailyTotals = electricityDataRepository.findDailyTotals(Pageable.unpaged());
            Map<Date, Long> negativePricePeriods = findConcecutiveHours(electricityDataRepository.findNegtaivePricePeriod());

            combineTotalsAndNegatives(dailyTotals.getContent(), negativePricePeriods);

            List<DailyTotals> sortedNegativeHours = retrieveSortedNegativeHours(page, size, sortOrder, dailyTotals.getContent());
            
            Pageable pageable = PageRequest.of(page-1, size);

            return new PageImpl<>(sortedNegativeHours, pageable, dailyTotals.getTotalElements());  
        }
            

        // Define the sorting direction based on the input
        Sort.Direction direction = Sort.Direction.fromString(sortOrder);
        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(page-1, size, sort);
        Page<DailyTotals> dailyTotals;
            
        //check if search string is provided and return dailytotals based on that. Sanitize the search string as the table holds only numerics, - and . characters.
        if (search==null || search.isEmpty()){
            dailyTotals = electricityDataRepository.findDailyTotals(pageable);
        } else{
            String sanitizedInput = search.replaceAll("[^0-9.-]", "");
            dailyTotals = electricityDataRepository.findDailyTotalsSearchQuery(sanitizedInput, pageable);
        }

        Map<Date, Long> negativePricePeriods = findConcecutiveHours(electricityDataRepository.findNegtaivePricePeriod());

        //add negative time periods into daily totals
        combineTotalsAndNegatives(dailyTotals.getContent(), negativePricePeriods);
            
        return new PageImpl<>(dailyTotals.getContent(), pageable, dailyTotals.getTotalElements());

        }catch(Exception e){
            throw new RuntimeException("Error retrieving the daily totals");
        }
    }

    /*
     * 
     * Find the longest consecutive time in hours when the price has been negative
     * The method loops through the given negative hours and check whether the current time is
     * one hour before the latest time stamp on the given day and updates the duration according to that
     * 
     * @param negativePricePeriods all of the negative prices
     * @return Map<Date, Long> a data map with the dates and longest negative price period per that day
     * 
     */

    private Map<Date, Long> findConcecutiveHours (List<NegativeHours> negativePricePeriods){

        try{
        
        Map<Date, Long> longestNegativePeriods = new HashMap<>();
        long duration=0;
        long longestDuration=0;

        LocalDateTime lastTimeStamp = negativePricePeriods.get(0).getDateTime().toLocalDateTime();
        LocalDate currentDate=negativePricePeriods.get(0).getDateTime().toLocalDateTime().toLocalDate();

        for(NegativeHours negativeHour:negativePricePeriods){
            LocalDateTime recordTime = negativeHour.getDateTime().toLocalDateTime();

            //if time is 1 hr less and date is same as current inspected date add 1 hr to duration
            if(recordTime.minusHours(1).equals(lastTimeStamp) && recordTime.toLocalDate().equals(currentDate)){
                duration++;
                lastTimeStamp=recordTime;
            //if the date is the same save the longest duration to the longest duration variable and start a new counter for duration
            }else if(recordTime.toLocalDate().equals(currentDate)){
                longestDuration=Math.max(duration, longestDuration);
                duration=0L;
                lastTimeStamp=recordTime;
            //otherwise create a new instance to the map with the date and longest duration
            }else{
                Date date = Date.valueOf(currentDate);
                longestNegativePeriods.put(date, Math.max(duration, longestDuration));

                longestDuration=0L;
                duration=0L;

                lastTimeStamp=recordTime;
                currentDate=recordTime.toLocalDate();
            }
        }

        return longestNegativePeriods;
    
        }catch(Exception e){
            throw new RuntimeException("Error finding the longest negative price period");
        }
    }

    /*
     * 
     * Combine the daily totals and negative price periods into one list
     * The method loops through the dailytotals and checks if the negative price period contains a value for that given date.
     * If yes, insert the value, otherwise insert 0
     * 
     * @param dailyTotals the list of the daily totals
     * @param negativePricePeriods the concecutive negative prices per day
     * 
     * 
     */

    private void combineTotalsAndNegatives (List<DailyTotals> dailyTotals, Map<Date, Long> negativePricePeriods){

    try{
            
        for (DailyTotals dailyTotal : dailyTotals){
            if(negativePricePeriods.containsKey(dailyTotal.getDate())){
                Long negativeHour=negativePricePeriods.get(dailyTotal.getDate());
                dailyTotal.setNegativeHours(negativeHour);
            }else{
                dailyTotal.setNegativeHours(0L);
            }
        }
    }catch(Exception e){
        throw new RuntimeException("Error combining the totals and negative prices");
    }

    }

    /*
     * 
     * sort the dataset based on the longest concecutive negative hours and the given sort order
     * add pagination after
     * 
     * @param page the page to start from
     * @param size the number of elements on the page
     * @param sortOrder sort asc/desc order
     * @param dailyTotals a list of all of the daily totals
     * 
     * @return List<DailyTotals> return the sorted daily totals
     */

    private List<DailyTotals> retrieveSortedNegativeHours(int page, int size, String sortOrder, List<DailyTotals> dailyTotals) {

        // Sort the entire dataset based on negativeHours (ascending or descending)
        List<DailyTotals> sortedByNegativeHours = dailyTotals.stream()
            .sorted((d1, d2) -> {
                if (sortOrder.equalsIgnoreCase("asc")) {
                    return d1.getNegativeHours().compareTo(d2.getNegativeHours());
                } else {
                    return d2.getNegativeHours().compareTo(d1.getNegativeHours());
                }
            })
            .collect(Collectors.toList());
    
        // Apply pagination: extract the correct subset of the sorted list
        int start = Math.min(page * size, sortedByNegativeHours.size());
        int end = Math.min((page + 1) * size, sortedByNegativeHours.size());
        return sortedByNegativeHours.subList(start, end);
    }
}

