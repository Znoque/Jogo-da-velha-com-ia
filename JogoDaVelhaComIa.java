import java.util.Scanner;

/**
 *
 * @authors Enoque Teixeira Barbosa,
 *          Gelber Souza dos Santos,
 *          Brenda Lorena Meira Ribeiro,
 *          Isaque coelho de Jesus.
 */
public class JogoDaVelhaComIa {

    public static void main(String[] args) {
        char[][] tabuleiro = {
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '}
        };

        Scanner scanner = new Scanner(System.in);

        System.out.println("Bem-vindo ao Jogo da Velha!");
        System.out.println("");
        imprimirTabuleiro(tabuleiro);

        while (true) {
            jogadaHumano(tabuleiro, scanner);
            System.out.println("\n(Sua Jogada)");
            imprimirTabuleiro(tabuleiro);

            if (verificarVencedor(tabuleiro, 'X')) {
                System.out.println("======RESULTADO=====");
                System.out.println("Você venceu! Parabéns!");
                System.out.println("=====================\n");
                break;
            } else if (tabuleiroCompleto(tabuleiro)) {
                System.out.println("======RESULTADO=====");
                System.out.println("Empate! Deu Velha!!");
                System.out.println("=====================\n");
                break;
            }
            
            System.out.println("======================================\n");
            System.out.println("(Jogada da I.A)");
            jogadaIA(tabuleiro);
            imprimirTabuleiro(tabuleiro);
            
            if (verificarVencedor(tabuleiro, 'O')) {
                System.out.println("======RESULTADO=====");
                System.out.println("A I.A venceu!");
                System.out.println("=====================\n");
                break;
            } else if (tabuleiroCompleto(tabuleiro)) {
                System.out.println("======RESULTADO=====");
                System.out.println("Empate! Deu Velha!!");
                System.out.println("====================\n");
                break;
            }
        }
        scanner.close();
    }

    public static void imprimirTabuleiro(char[][] tabuleiro) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(tabuleiro[i][j]);
                if (j < 2) {
                    System.out.print(" | ");
                }
            }
            System.out.println();
            if (i < 2) {
                System.out.println("---------");
            }
        }
        System.out.println();
    }

    public static void jogadaHumano(char[][] tabuleiro, Scanner scanner) {
        int linha, coluna;
        do {
            System.out.println("==============ENTRADA==================");
            System.out.print("Digite o numero da linha (1, 2 ou 3): ");
            linha = scanner.nextInt();
            System.out.print("Digite o numero da Coluna (1, 2 ou 3): ");
            coluna = scanner.nextInt();
            System.out.println("======================================");
        } while (linha < 1 || linha > 3 || coluna < 1 || coluna > 3 || tabuleiro[linha-1][coluna-1] != ' ');
        tabuleiro[linha-1][coluna-1] = 'X';
    }

    public static void jogadaIA(char[][] tabuleiro) {
        int[] melhorJogada = minimax(tabuleiro, 'O');
        tabuleiro[melhorJogada[0]][melhorJogada[1]] = 'O';
    }

    public static int[] minimax(char[][] tabuleiro, char jogador) {
        int[] melhorJogada = new int[]{-1, -1};
        int melhorValor = (jogador == 'O') ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro[i][j] == ' ') {
                    tabuleiro[i][j] = jogador;
                    int valor = minimaxHelper(tabuleiro, 0, false);
                    if ((jogador == 'O' && valor > melhorValor) || (jogador == 'X' && valor < melhorValor)) {
                        melhorValor = valor;
                        melhorJogada[0] = i;
                        melhorJogada[1] = j;
                    }
                    tabuleiro[i][j] = ' ';
                }
            }
        }
        return melhorJogada;
    }

    public static int minimaxHelper(char[][] tabuleiro, int profundidade, boolean isMaximizing) {
        char adversario = (isMaximizing) ? 'O' : 'X';
        int melhorValor = (isMaximizing) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        if (verificarVencedor(tabuleiro, 'O')) {
            return 1;
        } else if (verificarVencedor(tabuleiro, 'X')) {
            return -1;
        } else if (tabuleiroCompleto(tabuleiro)) {
            return 0;
        }
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro[i][j] == ' ') {
                    tabuleiro[i][j] = adversario;
                    int valor = minimaxHelper(tabuleiro, profundidade + 1, !isMaximizing);
                    tabuleiro[i][j] = ' ';
                    if (isMaximizing) {
                        melhorValor = Math.max(melhorValor, valor);
                    } else {
                        melhorValor = Math.min(melhorValor, valor);
                    }
                }
            }
        }
        return melhorValor;
    }

    public static boolean verificarVencedor(char[][] tabuleiro, char jogador) {
        // Verificar linhas, colunas e diagonais
        for (int i = 0; i < 3; i++) {
            if (tabuleiro[i][0] == jogador && tabuleiro[i][1] == jogador && tabuleiro[i][2] == jogador) {
                return true;
            }
            if (tabuleiro[0][i] == jogador && tabuleiro[1][i] == jogador && tabuleiro[2][i] == jogador) {
                return true;
            }
        }
        if (tabuleiro[0][0] == jogador && tabuleiro[1][1] == jogador && tabuleiro[2][2] == jogador) {
            return true;
        }
        if (tabuleiro[0][2] == jogador && tabuleiro[1][1] == jogador && tabuleiro[2][0] == jogador) {
            return true;
        }
        return false;
    }

    public static boolean tabuleiroCompleto(char[][] tabuleiro) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
}
