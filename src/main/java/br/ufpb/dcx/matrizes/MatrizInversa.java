package br.ufpb.dcx.matrizes;

/**
 *
 * @author jmath153
 */

public class MatrizInversa {

    public Matriz criarIdentidade(Matriz matriz) {

        int comprimento = matriz.getComprimento();
        Matriz identidade = new Matriz(comprimento, comprimento);

        for (int i = 0; i < comprimento; i++) {
            for (int j = 0; j < comprimento; j++) {
                if (i == j) {
                    identidade.setElemento(i, j, 1);
                } else {
                    identidade.setElemento(i, j, 0);
                }
            }
        }
        return identidade;
    }

    public Matriz juntarMatrizComAIdentidade(Matriz matriz) {
        int comprimento = matriz.getComprimento();
        Matriz matrizComIdentidade = new Matriz(comprimento, comprimento * 2);
        Matriz identidade = criarIdentidade(matriz);

        for (int i = 0; i < comprimento; i++) {
            for (int j = 0; j < comprimento; j++) {
                double elemento = matriz.getElemento(i, j);
                matrizComIdentidade.setElemento(i, j, elemento);
            }
        }
        for (int k = 0; k < comprimento; k++) {
            for (int l = comprimento; l < comprimento * 2; l++) {
                double tmp = identidade.getElemento(k, l - comprimento);
                matrizComIdentidade.setElemento(k, l, tmp);
            }
        }
        return matrizComIdentidade;
    }

    public Matriz separarIdentidade(Matriz matriz) {

        int comprimento = matriz.getComprimento();
        int comprimentoLinha = matriz.getComprimentoLinha();
        Matriz inversa = new Matriz(comprimento, comprimentoLinha / 2);
        int comprimentoInversa = inversa.getComprimento();
        for (int i = 0; i < comprimentoInversa; i++) {
            for (int j = 0; j < comprimentoInversa; j++) {
                double tmp = matriz.getElemento(i, j + comprimentoInversa);
                inversa.setElemento(i, j, tmp);
            }
        }
        return inversa;
    }
}
