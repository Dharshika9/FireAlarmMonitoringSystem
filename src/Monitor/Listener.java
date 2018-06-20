

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


/**
 * Remote Interface which is used to notify monitors on any change of sensor readings
 * 
 * @author DharshikaSingarathnam
 */
public interface Listener extends Remote{
    
    /**
     * 
     * Handles new updated readings
     * @param sensors
     * @param monitorCount
     * @param sensorCount
     * @throws java.rmi.RemoteException 
     */
    public void Changed(List<Sensor> sensors, int monitorCount, int sensorCount) throws java.rmi.RemoteException;
    
    /**
     * 
     * Alerts monitor if the temperatures reach critical levels
     * @param sensor
     * @throws RemoteException 
     */
    public void alertMonitor(Sensor sensor) throws RemoteException;
    
    /**
     * 
     * Notifies monitors when a particular sensor is down
     * @param sensor
     * @throws RemoteException 
     */
    public void notifySensorFailure(Sensor sensor) throws RemoteException;
}
