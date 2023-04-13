package robotPackage;

import lejos.hardware.Button;

//import lejos.utility.Delay;

public class Robi extends Object{


	//protected Moteurs moteurs=new Moteurs();
	//protected Senseurs senseurs=new Senseurs();;
	protected Reflexion ref = new Reflexion();
	float angle;
	float distance;
	int rotationScan=90;
	String etatCourant="ETA2";
	int departGauche;

	public void Run() {
		
		System.out.println("Gauche ou Droite ?");
		Button.waitForAnyPress();
		
		if(Button.LEFT.isDown()) {
			etatCourant="ETA1G";
			departGauche=1;
		}
		if(Button.RIGHT.isDown()) {
			etatCourant="ETA1D";
			departGauche=0;
		}
		
		while(etatCourant!="ETA6") {
			switch(etatCourant) {
			case "ETA1G"://actions de base pour le premier palet;
				System.out.println("etat1G");
				ref.moteurs.forwardAsync(57);
				ref.moteurs.ouvrirPinces();
				ref.moteurs.fermerPinces();
				ref.moteurs.stop();
				ref.moteurs.rotate(-8.1f);
				ref.moteurs.forward(100);
				ref.moteurs.rotate(8.5f);
				ref.allerBut();
				while(ref.moteurs.isMoving()) {
					secuAsync();
				}
				ref.moteurs.ouvrirPinces();
				ref.moteurs.forward(-15);
				ref.moteurs.fermerPinces();
				/*
				ref.moteurs.ouvrirPinces();
				ref.moteurs.forwardAsync(100);
				ref.moteurs.fermerPinces();
				ref.moteurs.rotate(360-190);
				ref.allerBut();
				*/
				ref.moteurs.rotate(180);
				etatCourant="ETA2";
				break;
				
				
			case "ETA1D"://actions de base pour le premier palet si depart a droite;
				System.out.println("etat1D");
				ref.moteurs.forwardAsync(57);
				ref.moteurs.ouvrirPinces();
				ref.moteurs.fermerPinces();
				ref.moteurs.stop();
				ref.moteurs.rotate(8.1f);
				ref.moteurs.forward(100);
				ref.moteurs.rotate(-8.5f);
				ref.allerBut();
				while(ref.moteurs.isMoving()) {
					secuAsync();
				}

				ref.moteurs.ouvrirPinces();
				ref.moteurs.forward(-15);
				ref.moteurs.fermerPinces();
				/*
				ref.moteurs.ouvrirPinces();
				ref.moteurs.forwardAsync(100);
				ref.moteurs.fermerPinces();
				ref.moteurs.rotate(360-190);
				ref.allerBut();
				*/
				ref.moteurs.rotate(180);
				etatCourant="ETA2";
				break;
				
				
			case "ETA2"://action centrale de recherche de palet;
				System.out.println("etat2");
				if(departGauche==1)
					rotationScan=-90;
				angle= ref.chercherPalet(rotationScan);
				System.out.println("-----");
				System.out.println(angle);
				if(angle==0) {
					System.out.println("aucun palet trouve");
					ref.deplacementRandom();
					ref.moteurs.rotate(45);
					etatCourant="ETA2";
					break;
				}
				if(angle<2||angle>358) {//si le palet est devant le robot alors on va directement à ETA3;
					System.out.println("palet devant nous");
					distance=ref.senseurs.getDistance();
					etatCourant="ETA3";
					break;
				}
				etatCourant="ETA4";//si le palet n'est pas directement devant nous, on repositionne le robot en passant par ETA4
				System.out.println("palet trouve pas devant le robot");
				break;

			case "ETA3"://pour attraper le palet devant soi si on en a pas deja un ( secuAsync peut aussi amener ici) et aller au but, lacher le palet et se positionner pour le prochain chercherPalet;
				System.out.println("etat3");
				System.out.println("direction palet");
				if(ref.senseurs.isTouch()==false)
					ref.attraperPaletDevant();
				//if(ref.senseurs.isTouch()) {
					ref.allerBut();
					ref.moteurs.ouvrirPinces();
					ref.moteurs.forward(-50);
					ref.moteurs.fermerPinces();
					ref.moteurs.rotate(135);
					etatCourant="ETA2";
					break;
				//}
			//	ref.moteurs.forward(-50);
				//ref.moteurs.rotate(135);
				//etatCourant="ETA2";
				//break;
				



			case "ETA4":
				System.out.println("etat4");
				System.out.println("on se met face au palet");
				System.out.println("rotation inverse de "+ -(rotationScan-angle) +"degre");
				ref.moteurs.rotate(-(rotationScan-angle));
				distance=ref.senseurs.getDistance();
				etatCourant="ETA3";
				break;

			case "ETA5":

			}
		}

	}

	public void secuAsync() {
		if(ref.senseurs.getDistance()<0.4) {
			System.out.println("secu");
			ref.moteurs.stop();
			ref.moteurs.rotate(90);
			if(ref.senseurs.isTouch()==true) {
				etatCourant="ETA3";
			}
			else etatCourant="ETA2";		
		}
	}


	public static void main(String[] args) {

		Robi rob= new Robi();
		rob.Run();
		//rob.ref.attraperPaletDevant();


	}




}
