/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;

/**
 *
 * @author DharshikaSingarathnam
 */
public class Reading implements Serializable{
    private double temperature;
    private double smokelevel;
    private double co2level;
    private int batterylevel;

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getSmokelevel() {
        return smokelevel;
    }

    public void setSmokelevel(double smokelevel) {
        this.smokelevel = smokelevel;
    }

    public double getCo2level() {
        return co2level;
    }

    public void setCo2level(double co2level) {
        this.co2level = co2level;
    }

    public int getBatterylevel() {
        return batterylevel;
    }

    public void setBatterylevel(int batterylevel) {
        this.batterylevel = batterylevel;
    }

    @Override
    public String toString() {
        return "Reading{" + "temperature=" + temperature + ", smokelevel=" + smokelevel + ", co2level=" + co2level + ", batterylevel=" + batterylevel + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.temperature) ^ (Double.doubleToLongBits(this.temperature) >>> 32));
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.smokelevel) ^ (Double.doubleToLongBits(this.smokelevel) >>> 32));
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.co2level) ^ (Double.doubleToLongBits(this.co2level) >>> 32));
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.batterylevel) ^ (Double.doubleToLongBits(this.batterylevel) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Reading other = (Reading) obj;
        if (Double.doubleToLongBits(this.temperature) != Double.doubleToLongBits(other.temperature)) {
            return false;
        }
        if (Double.doubleToLongBits(this.smokelevel) != Double.doubleToLongBits(other.smokelevel)) {
            return false;
        }
        if (Double.doubleToLongBits(this.co2level) != Double.doubleToLongBits(other.co2level)) {
            return false;
        }
        if (Double.doubleToLongBits(this.batterylevel) != Double.doubleToLongBits(other.batterylevel)) {
            return false;
        }
        return true;
    }
    
    
}
