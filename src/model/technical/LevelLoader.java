package model.technical;

import model.ui.Box;
import model.ui.Grid;
import model.util.Level;

import java.util.List;

/**
 * Le LevelLoader permet de récupérer une liste des niveaux disponibles et de charger ceux demandés.
 */
public interface LevelLoader {
    //REQUETES
    /**
     * Donne la liste de tous les niveaux disponibles.
     * @return une liste
     */
    List<Level> getLevels();

    /**
     * Charge le niveau. C'est à dire renvoie la grille solution demandée.
     * @pre <pre>
     *     getLevels().contains(level)
     * </pre>
     * @param level: le niveau demandé
     * @return la grille solution
     */
    Grid<Box> getLevel(Level level);
}
