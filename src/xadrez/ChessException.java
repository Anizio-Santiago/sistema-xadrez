    package xadrez;

    import boardgame.BoardExcepiton;

    public class ChessException extends BoardExcepiton {
        public static final long serialVersionUID = 1L;

        public ChessException(String msg) {
            super(msg);
        }
    }
