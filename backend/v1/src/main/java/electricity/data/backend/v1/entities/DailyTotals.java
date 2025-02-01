package electricity.data.backend.v1.entities;

import java.math.BigDecimal;
import java.sql.Date;

public class DailyTotals {

    private Date date;

    private BigDecimal dailyConsumption;

    private BigDecimal dailyProduction;

    private BigDecimal dailyPrice;

    private Long negativeHours;

    public DailyTotals(Date date, BigDecimal dailyConsumption, BigDecimal dailyProduction, BigDecimal dailyPrice){
        this.date=date;
        this.dailyConsumption=dailyConsumption;
        this.dailyProduction=dailyProduction;
        this.dailyPrice=dailyPrice;
    }

    public Long getNegativeHours() {
        return negativeHours;
    }

    public void setNegativeHours(Long negativeHours) {
        this.negativeHours = negativeHours;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getDailyConsumption() {
        return dailyConsumption;
    }

    public void setDailyConsumption(BigDecimal dailyConsumption) {
        this.dailyConsumption = dailyConsumption;
    }

    public BigDecimal getDailyProduction() {
        return dailyProduction;
    }

    public void setDailyProduction(BigDecimal dailyProduction) {
        this.dailyProduction = dailyProduction;
    }

    public BigDecimal getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(BigDecimal dailyPrice) {
        this.dailyPrice = dailyPrice;
    }
    
}
