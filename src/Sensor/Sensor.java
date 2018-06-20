

import java.io.Serializable;


/**
 * This class represents the sensor
 * 
 * @author DharshikaSingarathnam
 */
public class Sensor implements Serializable{

    private String id;
    private Reading latestReading;

    public Sensor() {
    }



    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public Reading getLatestReading() {
        return latestReading;
    }

    public void setLatestReading(Reading latestReading) {
        this.latestReading = latestReading;
    }

    @Override
    public String toString() {
        return "Sensor{" + "ID=" + id + ", latestReading=" + latestReading + '}';
    }

    
    
}
