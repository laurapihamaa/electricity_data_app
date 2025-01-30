package electricity.data.backend.v1.entities;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Table;

@EntityScan
@Table(name="electricitydata")
public class ElectricityData {

    @Id
    private Long id;

    @Column(name="date")
    private String date;

    @Column(name="starttime")
    private String start_time;

    @Column(name="consumptionamount")
    private double consumption;

    @Column(name="productionamount")
    private double production;

    @Column(name="hourlyprice")
    private double averagePrice;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getConsumption() {
        return consumption;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }

    public double getProduction() {
        return production;
    }

    public void setProduction(double production) {
        this.production = production;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }


    
}
