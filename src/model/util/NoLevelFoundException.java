package model.util;

import java.nio.file.Path;

/**
 * Cette exception personnalisée indique qu'aucun niveau n'a été trouvé.
 * @inv <pre>
 *     getDirectoryPath() != null
 * </pre>
 */
public class NoLevelFoundException extends Exception {
    //ATTRIBUTS
    private final Path directoryPath;

    /**
     * Créer une nouvelle exception. Un message par défaut sera fourni.
     * @pre <pre>
     *     dir != null
     * </pre>
     * @param dir le chemin vers le répertoire testé
     */
    public NoLevelFoundException(Path dir) {
        super("No levels found in directory at " + dir.toAbsolutePath().toString());
        directoryPath = dir.toAbsolutePath();
    }

    /**
     * Créer une nouvelle exception. Le message fourni sera utilisé, complété par une phrase indiquant le chemin testé.
     * @pre <pre>
     *     dir != null
     *     message != null
     * </pre>
     * @param dir le chemin vers le répertoire testé
     * @param message un message
     */
    public NoLevelFoundException(Path dir, String message) {
        super(message + "\nPath to directory was : " + dir.toAbsolutePath().toString());
        directoryPath = dir.toAbsolutePath();
    }

    public Path getDirectoryPath() {
        return directoryPath.toAbsolutePath();
    }
}
