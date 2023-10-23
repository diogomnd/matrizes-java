package br.ufpb.dcx.matrizes;

import org.apache.commons.math3.fraction.Fraction;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author jmath153, diogomnd
 */

public class Matriz {

    private final int linhas;
    private final int colunas;
    private double[][] matriz;

    public Matriz(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.matriz = new double[linhas][colunas];
    }

    public Matriz() {
        linhas = 0;
        colunas = 0;
        matriz = new double[linhas][colunas];
    }

    public void setMatriz(double[][] matriz) {
        this.matriz = matriz;
    }

    public double getElemento(int linha, int coluna) {
        return matriz[linha][coluna];
    }

    public void setElemento(int linha, int coluna, double elemento) {
        matriz[linha][coluna] = elemento;
    }

    public int getComprimento() {
        return matriz.length;
    }

    public int getComprimentoLinha() {
        return matriz[0].length;
    }

    public Matriz gerarMatrizAleatoria() {
        Matriz matriz = new Matriz(linhas, colunas);
        Random ran = new Random();
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                matriz.setElemento(i, j, ran.nextInt(10));
            }
        }
        return matriz;
    }

    public Matriz calcularTransposta() {
        Matriz matrizTransposta = new Matriz(colunas, linhas);
        for (int i = 0; i < colunas; i++) {
            for (int j = 0; j < linhas; j++) {
                double elemento = getElemento(j, i);
                matrizTransposta.setElemento(i, j, elemento);
            }
        }
        return matrizTransposta;
    }

    public Matriz calcularMatrizDeCofatores() throws MatrizNaoQuadradaException {
        if (linhas != colunas)
            throw new MatrizNaoQuadradaException(
                    "Só é possível calcular a matriz de cofatores de matrizes quadradas.");

        Matriz matrizDeCofatores = new Matriz(linhas, colunas);
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                Matriz matrizTemporaria = removerLinhaEColuna(i, j);
                double cofator = Math.pow(-1, (i + 1) + (j + 1)) * matrizTemporaria.calcularDeterminanteDouble();
                matrizDeCofatores.setElemento(i, j, cofator);
            }
        }
        return matrizDeCofatores;
    }

    public Fraction calcularDeterminante() throws MatrizNaoQuadradaException {
        return new Fraction(calcularDeterminanteDouble());
    }

    public Matriz calcularMatrizAdjunta() throws MatrizNaoQuadradaException {
        if (linhas != colunas)
            throw new MatrizNaoQuadradaException(
                    "Só é possível calcular o determinante de matrizes quadradas.");
        Matriz matrizDeCofatores = calcularMatrizDeCofatores();
        return matrizDeCofatores.calcularTransposta();
    }

    public Matriz calcularInversaPelaAdjunta() throws MatrizNaoQuadradaException, DeterminanteNuloException {
        if (calcularDeterminanteDouble() == 0)
            throw new DeterminanteNuloException("A matriz não tem inversa, pois o determinante é nulo");

        double determinante = calcularDeterminanteDouble();
        Matriz matrizAdjunta = calcularMatrizAdjunta();
        Matriz matrizInversa = new Matriz(linhas, colunas);

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++)
                matrizInversa.setElemento(i, j, (1 / determinante) * matrizAdjunta.getElemento(i, j));
        }
        return matrizInversa;
    }

    public Matriz calcularInversaPorEscalonamento()
            throws MatrizNaoQuadradaException, DeterminanteNuloException {
        if (calcularDeterminanteDouble() == 0)
            throw new DeterminanteNuloException("A matriz não tem inversa, pois o determinante é nulo");

        MatrizInversa matrizInversa = new MatrizInversa();

        Matriz matrizComIdentidade = matrizInversa.juntarMatrizComAIdentidade(this);
        Matriz escalonada = matrizComIdentidade.escalonar();

        return matrizInversa.separarIdentidade(escalonada);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matriz matriz1 = (Matriz) o;
        return linhas == matriz1.linhas && colunas == matriz1.colunas
                && Arrays.deepEquals(matriz, matriz1.matriz);
    }

    @Override
    public String toString() {
        StringBuilder matrizString = new StringBuilder();
        int i = 0;
        for (double[] linha : matriz) {
            matrizString.append("(");
            for (double elemento : linha) {
                matrizString.append(new Fraction(elemento));
                if (i != linha.length - 1)
                    matrizString.append(" | ");
                i++;
            }
            matrizString.append(")\n");
            i = 0;
        }
        return matrizString.toString();
    }

    private Matriz removerLinhaEColuna(int linhaASerRemovida, int colunaASerRemovida) {
        Matriz novaMatriz = new Matriz(linhas - 1, colunas - 1);
        int k = 0;
        for (int i = 0; i < matriz.length; i++) {
            if (i == linhaASerRemovida) continue;
            int m = 0;
            for (int j = 0; j < matriz[i].length; j++) {
                if (j == colunaASerRemovida) continue;
                novaMatriz.setElemento(k, m, getElemento(i, j));
                m++;
            }
            k++;
        }
        return novaMatriz;
    }

    private double calcularDeterminanteDouble() throws MatrizNaoQuadradaException {
        if (linhas != colunas)
            throw new MatrizNaoQuadradaException(
                    "Só é possível calcular o determinante de matrizes quadradas.");

        if (matriz.length == 1)
            return matriz[0][0];
        else if (matriz.length == 2)
            return matriz[0][0] * matriz[1][1] - matriz[1][0] * matriz[0][1];
        else if (matriz.length == 3)
            return matriz[0][0] * matriz[1][1] * matriz[2][2]
                    + matriz[0][1] * matriz[1][2] * matriz[2][0]
                    + matriz[0][2] * matriz[1][0] * matriz[2][1]
                    - (matriz[2][0] * matriz[1][1] * matriz[0][2]
                    + matriz[2][1] * matriz[1][2] * matriz[0][0]
                    + matriz[2][2] * matriz[1][0] * matriz[0][1]);

        Matriz matrizDeCofatores = calcularMatrizDeCofatores();

        double determinante = 0;
        for (int j = 0; j < matriz[0].length; j++)
            determinante += matriz[0][j] * matrizDeCofatores.getElemento(0, j);
        return determinante;
    }

    public Matriz escalonar() throws MatrizNaoQuadradaException, DeterminanteNuloException {

        if (linhas != colunas)
            throw new MatrizNaoQuadradaException("Só é possível escalonar na forma reduzida matrizes quadradas");
        if (calcularDeterminanteDouble() == 0)
            throw new DeterminanteNuloException("Não é possível escalonar na forma reduzida matrizes singulares");

        Escalonador escalonador = new Escalonador();

        Matriz matrizEscalonada = new Matriz(getComprimento(), getComprimentoLinha());
        for (int i = 0; i < getComprimento(); i++) {
            for (int j = 0; j < getComprimentoLinha(); j++) {
                double elemento = getElemento(i, j);
                matrizEscalonada.setElemento(i, j, elemento);
            }
        }

        escalonador.ordenarLinhas(matrizEscalonada);

        for (int i = 0; i < matrizEscalonada.getComprimento(); i++) {
            double pivo = escalonador.encontrarPivo(matrizEscalonada, i);
            int colunaPivo = 0;

            for (int j = 0; j < matrizEscalonada.getComprimentoLinha(); j++) {
                if (matrizEscalonada.getElemento(i, j) == pivo) {
                    colunaPivo = j;
                    break;
                }
            }
            if (pivo != 1)
                escalonador.multiplicarPorEscalar(matrizEscalonada, i);
            escalonador.zerarColuna(matrizEscalonada, i, colunaPivo);
        }
        return matrizEscalonada;
    }

}