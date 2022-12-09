import java.util.ArrayList;

public class Aigle extends Agent {

	private int nb_branches;
	
	//Constructeur 1
    public Aigle(Terrain terre, double p) {
		super(terre,"Aigle",p);
		nb_branches = 0;
	}

	//Constructeur 2
    public Aigle(int x, int y, Terrain terre, double p) {
		super(x, y, terre,"Aigle",p);
		nb_branches = 0;
	}

	//Fais se deplacer l'agent
	public void seDeplacer(int xnew, int ynew) {

        //On verifie que les coordonnées sont dans le terrain
        if (getTerrain().sontValides(xnew,ynew)) {

            //Affichage des coordonnées initiales
            System.out.println(getType()+" "+getId()+" est placé en ("+getPosX()+";"+getPosY()+")");

            //Positionnement de la coordonées dans une case autour de lui
            this.setPosX( ( getPosX()+( (int)(Math.random()*2)-1 + getTerrain().nbLignes) ) % getTerrain().nbLignes);
            this.setPosY( ( getPosY()+( (int)(Math.random()*2)-1 + getTerrain().nbColonnes) ) % getTerrain().nbColonnes);

            //Affichage des nouvelles coordonnées
            System.out.println(getType()+" "+getId()+" s'est déplacé en ("+getPosX()+";"+getPosY()+")");
	    }
    }
	
	@Override
	public String toString() {
		return super.toString() + ", a ramassé " + nb_branches + " branches" + ", a mangé " +proiesmangees+" lapins";
	}

	//Incremente le nombre de branches
	public void addNbBranches() {
		nb_branches++;
	}

	//Creer un nid sur le Terrain a la position de l'agent
	public Nid creerNid() {
		Nid n = new Nid();

        //Coordonnées aux positions de l'aigle
		n.setPosition(this.getPosX(),this.getPosY());
        
        //Positionnement dans le terrain aux coordonnees de l'aigle
        getTerrain().setCase(this.getPosX(),this.getPosY(),n);

        //foyercree -> l'aigle ne pourra plus créer de nid 
        foyerCree = true;
        
		return n;
	}

	//Renvoie le nombre de branches
	public int getNbBranches() {
		return nb_branches;
	}
}
