package model.util;

import java.awt.Dimension;

/**
 * Un niveau est une représentation abstraite. Cela permet de transporter des metadonnés commme la taille de la grille,
 * le mode (noir et blanc / couleur) ...
 * @inv <pre>
 *     getName() != null
 *     getMode() != null && (getMode().equals(BLACK_MODE) || getMode().equals(COLOR_MODE))
 *     getSize() != null
 * </pre>
 */
public class MetaLevel {
    //STATIQUES
    public static final String BLACK_MODE = "black";
    public static final String COLOR_MODE = "color";

    //ATTRIBUTS
    private final String name;
    private final String mode;
    private final Dimension size;

    //CONSTRUCTEUR
    /**
     * Créer un nouveau niveau.
     * @pre <pre>
     *     name != null && mode != null && size != null
     *     mode.equals(BLACK_MODE) || mode.equals(COLOR_MODE)
     * </pre>
     * @post <pre>
     *     getName().equals(name)
     *     getMode().equals(mode)
     *     getSize().equals(size)
     * </pre>
     * @param name le nom du niveau
     * @param mode le mode (couleur ou noir et blanc)
     * @param size la taille de la grille
     */
    public MetaLevel(String name, String mode, Dimension size) {
        if (name == null || mode == null || size == null) {
            throw new AssertionError("Les arguments 'name', 'mode' et 'size' ne peuvent pas être nuls");
        }
        if (!mode.equals(BLACK_MODE) && !mode.equals(COLOR_MODE)) {
            throw new AssertionError("Le mode est inconnu");
        }
        this.name = name;
        this.mode = mode;
        this.size = new Dimension(size);
    }

    //REQUETES
    /**
     * Donne le nom du niveau.
     * @return le nom
     */
    public String getName() {
        return name;
    }

    /**
     * Donne le mode du niveau.
     * @return le mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * Donne les dimensions de la grille.
     * @return un dimension
     */
    public Dimension getSize() {
        return new Dimension(size);
    }

    @Override
    public String toString() {
        return "LEVEL : " + name
                + "\n  " + mode
                + "\n  " + size.getWidth() + "x" + size.getHeight();
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o.getClass() == this.getClass()) {
            MetaLevel level = (MetaLevel) o;
            return level.getName().equals(this.getName()) && level.getMode().equals(this.getMode())
                    && level.getSize().equals(this.getSize());
        }
        return false;
    }

    @Override
    public int hashCode() {

        return (int) (getName().hashCode() * getSize().getHeight()) << getMode().hashCode();
    }
}
