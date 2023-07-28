package br.ufpb.dcx.diogo.matrizes;

import java.util.Arrays;

public class GeradorMatrizes {

    private final int linhas;
    private final int colunas;

    public GeradorMatrizes(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;
    }

    public int[][] criarMatriz() {
        return new int[this.linhas][this.colunas];
    }

    public String imprimirMatriz(int[][] matriz) {
        StringBuilder matrizString = new StringBuilder();
        for (int[] linha : matriz) {
            matrizString.append("[ ");
            for (int elemento : linha) {
                matrizString.append(elemento).append(". ");
            }
            matrizString.append("]\n");
        }
        return matrizString.toString();
    }

}