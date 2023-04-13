package robotPackage;
import lejos.robotics.SampleProvider;
import lejos.hardware.sensor.SensorModes;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.ev3.LocalEV3;

public class Senseurs extends Object{
	private SensorModes ultraSensor;
	private SensorModes touchSensor;
	private EV3ColorSensor colorSensor;
	/*Initialisation de tout les attributs senseurs
	 */
	public Senseurs() {
		Port port1 = LocalEV3.get().getPort("S1");
		touchSensor = new EV3TouchSensor(port1);

		Port port2 = LocalEV3.get().getPort("S2");
		ultraSensor = new EV3UltrasonicSensor(port2);

		Port port4 = LocalEV3.get().getPort("S4");
        colorSensor = new EV3ColorSensor(port4);
	}
	/*Mesure la distance avec le capteur ultrason.
	 * @return float de la distance mesuré par ultrason en metres.
	 */
	public  float getDistance() {
		SampleProvider distance = ultraSensor.getMode("Distance");
		float[] sample= new float[distance.sampleSize()];
		distance.fetchSample(sample, 0);
		return sample[0];
	}
	/**Mesure avec le senseur touch si le robot à touché un obstacle devant.
	 * @return true si le capteur touch est touché et false sinon.
	 */
	public boolean isTouch() {
		SampleProvider touch = touchSensor.getMode("Touch");
		float[] sample= new float[touch.sampleSize()];
		touch.fetchSample(sample, 0);
		if (sample[0]==1.00) {
			return true;
		}else return false;

	}
	
	public int getColor() {
        return colorSensor.getColorID();
    }


public static void main(String[] args) {
    Reflexion r = new Reflexion ();
    //while(true) {
      //  System.out.println(r.senseurs.getColor());
    //}
}
}