package electricity.data.backend.v1.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class NegativeHours {

    private Timestamp dateTime;

    private BigDecimal hourlyPrice;

    public NegativeHours (Timestamp localDateTime, BigDecimal hourlyPrice){
        this.dateTime=localDateTime;
        this.hourlyPrice=hourlyPrice;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public BigDecimal getHourlyPrice() {
        return hourlyPrice;
    }

    public void setHourlyPrice(BigDecimal hourlyPrice) {
        this.hourlyPrice = hourlyPrice;
    }
    
}
