

import java.util.List;


/**
 * Handles the monitors connected to the server
 * 
 * @author DharshikaSingarathnam
 */
public class MonitorHandler extends Thread {
        
        private final Server server;
        private List<Sensor> sensors;
        
        public MonitorHandler(Server server, List<Sensor> sensors) {
            this.server = server;
            this.sensors = sensors;
        }
        
        @Override
        public void run(){
            
            while(true){
                
                try {
                    Thread.sleep(3600000);
                    synchronized( sensors ){
                        // Notify listeners every hour
                        server.notifyListeners(sensors);
                    }
                    System.out.println("Thread Success");
                } catch (InterruptedException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }
