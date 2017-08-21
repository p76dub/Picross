package model.technical;

import model.ui.Box;
import model.ui.Grid;
import model.util.MetaLevel;

import java.util.List;

/**
 * Le GridLoader permet de récupérer une liste des niveaux disponibles et de charger ceux demandés.
 */
public interface GridLoader {
    //REQUETES
    /**
     * Donne la liste de toutes les grilles disponibles, sous forme de métadonnées.
     * @return une liste
     */
    List<MetaLevel> getMetaLevels();

    /**
     * Charge le niveau. C'est à dire renvoie la grille solution demandée.
     * @pre <pre>
     *     getMetaLevels().contains(level)
     * </pre>
     * @param level: le niveau demandé
     * @return la grille solution
     */
    Grid<Box> getGrid(MetaLevel level);
}
