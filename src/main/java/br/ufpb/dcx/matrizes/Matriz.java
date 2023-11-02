package br.ufpb.dcx.matrizes;

import org.apache.commons.math3.fraction.Fraction;

import java.util.Arrays;
import java.util.Random;

/**
 * @author diogomnd, jmath153
 */
public class Matriz {

    private final int linhas;
    private final int colunas;
    private final double[][] matriz;

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

    public double getElemento(int linha, int coluna) {
        return matriz[linha][coluna];
    }

    public void setElemento(int linha, int coluna, double elemento) {
        matriz[linha][coluna] = elemento;
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    public Matriz gerarMatrizAleatoria() {
        Matriz matriz = new Matriz(linhas, colunas);
        Random ran = new Random();
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                double elemento = ran.nextInt(10);
                matriz.setElemento(i, j, elemento);
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
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
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
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++)
                matrizInversa.setElemento(i, j, (1 / determinante) * matrizAdjunta.getElemento(i, j));
        }
        return matrizInversa;
    }

    public Matriz calcularInversaPorEscalonamento()
            throws MatrizNaoQuadradaException, DeterminanteNuloException {
        if (calcularDeterminanteDouble() == 0)
            throw new DeterminanteNuloException("A matriz não tem inversa, pois o determinante é nulo");

        Matriz matrizComIdentidade = juntarMatrizComAIdentidade();
        Matriz escalonada = matrizComIdentidade.escalonar();
        return escalonada.calcularInversa();
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
        for (int i = 0; i < linhas; i++) {
            if (i == linhaASerRemovida) continue;
            int m = 0;
            for (int j = 0; j < colunas; j++) {
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

        if (linhas == 1)
            return getElemento(0, 0);
        else if (linhas == 2)
            return getElemento(0, 0) * getElemento(1, 1)
                    - getElemento(1, 0) * getElemento(0, 1);
        else if (linhas == 3)
            return getElemento(0, 0) * getElemento(1, 1) * getElemento(2, 2)
                    + getElemento(0, 1) * getElemento(1, 2) * getElemento(2, 0)
                    + getElemento(0, 2) * getElemento(1, 0) * getElemento(2, 1)
                    - (getElemento(2, 0) * getElemento(1, 1) * getElemento(0, 2)
                    + getElemento(2, 1) * getElemento(1, 2) * getElemento(0, 0)
                    + getElemento(2, 2) * getElemento(1, 0) * getElemento(0, 1));

        Matriz matrizDeCofatores = calcularMatrizDeCofatores();
        double determinante = 0;
        for (int j = 0; j < colunas; j++)
            determinante += getElemento(0, j) * matrizDeCofatores.getElemento(0, j);
        return determinante;
    }

    public Matriz escalonar() {
        Escalonador escalonador = new Escalonador();
        Matriz matrizEscalonada = new Matriz(linhas, colunas);
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                double elemento = getElemento(i, j);
                matrizEscalonada.setElemento(i, j, elemento);
            }
        }
        escalonador.ordenarLinhas(matrizEscalonada);
        for (int i = 0; i < linhas; i++) {
            double pivo = escalonador.encontrarPivo(matrizEscalonada, i);
            int colunaPivo = 0;
            for (int j = 0; j < colunas; j++) {
                if (matrizEscalonada.getElemento(i, j) == pivo) {
                    colunaPivo = j;
                    break;
                }
            }
            if (pivo != 1) {
                escalonador.multiplicarPorEscalar(matrizEscalonada, i);
            }
            escalonador.zerarColuna(matrizEscalonada, i, colunaPivo);
        }
        escalonador.ordenarLinhas(matrizEscalonada);
        return matrizEscalonada;
    }

    private Matriz criarIdentidade() {
        Matriz identidade = new Matriz(linhas, colunas);
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < linhas; j++) {
                if (i == j)
                    identidade.setElemento(i, j, 1);
                else
                    identidade.setElemento(i, j, 0);
            }
        }
        return identidade;
    }

    private Matriz juntarMatrizComAIdentidade() {
        Matriz matrizComIdentidade = new Matriz(linhas, colunas * 2);
        Matriz identidade = criarIdentidade();
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < linhas; j++) {
                double elemento = getElemento(i, j);
                matrizComIdentidade.setElemento(i, j, elemento);
            }
        }
        for (int i = 0; i < linhas; i++) {
            for (int j = linhas; j < linhas * 2; j++) {
                double elemento = identidade.getElemento(i, j - linhas);
                matrizComIdentidade.setElemento(i, j, elemento);
            }
        }
        return matrizComIdentidade;
    }

    private Matriz calcularInversa() {
        Matriz inversa = new Matriz(linhas, colunas / 2);
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < linhas; j++) {
                double elemento = getElemento(i, j + linhas);
                inversa.setElemento(i, j, elemento);
            }
        }
        return inversa;
    }
}