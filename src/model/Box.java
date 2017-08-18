/**
 * Une case peut être dans différents états.
 * @inv <pre>
 *      getState() != null
 * </pre>
 */
public interface Box {
    //STATIQUES
    enum State {
        UNKNOWN,
        CHECKED,
        FILLED;

        //STATIQUES
        /**
         * Donne l'état suivant par rapport à <code>current</code>.
         * @pre <pre>
         *     current != null
         * </pre>
         * @post <pre>
         *     State.next(current) != null
         * </pre>
         * @param current l'état à partir duquel on veut le suivant
         * @return un état
         */
        static State next(State current) {
            if (current == null) {
                throw new AssertionError("L'argument 'current' est null");
            }
            return State.values()[(current.ordinal() + 1) % State.values().length];
        }

        //REQUETES
        /**
         * Donne l'état suivant par rapport à la cible.
         * @post <pre>
         *     next() != null
         * </pre>
         * @return un état
         */
        public State next() {
            return State.next(this);
        }
    }

    //REQUETES
    /**
     * Donne l'état dans lequel est la case actuellement.
     * @return un état
     */
    State getState();

    //COMMANDES
    /**
     * Modifie l'état de la case par celui spécifié.
     * @pre <pre>
     *     state != null
     * </pre>
     * @post <pre>
     *     getState() == state
     * </pre>
     * @param state: le nouvel état
     */
    void setState(State state);

    /**
     * Modifie l'état de la case par celui suivant l'état actuel de celle-ci.
     * @post <pre>
     *     getState() == old getState().next()
     * </pre>
     */
    void nextState();
}
