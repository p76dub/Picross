package model.ui;

import java.awt.Dimension;

/**
 * Les grilles permettent de d'arranger des objets sous forme de grille. Chaque objet est associé à une coordonnée dans
 * grille. Il est possible d'interroger une grille pour connaître l'objet à une position donnée.
 * @param <T> le type des objets contenus dans la grille
 * @inv <pre>
 *     getWidth() == getSize().getWidth()
 *     getHeight() == getSize().getHeight()
 *     getSize() != null
 *     getSize().getWidth() >= 0
 *     getSize().getHeight() >= 0
 * </pre>
 */
public interface Grid<T> {
    // REQUETES

    /**
     * La taille de la grille.
     * @return une Dimension
     */
    Dimension getSize();

    /**
     * La longueur de la grille.
     * @return un entier positif
     */
    int getWidth();

    /**
     * La hauteur de la grille.
     * @return un entier positif
     */
    int getHeight();

    /**
     * Donne l'élément en position x, y.
     * @param x l'ordonnée de l'élément
     * @param y l'abscisse de l'élément
     * @return un objet de type T
     * @pre <pre>
     *     0 <= x < getWidth()
     *     0 <= y < getHeight()
     * </pre>
     */
    T getObjectAt(int x, int y);

    // COMMANDES
    /**
     * Permet de changer l'objet stocké dans la grille en position (x, y).
     * @pre <pre>
     *      0 <= x
     *      0 <= y
     * </pre>
     * @post <pre>
     *     getObjectAt(x, y) == o
     * </pre>
     * @param x l'ordonnée de l'élément
     * @param y l'abscisse de l'élément
     * @param o : l'objet à placer
     */
    void setObjectAt(int x, int y, T o);
}
