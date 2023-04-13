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
			

		 	 //************************************* Reconnaître un palet
		 	 float t = 0,h=0,dernier = 0;
		 	 boolean b=true;
		 	 while(b) {
		 	 	 distance.fetchSample(sample1, 0); //fetching des données du capteur UltraSon
				

		 	 	 if((h==0)&&(sample1[0]!=Float.POSITIVE_INFINITY)&&(sample1[0]!=Float.NEGATIVE_INFINITY)) {
		 	 	 	 // premiers donées qu'on reçoit
	if (t==0) {
		 	 	 	 	 //premier donnée reçu autre que l'infini
	t=sample1[0];
	dernier=t;
		 	 	 	 }else {
		 	 	 	 	 //pour les autres données
	if(dernier-sample1[0]==0) { //dans la condition il faut laisser une marge et pas 0
		 	 	 	 	 	 //les données ont convergé
	h=sample1[0];
	//
		 	 	 	 	 }else {
		 	 	 	 	 	 dernier=sample1[0];
		 	 	 	 	 }
		 	 	 	 }
		 	 	 	 Delay.msDelay(500); //pour que le robot continue de tourner
		 	 	 }else {
		 	 	 	 //les données qu'on reçoit après avoit trouve t et h ,plutôt des infinis
		 	 	 	 if((sample1[0]!=Float.POSITIVE_INFINITY)&&(sample1[0]!=Float.NEGATIVE_INFINITY)) {
		 	 	 	 	 //on continue d'avoir des données autre que l'infini
	//soit les mêmes que h (avec la marge d'erreur)
	//soit d'autres valeurs,qui veut dire qu'il y a peut-être un objet plus proche encore
		 	 	 	 }else {
		 	 	 	 	 //donc on retombe sur l'infini qui veut dire qu'il n'y a plus d'objet à mesurer
		 	 	 	 	 pilot.stop();
	b=false; //on sort du boucle
		 	 	 	 }
		 	 	 }	 	 	 

		 	 }
		 	 //*************************************

		 	 if(((h/t)-((Math.sqrt(t*t+0.06*0.06)-0.06)/t))==0) {
		 	 	 //attention à la marge du condition
		 	 	 //si le ratio h/t trouvée rend la cond false alors ce n'était pas un palet
		 	 	 LCD.drawString("il y a un palet", 0, 4);
		 	 	 Delay.msDelay(5000);
		 	 }
			

		 	 //après on peut mettre un GyroSenseur pour repositionner le robot devant le palet (moment ou on trouve h)
	}
	}


