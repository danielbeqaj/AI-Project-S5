package robotPackage;



	import lejos.hardware.ev3.LocalEV3;
	import lejos.hardware.motor.Motor;
	import lejos.hardware.port.Port;
	import lejos.hardware.sensor.EV3UltrasonicSensor;
	import lejos.hardware.sensor.SensorModes;
	import lejos.robotics.SampleProvider;
	import lejos.robotics.chassis.Chassis;
	import lejos.robotics.chassis.Wheel;
	import lejos.robotics.chassis.WheeledChassis;
	import lejos.robotics.navigation.MovePilot;
	import lejos.utility.Delay;
	import lejos.hardware.lcd.LCD;
	public class chercherPalet {
	public static void main(String[] args) {
		 	 Port port2 = LocalEV3.get().getPort("S2");
	 SensorModes ultraSensor = new EV3UltrasonicSensor(port2);
	 SampleProvider distance = ultraSensor.getMode("Distance");
	 float[] sample1= new float[distance.sampleSize()];

	 Wheel wheel1 = WheeledChassis.modelWheel(Motor.B, 5.6).offset(-6.15);
	 Wheel wheel2 = WheeledChassis.modelWheel(Motor.C, 5.6).offset(6.15);
		 	 Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 },
	WheeledChassis.TYPE_DIFFERENTIAL);
		 	 MovePilot pilot = new MovePilot(chassis);
			

		 	 pilot.rotateLeft();
			

		 	 //************************************* Reconna�tre un palet
		 	 float t = 0,h=0,dernier = 0;
		 	 boolean b=true;
		 	 while(b) {
		 	 	 distance.fetchSample(sample1, 0); //fetching des donn�es du capteur UltraSon
				

		 	 	 if((h==0)&&(sample1[0]!=Float.POSITIVE_INFINITY)&&(sample1[0]!=Float.NEGATIVE_INFINITY)) {
		 	 	 	 // premiers don�es qu'on re�oit
	if (t==0) {
		 	 	 	 	 //premier donn�e re�u autre que l'infini
	t=sample1[0];
	dernier=t;
		 	 	 	 }else {
		 	 	 	 	 //pour les autres donn�es
	if(dernier-sample1[0]==0) { //dans la condition il faut laisser une marge et pas 0
		 	 	 	 	 	 //les donn�es ont converg�
	h=sample1[0];
	//
		 	 	 	 	 }else {
		 	 	 	 	 	 dernier=sample1[0];
		 	 	 	 	 }
		 	 	 	 }
		 	 	 	 Delay.msDelay(500); //pour que le robot continue de tourner
		 	 	 }else {
		 	 	 	 //les donn�es qu'on re�oit apr�s avoit trouve t et h ,plut�t des infinis
		 	 	 	 if((sample1[0]!=Float.POSITIVE_INFINITY)&&(sample1[0]!=Float.NEGATIVE_INFINITY)) {
		 	 	 	 	 //on continue d'avoir des donn�es autre que l'infini
	//soit les m�mes que h (avec la marge d'erreur)
	//soit d'autres valeurs,qui veut dire qu'il y a peut-�tre un objet plus proche encore
		 	 	 	 }else {
		 	 	 	 	 //donc on retombe sur l'infini qui veut dire qu'il n'y a plus d'objet � mesurer
		 	 	 	 	 pilot.stop();
	b=false; //on sort du boucle
		 	 	 	 }
		 	 	 }	 	 	 

		 	 }
		 	 //*************************************

		 	 if(((h/t)-((Math.sqrt(t*t+0.06*0.06)-0.06)/t))==0) {
		 	 	 //attention � la marge du condition
		 	 	 //si le ratio h/t trouv�e rend la cond false alors ce n'�tait pas un palet
		 	 	 LCD.drawString("il y a un palet", 0, 4);
		 	 	 Delay.msDelay(5000);
		 	 }
			

		 	 //apr�s on peut mettre un GyroSenseur pour repositionner le robot devant le palet (moment ou on trouve h)
	}
	}


