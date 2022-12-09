public class Arbre extends Ressource implements Statique {

	private Terrain terrain;

	//Constructeur
	public Arbre(Terrain t) {
		super("Arbre", 1);
		terrain = t;
	}

	//Creer une branche dans la premiere case vide trouvee autour de l'arbre
	public void creerBranche(Terrain terre) {
		boolean sortie = false;
		for (int i = -1; i <= 1; i++)
		{
			for(int j = -1; j <= 1; j++)
			{
				int x = (i + getX() + terrain.nbLignes) % terrain.nbLignes;
				int y = (j + getY() + terrain.nbColonnes) % terrain.nbColonnes;
			 	if(terre.caseEstVide(x, y) && Math.random()<0.1)
			 	{
			 		terre.setCase(x, y, new Branche());
			 		sortie = true;
			 		break;
			 	}
			}
			if(sortie)
				break;
		}
	}
}
