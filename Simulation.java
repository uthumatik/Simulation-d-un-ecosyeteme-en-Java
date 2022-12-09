import java.util.ArrayList;

public class Simulation {
    
    private ArrayList<Ressource> ressources;
    private static ArrayList<Agent> agents;
    private Terrain terrain;

    //Constructeur
    public Simulation(Terrain t, int m, int n) {

        //ArrayList dans lesquelles on va mettre tous les agents et ressource.
        ressources = new ArrayList<Ressource>();
        agents = new ArrayList<Agent>();
        terrain = t;
        boolean valide;
        int x = 0, y = 0;

        //Insertion des ressources dans l'ArrayList ressources
        for (int i=0;i<m;i++) {
            if (Math.random() > 0.1) {
                ressources.add(new Lapin(terrain));
            }
            else {
                ressources.add(new Arbre(terrain));
            }
            valide = false;
            while(!valide)
            {
                x = (int)(Math.random()*terrain.nbLignes);
                y = (int)(Math.random()*terrain.nbColonnes);
                if(terrain.caseEstVide(x, y))
                    valide = true;
            }
            terrain.setCase(x, y,ressources.get(i));
        }

        //Insertion des agents dans l'ArrayList agents
        for (int j=0;j<n;j++) {
            if (Math.random() > 0.5) {
                agents.add(new Loup(terrain,0.5));
            }
            else {
                agents.add(new Aigle(terrain,0.5));
            }
            valide = false;
            while(!valide)
            {
                x = (int)(Math.random()*terrain.nbLignes);
                y = (int)(Math.random()*terrain.nbColonnes);
                valide = true;
                for(Agent a : agents)
                {
                        if(a.getPosX() == x && a.getPosY() == y)
                        {
                            valide = false;
                            break;
                        }
                }
            }
            agents.get(j).setPosX((int)(Math.random()*(terrain.nbLignes)));
            agents.get(j).setPosY((int)(Math.random()*(terrain.nbColonnes)));
        }
    }

    //Methode pour faire se reproduire les loups
    public void seReproduire(Agent a) {

        //On baisse l'energie
        a.setEnergie(a.getEnergie()/2);
        Agent newa;

        int x =  ( a.getPosX()+( (int)(Math.random()*2)-1 ) % terrain.nbLignes);
        int y =  ( a.getPosY()+( (int)(Math.random()*2)-1 ) % terrain.nbColonnes);

        //Creation du nouveau loup avec les coordonnées de sa mere.
        if (a instanceof Loup ) {
            newa = new Loup(x, y, terrain, a.getPReproduce());
            agents.add(newa);
        }

        else {
            newa = new Aigle(x, y, terrain, a.getPReproduce() );
            agents.add(newa);
        }
        System.out.println(newa + " est placé en ("+y+";"+x+")");
    }

    //Methode pour abreuver les agents
    public void pleuvoir(Terrain t) {
        if (Math.random()<0.04) {
            for (Agent a : agents)
            {
                a.setEnergie(a.getEnergie()+5);
            }
            System.out.println("Il pleut !!! Les agents sont revigorés !\n");
        }
    }

