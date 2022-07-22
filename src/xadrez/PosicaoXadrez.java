package xadrez;

import boardgame.Posicao;

public class PosicaoXadrez {
    private char coluna;
    private int linha;

    public PosicaoXadrez(char coluna, int linha) {
        if(coluna < 'A' || coluna > 'H' || linha < 1 || linha > 8){
            throw new ChessException("Erro instanciando PosicaoXadrez : Valoress validos de A1 ate H8! ");
        }
        this.coluna = coluna;
        this.linha = linha;
    }

    public char getColuna() {
        return coluna;
    }

    public int getLinha() {
        return linha;
    }

    protected Posicao toPosition(){
        return new Posicao(8 - linha,coluna - 'A');
    }

    protected static PosicaoXadrez fromPosition(Posicao posicao){
        return new PosicaoXadrez((char)('A' + posicao.getColuna()),8 - posicao.getLinha());
    }

    @Override
    public String toString(){
        return "" + coluna + linha;
    }
}
