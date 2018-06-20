FireAlarmMonitoringSystem

This fire alarm monitoring system built with the aid of fire alarms which containing sensors that can measure the temperature (Celsius), the battery level (percentage), smoke level (on a scale of 0-10) and the CO2 level (parts per million, on average, 300 in the normal atmosphere). It consists of 3 main components, namely sensors (emulator), remote server and monitors (clients).The sensors attached to the fire alarms performs the measurements in five minutes. The sensors send data to the server. The server stores these values and updates periodically each hour with the last measurements to each monitor. Monitors able to view the connected sensors with their latest readings along with their IDs.The monitors can view the number of sensors and monitors connected to the server at any given time. If necessary the monitors able to query the current readings of any sensor connected to the server at any time.


The server will alert the monitors if the sensor reading at any location reaches the critical values specified below.

Temperature - If exceeds 500 C
Smoke Level - If exceeds 7 (out of the 0-10 scale)

Server will also alert the monitors if any sensor stops responding. Sensors or monitors can be added as needed.

Overview of the implementation

 The system is implemented in Java with Java Socket programming and Java RMI.
 The sensor class performs basic socket programming to make the connection with the server through TCP/IP ports to send data readings to the server. This is because the sensor and the server work on two different machines. AlarmSensor Class(client program)creates a socket to connect that socket to the server. When the connection is made the server creates a socket object. The client and server communicate by writing to and reading from the socket.
 Monitor class (Client) uses Java RMI to communicate with the central server because both server & monitor need to perform complex operations. This can be difficult in socket programming. Java RMI allows monitors to remotely call methods on the server.
 The measured readings received by the server written to a text file called ‘readings.txt’.
