    package xadrez;

    import boardgame.Peca;
    import boardgame.Posicao;
    import boardgame.Tabuleiro;

    public abstract class PecaXadrez extends Peca {

        private Color color;
        private int contagemDemoviementos;

        public PecaXadrez(Tabuleiro tabuleiro, Color color) {
            super(tabuleiro);
            this.color = color;
        }

        public Color getColor() {
            return color;
        }

        public int getContagemDemoviementos(){
            return contagemDemoviementos;
        }

        public void incrementarContagemDeMovimentos(){
            contagemDemoviementos ++;
        }

        public void decrementarContagemDeMovimentos(){
            contagemDemoviementos --;
        }


        public PosicaoXadrez getPosicaoXadrez(){
            return PosicaoXadrez.fromPosition(posicao);
        }

        protected boolean existePessaAdversaria(Posicao posicao){
            PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
            return p != null && p .getColor() != color;
        }
    }
