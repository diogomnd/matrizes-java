package br.ufpb.dcx.diogo.matrizes;

import java.util.Scanner;

public class TestaMatriz {

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        System.out.print("Digite a quantidade de linhas: ");
        int linhas = entrada.nextInt();

        System.out.print("Digite a quantidade de colunas: ");
        int colunas = entrada.nextInt();

        GeradorMatrizes geradorMatrizes = new GeradorMatrizes(linhas, colunas);
        int[][] matriz = geradorMatrizes.criarMatriz();

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                System.out.printf("Digite o elemento da %sª linha e %sª coluna: ", i+1, j+1);
                int elemento = entrada.nextInt();
                matriz[i][j] = elemento;
            }
        }
        System.out.println(geradorMatrizes.imprimirMatriz(matriz));
        entrada.close();
    }

}
