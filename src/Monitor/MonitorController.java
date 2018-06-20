

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


/**
 * Monitor program
 * Invokes remote operations of the server
 * Also the implementation for the Remote Interface Listener
 * 
 * @author DharshikaSingarathnam
 */
public class MonitorController extends UnicastRemoteObject implements Listener{
    
    public ServerRemote remoteServer;
    // Handle the Sensor readings and display on the relevant frame
    MonitorManager handler;
    private static MonitorGUI monitoringStation;
    private MonitorController Monitor;
    
    public MonitorController(ServerRemote sensor) throws RemoteException{
        this.remoteServer = sensor;
        this.handler = new MonitorManager();
    }
    
    /**
     * Indicates that the monitor initiated successfully
     * @throws RemoteException 
     */
    public void report() throws RemoteException{
        System.out.println("Monitor initiated ");
    }

    @Override
    public void Changed(List<Sensor> sensors, int monitorCount, int sensorCount) throws RemoteException {
        
        monitoringStation.setMonitorCount(monitorCount);
        monitoringStation.setSensorCount(sensorCount);
        
        if( null != sensors ){
            this.handler.addMonitor(monitoringStation, sensors);
            
        }
        else{
            JOptionPane.showMessageDialog(null, "Server failed to send updates. Please restart the monitor");
        }
    }
    
    @Override
    public void alertMonitor(Sensor sensor) throws RemoteException {
        // Once the monitor recieves an alert, it updates all readings to the latest
        updateAllReadings();
        JOptionPane.showMessageDialog(
                                    monitoringStation,
                                    "Readings in "+sensor.getid()+" reached critical levels!",
                                    "CRITICAL LEVEL",
                                    JOptionPane.WARNING_MESSAGE);
    }
    
    @Override
    public void notifySensorFailure(Sensor sensor) throws RemoteException {
        JOptionPane.showMessageDialog(
                                    monitoringStation,
                                    "Sensor in "+sensor.getid()+" is unavailable!",
                                    "CRITICAL LEVEL",
                                    JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Updates all readings to the latest
     */
    public void updateAllReadings(){
        try {
            remoteServer.getLatestReadings(Monitor);
        } catch (RemoteException ex) {
            Logger.getLogger(MonitorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Gets all the latest readings of a specified id
     * @param location 
     */
    public void getLatestReadingsByid(String id){
        try {
            Sensor sensor = remoteServer.getLatestReadingsByid(Monitor,id);
            handler.updateMonitor(monitoringStation, sensor);
        } catch (RemoteException | SensorNotFoundException ex) {
            Logger.getLogger(MonitorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * Starts the monitor window
     * @param monitor 
     */
    void initiateMonitorWindow(MonitorController monitor) throws RemoteException{
        // 
    	Monitor = monitor;
        // Initiate a new UI instance of the Monitor
        monitoringStation = new MonitorGUI(monitor);
        monitoringStation.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        // Removing the monitor from the server on closing the application
        monitoringStation.addWindowListener(new WindowAdapter()
            {
                @Override
                public void windowClosing(WindowEvent e)
                {
                        int result = JOptionPane.showConfirmDialog(
                                monitoringStation,
                                "Are you sure you want to exit the application?",
                                "Exit Application",
                                JOptionPane.YES_NO_OPTION);

                        if (result == JOptionPane.YES_OPTION){
                            try {
                                remoteServer.removeMonitor(monitor);
                                monitoringStation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            } catch (RemoteException ex) {
                                Logger.getLogger(MonitorController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                }
            });
        
        monitoringStation.setVisible(true);
        monitoringStation.setMonitorCount(remoteServer.getMonitorCount());
        monitoringStation.setSensorCount(remoteServer.getSensorCount());

        // Add sensors to UI
        monitor.handler.addMonitor(monitoringStation, monitor.remoteServer.getSensor());
        
    }
}
