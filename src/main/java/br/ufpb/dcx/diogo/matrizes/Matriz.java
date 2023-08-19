package br.ufpb.dcx.diogo.matrizes;

public class Matriz {

    private int linhas;
    private int colunas;
    private double[][] matriz;

    public Matriz(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.matriz = new double[linhas][colunas];
    }

    public double getElemento(int linha, int coluna) {
        return this.matriz[linha][coluna];
    }

    public void setElemento(int linha, int coluna, double elemento) {
        this.matriz[linha][coluna] = elemento;
    }


    public Matriz calcularTransposta() {
        Matriz transposta = new Matriz(colunas, linhas);

        for (int i = 0; i < colunas; i++) {
            for (int j = 0; j < linhas; j++) {
                double elemento = matriz[j][i];
                transposta.setElemento(i, j, elemento);
            }
        }
        return transposta;
    }

    public Matriz calcularMatrizDeCofatores() throws MatrizNaoQuadradaException {
        if (this.linhas != this.colunas)
            throw new MatrizNaoQuadradaException(
                    "Só é possível calcular a matriz de cofatores de matrizes quadradas.");

        Matriz matrizDeCofatores = new Matriz(this.linhas, this.colunas);

        for (int i = 0; i < this.matriz.length; i++) {
            for (int j = 0; j < this.matriz[i].length; j++) {
                Matriz matrizTemporaria = this.removerLinhaEColuna(i, j);
                double cofator = Math.pow(-1, (i + 1) + (j + 1)) * matrizTemporaria.calcularDeterminante();
                matrizDeCofatores.setElemento(i, j, cofator);
            }
        }
        return matrizDeCofatores;
    }

    public Matriz removerLinhaEColuna(int linhaASerRemovida, int colunaASerRemovida) {
        Matriz novaMatriz = new Matriz(this.linhas - 1, this.colunas - 1);
        int k = 0;
        for (int i = 0; i < this.matriz.length; i++) {
            if (i == linhaASerRemovida) continue;
            int m = 0;
            for (int j = 0; j < this.matriz[i].length; j++) {
                if (j == colunaASerRemovida) continue;
                novaMatriz.setElemento(k, m, this.matriz[i][j]);
                m++;
            }
            k++;
        }
        return novaMatriz;
    }

    public double calcularDeterminante() throws MatrizNaoQuadradaException {
        if (this.linhas != this.colunas)
            throw new MatrizNaoQuadradaException(
                    "Só é possível calcular o determinante de matrizes quadradas.");

        if (this.matriz.length == 1)
            return this.matriz[0][0];
        else if (this.matriz.length == 2)
            return this.matriz[0][0] * this.matriz[1][1] - this.matriz[1][0] * this.matriz[0][1];
        else if (this.matriz.length == 3) {
            return this.matriz[0][0] * this.matriz[1][1] * this.matriz[2][2]
                    + this.matriz[0][1] * this.matriz[1][2] * this.matriz[2][0]
                    + this.matriz[0][2] * this.matriz[1][0] * this.matriz[2][1]
                    - (this.matriz[2][0] * this.matriz[1][1] * this.matriz[0][2]
                    + this.matriz[2][1] * this.matriz[1][2] * matriz[0][0]
                    + this.matriz[2][2] * this.matriz[1][0] * this.matriz[0][1]);
        }

        Matriz matrizDeCofatores = this.calcularMatrizDeCofatores();

        double determinante = 0;
        for (int j = 0; j < this.matriz[0].length; j++)
            determinante += this.matriz[0][j] * matrizDeCofatores.getElemento(0, j);
        return determinante;
    }

    public Matriz calcularMatrizAdjunta() throws MatrizNaoQuadradaException {
        if (this.linhas != this.colunas)
            throw new MatrizNaoQuadradaException(
                    "Só é possível calcular o determinante de matrizes quadradas.");
        Matriz matrizDeCofatores = this.calcularMatrizDeCofatores();
        return matrizDeCofatores.calcularTransposta();
    }

    public Matriz calcularInversa() throws MatrizNaoQuadradaException, DeterminanteNuloException {
        if (this.calcularDeterminante() == 0)
            throw new DeterminanteNuloException("O determinante da matriz é nulo");

        double determinante = this.calcularDeterminante();
        Matriz matrizAdjunta = this.calcularMatrizAdjunta();
        Matriz matrizInversa = new Matriz(this.linhas, this.colunas);

        for (int i = 0; i < this.matriz.length; i++) {
            for (int j = 0; j < this.matriz[i].length; j++)
                matrizInversa.setElemento(i, j, (1 / determinante) * matrizAdjunta.getElemento(i, j));
        }
        return matrizInversa;
    }

    @Override
    public String toString() {
        StringBuilder matrizString = new StringBuilder();
        for (double[] linha : this.matriz) {
            matrizString.append("[ ");
            for (double elemento : linha)
                matrizString.append(String.format("%3f", elemento)).append(" ");
            matrizString.append(" ]\n");
        }
        return matrizString.toString();
    }

}