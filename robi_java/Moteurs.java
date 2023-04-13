package robotPackage;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.WheeledChassis;
import lejos.hardware.motor.Motor;

public class Moteurs extends Object {
	protected MovePilot pilot;
	public static float angleAuBut;
	public static double distanceBut;

	public Moteurs() {
		Wheel roue1 = WheeledChassis.modelWheel((RegulatedMotor)Motor.B, 5.6).offset(-6.142);
		Wheel roue2 = WheeledChassis.modelWheel((RegulatedMotor)Motor.C, 5.6).offset(6.142);
		Chassis chassis = new WheeledChassis(new Wheel[] { roue1, roue2}, WheeledChassis.TYPE_DIFFERENTIAL);
		pilot = new MovePilot(chassis);
	}

	/*public void forward() {
	forward(5000);
}*/

	//public void forwardTemps(int tempsSecondes) {
	//	pilot.forward();
	//	Delay.msDelay(tempsSecondes*1000);
	//}

	public void forward(double distance){
		pilot.travel(distance);
	}
	public void forwardAsync(double distance) {
		pilot.travel(distance, true);
	}

	public void stop() {
		pilot.stop();
	}

	public void rotate(float angle) {
		angleAuBut=angleAuBut+angle;
		angleAuBut=angleAuBut%360;
		pilot.rotate(angle);
	}
	public void rotateAsync(float angle) {
		angleAuBut=angleAuBut+angle;
		angleAuBut=angleAuBut%360;
		pilot.rotate(angle, true);
	}
	public boolean isMoving() {
		return pilot.isMoving();
	}

	public void ouvrirPinces() {
		Motor.A.rotate(720);
	}

	public void fermerPinces() {
		Motor.A.rotate(-720);
		
	}
	public static void main(final String[] args) {

		//   Rotate(360);
	}
}