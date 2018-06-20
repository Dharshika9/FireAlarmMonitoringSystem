

import java.util.Random;

/**
 *
 * @author DharshikaSingarathnam
 */
public class SmokeLevel implements SensorReading{

    private double smokelevel;
    
    public SmokeLevel() {
    	smokelevel = 7.0;
    }
    
    @Override
    public double getReading(){
        Random r = new Random();
        // Get random number, to decide sensor reading goes up or down
        int num = r.nextInt();
        if (num < 0) {
        	smokelevel += 0.2;
        } else {
        	smokelevel -= 0.2;
        }
        return Math.round(smokelevel * 100.0) / 100.0;
    }
    
}
