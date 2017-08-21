package model.ui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation des grilles avec des ArrayList.
 * Une structure possible serait (0 élément, x null):
 *  0 | 0 | 0 | 0
 *  x
 *  x
 *  x | x | 0
 *  Au niveau des opérations (n hauteur, m largeur) : <pre>
 *      - accéder aux dimensions : O(n) + n*0(m)
 *      - accéder à un élément : 0(1)
 *      - ajout d'éléments : 0(y - n) + 0(x - m)
 *  </pre>
 * @param <T> le type des éléments
 */
public class ArrayListGrid<T> implements Grid<T> {
    //ATTRIBUTS
    private final List<List<T>> rows;

    //CONSTRUCTEUR
    /**
     * Créer une nouvelle grille de taille zéro.
     * @post <pre>
     *     getWidth() == 0
     *     getHeight() == 0
     * </pre>
     */
    public ArrayListGrid() {
        rows = new ArrayList<>();
    }

    //REQUETES
    @Override
    public Dimension getSize() {
        return new Dimension(getWidth(), getHeight());
    }

    @Override
    public int getWidth() {
        int colums = 0;
        for (List<T> row : rows) {
            colums = Math.max(colums, row.size());
        }
        return colums;
    }

    @Override
    public int getHeight() {
        return rows.size();
    }

    @Override
    public T getObjectAt(int x, int y) {
        if (x < 0 || x > getWidth()) {
            throw new AssertionError("Abscisse invalide");
        }
        if (y < 0 || y > getHeight()) {
            throw new AssertionError("Ordonnée invalide");
        }
        List<T> row = rows.get(y);
        if (row == null || x >= row.size()) {
            return null;
        }
        return row.get(x);
    }

    @Override
    public void setObjectAt(int x, int y, T o) {
        if (x < 0) {
            throw new AssertionError("Abscisse invalide");
        }
        if (y < 0) {
            throw new AssertionError("Ordonnée invalide");
        }

        // Construction de la ligne si besoin
        if (y >= getHeight()) {
            for (int i = getHeight(); i <= y; ++i) {
                rows.add(null);
            }
        }
        if (rows.get(y) == null) {
            rows.set(y, new ArrayList<>());
        }

        // Construction de la colonne si besoin
        List<T> row = rows.get(y);
        if (x >= row.size()) {
            for (int i = row.size(); i <= x; ++i) {
                row.add(null);
            }
        }
        row.set(x, o);
    }
}
