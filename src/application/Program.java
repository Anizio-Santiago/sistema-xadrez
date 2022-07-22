    package application;


    import xadrez.ChessException;
    import xadrez.PartidaXadrez;
    import xadrez.PecaXadrez;
    import xadrez.PosicaoXadrez;

    import java.util.*;


    public class Program {
        public static void main(String[] args){

            Scanner sc = new Scanner(System.in);

            PartidaXadrez px = new PartidaXadrez();
            List<PecaXadrez> capturadas = new ArrayList<>();


            while (!px.getCheckMate()){
                try{
                    UI.limparTela();
                    UI.imprimirPartida(px,capturadas);
                    System.out.println( );
                    System.out.print("Origem: ");
                    PosicaoXadrez origem = UI.lerPosicaoDoUsuario(sc);

                    boolean[][] movimentosPossiveis =  px.movimentosPossiveis(origem);
                    UI.limparTela();
                    UI.printTabuleiro(px.getPecas(),movimentosPossiveis);
                    System.out.println( );
                    System.out.print("Destino: ");
                    PosicaoXadrez destino = UI.lerPosicaoDoUsuario(sc);

                    PecaXadrez pecaCapiturada = px.executarMovimentoXadrez(origem,destino);

                    if(pecaCapiturada != null ){
                        capturadas.add(pecaCapiturada);
                    }

                    if(px.getPromover() != null){
                        System.out.print("Informe a peca a ser promovida (B/C/T/R): ");
                        String type = sc.nextLine().toLowerCase();
                        while (!type.equals("b")  && !type.equals("c") && !type.equals("t") && !type.equals("q")){
                            System.out.print("Valor invalido!! Informe a peca a ser promovida (B/C/T/R): ");
                            type = sc.nextLine().toLowerCase();
                        }
                        px.substituirPecaPromovida(type);
                    }
                }
                catch(ChessException e){
                    System.out.println(e.getMessage());
                    sc.nextLine();
                }
                catch(InputMismatchException e){
                    System.out.println(e.getMessage());
                    sc.nextLine();
                }
            }

            UI.limparTela();
            UI.imprimirPartida(px,capturadas);
        }
    }
