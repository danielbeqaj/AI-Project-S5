package robotPackage;

import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lejos.hardware.sensor.SensorModes;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.ev3.LocalEV3;

public class testGetDistance {
	
    public static void main(final String[] args) {
    	 final  Port port = LocalEV3.get().getPort("S2");
         final  SensorModes ultraSensor = (SensorModes)new EV3UltrasonicSensor(port);
         final  SampleProvider dist = (SampleProvider)ultraSensor.getMode("Distance");
         final float[] sample = new float[dist.sampleSize()];
         dist.fetchSample(sample, 0);
           float  distance=sample[0];
         System.out.println( distance);
         Delay.msDelay(10000);
        }
    }
