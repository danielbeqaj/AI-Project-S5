package robotPackage;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

//import lejos.hardware.Button;

//import lejos.utility.Delay;



public class Reflexion extends Object{

	protected Moteurs moteurs;
	protected Senseurs senseurs;
	protected int blanc=6;

	public Reflexion () {
		moteurs=new Moteurs();
		senseurs=new Senseurs();
	}

	public void attraperPaletDevant() {
		attraperPaletDevant(senseurs.getDistance());
	}

	public void attraperPaletDevant(float distance) {
		moteurs.ouvrirPinces();
		moteurs.forwardAsync(100);
		while(!(senseurs.isTouch())) {
			if(!(moteurs.isMoving())) {
				return;
			}
		}
		moteurs.stop();
		moteurs.fermerPinces();
	}
		/*
        // conversion unités et soustraction du 8 cm de la pice pour ne pas pousser le
        // palet
        moteurs.forward(distance * 100 - 8);
        moteurs.ouvrirPinces();
        boolean fin=false;
        //moteurs.pilot.forward(); //(si on utilise pas moteurs.forwardTemps())
        while(fin==false) {
            boolean touche=senseurs.isTouch();
            if(touche==false) {
                moteurs.forward(100);
                //Delay.msDelay(10);//le plus petit possible sinon trop de temps (si on utilise pas moteurs.forwardTemps())
            }else {
                moteurs.stop();
                moteurs.fermerPinces();
                fin=true;
            }
        } */
    
   
	///*
	public float chercherPalet(int degreRotation) {
		moteurs.pilot.setAngularSpeed(10);
		List<float[]> echantillon = new ArrayList<float[]>();//creation tableau dynamique pour stocker les données lors de la rotation
		//List<float[]> echantillonV2 = new ArrayList<float[]>();
		moteurs.rotateAsync(degreRotation);
		int indice=0;
		float distanceMin=5000;
		float angleRetour=0;
		while(moteurs.isMoving()) {
			float tab[] = {senseurs.getDistance(),indice};
			echantillon.add(tab);//on ajoute les données du getDistance dans le tableau echantillon pendant toute la rotation du robot et l'indice qui permettra de retrouver l'angle
			
			System.out.print(senseurs.getDistance()+",");
			indice++;
		}
		for(int i=0;i<echantillon.size();i++) {
			if((echantillon.get(i)[0])<=distanceMin){
				angleRetour=echantillon.get(i)[1];
				distanceMin=echantillon.get(i)[0];
			}
		}
		moteurs.pilot.setAngularSpeed(90);
		System.out.println("la valeur la plus proche est a l'indice"+angleRetour+"sur les "+echantillon.size()+"valeurs");
		return (angleRetour*degreRotation)/echantillon.size();
		
		/*System.out.println("");
		//System.out.println("-------------------");
		/*for(int i=2;i<echantillon.size();i++) {
			float tab[] = {((echantillon.get(i-1))[0]+(echantillon.get(i+1))[0])/2,i-1};
			System.out.print(tab[0]+",");
			echantillonV2.add(tab);
		}*/
		//echantillon=echantillonV2;
	}
	//*/
	/*
	public float chercherPalet(int degreRotation) {
		moteurs.pilot.setAngularSpeed(50);
		List<Float> echantillon = new ArrayList<>();//creation tableau dynamique pour stocker les données lors de la rotation
		List<Float> echantillonV2 = new ArrayList<>();
		List<Float> cibles= new ArrayList<>();//creation du tableau dynamique pour stocker les données ou on suppose qu'il y a des palets ou un robot
		List<Integer> corrCibles= new ArrayList<>();//creation tab dynamique pour les indices de cibles dans echantillon
		List<Integer> indicesCibles= new ArrayList<>();//creation tab dynamique pour les indices des milieux des obstacles dans echantillon
		moteurs.rotateAsync(degreRotation);
		while(moteurs.isMoving()) {
			echantillon.add(senseurs.getDistance());//on ajoute les données du getDistance dans le tableau echantillon pendant toute la rotation du robot
		}
		System.out.println("echantillonage ok");
		for(int i=2;i<echantillon.size();i++) {
			System.out.println(echantillon.get(i));
			System.out.println(echantillonV2.get(i));
			echantillonV2.add((echantillon.get(i-1)+echantillon.get(i+1))/2);
			echantillon=echantillonV2;
		}
		System.out.println("lissage ok");
		for(int i=1;i<echantillon.size();i++) {
			if(Math.abs((float)echantillon.get(i)-echantillon.get(i-1))>0.07){//si la donnée de l'échantillon à l'indice i est plus petite d'au moins 7cm que la précedente alors on l'ajoute au tableau cible car il s'agit d'un palet ou d"un robot
				if(cibles.size()%2==1) {//si l'arrayList est impaire alors j'enrrgistre la darnière valeur avant la discontinuite car fin de l'obstacle
					cibles.add(echantillon.get(i-1));
					corrCibles.add(i-1);
					System.out.println("fin cible");
					
				}
				else {				//sinon c'est qu'on a pas encore enregistré de début de nouvel obstacle donc on le met directement
					cibles.add(echantillon.get(i));
					corrCibles.add(i);
					System.out.println("debut cible");
				}

			}
		}
		for(int i=1;i<cibles.size();i=i+2) {
			int j=((corrCibles.get(i-1)+corrCibles.get(i))/2);
			indicesCibles.add(j);
			System.out.println("ajout indice cible");
		}
		int angleVisee=indicesCibles.get(0);
		if(indicesCibles.size()>1) {
			for(int i=0;i<indicesCibles.size();i++) {
				if(echantillon.get(i)<echantillon.get(angleVisee))
					angleVisee=i;
			}
		}
	moteurs.pilot.setAngularSpeed(moteurs.pilot.getMaxLinearSpeed());
		return angleVisee;
		//moteurs.rotate((angleVisee*360)/echantillon.size());
		//attraperPalet(echantillon.get(angleVisee));
	}
	//*/

	/*public void attraperPalet(float distance) {

	}*/
public void allerBut() {
	System.out.println("direction but");
		moteurs.rotate(- moteurs.angleAuBut);
	moteurs.forwardAsync(300);

	while(senseurs.getColor()!=6) {
	}
	moteurs.stop();
}
	public void deplacementRandom() {
		
	}

	

}
