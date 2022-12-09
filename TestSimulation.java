import java.util.Scanner;
import java.util.InputMismatchException;

public class TestSimulation {

    //Methode pour verifier la validité du nombre de tours 
    public static int nbTours() throws InterruptedException, NbToursException, InputMismatchException {

        int nbTours=0;

        Scanner nb = new Scanner(System.in);
        System.out.print( "Entrez le nombre de tours (400 tours recommandés) : ");
        nbTours = nb.nextInt();
        if(nbTours < 0)
            throw new NbToursException();

        return nbTours;
    }
    
    //Methode main
    public static void main(String[] args) throws InterruptedException, InputMismatchException {

        Terrain terre = new Terrain(10,10);
        Simulation s = new Simulation(terre, 30,5);
        int t = -1;
        
        while(t < 0)
        {
            try
            {
                t = nbTours();
            }
            catch(InputMismatchException e)
            {
                System.out.println("La donnée entrée n'est pas un nombre entier !");
            }
            catch(NbToursException e)
            {
                System.out.println(e.toString());
            }
        }

        System.out.println("\n-------------------------- Debut Simulation --------------------------\n");

        for (int i=0;i<t;i++) {
            s.unTour();
            Thread.sleep(2000);
        }

        System.out.println("\n--------------------------- Fin Simulation ---------------------------\n");
    }
}
