@SuppressWarnings("serial")
public class NbToursException extends Exception {

    public NbToursException() {
        super("Le nombre de tours doit être positif ou nul !");
    }
}
