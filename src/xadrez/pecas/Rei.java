    package xadrez.pecas;

    import boardgame.Posicao;
    import boardgame.Tabuleiro;
    import xadrez.Color;
    import xadrez.PartidaXadrez;
    import xadrez.PecaXadrez;

    public class Rei extends PecaXadrez {

        private PartidaXadrez partidaXadrez;

        public Rei(Tabuleiro tabuleiro, Color color, PartidaXadrez partidaXadrez) {
            super(tabuleiro, color);
            this.partidaXadrez = partidaXadrez;
        }

        @Override
        public String toString() {
            return "R";
        }

        private boolean podeMover(Posicao posicao) {
            PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
            return p == null || p.getColor() != getColor();
        }

        private boolean testRoockCastling(Posicao posicao) {
            PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
            return p != null && p instanceof Torre && p.getColor() == getColor() && p.getContagemDemoviementos() == 0;
        }

        @Override
        public boolean[][] movimentosPossiveis() {
            boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

            Posicao p = new Posicao(0, 0);

            //Acima
            p.definirValores(posicao.getLinha() - 1, posicao.getColuna());
            if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }

            //Esquerda
            p.definirValores(posicao.getLinha(), posicao.getColuna() - 1);
            if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }

            //Direita
            p.definirValores(posicao.getLinha(), posicao.getColuna() + 1);
            if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }

            //Abaixo
            p.definirValores(posicao.getLinha() + 1, posicao.getColuna());
            if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // NW =(Noroeste)
            p.definirValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
            if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }

            //NE (Nordeste)
            p.definirValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
            if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }

            //SW (Suldoeste)
            p.definirValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
            if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }

            //SE (Suldeste)
            p.definirValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
            if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // #Movimento especial Castling
            if (getContagemDemoviementos() == 0 && !partidaXadrez.getCheck()) {
                //#Movimento especial Torre de rook na ala do Rei.
                Posicao posit1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
                if (testRoockCastling(posit1)) {
                    Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                    Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
                    if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) {
                        mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
                    }
                }
                if (getContagemDemoviementos() == 0 && !partidaXadrez.getCheck()) {
                    //#Movimento especial Torre de rook na ala da Rainha.
                    Posicao posit2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
                    if (testRoockCastling(posit2)) {
                        Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                        Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
                        Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);
                        if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null && getTabuleiro().peca(p3) == null) {
                            mat[posicao.getLinha()][posicao.getColuna() - 2] = true;
                        }
                    }
                }
            }
            return mat;
        }
    }

