

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Handles the sensor UIs and the readings shown on them
 * 
 * @author DharshikaSingarathnam
 */
public class MonitorManager {
    
    private Map<String,SensorMonitorGUI> sensorMonitors = new HashMap<>();
    private List<String> sensorID = new ArrayList();
    
    /**
     * Adding a new monitor/s or updating the existing readings
     * @param monitorGUI
     * @param sensors 
     */
    public void addMonitor(MonitorGUI monitorGUI,List<Sensor> sensors){
        
        sensors.forEach(sensor ->{
            SensorMonitorGUI monitor;
            String id= sensor.getid();
            
            // If the monitor for the relevant Sensor ID has not been initialized yet
            if(!sensorMonitors.containsKey(id)){
                monitor = new SensorMonitorGUI(id);
                // Storing each monitor with reference to its location
                sensorMonitors.put(id, monitor);
                // Adding new monitor
                monitorGUI.addMonitor(monitor);
                // Add location to Combo box
                monitorGUI.fillLocations(id);
                // Add location to a list
                sensorID.add(id);
            }
            else{
                monitor = sensorMonitors.get(sensor.getid());
            }
            setReading(sensor, monitor);
        });
        
    }
    
    /**
     * Updating the monitor reading when readings are required
     * for a particular id
     * @param monitorGUI
     * @param sensor 
     */
    public void updateMonitor(MonitorGUI monitorGUI, Sensor sensor){
        if(sensorMonitors.containsKey(sensor.getid())){
            SensorMonitorGUI monitor = sensorMonitors.get(sensor.getid());
            setReading(sensor, monitor);
        }
    }
    
    /**
     * Set readings in the UI
     * @param sensor
     * @param monitor 
     */
    public void setReading(Sensor sensor,SensorMonitorGUI monitor){

        monitor.setTemperature(sensor.getLatestReading().getTemperature());
        monitor.setCo2level(sensor.getLatestReading().getCo2level());
        monitor.setBatterylevel(sensor.getLatestReading().getBatterylevel());
        monitor.setSmokelevel(sensor.getLatestReading().getSmokelevel());
                
    } 
    
    /**
     * Initiate a new monitor
     */
    public void startMonitor(){
        try {

            // Retrieving remote object
            ServerRemote temperatureSensorServerRemote = (ServerRemote) Naming.lookup("rmi://localhost/firealarm");

            // Initializing a monitor instance
            MonitorController monitor = new MonitorController(temperatureSensorServerRemote);
            monitor.report();
            // Adding the monitor instance to server by invoking the method through the remote interface provided
            temperatureSensorServerRemote.addMonitor(monitor);

            monitor.initiateMonitorWindow(monitor);
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            System.err.println(ex.getMessage());
        }
    
    }
    
}
