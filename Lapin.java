import java.util.ArrayList;

public class Lapin extends Ressource implements Dynamique {

    private Terrain terrain;
    private int age;
    private boolean vivant;

    //Constructeur
    public Lapin(Terrain t) {
        super("Lapin",1);
        age=0;
        terrain = t;
        vivant=true;
    }

    //Fait se reproduire l'agent
    public Lapin seReproduire() {
		Lapin l = new Lapin(terrain);

        //Coordonnées aux positions du lapin
		l.setPosition(( (this.getX() + (int)(Math.random()*(2)-1) ) % terrain.nbLignes ), ( (this.getY() + (int)(Math.random()*(2)-1) ) % terrain.nbLignes ));

        //Positionnement dans le terrain aux coordonnees du lapin si il y a de la place
        if (terrain.caseEstVide(this.getX(),this.getY())) {
            terrain.setCase(this.getX(),this.getY(),l);
            System.out.println("Nouveau lapin "+ l.toString());
        }

		return l;
	}

    //Fait vieillir le Lapin
    public void vieillir() {
        age++;
    }
}
