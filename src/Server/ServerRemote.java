

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


/**
 * Remote Interface that is exposed to the client
 * Contains the methods that can be invoked by Clients remotely
 * 
 * @author DharshikaSingarathnam
 */
public interface ServerRemote extends Remote{
    /**
     * 
     * @return the number of sensors connected
     * @throws RemoteException 
     */
    int getSensorCount() throws RemoteException;
    
    /**
     * 
     * @return the number of monitors connected
     * @throws RemoteException 
     */
    int getMonitorCount() throws RemoteException;
    
    /**
     * 
     * @return a particular sensor
     * @throws RemoteException 
     */
    List<Sensor> getSensor() throws RemoteException;
    
    /**
     * Adds a remote monitor to the list
     * @param Listener
     * @throws RemoteException 
     */
    void addMonitor(Listener Listener) throws RemoteException;
    
    /**
     * Removes a remote monitor from the list
     * @param Listener
     * @throws RemoteException 
     */
    void removeMonitor(Listener Listener) throws RemoteException;
    
    /**
     * Returns the latest readings of all sensors
     * @param Listener
     * @throws RemoteException 
     */
    void getLatestReadings(Listener Listener) throws RemoteException;
    
    
    /**
     * Retrieves the sensor readings at a particular ID
     * @param ID
     * @return
     * @throws SensorNotFoundException
     * @throws RemoteException 
     */
    Sensor getLatestReadingsByid(Listener Listener, String id) throws SensorNotFoundException, RemoteException;

}
