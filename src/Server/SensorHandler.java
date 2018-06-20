

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * 
 * @author DharshikaSingarathnam
 * Handles a single sensor  
 */
public class SensorHandler extends Thread {
    
    private String name;
    private final Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    // Stores all data of this sensor
    private final Sensor sensor;
    // Stores the latest readings
    private Reading reading;
    
    // Set of all sensor names with unique identification
    private static HashSet<String> sensorNames = new HashSet<String>();

    // Map that holds the sensor with reference to its unique identification
    private static Map<String,Sensor> sensors = new HashMap<>();
    
    // Handles all the sensors and their information
    private static Server remoteServer;

   /**
    * Initiating handler thread
     * @param socket
     * @param remoteServer
    */
   public SensorHandler(Socket socket, Server remoteServer) {
       this.socket = socket;
       this.sensor = new Sensor();
       this.remoteServer = remoteServer;
       this.reading = new Reading();
   }

   /**
    * Requests unique name for sensor and adds it to the set of sensors.
    * Repeatedly receives inputs and broadcasts them
    */
   @Override
   public void run() {
       try {

           // Create character streams for the socket for inputs and outputs.
           in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           out = new PrintWriter(socket.getOutputStream(), true);

           // Request the unique name of the sensor. Note that
           // checking for the existence of a name and adding the name
           // must be done while locking the set of names.
           while (true) {
               out.println("SUBMITNAME");
               name = in.readLine();
               if (name == null) {
                   return;
               }
               synchronized (sensorNames) {
                   if (!sensorNames.contains(name)) {
                       sensorNames.add(name);
                       break;
                   }
               }
           }
           
           // Request the password from the sensor
           while (true) {
               out.println("SUBMITPASSWORD");
               String password = in.readLine();
               if (password == null) {
                   return;
               }
               // Authenticate the user
               if(authenticateUser(name, password)){
                   break;
               }    
            }
           
           // Request ID of the sensor
           while (true) {
               out.println("SUBMITSENSORID");
               String id = in.readLine();
               if (id == null) {
                   return;
               }
               sensor.setid(id);
               break;
            }
           
           // Notify sensor that the sensor was registered successfully
           out.println("SENSORREGISTERED");
           // Add new sensor record to the map
           sensors.put(name, sensor);
           // Adding sensor to the server
           remoteServer.addSensor(sensor);
           
           // Receive readings from sensors
           while (true) {
                String input = in.readLine();
                if (input == null) {
                    return;
                }
                // Decoding and Deserializing the reading object received
                try(ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(Base64.getDecoder().decode(input)))){  
                    reading = (Reading) oin.readObject();  
                    // Set the latest readings in the sensor
                    sensor.setLatestReading(reading);
                    sensors.entrySet().forEach(x->System.out.println(x.getValue())); 
                    writeReadingsToFile(sensor);
                    // Validate whether the readings have reached a critical limit
                    validateConditions(sensor);
                } catch (IOException ex) {  
                    System.err.println(ex.getMessage());
                }  
           } 
       } catch (IOException e) {
           System.out.println(e);
       } catch (ClassNotFoundException ex) {
            Logger.getLogger(SensorHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
           // Removing the client when the client connection is lost
           remoteServer.removeSensor(sensor);
           // Notifying the monitors when the sensor connection is lost
           remoteServer.alertSensorFailure(sensor);
           // Remove sensor name from list if it is disconnected
           sensorNames.remove(name);
           try {
               socket.close();
           } catch (IOException e) {
                System.err.println(e.getMessage());
           }
       }
   }
   
   /**
    * Validates whether the readings are valid
    * Alert Monitors if the readings are in critical range
    * @param sensor 
    */
   public void validateConditions(Sensor sensor){
        double temp = sensor.getLatestReading().getTemperature();
        double smokelevel = sensor.getLatestReading().getSmokelevel();

        if( temp > 50 || smokelevel > 7){
            System.out.println("Alert - Server");
            remoteServer.alertMonitors(sensor);
        }

   }
   
   /***
    * Authenticates the user
    * @param username
    * @param password
    * @return 
    */
   public static boolean authenticateUser(String username, String password){
        // Retrieve file path
        try (BufferedReader br = new BufferedReader(new InputStreamReader(SensorHandler.class.getResourceAsStream("users.txt")))) {

            String line;

            // Read line by line 
            while ( null != (line = br.readLine()) ) { 
                String[] data = line.split(":");
                if(data.length == 2){
                    String un = data[0];
                    String pw = data[1];
                    // Check username and password
                    if(un.equals(username) && pw.equals(password)){
                        return true;
                    }
                }
            }
            return false;
        } catch (IOException e) {
            System.err.println(e.getMessage());

        }
        return false;
    }
   
    /**
     * Writes the history of all sensor readings to a text file
     * @param sensor
     * @throws IOException 
     */
    public void writeReadingsToFile(Sensor sensor) throws IOException{
      File file = new File("readings.txt");
      // creates the file
      file.createNewFile();
      
      // creates a FileWriter Object
      FileWriter writer = new FileWriter(file,true); 
      
      Timestamp timestamp = new Timestamp(System.currentTimeMillis());
      // Writes the content to the file
      writer.write(timestamp + ":" + sensor.toString()+"\n"); 
      writer.flush();
      writer.close();
        
    }
}

