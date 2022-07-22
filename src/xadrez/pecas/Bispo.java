    package xadrez.pecas;

    import boardgame.Posicao;
    import boardgame.Tabuleiro;
    import xadrez.Color;
    import xadrez.PecaXadrez;

    public class Bispo extends PecaXadrez {

        public Bispo(Tabuleiro tabuleiro, Color color) {
            super(tabuleiro, color);
        }

        @Override
        public String toString(){
            return "B";
        }


        @Override
        public boolean[][] movimentosPossiveis() {
            boolean [][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

            Posicao p = new Posicao(0,0);

            // NW noroeste

            p.definirValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
            while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temUmaPeca(p)){
                mat[p.getLinha()][p.getColuna()] = true;
                p.definirValores(p.getLinha() -1 , p.getColuna() - 1 );
            }
            if(getTabuleiro().posicaoExistente(p) && existePessaAdversaria(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // NE nordeste

            p.definirValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
            while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temUmaPeca(p)){
                mat[p.getLinha()][p.getColuna()] = true;
                p.definirValores(p.getLinha() -1 , p.getColuna() + 1);
            }
            if(getTabuleiro().posicaoExistente(p) && existePessaAdversaria(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // SE sudeste

            p.definirValores(posicao.getLinha() + 1 , posicao.getColuna() + 1);
            while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temUmaPeca(p)){
                mat[p.getLinha()][p.getColuna()] = true;
                p.definirValores(p.getLinha() + 1 ,p.getColuna() + 1);
            }
            if(getTabuleiro().posicaoExistente(p) && existePessaAdversaria(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // SW sudoeste

            p.definirValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
            while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temUmaPeca(p)){
                mat[p.getLinha()][p.getColuna()] = true;
                p.definirValores(p.getLinha() + 1 , p.getColuna() - 1);
            }
            if(getTabuleiro().posicaoExistente(p) && existePessaAdversaria(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }
            return mat;

        }
    }
