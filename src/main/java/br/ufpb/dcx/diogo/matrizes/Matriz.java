package br.ufpb.dcx.diogo.matrizes;

public class Matriz {

    private int linhas;
    private int colunas;
    private int[][] matriz;

    public Matriz(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.matriz = new int[linhas][colunas];
    }

    public int getElemento(int linha, int coluna) {
        return matriz[linha][coluna];
    }

    public void setElemento(int linha, int coluna, int elemento) {
        matriz[linha][coluna] = elemento;
    }


    @Override
    public String toString() {
        StringBuilder matrizString = new StringBuilder();
        for (int[] linha : this.matriz) {
            matrizString.append("[ ");
            for (int elemento : linha) {
                matrizString.append(String.format("%3d", elemento)).append(" ");
            }
            matrizString.append(" ]\n");
        }
        return matrizString.toString();
    }

    public Matriz calcularTransposta() {
        Matriz transposta = new Matriz(colunas, linhas);
        for (int i = 0; i < colunas; i++) {
            for (int j = 0; j < linhas; j++) {
                int elemento = matriz[j][i];
                transposta.setElemento(i, j, elemento);
            }

        }
        return transposta;
    }

    public int calcularDeterminante() throws MatrizNaoQuadradaException {
        if (linhas != colunas) {
            throw new MatrizNaoQuadradaException("Só é possível calcular o determinante de matrizes quadradas.");
        }

        int n = linhas = colunas;
        int somatorio = 0;
        if (n == 1) {
            return matriz[0][0];
        } else if (n == 2) {
            return matriz[0][0] * matriz[1][1] - matriz[1][0] * matriz[0][1];
        } else {
            //TODO
        }
        return 0;
    }

}