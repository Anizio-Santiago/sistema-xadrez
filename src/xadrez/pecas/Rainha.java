    package xadrez.pecas;

    import boardgame.Posicao;
    import boardgame.Tabuleiro;
    import xadrez.Color;
    import xadrez.PecaXadrez;

    public class Rainha extends PecaXadrez {

        public Rainha(Tabuleiro tabuleiro, Color color) {
            super(tabuleiro, color);
        }

        @Override
        public String toString(){
            return "Q";
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
