package br.ufpb.dcx.matrizes;

import java.util.Scanner;

public class TestaMatriz {

    public static void main(String[] args) {

        boolean sair = false;
        Scanner entrada = new Scanner(System.in);

        while (!sair) {
            System.out.println("Calculadora de matrizes\n" +
                    "[1] Selecionar matriz\n" +
                    "[2] Sair");
            String opcaoPrincipal = entrada.nextLine();

            if (opcaoPrincipal.equals("1")) {
                String opcaoGerador = "";
                while (!opcaoGerador.equals("3")) {
                    boolean temMatriz = false;

                    System.out.println(
                            "\n[1] Inserir matriz\n" +
                                    "[2] Gerar Matriz Aleatória\n" +
                                    "[3] Voltar");
                    opcaoGerador = entrada.nextLine();

                    Matriz matrizGerada = new Matriz();

                    if (opcaoGerador.equals("1")) {
                        System.out.println("\nInforme a quantidade de linhas:");
                        int linhas = entrada.nextInt();

                        System.out.println("Informe a quantidade de colunas:");
                        int colunas = entrada.nextInt();

                        Matriz matriz = new Matriz(linhas, colunas);
                        for (int i = 0; i < linhas; i++) {
                            for (int j = 0; j < colunas; j++) {
                                System.out.println("Digite o elemento da " + (i + 1) +
                                        "ª Linha e " + (j + 1) + "ª coluna: ");
                                matriz.setElemento(i, j, entrada.nextDouble());
                            }
                        }
                        matrizGerada = matriz;
                        temMatriz = true;
                    }
                    if (opcaoGerador.equals("2")) {
                        System.out.println("\nInforme a quantidade de linhas:");
                        int linhas = entrada.nextInt();

                        System.out.println("Informe a quantidade de colunas:");
                        int colunas = entrada.nextInt();

                        Matriz matriz = new Matriz(linhas, colunas);
                        matrizGerada = matriz.gerarMatrizAleatoria();
                        temMatriz = true;
                    }

                    if (temMatriz) {
                        String menuOperacoes = entrada.nextLine();
                        while (!menuOperacoes.equals("7")) {
                            System.out.println("Matriz original:");
                            System.out.println(matrizGerada);

                            System.out.println(
                                    "Selecione a operação:\n" +
                                            "[1] Calcular determinante\n" +
                                            "[2] Calcular matriz transposta\n" +
                                            "[3] Calcular matriz de cofatores\n" +
                                            "[4] Calcular matriz adjunta\n" +
                                            "[5] Escalonar matriz\n" +
                                            "[6] Calcular matriz inversa\n" +
                                            "[7] Voltar");

                            menuOperacoes = entrada.nextLine();
                            try {
                                switch (menuOperacoes) {
                                    case "1" -> {
                                        System.out.print("\nDeterminante da matriz: ");
                                        System.out.println(matrizGerada.calcularDeterminante());
                                    }
                                    case "2" -> {
                                        System.out.println("\nMatriz transposta:");
                                        System.out.println(matrizGerada.calcularTransposta());
                                    }
                                    case "3" -> {
                                        System.out.println("\nMatriz de cofatores:");
                                        System.out.println(matrizGerada.calcularMatrizDeCofatores());
                                    }
                                    case "4" -> {

                                        System.out.println("\nMatriz adjunta:");
                                        System.out.println(matrizGerada.calcularMatrizAdjunta());
                                    }
                                    case "5" -> {
                                        System.out.println("\nMatriz escalonada:");
                                        System.out.println(matrizGerada.escalonar());
                                    }
                                    case "6" -> {
                                        System.out.println("\nCalcular através da matriz " +
                                                "adjunta ou escalonamento? [1/2]");
                                        String opcao = entrada.nextLine();
                                        while (!(opcao.equals("1") || opcao.equals("2"))) {
                                            System.out.println("Informe uma opção válida");
                                            opcao = entrada.nextLine();
                                        }
                                        if (opcao.equals("1")) {
                                            System.out.println("\nMatriz inversa através da adjunta:");
                                            System.out.println(matrizGerada.calcularInversaPelaAdjunta());
                                        } else {
                                            System.out.println("\nMatriz inversa através de escalonamento:");
                                            System.out.println(matrizGerada.calcularInversaPorEscalonamento());
                                        }
                                    }
                                    default -> System.out.println("\nInforme uma opção válida");
                                }
                            } catch (MatrizNaoQuadradaException | DeterminanteNuloException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }

                }

            }
            if (opcaoPrincipal.equals("2")) sair = true;
        }
    }
}
