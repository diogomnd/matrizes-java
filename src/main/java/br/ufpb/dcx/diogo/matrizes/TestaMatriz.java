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

            System.out.println("\nMatriz obtida:");
            System.out.print(matriz);

            System.out.printf("Determinante da matriz obtida: %.3f\n", matriz.calcularDeterminante());

            System.out.println("\nAdjunta da matriz obtida:");
            System.out.println(matriz.calcularMatrizAdjunta());

            System.out.println("\nInversa da matriz obtida:");
            System.out.println(matriz.calcularInversa());

            System.out.println("\nMatriz de cofatores obtida:");
            System.out.println(matriz.calcularMatrizDeCofatores());

            entrada.close();

        } catch (MatrizNaoQuadradaException | DeterminanteNuloException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

}
