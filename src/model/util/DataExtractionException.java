package model.util;

/**
 * Cette exception indique qu'une erreur s'est produite durant l'extraction de données.
 */
public class DataExtractionException extends Exception {
    public DataExtractionException() {
        super();
    }

    public DataExtractionException(String message) {
        super(message);
    }
}
