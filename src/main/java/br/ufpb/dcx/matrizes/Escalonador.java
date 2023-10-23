package br.ufpb.dcx.matrizes;

/**
 *
 * @author jmath153
 */
public class Escalonador {

    public void permutarLinhas(Matriz matriz, int linha1, int linha2) {
        double temp;
        int comprimentoLinha = matriz.getComprimentoLinha();
        for (int j = 0; j < comprimentoLinha; j++) {
            temp = matriz.getElemento(linha1, j);
            double elemento = matriz.getElemento(linha2, j);
            matriz.setElemento(linha1, j, elemento);
            matriz.setElemento(linha2, j, temp);
        }

    }

    public double encontrarPivo(Matriz matriz, int linha) {
        int comprimentoLinha = matriz.getComprimentoLinha();
        for (int i = 0; i < comprimentoLinha; i++)
            if (matriz.getElemento(linha, i) != 0)
                return matriz.getElemento(linha, i);
        return 0;
    }

    public void zerarColuna(Matriz matriz, int linhaPivo, int colunaPivo) {
        int comprimento = matriz.getComprimento();
        for (int i = 0; i < comprimento; i++) {
            if (matriz.getElemento(i, colunaPivo) != 0) {
                if (i != linhaPivo) {
                    double escalar = matriz.getElemento(i, colunaPivo);
                    int comprimentoLinha = matriz.getComprimentoLinha();
                    for (int j = 0; j < comprimentoLinha; j++) {
                        double tmp = matriz.getElemento(i, j) - (escalar * matriz.getElemento(linhaPivo, j));
                        matriz.setElemento(i, j, tmp);
                    }
                }
            }
        }
    }

    public void multiplicarPorEscalar(Matriz matriz, int linha) {
        double pivo = encontrarPivo(matriz, linha);
        int comprimentoLinha = matriz.getComprimentoLinha();
        for (int i = 0; i < comprimentoLinha; i++) {
            double escalar = matriz.getElemento(linha, i) / pivo;
            matriz.setElemento(linha, i, escalar);
        }
    }

    public void ordenarLinhas(Matriz matriz) {
        double[][] zerosLinhas = new double[matriz.getComprimento()][2];
        int trocas;
        int comprimento = matriz.getComprimento();
        do {
            for (int i = 0; i < comprimento; i++) {
                int cont = 0;
                double pivoLinha = encontrarPivo(matriz, i);
                int comprimentoLinha = matriz.getComprimentoLinha();
                for (int j = 0; j < comprimentoLinha; j++) {
                    if (matriz.getElemento(i, j) != pivoLinha)
                        if (matriz.getElemento(i, j) == 0) cont++;
                    else break;
                }
                zerosLinhas[i][1] = cont;
                zerosLinhas[i][0] = i;
            }
            trocas = 0;
            for (int k = 0; k < zerosLinhas.length - 1; k++) {
                if (zerosLinhas[k][1] > zerosLinhas[k + 1][1]) {
                    permutarLinhas(matriz, k, k + 1);
                    trocas++;
                }
            }
        } while (trocas != 0);
    }

}