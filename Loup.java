import java.util.ArrayList;

public class Loup extends Agent{

    //Constructeur 1
    public Loup(Terrain ter, double p) {
        super(ter,"Loup",p);
        proiesmangees = 0;
    }

    //Constructeur
    public Loup(int x, int y, Terrain terre, double p) {
		super(x, y, terre,"Aigle",p);
        proiesmangees = 0;
	}

    //Methode appelée dans rafraichir pour deplacer le loup
    public void seDeplacer(int xnew, int ynew) {

        //On verifie que les coordonnées sont dans le terrain
        if (getTerrain().sontValides(xnew,ynew)) {

            //Impression des coordonnées initiales
            System.out.println(getType()+" "+getId()+" est placé en ("+getPosX()+";"+getPosY()+")");

            //Positionnement de la coordonées dans une case autour de lui
            this.setPosX( ( getPosX()+( (int)(Math.random()*2)-1 + getTerrain().nbLignes) ) % getTerrain().nbLignes);
            this.setPosY( ( getPosY()+( (int)(Math.random()*2)-1 + getTerrain().nbColonnes) ) % getTerrain().nbColonnes);

            //Impression des nouvelles coordonnées
            System.out.println(getType()+" "+getId()+" s'est déplacé en ("+getPosX()+";"+getPosY()+")");
	    }
    }

    //Fonction qui affiche les infos du loup
    public String toString() {
        return super.toString() + ", a mangé " +proiesmangees+" lapins";
    }

    //Methode pour créer un terrier au coordonnées du loup
    public Terrier creerTerrier() {
		Terrier t = new Terrier();

        //Coordonnées aux positions du loup
		t.setPosition(this.getPosX(),this.getPosY());
        
        //Positionnement dans le terrain aux coo du loup
        getTerrain().setCase(this.getPosX(),this.getPosY(),t);

        //foyercree -> le loup ne pourra plus créer de foyer 
        foyerCree = true;
        
		return t;
	}
}
