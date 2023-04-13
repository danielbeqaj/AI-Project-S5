package robotPackage;

class Threads extends Thread{
    Reflexion r;
    String name;

    Threads (Reflexion r,String name){
        this.r=r;
        this.name=name;
    }

    public void run() {
        synchronized(r) {
            if(this.name=="tourner") {
                r.moteurs.rotate(360);
            }else {
                System.out.print(r.senseurs.getDistance());
            }
        }
    }

}
//

public class synchronization {
    public static void main(String[] args) {
        //
        Reflexion robot=new Reflexion();
        Threads tourner = new Threads(robot,"tourner");
        Threads mesurer = new Threads (robot,"mesurer");
        tourner.start();
        mesurer.start();
        //
    }

}