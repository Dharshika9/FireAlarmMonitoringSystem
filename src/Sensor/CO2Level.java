

import java.util.Random;

/**
 *
 * @author DharshikaSingarathnam
 */
public class CO2Level implements SensorReading{

    private double co2level;
    
    public CO2Level() {
    	co2level = 300.00;
    }
    
    @Override
    public double getReading(){
        Random r = new Random();
        // Get random number, to decide sensor reading goes up or down
       
        int num = r.nextInt();
        if (num < 0) {
        	co2level += 0.5;
        } else {
        	co2level -= 0.5;
        }
        return Math.round(co2level * 100.0) / 100.0;
    }
    
}
