package br.ufpb.dcx.matrizes;

/**
 * @author jmath153
 */
public class Escalonador {

    public void permutarLinhas(Matriz matriz, int linha1, int linha2) {
        double temp;
        int numColunas = matriz.getColunas();
        for (int j = 0; j < numColunas; j++) {
            temp = matriz.getElemento(linha1, j);
            double elemento = matriz.getElemento(linha2, j);
            matriz.setElemento(linha1, j, elemento);
            matriz.setElemento(linha2, j, temp);
        }

    }

    public double encontrarPivo(Matriz matriz, int linha) {
        int numColunas = matriz.getColunas();
        for (int i = 0; i < numColunas; i++)
            if (matriz.getElemento(linha, i) != 0)
                return matriz.getElemento(linha, i);
        return -1;
    }

    public void zerarColuna(Matriz matriz, int linhaPivo, int colunaPivo) {
        int numLinhas = matriz.getLinhas();
        for (int i = 0; i < numLinhas; i++) {
            if (matriz.getElemento(i, colunaPivo) != 0) {
                if (i != linhaPivo) {
                    double escalar = matriz.getElemento(i, colunaPivo);
                    int numColunas = matriz.getColunas();
                    for (int j = 0; j < numColunas; j++) {
                        double tmp = matriz.getElemento(i, j) - (escalar * matriz.getElemento(linhaPivo, j));
                        matriz.setElemento(i, j, tmp);
                    }
                }
            }
        }
    }

    public void multiplicarPorEscalar(Matriz matriz, int linha) {
        double pivo = encontrarPivo(matriz, linha);
        int numColunas = matriz.getColunas();
        for (int i = 0; i < numColunas; i++) {
            double escalar = matriz.getElemento(linha, i) / pivo;
            matriz.setElemento(linha, i, escalar);
        }
    }

    public void ordenarLinhas(Matriz matriz) {
        // Cria um array que serve para contar linhas que tem zeros antes do pivô
        // A primeira linha serve como contador, e a segunda para marcar as linhas em si
        int[][] zerosLinhas = new int[matriz.getLinhas()][2];
        int trocas;
        int numLinhas = matriz.getLinhas();
        do {
            for (int i = 0; i < numLinhas; i++) {
                int cont = 0;
                double pivoLinha = encontrarPivo(matriz, i);
                int numColunas = matriz.getColunas();
                for (int j = 0; j < numColunas; j++) {
                    if (matriz.getElemento(i, j) != pivoLinha) {
                        if (matriz.getElemento(i, j) == 0) cont++;
                    } else break;
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