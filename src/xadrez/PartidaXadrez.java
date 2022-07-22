    package xadrez;


    import boardgame.Peca;
    import boardgame.Posicao;
    import boardgame.Tabuleiro;
    import xadrez.pecas.*;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.stream.Collectors;

    public class PartidaXadrez {

        private int turn;
        private Color jogadorAtual;
        private Tabuleiro tabuleiro;
        private boolean check;
        private boolean checkMate;
        private PecaXadrez enPassantVulnerable;   // Propriedade de jogada especial.
        private PecaXadrez promover;     //Propriedade de promocao para a peca peao quando necessario.

        private List<Peca> pecasNoTabuleiro = new ArrayList<>();
        private List<Peca> pecasCapturadas = new ArrayList<>();

        public PartidaXadrez(){
            tabuleiro = new Tabuleiro(8,8);
            turn = 1;
            jogadorAtual = Color.WHITE;
            configuracaoInicia();
        }

        public int getTurn(){
            return turn;
        }

        public Color getJogadorAtual(){
            return jogadorAtual;
        }

        public boolean getCheck(){
            return check;
        }

        public boolean getCheckMate(){
            return checkMate;
        }

        public PecaXadrez getEnPassantVulnerable(){
            return enPassantVulnerable;
        }

        public PecaXadrez getPromover(){
            return promover;
        }

        public PecaXadrez[][] getPecas() {
            PecaXadrez [][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
            for(int i = 0; i < tabuleiro.getLinhas(); i++){

                for(int j = 0; j < tabuleiro.getColunas(); j++){
                    mat[i][j] = (PecaXadrez) tabuleiro.peca(i,j);
                }
            }
            return mat;
        }

        public boolean [][] movimentosPossiveis(PosicaoXadrez posicaoOrigem){
                    Posicao posicao = posicaoOrigem.toPosition();
                    validarPosicaoDeOrigem(posicao);
                    return tabuleiro.peca(posicao).movimentosPossiveis();

        }

        public PecaXadrez executarMovimentoXadrez(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino){
            Posicao origem = posicaoOrigem.toPosition();
            Posicao destino = posicaoDestino.toPosition();
            validarPosicaoDeOrigem(origem);
            ValidarPosicaoDeDestino(origem,destino);
            Peca pecaCapiturada = fazerMover(origem,destino);

            if(testCheck(jogadorAtual)){
                desfazerMovimento(origem,destino,pecaCapiturada);
                throw  new ChessException("Voce nao pode se colocar em Check!");
            }

            PecaXadrez pecaMovida = (PecaXadrez) tabuleiro.peca(destino);

            //#Moviemento especial promocao.
            promover = null;
            if(pecaMovida instanceof Peao){
                if((pecaMovida.getColor() == Color.WHITE && destino.getLinha() == 0) || (pecaMovida.getColor() == Color.BLACK && destino.getLinha() == 7)){
                    promover = (PecaXadrez) tabuleiro.peca(destino);
                    promover = substituirPecaPromovida("q");
                }
            }

            check = (testCheck(oponente(jogadorAtual))) ? true : false;

            if(testCheckMate(oponente(jogadorAtual))){
                checkMate = true;
            }
            else{
                proximoTurn();
            }

            //#Moviemento especial en Passant no xadrez.
            if(pecaMovida instanceof Peao && (destino.getLinha() == origem.getLinha() - 2 || destino.getLinha() == origem.getLinha() + 2 )){
                enPassantVulnerable = pecaMovida;
            }
            else{
                enPassantVulnerable =  null;
            }

            return (PecaXadrez) pecaCapiturada;
        }

        public PecaXadrez substituirPecaPromovida(String type){
            if(promover == null){ //#Programacao defenciva.
                throw new IllegalStateException("Nao ha peca para ser promovida!");
            }
            if(!type.equals("b")  && !type.equals("c") && !type.equals("t") && !type.equals("q")){
                return promover;
            }

            Posicao pos = promover.getPosicaoXadrez().toPosition();
            Peca p = tabuleiro.removerPeca(pos);
            pecasNoTabuleiro.remove(p);

            PecaXadrez novaPeca = novaPeca(type,promover.getColor());
            tabuleiro.lugarPeca(novaPeca,pos);
            pecasNoTabuleiro.add(novaPeca);

            return novaPeca;
        }

        private PecaXadrez  novaPeca(String type, Color color){
            if(type.equals("b")) return new Bispo(tabuleiro,color);
            if(type.equals("c")) return new Cavalo(tabuleiro,color);
            if(type.equals("t")) return new Rainha(tabuleiro,color);
            return new Torre(tabuleiro,color);

        }

        private Peca fazerMover(Posicao origem, Posicao destino){
            PecaXadrez p = (PecaXadrez)tabuleiro.removerPeca(origem);
            p.incrementarContagemDeMovimentos();
            Peca pecaCapiturada = tabuleiro.removerPeca(destino);
            tabuleiro.lugarPeca(p,destino);
            if(pecaCapiturada != null){
                pecasNoTabuleiro.remove(pecaCapiturada);
                pecasCapturadas.add(pecaCapiturada);
            }

            //#Movimento especial Torre na ala do Rei.
            if(p instanceof Rei && destino.getColuna() == origem.getColuna() + 2){
                Posicao origemT = new Posicao(origem.getLinha(),origem.getColuna() + 3 );
                Posicao destinoT = new Posicao(origem.getLinha(),origem.getColuna() + 1);
                PecaXadrez torre = (PecaXadrez)tabuleiro.removerPeca(origemT);  //DownCasting.
                tabuleiro.lugarPeca(torre,destinoT);
                torre.incrementarContagemDeMovimentos();
            }
            //#Movimento especial Torre na ala da Rainha    .
            if(p instanceof Rei && destino.getColuna() == origem.getColuna() - 2){
                Posicao origemT = new Posicao(origem.getLinha(),origem.getColuna() - 4 );
                Posicao destinoT = new Posicao(origem.getLinha(),origem.getColuna() - 1);
                PecaXadrez torre = (PecaXadrez)tabuleiro.removerPeca(origemT);  //DownCasting.
                tabuleiro.lugarPeca(torre,destinoT);
                torre.incrementarContagemDeMovimentos();
            }

            //#Movimento especial en Passant no xadrez.
            if(p instanceof Peao ){
                if(origem.getColuna() != destino.getColuna() && pecaCapiturada == null){
                    Posicao posicaoPeao;
                    if(p.getColor() == Color.WHITE){
                        posicaoPeao = new Posicao(destino.getLinha() + 1,destino.getColuna());
                    }
                    else{
                        posicaoPeao = new Posicao(destino.getLinha() - 1,destino.getColuna());
                    }
                    pecaCapiturada = tabuleiro.removerPeca(posicaoPeao);
                    pecasCapturadas.add(pecaCapiturada);
                    pecasNoTabuleiro.remove(pecaCapiturada);
                }
            }

            return  pecaCapiturada;
        }

        private void desfazerMovimento(Posicao origem,Posicao destino,Peca pecaCapturada){
            PecaXadrez p = (PecaXadrez)tabuleiro.removerPeca(destino);
            p.decrementarContagemDeMovimentos();
            tabuleiro.lugarPeca(p,origem);

            if(pecaCapturada != null){
                tabuleiro.lugarPeca(pecaCapturada,destino);
                pecasCapturadas.remove(pecaCapturada);
                pecasNoTabuleiro.add(pecaCapturada);
            }

            //#Desfazendo movimento especial Torre na ala do Rei.
            if(p instanceof Rei && destino.getColuna() == origem.getColuna() + 2){
                Posicao origemT = new Posicao(origem.getLinha(),origem.getColuna() + 3 );
                Posicao destinoT = new Posicao(origem.getLinha(),origem.getColuna() + 1);
                PecaXadrez torre = (PecaXadrez)tabuleiro.removerPeca(destinoT);  //DownCasting.
                tabuleiro.lugarPeca(torre,origemT);
                torre.decrementarContagemDeMovimentos();
            }
            //#Desfazendo movimento especial Torre na ala da Rainha    .
            if(p instanceof Rei && destino.getColuna() == origem.getColuna() - 2){
                Posicao origemT = new Posicao(origem.getLinha(),origem.getColuna() - 4 );
                Posicao destinoT = new Posicao(origem.getLinha(),origem.getColuna() - 1);
                PecaXadrez torre = (PecaXadrez)tabuleiro.removerPeca(destinoT);  //DownCasting.
                tabuleiro.lugarPeca(torre,origemT);
                torre.decrementarContagemDeMovimentos();
            }

            //#Desfazendo movimento especial en Passant no xadrez.
            if(p instanceof Peao ){
                if(origem.getColuna() != destino.getColuna() && pecaCapturada == enPassantVulnerable){
                    PecaXadrez peao = (PecaXadrez)tabuleiro.removerPeca(destino);
                    Posicao posicaoPeao;
                    if(p.getColor() == Color.WHITE){
                        posicaoPeao = new Posicao(3,destino.getColuna());
                    }
                    else{
                        posicaoPeao = new Posicao(4,destino.getColuna());
                    }
                    tabuleiro.lugarPeca(peao,posicaoPeao);
                }
            }
        }

        private void validarPosicaoDeOrigem(Posicao posicao){
            if(!tabuleiro.posicaoExistente(posicao)){
                throw new ChessException("Nao existe peca na posicao de origem.");
            }
            if(jogadorAtual != ((PecaXadrez)tabuleiro.peca(posicao)).getColor()){
                throw new ChessException("A peca escolhida nao eh sua! ");
            }
            if(!tabuleiro.peca(posicao).existeMovimentoPossivel()){
                throw new ChessException("Nao existe movimentos possiveis para a peca escolhida.");
            }
        }

        private void ValidarPosicaoDeDestino(Posicao origem, Posicao destino){
            if(!tabuleiro.peca(origem).movimentoPossivel(destino)){
                throw new ChessException("A peca escolhida nao pode se mover para a posicao de destino.");
            }

        }

        private void proximoTurn(){
            turn ++;
            jogadorAtual = (jogadorAtual == Color.WHITE) ? Color.BLACK : Color.WHITE;
        }

        private Color oponente(Color color){
            return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
        }

        private PecaXadrez rei(Color color){
            List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getColor() == color).collect(Collectors.toList());
            for(Peca p : list){
                if(p instanceof Rei){
                    return (PecaXadrez) p;
                }
            }
            throw new IllegalStateException("Nao existe o Rei da cor" + color + " no tabuleiro.");
        }

        private boolean  testCheck(Color color){
            Posicao posicaoRei = rei(color).getPosicaoXadrez().toPosition();
            List<Peca> pecasDoOponente = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getColor() == oponente(color)).collect(Collectors.toList());
            for(Peca p : pecasDoOponente){
                boolean[][] mat = p.movimentosPossiveis();
                if(mat[posicaoRei.getLinha()][posicaoRei.getColuna()]){
                    return true;
                }
            }
            return false;
        }

        private boolean testCheckMate(Color color){
            if(!testCheck(color)){
                return false;
            }
            List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getColor() == color).collect(Collectors.toList());
            for(Peca p : list){
               boolean[][] mat = p.movimentosPossiveis();
               for(int i = 0; i < tabuleiro.getLinhas(); i ++){
                   for(int j = 0; j < tabuleiro.getColunas(); j++){
                       if(mat[i][j]){
                           Posicao origem = ((PecaXadrez)p).getPosicaoXadrez().toPosition();
                           Posicao destino = new Posicao(i,j);
                           Peca pecaCapiturda = fazerMover(origem,destino);
                           boolean testCheck = testCheck(color);
                           desfazerMovimento(origem,destino,pecaCapiturda);
                           if(!testCheck){
                               return false;
                           }
                       }
                   }
               }
            }
            return true;
        }

        private void coloqueNovaPeca(char coluna, int linha, PecaXadrez peca){
            tabuleiro.lugarPeca(peca,new PosicaoXadrez(coluna,linha).toPosition());
            pecasNoTabuleiro.add(peca);

        }


        private void configuracaoInicia(){
            coloqueNovaPeca('A', 1, new Torre(tabuleiro, Color.WHITE));
            coloqueNovaPeca('B', 1, new Cavalo(tabuleiro, Color.WHITE));
            coloqueNovaPeca('C', 1, new Bispo(tabuleiro, Color.WHITE));
            coloqueNovaPeca('D', 1, new Rainha(tabuleiro, Color.WHITE));
            coloqueNovaPeca('E', 1, new Rei(tabuleiro, Color.WHITE,this));
            coloqueNovaPeca('F', 1, new Bispo(tabuleiro, Color.WHITE));
            coloqueNovaPeca('G', 1, new Cavalo(tabuleiro, Color.WHITE));
            coloqueNovaPeca('H', 1, new Torre(tabuleiro, Color.WHITE));
            coloqueNovaPeca('A', 2, new Peao(tabuleiro, Color.WHITE,this));
            coloqueNovaPeca('B', 2, new Peao(tabuleiro, Color.WHITE,this));
            coloqueNovaPeca('C', 2, new Peao(tabuleiro, Color.WHITE,this));
            coloqueNovaPeca('D', 2, new Peao(tabuleiro, Color.WHITE,this));
            coloqueNovaPeca('E', 2, new Peao(tabuleiro, Color.WHITE,this));
            coloqueNovaPeca('F', 2, new Peao(tabuleiro, Color.WHITE,this));
            coloqueNovaPeca('G', 2, new Peao(tabuleiro, Color.WHITE,this));
            coloqueNovaPeca('H', 2, new Peao(tabuleiro, Color.WHITE,this));

            coloqueNovaPeca('A', 8, new Torre(tabuleiro, Color.BLACK));
            coloqueNovaPeca('B', 8, new Cavalo(tabuleiro, Color.BLACK));
            coloqueNovaPeca('C', 8, new Bispo(tabuleiro, Color.BLACK));
            coloqueNovaPeca('D', 8, new Rainha(tabuleiro, Color.BLACK));
            coloqueNovaPeca('E', 8, new Rei(tabuleiro, Color.BLACK,this));
            coloqueNovaPeca('F', 8, new Bispo(tabuleiro, Color.BLACK));
            coloqueNovaPeca('G', 8, new Cavalo(tabuleiro, Color.BLACK));
            coloqueNovaPeca('H', 8, new Torre(tabuleiro, Color.BLACK));
            coloqueNovaPeca('A', 7, new Peao(tabuleiro, Color.BLACK,this));
            coloqueNovaPeca('B', 7, new Peao(tabuleiro, Color.BLACK,this));
            coloqueNovaPeca('C', 7, new Peao(tabuleiro, Color.BLACK,this));
            coloqueNovaPeca('D', 7, new Peao(tabuleiro, Color.BLACK,this));
            coloqueNovaPeca('E', 7, new Peao(tabuleiro, Color.BLACK,this));
            coloqueNovaPeca('F', 7, new Peao(tabuleiro, Color.BLACK,this));
            coloqueNovaPeca('G', 7, new Peao(tabuleiro, Color.BLACK,this));
            coloqueNovaPeca('H', 7, new Peao(tabuleiro, Color.BLACK,this));

        }
    }
