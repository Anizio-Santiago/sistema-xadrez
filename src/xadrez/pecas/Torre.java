    package xadrez.pecas;

    import boardgame.Posicao;
    import boardgame.Tabuleiro;
    import xadrez.Color;
    import xadrez.PecaXadrez;

    public class Torre extends PecaXadrez {

        public Torre(Tabuleiro tabuleiro, Color color) {
            super(tabuleiro, color);
        }

        @Override
        public String toString(){
            return "T";
        }


        @Override
        public boolean[][] movimentosPossiveis() {
            boolean [][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

            Posicao p = new Posicao(0,0);

            // Acima

            p.definirValores(posicao.getLinha() - 1, posicao.getColuna());
            while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temUmaPeca(p)){
                mat[p.getLinha()][p.getColuna()] = true;
                p.setLinha(p.getLinha() - 1);
            }
            if(getTabuleiro().posicaoExistente(p) && existePessaAdversaria(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // Esquerda

            p.definirValores(posicao.getLinha(), posicao.getColuna() - 1);
            while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temUmaPeca(p)){
                mat[p.getLinha()][p.getColuna()] = true;
                p.setColuna(p.getColuna() - 1);
            }
            if(getTabuleiro().posicaoExistente(p) && existePessaAdversaria(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // Direita

            p.definirValores(posicao.getLinha() , posicao.getColuna() + 1);
            while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temUmaPeca(p)){
                mat[p.getLinha()][p.getColuna()] = true;
                p.setColuna(p.getColuna() + 1);
            }
            if(getTabuleiro().posicaoExistente(p) && existePessaAdversaria(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // Para Baixo

            p.definirValores(posicao.getLinha() + 1, posicao.getColuna());
            while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temUmaPeca(p)){
                mat[p.getLinha()][p.getColuna()] = true;
                p.setLinha(p.getLinha() + 1);
            }
            if(getTabuleiro().posicaoExistente(p) && existePessaAdversaria(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }
            return mat;

        }
    }
