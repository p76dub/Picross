package model.technical;

import javafx.scene.paint.Color;
import model.ui.ArrayListGrid;
import model.ui.Box;
import model.ui.Grid;
import model.ui.Indicator;
import model.util.MetaLevel;

/**
 * Construit un niveau à partir d'une grille solution. A utiliser typiquement avec un LevelLoarder.
 *
 */
public class Level {
    //ATTRIBUTS
    private final Grid<Box> solution;
    private final MetaLevel meta;
    private final Grid<Indicator> leftIndicators;
    private final Grid<Indicator> topIndicators;

    //CONSTRUCTEUR
    /**
     * Construire un nouveau niveau à partir de métadonnées et d'une grille solution.
     * @pre <pre>
     *     metaLevel != null
     *     grid != null
     *     metaLevel.getSize().equals(grid.getSize())
     * </pre>
     * @post <pre>
     *
     * </pre>
     * @param metaLevel les métadonnées
     * @param grid la grille
     */
    public Level(MetaLevel metaLevel, Grid<Box> grid) {
        if (metaLevel == null || grid == null) {
            throw new AssertionError("Les arguments 'metaLevel' et 'grid' ne peuvent pas être nuls");
        }
        if (!metaLevel.getSize().equals(grid.getSize())) {
            throw new AssertionError("La taille de la grille ne correspond pas à celle déclarée dans les métadonnées");
        }
        solution = grid;
        meta = metaLevel;

        leftIndicators = new ArrayListGrid<>();
        generateLeftIndicators();

        topIndicators = new ArrayListGrid<>();
        generateTopIndicators();
    }

    //REQUETES
    /**
     * Donne la grille vierge du niveau.
     * @return une grille vierge.
     */
    public Grid<Box> getRawGrid() {
        Grid<Box> raw = new ArrayListGrid<>();
        for (int i = 0; i < solution.getWidth(); ++i) {
            for (int j = 0; j < solution.getHeight(); ++j) {
                raw.setObjectAt(i, j, null); //TODO: remplir avec des Box
            }
        }
        return raw;
    }

    public Grid<Box> getSolution() {
        return solution;
    }

    public Grid<Indicator> getLeftIndicators() {
        return leftIndicators;
    }

    public Grid<Indicator> getTopIndicators() {
        return topIndicators;
    }

    //OUTILS
    private void generateLeftIndicators() {
        for (int i = 0; i < solution.getHeight(); ++i) {

            Color lastColor = solution.getObjectAt(solution.getWidth() - 1, i).getColor();
            Box.State lastState = solution.getObjectAt(solution.getWidth() - 1, i).getState();
            int consecutive = 1;
            int indicatorNumber = 0;

            for (int j = 0; j < solution.getWidth(); ++j) {
                Box b = solution.getObjectAt(j, i);
                if (!b.getColor().equals(lastColor) || !b.getState().equals(lastState)) {
                    if (lastState.equals(Box.State.FILLED)) {
                        leftIndicators.setObjectAt(i, indicatorNumber, null); //TODO: replace with indicator
                        indicatorNumber += 1;
                        consecutive = 0;
                    }
                    if (b.getState().equals(Box.State.FILLED)) {
                        consecutive = 1;
                    }
                }
            }
        }
    }

    private void generateTopIndicators() {
        for (int i = 0; i < solution.getWidth(); ++i) {

            Color lastColor = solution.getObjectAt(i, solution.getHeight() - 1).getColor();
            Box.State lastState = solution.getObjectAt(i, solution.getHeight() - 1).getState();
            int consecutive = 1;
            int indicatorNumber = 0;

            for (int j = 0; j < solution.getHeight(); ++j) {
                Box b = solution.getObjectAt(j, i);
                if (!b.getColor().equals(lastColor) || !b.getState().equals(lastState)) {
                    if (lastState.equals(Box.State.FILLED)) {
                        topIndicators.setObjectAt(i, indicatorNumber, null); //TODO: replace with indicator
                        indicatorNumber += 1;
                        consecutive = 0;
                    }
                    if (b.getState().equals(Box.State.FILLED)) {
                        consecutive = 1;
                    }
                }
            }
        }
    }
}
