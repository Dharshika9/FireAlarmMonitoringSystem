FireAlarmMonitoringSystem

Introduction 

This fire alarm monitoring system built with the aid of fire alarms which containing sensors that can measure the temperature (Celsius), the battery level (percentage), smoke level (on a scale of 0-10) and the CO2 level (parts per million, on average, 300 in the normal atmosphere). It consists of 3 main components, namely sensors (emulator), remote server and monitors (clients).The sensors attached to the fire alarms performs the measurements in five minutes. The sensors send data to the server. The server stores these values and updates periodically each hour with the last measurements to each monitor. Monitors able to view the connected sensors with their latest readings along with their IDs.The monitors can view the number of sensors and monitors connected to the server at any given time. If necessary the monitors able to query the current readings of any sensor connected to the server at any time.

The server will alert the monitors if the sensor reading at any location reaches the critical values specified below.
Temperature - If exceeds 500 C
Smoke Level - If exceeds 7 (out of the 0-10 scale)

Server will also alert the monitors if any sensor stops responding. Sensors or monitors can be added as needed.
