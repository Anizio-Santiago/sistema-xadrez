    package xadrez.pecas;

    import boardgame.Posicao;
    import boardgame.Tabuleiro;
    import xadrez.Color;
    import xadrez.PecaXadrez;

    public class Cavalo extends PecaXadrez {

        public Cavalo(Tabuleiro tabuleiro, Color color) {
            super(tabuleiro, color);
        }

        @Override
        public String toString(){
            return "C";
        }

        private boolean podeMover(Posicao posicao){
            PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
            return p == null || p.getColor() != getColor();
        }

        @Override
        public boolean[][] movimentosPossiveis() {
            boolean [][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

            Posicao p = new Posicao(0,0);

            // Regras de movimentacao.

            p.definirValores(posicao.getLinha() - 1, posicao.getColuna() - 2);
            if(getTabuleiro().posicaoExistente(p) && podeMover(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            p.definirValores(posicao.getLinha() - 2 , posicao.getColuna() - 1);
            if(getTabuleiro().posicaoExistente(p) && podeMover(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            p.definirValores(posicao.getLinha() - 2 , posicao.getColuna() + 1);
            if(getTabuleiro().posicaoExistente(p) && podeMover(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            p.definirValores(posicao.getLinha() - 1, posicao.getColuna() + 2 );
            if(getTabuleiro().posicaoExistente(p) && podeMover(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            p.definirValores(posicao.getLinha() + 1, posicao.getColuna() + 2 );
            if(getTabuleiro().posicaoExistente(p) && podeMover(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            p.definirValores(posicao.getLinha() + 2, posicao.getColuna() + 1);
            if(getTabuleiro().posicaoExistente(p) && podeMover(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            p.definirValores(posicao.getLinha() + 2, posicao.getColuna() - 1);
            if(getTabuleiro().posicaoExistente(p) && podeMover(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            p.definirValores(posicao.getLinha() + 1, posicao.getColuna() - 2);
            if(getTabuleiro().posicaoExistente(p) && podeMover(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }
            return mat;
        }
    }
