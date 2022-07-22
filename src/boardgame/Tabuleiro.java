        package boardgame;

        public class Tabuleiro {
            private int linhas;
            private int colunas;
            private Peca[][] pecas;

            public Tabuleiro(int linhas, int colunas) {
                if(linhas < 1 || colunas < 1){
                    throw new BoardExcepiton("Erro criando tabuleiro: Eh necessario que haja pelo menos 1 linha e 1 coluna!");
                }
                this.linhas = linhas;
                this.colunas = colunas;
                pecas = new Peca[linhas][colunas];
            }

            public int getLinhas() {
                return linhas;
            }


            public int getColunas() {
                return colunas;
            }


            public Peca peca(int linha,int coluna){
                if(!posicaoExitente(linha,coluna)){
                    throw new BoardExcepiton("Posicao nao encontrada no tabuleiro!");
                }
                return pecas[linha][coluna];
            }

            public Peca peca(Posicao posicao){
                if(!posicaoExistente(posicao)){
                    throw new BoardExcepiton("Posicao nao encontrada no tabuleiro!");
                }
                return  pecas[posicao.getLinha()][posicao.getColuna()];
            }

            public void lugarPeca(Peca peca, Posicao posicao){
                if(temUmaPeca(posicao)){
                    throw new BoardExcepiton("Já exite uma peca nessa posicao " + posicao);
                }
                pecas [posicao.getLinha()][posicao.getColuna()] = peca;
                peca.posicao = posicao;
            }

            public Peca removerPeca(Posicao posicao){
                if(!posicaoExistente(posicao)){
                    throw new BoardExcepiton("Posicao nao encontrada no tabuleiro!");
                }
                if(peca(posicao) == null){
                    return null;
                }
                Peca aux = peca(posicao);
                aux.posicao = null;
                pecas[posicao.getLinha()][posicao.getColuna()] = null;
                return aux;
            }

            public boolean posicaoExitente(int linha, int coluna){
                return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
            }

            public boolean posicaoExistente(Posicao posicao){
                return posicaoExitente(posicao.getLinha(), posicao.getColuna());
            }

            public boolean temUmaPeca(Posicao posicao){
                if(!posicaoExistente(posicao)){
                    throw new BoardExcepiton("Posicao nao encontrada no tabuleiro!");
                }
               return peca(posicao) != null;
            }
        }
