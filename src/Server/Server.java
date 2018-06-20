

import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation class of the Remote Interface 
 * 
 * @author DharshikaSingarathnam
 */
public class Server extends UnicastRemoteObject implements ServerRemote{

    //Sensors that are connected to the server
    private List<Sensor> sensors= new ArrayList<>();
    
    //monitors that are connected to the server
    private ArrayList<Listener> monitors = new ArrayList<>();

    public Server() throws RemoteException{
    }  

    @Override
    public int getSensorCount() throws RemoteException{
        return sensors.size();
    }
    
    @Override
    public int getMonitorCount() throws RemoteException{
        return monitors.size();
    }

    @Override
    public List<Sensor> getSensor() throws RemoteException{
        return sensors;
    }
    
    @Override
    public void addMonitor(Listener Listener) throws RemoteException {
        // Other threads can only read sensor list while a sensor is being added
        System.out.println("adding listener -" + Listener);
        synchronized(monitors){
            monitors.add(Listener);
        }
        // Start Thread for this monitor
        new MonitorHandler(this,sensors).start();
    }
    
    @Override
    public void removeMonitor(Listener Listener) throws RemoteException {
        // Other threads can only read monitor list while a monitor is being removed
        synchronized(monitors){
            monitors.remove(Listener); 
        }
    }
    
    @Override
    public void getLatestReadings(Listener Listener) throws RemoteException {
        // When the latest readings are requested, update only the requested monitor
        Listener.Changed(sensors, getMonitorCount(), getSensorCount());
    }
    
    @Override
    public Sensor getLatestReadingsByid(Listener Listener, String id) throws SensorNotFoundException{
        for(Sensor sensor:sensors){
            if (sensor.getid().equals(id)){
                return sensor;
            }
        }
        throw new SensorNotFoundException();
    }
    
    /**
     * Adds a new sensor
     * @param sensor 
     */
    public void addSensor(Sensor sensor) {
        // Other threads can only read sensor list while a sensor is being added
        synchronized(sensors){
            this.sensors.add(sensor);
        }
    }
    
    /**
     * Removes a particular sensor
     * @param sensor 
     */
    void removeSensor(Sensor sensor) {
        // Other threads can only read sensor list while a sensor is being removed
        synchronized(sensors){
            this.sensors.remove(sensor);
        }
    }
    
    /**
     * Alerts all monitors in case the sensor readings reach critical levels
     * @param sensor 
     */
    public void alertMonitors(Sensor sensor){
        
        if(!monitors.isEmpty()){
            // Notify all the listeners in the list
            monitors.forEach((listener) -> {
                try {
                    System.out.println("Alert - Server");
                    // Calling the remote method of the Listener Interface
                    listener.alertMonitor(sensor);
                } catch (RemoteException ex) {
                    try {
                        // Remove  monitor if it fails
                        removeMonitor(listener);
                    } catch (RemoteException ex1) {
                        System.err.println(ex1.getMessage());
                    }
                }
            });
        }
    }
    
    /**
     * Alerts all monitors if a sensor fails to send updates
     * @param sensor 
     */
    public void alertSensorFailure(Sensor sensor){
        
        if(!monitors.isEmpty()){
            // Notify all the listeners in the list
            monitors.forEach((listener) -> {
                try {
                    System.out.println("Alert -Server");
                    // Calling the remote method of the Listener Interface
                    listener.notifySensorFailure(sensor);
                } catch (RemoteException ex) {
                    try {
                        // Remove monitor if it fails
                        removeMonitor(listener);
                    } catch (RemoteException ex1) {
                        System.err.println(ex1.getMessage());
                    }
                }
            });
        }
    }
    
    /**
     * Notifies monitors on hourly updates
     * @param sensors
     */
    public void notifyListeners(List<Sensor> sensors) {
        if(!monitors.isEmpty()){
            // Notify all the listeners in the list
            monitors.forEach((listener) -> {
                try {
                    listener.Changed(sensors, getMonitorCount(), getSensorCount());
                } catch (ConnectException e){
                    // remove client in case of a connection issue
                    monitors.remove(listener);
                    System.err.println("Error - Removing client - " + e);
                } catch (RemoteException e) {
                    System.err.println("Error - " + e);
                }
            });
        }
    }

    
}
