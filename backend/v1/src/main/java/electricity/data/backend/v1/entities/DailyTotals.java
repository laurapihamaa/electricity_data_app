package electricity.data.backend.v1.entities;

public class DailyTotals {

    private String date;

    private double dailyConsumption;

    private double dailyProduction;

    private double dailyPrice;

    private double negativeHours;

    public DailyTotals(String date, double dailyConsumption, double dailyProduction, double dailyPrice, double negativeHours){
        this.date=date;
        this.dailyConsumption=dailyConsumption;
        this.dailyProduction=dailyProduction;
        this.dailyPrice=dailyPrice;
        this.negativeHours=negativeHours;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getDailyConsumption() {
        return dailyConsumption;
    }

    public void setDailyConsumption(double dailyConsumption) {
        this.dailyConsumption = dailyConsumption;
    }

    public double getDailyProduction() {
        return dailyProduction;
    }

    public void setDailyProduction(double dailyProduction) {
        this.dailyProduction = dailyProduction;
    }

    public double getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(double dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public double getNegativeHours() {
        return negativeHours;
    }

    public void setNegativeHours(double negativeHours) {
        this.negativeHours = negativeHours;
    }
    
}
