package snakepackage;

import java.util.EventObject;

public class GameEvent extends EventObject {

    public static class gameEventType {


        public static final gameEventType EATFOOD = new gameEventType(0);
        public static final gameEventType HITSELF = new gameEventType(2);
        public static final gameEventType HITWALL = new gameEventType(1);
        public static final gameEventType RESTART = new gameEventType(3);

        private int type;

        private gameEventType(final int type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return Integer.toString(type);
        }

    }

    private static final long serialVersionUID = 1L;
    private gameEventType type;

    public GameEvent(final Object source, final gameEventType type) {
        super(source);
        this.type = type;
    }



    public gameEventType getType() {
        return type;
    }

}
