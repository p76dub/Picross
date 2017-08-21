package model.ui;

import javafx.scene.paint.Color;

/**
 * Un indicateur permet de donner une indication à l'utilisateur quand au nombre de cases de même couleur successives.
 * A cet effet, un indicateur connaît : <pre>
 *     - le nombre de cases consécutives de même couleur
 *     - une couleur
 *     - est dans un état particulier : coché ou non-coché
 * </pre>
 * @inv <pre>
 *     getState() != null
 *     getIndication >= 1
 *     getColor() != null
 * </pre>
 */
public interface Indicator {
    //STATIQUES
    enum State {
        CHECKED,
        UNCHECKED;
    }

    //REQUETES
    /**
     * Donne l'état courant de l'indicateur.
     * @return une état
     */
    State getState();

    /**
     * Donne la valeur de l'indicateur, c'est à dire le nombre de cases de même couleur consécutives.
     * @return un entier >= 1
     */
    int getIndication();

    /**
     * Donne la couleur de l'indicateur.
     * @return une couleur
     */
    Color getColor();

    //COMMANDES
    /**
     * Changer l'état courant de l'indicateur.
     * @post <pre>
     *     old getState() == CHECKED => getState() == UNCHECKED
     *     old getState() == UNCHECKED => getState() == CHECKED
     * </pre>
     */
    void changeState();
}
