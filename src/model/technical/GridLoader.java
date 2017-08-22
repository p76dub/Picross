package model.technical;

import model.ui.Box;
import model.ui.Grid;
import model.util.DataExtractionException;
import model.util.MetaLevel;
import model.util.NoLevelFoundException;

import java.util.List;

/**
 * Le GridLoader permet de récupérer une liste des niveaux disponibles et de charger ceux demandés.
 */
public interface GridLoader {
    //REQUETES
    /**
     * Donne la liste de toutes les grilles disponibles, sous forme de métadonnées.
     * @return une liste
     * @throws NoLevelFoundException Aucun niveau n'a pu être extrait du répertoire
     */
    List<MetaLevel> getMetaLevels() throws NoLevelFoundException;

    /**
     * Charge le niveau. C'est à dire renvoie la grille solution demandée.
     * @pre <pre>
     *     getMetaLevels().contains(level)
     * </pre>
     * @param level le niveau demandé
     * @return la grille solution
     * @throws DataExtractionException Une erreur s'est produite durant l'extraction des données
     */
    Grid<Box> getGrid(MetaLevel level) throws DataExtractionException;
}