    //Methode pour actualiser tous les agents et ressources de la simulation
    public void unTour() throws InterruptedException {

        //Arret du programme si la liste d'agents est vide
        if (agents.isEmpty()) {
            System.out.println("Plus aucun agents !\n");
            System.out.println("-------------------------- Fin Simulation --------------------------");
            System.exit(0);
        }

        //Arret du programme si il n'y a plus de lapins dans la liste de ressources (inutile de continuer sans lapin)
        if (!ressources.stream().anyMatch(c -> c instanceof Lapin)) {
            System.out.println("Plus aucun lapins !\n");
            System.out.println("-------------------------- Fin Simulation --------------------------");
            System.exit(0);
        }

        
        //Il pleut ?

        pleuvoir(terrain);

        //Pour lancer la simulation, nous allons réaliser les actions des agents puis des ressources.

        System.out.println("--- Agents ---\n");

        //Iteration des agents de la liste d'agents
        for (int i=0;i<agents.size();i++) {

            //On verifie que l'agent est bien vivant (fonction vivant() dans la classe agent)
            if (agents.get(i).estVivant()) {

                //Fonction qui réalise les actions vieillir, déplacer, diminuer l'energie d'un (par tour) sur l'agent.
                agents.get(i).rafraichir();

                //Deux types d'agents différents avec des développements différents -> on va réaliser la boucle de maniere différente (methode de cast)

                //Si agent est un Loup
                if (agents.get(i) instanceof Loup) {

                    //On va essayer de creer un Terrier -> pour cela, il faut que le loup ait mangé 5 Proies et qu'il n'ait pas deja créé de Terrier.
                    if (((Loup)agents.get(i)).getProiesMangees() >=5 && (!agents.get(i).foyerCree)) {
                        //Fonction appelée a partir de Loup et qui retourne un terrier aux coordonnees du loup.
                        ressources.add(((Loup)agents.get(i)).creerTerrier());
                        System.out.println("Terrier créé en "+ agents.get(i).getPosX()+" "+agents.get(i).getPosY());
                        continue;
                    }
                    //Fonction dans la classe Agent qui vérifie si il y a une ressource commestible aux coordonnées de l'agent, si oui, il la mange.
                    agents.get(i).manger(ressources);

                    //Pour la reproduction: si le Loup se trouve aux coordonnées d'un Terrier, il crée un nouveau Loup
                    if (terrain.getCase(agents.get(i).getPosX(),agents.get(i).getPosY()) instanceof Terrier) {
                        System.out.println("Nouveau loup créé");
                        //Fonction qui crée le loup et qui le place dans la liste d'agents, avec des coordonnées autour de la mere si possible, sinon il n'est pas créé.
                        seReproduire(agents.get(i));
                    }
                }

                //Si agent est un Aigle
                else if (agents.get(i) instanceof Aigle ) {

                    //On regarde si il existe des branches aux coordonnees de l'agent
                    if (terrain.getCase(agents.get(i).getPosX(),agents.get(i).getPosY()) instanceof Branche) {
                        ((Aigle)agents.get(i)).addNbBranches();
                        ressources.remove(terrain.getCase(agents.get(i).getPosX(),agents.get(i).getPosY()));
                        terrain.lesRessources().remove(terrain.getCase(agents.get(i).getPosX(),agents.get(i).getPosY()));
                        terrain.videCase(agents.get(i).getPosX(),agents.get(i).getPosY());
                    }

                    //Fonction dans la classe Agent qui vérifie si il y a une ressource aux coordonnées de l'agent, si oui, il la mange.
                    agents.get(i).manger(ressources);

                    // On verifie si l'agent valide les conditions pour creer un nid (5 baton et pas deja cree un nid) 
                    if (((Aigle)agents.get(i)).getNbBranches()>=5 && (!agents.get(i).foyerCree)){
                        ressources.add(((Aigle)agents.get(i)).creerNid());
                        System.out.println("Nid créé en "+ agents.get(i).getPosX()+" "+agents.get(i).getPosY());
                        continue;
                    }

                    if (terrain.getCase(agents.get(i).getPosX(),agents.get(i).getPosY()) instanceof Nid) {
                        System.out.println("Nouvel aigle créé");
                        //Fonction qui crée le aigle et qui le place dans la liste d'agents, avec des coordonnées autour de la mere.
                        seReproduire(agents.get(i));
                    }
                }
            }        
            if(! agents.get(i).estVivant())
            {
                System.out.println(agents.get(i).getType()+agents.get(i).getId()+" est mort: plus d'énergie !\n");
                agents.remove(agents.get(i));
            }
        }

        System.out.println("--- Ressources ---\n");

        //Iteration de toutes les ressources de la liste
        for (int nb=0;nb<ressources.size(); nb++) {

            //On verifie que la ressource est bien dans le terrain
            if (terrain.sontValides(ressources.get(nb).getX(),ressources.get(nb).getY())) {

                //si la quantite de la ressource est <=0 on enleve la ressource
                if (ressources.get(nb).getQuantite() <= 0) {
                    terrain.lesRessources().remove(ressources.get(nb));
                    ressources.remove(ressources.get(nb));
                    continue;
                }

                //Si la ressource est un lapin, on le rafraichit
                if (ressources.get(nb) instanceof Lapin ) {

                    //Fonction dans la classe Lapin
                    ((Lapin)ressources.get(nb)).vieillir();
                }

                //Si la ressource est un arbre, il a une chance de creer une ressource branche autour de lui
                if (ressources.get(nb) instanceof Arbre ) {
                    ((Arbre) ressources.get(nb)).creerBranche(terrain);
                    continue;
                }
            }
            //Si elle n'est pas dans le terrain, on la supprime de la liste et du tableau
            else {
                ressources.get(nb).initialisePosition();
                ressources.remove(ressources.get(nb));
                continue;
            }
        }

        //On va maintenant refaire un tour des deux listes pour afficher les ressources

        for (int i=0; i<terrain.nbLignes;i++) {
            for (int j=0; j<terrain.nbColonnes;j++) {
                if (terrain.getCase(i,j) != null && terrain.getCase(i,j).getQuantite() > 0)
                System.out.println(terrain.getCase(i,j).toString()+"\n");
            }
        }

        //affichage du terrain
        System.out.println("\n--- Terrain ---\n");
        terrain.affiche(7);
    }
}
