import java.util.ArrayList;

public abstract class Agent {

    private static int cpt=0;
    private final int id;
    private int posx,posy;
    private int energie;
    private int age;
    private Terrain terrain;
    private String type;
    private double p_reproduce;
    protected boolean foyerCree;
    protected int proiesmangees;

    //Constructeur 1
    public Agent(int x, int y, Terrain ter, String type, double p) {
        cpt++;
        id = cpt;
        terrain = ter;
        posx = x;
        posy = y;
        this.type = type;
        age = 1;
        energie = 15;
        p_reproduce = p;
        foyerCree = false;
        proiesmangees = 0;
    }

    //Constructeur 2
    public Agent(Terrain ter, String type, double p) {
        this((int)(Math.random()*ter.nbLignes), (int)(Math.random()*ter.nbColonnes), ter, type, p);
    }

    //Renvoie la distance entre l'agent et la case de coordonnées (x,y)
    public double distance(int x, int y) {
		return Math.sqrt(Math.max(posx - x, x - posx) * Math.max(posx - x, x - posx) + Math.max(posy - y, y - posy) * Math.max(posy - y, y - posy));
	}

    //Fais manger l'agent si il peut
    public void manger(ArrayList<Ressource> ress) {

        for (int i=0; i<ress.size();i++) {

            if (ress.get(i).getX() == posx && ress.get(i).getY() == posy && ress.get(i) instanceof Lapin) {
                this.setEnergie(energie + (int)(Math.random()*10+5));
                System.out.println(type+" "+id+" a mangé "+ress.get(i).type+" "+ress.get(i).ident);
                proiesmangees++;
                ress.get(i).initialisePosition();
                terrain.videCase(ress.get(i).getX(),ress.get(i).getY());
                ress.get(i).initialisePosition();
                ress.remove(ress.get(i));
            }
        }
    }

    //Recuperer le nombre de proies mangés
    public int getProiesMangees() {
        return proiesmangees;
    }

    //Fait vieillir l'agent
    public void vieillir() {
		age++;
	}

    //Renvoie true si l'agent est vivant, false sinon
    public boolean estVivant() {
        return energie > 0;
    }

    @Override
	public String toString() {
		return type + " " + id + " : x = " + posx + ", y = "+ posy + ", energie = " + energie + ", age = " + age;
	}

    //Déclaration d'une méthode pour faire se deplacer les agents
    public abstract void seDeplacer(int x, int y);

    //Renvoie la position en x
    public int getPosX() {
        return posx;
    }

    //Change la position en x
    public void setPosX(int x) {
        posx = x;
    }

    //Renvoie la position en y
    public int getPosY() {
        return posy;
    }

    //Change la position en y
    public void setPosY(int y) {
        posy = y;
    }

    //Renvoie le Terrain de la simulation
    public Terrain getTerrain() {
        return terrain;
    }

    //Renvoie l'energie de l'agent
    public int getEnergie() {
        return energie;
    }

    //Change l'energie de l'agent
    public void setEnergie(int x) {
        energie = x;
    }

    //Renvoie le type de l'agent
    public String getType() {
        return type;
    }

    //Renvoie l'id de l'agent
    public int getId() {
        return id;
    }

    //Renvoie la probabilité qu'a l'agent de se reproduire
    public double getPReproduce() {
        return p_reproduce;
    }

    //Actualise l'agent
    public void rafraichir() {
        if (estVivant()) {
            vieillir();
            seDeplacer(( (posx + (int)(Math.random()*(2)-1) ) % terrain.nbLignes ), ( (posy + (int)(Math.random()*(2)-1) ) % terrain.nbLignes ));
            setEnergie(getEnergie()-1);
            System.out.println(toString()+"\n");
        }
	}
}
