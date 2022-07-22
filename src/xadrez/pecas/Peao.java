    package xadrez.pecas;


    import boardgame.Posicao;
    import boardgame.Tabuleiro;
    import xadrez.Color;
    import xadrez.PartidaXadrez;
    import xadrez.PecaXadrez;

    public class Peao extends PecaXadrez {

        private PartidaXadrez partidaXadrez;   // Dependencia para a partida.
        public Peao(Tabuleiro tabuleiro, Color color,PartidaXadrez partidaXadrez) {
            super(tabuleiro, color);
            this.partidaXadrez = partidaXadrez;
        }

        @Override
        public boolean[][] movimentosPossiveis() {
            boolean [][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

            Posicao p = new Posicao(0,0);

            //Possiveis movimentos para a peca de cor White.

            if(getColor() == Color.WHITE ){
                p.definirValores(posicao.getLinha() - 1, posicao.getColuna() );
                if(getTabuleiro().posicaoExistente(p) && !getTabuleiro().temUmaPeca(p)){
                    mat[p.getLinha()][p.getColuna()] = true;
                }
                p.definirValores(posicao.getLinha() - 2, posicao.getColuna() );
                Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
                if(getTabuleiro().posicaoExistente(p) && !getTabuleiro().temUmaPeca(p) && getTabuleiro().posicaoExistente(p2) && !getTabuleiro().temUmaPeca(p2) && getContagemDemoviementos() == 0 ){
                    mat[p.getLinha()][p.getColuna()] = true;
                }
                p.definirValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
                if(getTabuleiro().posicaoExistente(p) && existePessaAdversaria(p)){
                    mat[p.getLinha()][p.getColuna()] = true;
                }
                p.definirValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
                if(getTabuleiro().posicaoExistente(p) && existePessaAdversaria(p)){
                    mat[p.getLinha()][p.getColuna()] = true;
                }

                //#Movimento especial en Passant White no xadrez.
                if(posicao.getLinha() == 3){
                    Posicao pEsquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                    if(getTabuleiro().posicaoExistente(pEsquerda) && existePessaAdversaria(pEsquerda) && getTabuleiro().peca(pEsquerda) == partidaXadrez.getEnPassantVulnerable()){
                        mat[pEsquerda.getLinha() - 1][pEsquerda.getColuna()] = true;
                    }
                    Posicao pDireita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                    if(getTabuleiro().posicaoExistente(pDireita) && existePessaAdversaria(pDireita) && getTabuleiro().peca(pDireita) == partidaXadrez.getEnPassantVulnerable()){
                        mat[pDireita.getLinha() - 1][pDireita.getColuna()] = true;
                    }
                }
            }

            //Possiveis movimentos para a peca de cor Black
            else{
                p.definirValores(posicao.getLinha() + 1, posicao.getColuna() );
                if(getTabuleiro().posicaoExistente(p) && !getTabuleiro().temUmaPeca(p)){
                    mat[p.getLinha()][p.getColuna()] = true;
                }
                p.definirValores(posicao.getLinha() + 2, posicao.getColuna() );
                Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
                if(getTabuleiro().posicaoExistente(p) && !getTabuleiro().temUmaPeca(p) && getTabuleiro().posicaoExistente(p2) && !getTabuleiro().temUmaPeca(p2) && getContagemDemoviementos() == 0 ){
                    mat[p.getLinha()][p.getColuna()] = true;
                }
                p.definirValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
                if(getTabuleiro().posicaoExistente(p) && existePessaAdversaria(p)){
                    mat[p.getLinha()][p.getColuna()] = true;
                }
                p.definirValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
                if(getTabuleiro().posicaoExistente(p) && existePessaAdversaria(p)){
                    mat[p.getLinha()][p.getColuna()] = true;
                }

                //#Movimento especial en Passant Black no xadrez.
                if(posicao.getLinha() == 4){
                    Posicao pEsquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                    if(getTabuleiro().posicaoExistente(pEsquerda) && existePessaAdversaria(pEsquerda) && getTabuleiro().peca(pEsquerda) == partidaXadrez.getEnPassantVulnerable()){
                        mat[pEsquerda.getLinha() + 1][pEsquerda.getColuna()] = true;
                    }
                    Posicao pDireita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                    if(getTabuleiro().posicaoExistente(pDireita) && existePessaAdversaria(pDireita) && getTabuleiro().peca(pDireita) == partidaXadrez.getEnPassantVulnerable()){
                        mat[pDireita.getLinha() +   1][pDireita.getColuna()] = true;
                    }
                }
            }
            return mat;
        }
        @Override
        public String toString(){
            return "P";
        }
    }
