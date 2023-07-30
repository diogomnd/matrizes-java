package br.ufpb.dcx.diogo.matrizes;

import java.util.Scanner;

public class TestaMatriz {

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        try {
            System.out.print("Digite a quantidade de linhas: ");
            int linhas = entrada.nextInt();

            System.out.print("Digite a quantidade de colunas: ");
            int colunas = entrada.nextInt();

            Matriz matriz = new Matriz(linhas, colunas);

            for (int i = 0; i < linhas; i++) {
                for (int j = 0; j < colunas; j++) {
                    System.out.printf("Digite o elemento da %sª linha e %sª coluna: ", i + 1, j + 1);
                    int elemento = entrada.nextInt();
                    matriz.setElemento(i, j, elemento);
                }
            }
            System.out.println();
            System.out.println(matriz);
            System.out.println(matriz.calcularDeterminante());

            entrada.close();
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

}
