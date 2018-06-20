

import java.util.Random;

/**
 *
 * @author DharshikaSingarathnam
 */
public class BatteryLevel implements SensorReading{
    private int batterylevel;
    
    public BatteryLevel() {
    	batterylevel = 75;
    }
    
    public double getReading(){
        Random r = new Random();
        // Get random number, to decide sensor reading goes up or down
        int num = r.nextInt();
        if (num < 0) {
        	batterylevel += 3;
        } else {
        	batterylevel -= 3;
        }
        
        // if it exceeds 100 re initialize
        if( batterylevel >= 100 ){
        	batterylevel = 82;
        }
        return batterylevel;
    }
}
