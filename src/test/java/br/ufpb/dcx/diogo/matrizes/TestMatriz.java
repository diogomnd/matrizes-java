package br.ufpb.dcx.diogo.matrizes;

import static org.junit.jupiter.api.Assertions.*;

import br.ufpb.dcx.matrizes.Matriz;
import org.junit.jupiter.api.Test;

public class TestMatriz {

    @Test
    void testEquals() {
        Matriz matriz = new Matriz(3, 2);
        Matriz matriz2 = new Matriz(3, 2);

        double[][] matrizArray;
        double[][] matrizArray2;

        matrizArray = new double[][]{
                {1, 2},
                {3, 4},
                {5, 6}
        };
        matrizArray2 = new double[][]{
                {1, 2},
                {3, 4},
                {5, 6}
        };

        matriz.setMatriz(matrizArray);
        matriz2.setMatriz(matrizArray2);

        assertTrue(matriz.equals(matriz2));
    }


}
