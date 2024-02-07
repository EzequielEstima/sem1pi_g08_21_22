import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PrevisaoMatrizes {
    static final int LINHAS_MATRIZ_TRANSICAO = 5;
    static final int COLUNAS_MATRIZ_TRANSICAO = 5;
    static final int LINHAS_MATRIZ_REDUZIDA = 4; // matriz reduzida é a matriz sem o estado absorvente
    static final int COLUNAS_MATRIZ_REDUZIDA = 4;//
    static Scanner ler = new Scanner(System.in);
    static final int LINHAS_MATRIZES = 4;
    static final int COLUNAS_MATRIZES = 4;

    public static void main(String[] args) throws FileNotFoundException{
        System.out.println("Insira a localização do ficheiro da matriz de transição: ");
        String fileImput = ler.nextLine();
        double [][] matrizTransicao = lerMatrizTransicao(fileImput);

        double[][] matrizReduzida = criarMatrizSemEstadoAbsorvente (matrizTransicao);
        double[][] identidade = criarMatrizIdentidade();

        double[][] matrizOriginal = subtrairDuasMatrizes(matrizReduzida,identidade);

        double[][] matrizTriangularInferior = new double[LINHAS_MATRIZES][COLUNAS_MATRIZES];
        double[][] matrizTriangularSuperior = criarMatrizTriangularSuperior(matrizOriginal,matrizTriangularInferior);

        double[][] matrizAuxiliar = new double[LINHAS_MATRIZ_REDUZIDA][LINHAS_MATRIZ_REDUZIDA];
        double[][] matrizInversa = new double[LINHAS_MATRIZ_REDUZIDA][LINHAS_MATRIZ_REDUZIDA];

        for(int coluna = 0; coluna < LINHAS_MATRIZES ;coluna++) {
            forwardSubstituition(matrizTriangularInferior, matrizAuxiliar, identidade, coluna);
            backwardSubstituition(matrizTriangularSuperior, matrizAuxiliar, matrizInversa, coluna);
        }


        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.println(matrizInversa[i][j]);
            }
        }


    }

    public static double[][] lerMatrizTransicao (String fileInTotal) throws FileNotFoundException{
        Scanner in = new Scanner(new File(fileInTotal));
        double[][] matrizTransicao = new double[LINHAS_MATRIZ_TRANSICAO][COLUNAS_MATRIZ_TRANSICAO];
        int numLinha = 0, numColuna = 0;

        while (in.hasNext()){
            String linha = in.nextLine();
            if (!linha.equals("")){
                String[] linhaSplit = linha.split("=");
                matrizTransicao[numLinha][numColuna] = Double.parseDouble(linhaSplit [1]);
                numColuna++;
            }else{
                numLinha++;
                numColuna = 0;
            }
        }
        return matrizTransicao;
    }

    public static double[][] criarMatrizSemEstadoAbsorvente(double[][] matrizTransicao) {
        double[][] matrizReduzida = new double[LINHAS_MATRIZES][COLUNAS_MATRIZES];

        for (int i = 0; i < LINHAS_MATRIZES; i++) {
            for (int j = 0; j < COLUNAS_MATRIZES; j++) {
                matrizReduzida[i][j] = matrizTransicao[i][j];
            }
        }
        return matrizReduzida;
    }

    public static double[][] subtrairDuasMatrizes(double[][] arr1, double[][] arr2) {
        double[][] matrizResultado = new  double[arr1.length][arr1[0].length];
        for (int i = 0; i < matrizResultado.length; i++) {
            for (int j = 0; j < matrizResultado[0].length; j++) {
                matrizResultado[i][j] = arr2[i][j] - arr1[i][j];
            }
        }
        return matrizResultado;
    }

    public static double[][] criarMatrizTriangularSuperior(double[][] matrizOriginal, double[][] matrizTrinagularInferior) {
        double pivot;
        int i, j, linhaPivot, linha;
        double divisor = 0;

        for (j = 0; j < COLUNAS_MATRIZES; j++) {
            criarMatrizTriangularInferior(j, matrizTrinagularInferior, divisor, 0);
            i = 1;
            pivot = matrizOriginal[j][j];
            linhaPivot = j;
            while (j + i < LINHAS_MATRIZES) {
                linha = j + i;
                divisor = matrizOriginal[linha][j] / pivot;
                metodoDeGauss(matrizOriginal, divisor, linha, j, linhaPivot);
                criarMatrizTriangularInferior(j, matrizTrinagularInferior, divisor, linha);
                i++;
            }
        }
        return matrizOriginal;
    }

    public static void metodoDeGauss(double[][] matrizTrinagularSuperior, double divisor, int linha, int j, int linhaPivot) {
        while (j < COLUNAS_MATRIZES) {
            matrizTrinagularSuperior[linha][j] = (-divisor * (matrizTrinagularSuperior[linhaPivot][j])) + matrizTrinagularSuperior[linha][j];
            j++;
        }
    }

    public static void criarMatrizTriangularInferior(int j, double[][] matrizTrinagularInferior, double divisor, int linha) {
        matrizTrinagularInferior[j][j] = 1;
        if(linha > j) {
            matrizTrinagularInferior[linha][j] = divisor;
        }
    }

    public static double[][] criarMatrizIdentidade() {
        double[][] identidade = new double[LINHAS_MATRIZES][COLUNAS_MATRIZES];
        for (int i = 0; i < LINHAS_MATRIZES; i++) {
            identidade[i][i] = 1;
        }
        return identidade;
    }

    public static void forwardSubstituition(double[][] matrizTrinagularInferior, double[][] matrizAuxiliar, double [][] Identidade, int coluna) {
        int i = 0;
        int j;
        double soma;
        matrizAuxiliar[i][coluna] = ((Identidade[i][coluna])/matrizTrinagularInferior[i][0]);
        i = 1;
        while (i < LINHAS_MATRIZES) {
            soma = 0;
            j = 0;
            while (j < i) {
                soma += (matrizAuxiliar[j][coluna] * matrizTrinagularInferior[i][j]);
                j++;
            }
            matrizAuxiliar[i][coluna] = Identidade[i][coluna] - soma;
            i++;
        }
    }

    public static void backwardSubstituition(double[][] matrizTrinagularSuperior, double[][]matrizAuxiliar, double[][]matrizInversa, int coluna){
        int i = LINHAS_MATRIZES - 1;
        int j;
        double soma;
        matrizInversa[i][coluna] = ((matrizAuxiliar[i][coluna])/matrizTrinagularSuperior[i][LINHAS_MATRIZES -1]);
        i--;
        while(i >= 0){
            soma = 0;
            j = LINHAS_MATRIZES - 1;
            while (j > i) {
                soma += (matrizInversa[j][coluna] * matrizTrinagularSuperior[i][j]);
                j--;
            }
            soma = matrizAuxiliar[i][coluna] - soma;
            soma/= matrizTrinagularSuperior[i][j];
            matrizInversa[i][coluna] = soma;
            i--;
        }
    }

    public static double[][] multiplicarMatrizes (double[][] arr1, double [][] arr2){
        double[][] matrizResultado = new double[arr1.length][arr2[0].length];

        for (int i = 0; i < matrizResultado.length; i++) {
            for (int j = 0; j < matrizResultado[0].length; j++) {
                matrizResultado[i][j] = calcularElemento(arr1[i],criarArrayColuna(arr2,j));
            }
        }
        return matrizResultado;
    }

    public static double[] mutiplicarMatrizPorVetor (double [][] arr1, double[] arr2){
        double[] matrizResultado = new double[arr1.length];
        for (int i = 0; i < arr1.length; i++) {
            matrizResultado [i] = calcularElemento(arr1[i],arr2);
        }
        return matrizResultado;
    }

    public static double[] mutiplicarVetorPorMatriz (double [] arr1, double[][] arr2){
        double[] matrizResultado = new double[arr2[0].length];
        for (int i = 0; i < arr1.length; i++) {
            matrizResultado [i] = calcularElemento(arr1,criarArrayColuna(arr2,i));
        }
        return matrizResultado;
    }

    public static double[] criarArrayColuna (double[][] arr1, int numColuna){
        double[] coluna = new double[arr1[0].length];
        for (int i = 0; i < arr1[0].length; i++) {
            coluna [i] = arr1[i][numColuna];
        }
        return coluna;
    }

    public static double calcularElemento(double[] arr1, double [] arr2) {
        double soma = 0;
        for (int k = 0; k < arr2.length ; k++) {
            soma = soma + (arr1[k] * arr2[k] );
        }

        return soma;
    }


}

