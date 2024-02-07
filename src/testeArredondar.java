

public class testeArredondar {

    static final int LINHAS_MATRIZES = 4;
    static final int COLUNAS_MATRIZES = 4;
    public static void main(String[] args) {

        double[][] matrizOriginal = {{1,-2,3},{0,-1,4},{-2,2,0},};
        double[][] matrizTriangularSuperior = new double[matrizOriginal.length][matrizOriginal[0].length];
        double[][] matrizTriangularInferior = new double[matrizOriginal.length][matrizOriginal[0].length];
        double[][] inversaMatrizTriangularInferior = new double[LINHAS_MATRIZES][COLUNAS_MATRIZES];
        double[][] inversaMatrizTriangularSuperior = new double[LINHAS_MATRIZES][COLUNAS_MATRIZES];

        // preencher a matriz U com os valores conhecidos
        preencherDiagonalPrincipalCom1(matrizTriangularSuperior);

        //cáculo das matrizes L e U
        int contador = 0, numColuna=0;
        while (contador < matrizOriginal.length){
            calcularColunaMatrizTriangularInferior(contador,numColuna,matrizOriginal,matrizTriangularSuperior,matrizTriangularInferior);
            calcularLinhaMatrizTriangularSuperior(contador,numColuna,matrizOriginal,matrizTriangularSuperior,matrizTriangularInferior);
            contador++;
            numColuna++;

        }

        //cáculo das matrizes inversas de L e U
        double[][]identidade =  criarMatrizIdentidade();
        int iInferior = 0;
        int iSuperior = LINHAS_MATRIZES -1;
        for(int coluna = 0; coluna < LINHAS_MATRIZES ;coluna++) {
            inversaMatrizTriangularInferior(matrizTriangularInferior, identidade, coluna, inversaMatrizTriangularInferior, iInferior);
            iInferior++;
        }
        preencherDiagonalPrincipalCom1(inversaMatrizTriangularSuperior);
        for (int coluna = 1; coluna < LINHAS_MATRIZES ; coluna ++){
            inversaMatrizTriangularSuperior(matrizTriangularSuperior, identidade, coluna, inversaMatrizTriangularSuperior, iSuperior);
        }

        //cálculo da matriz inversa
        double[][] matrizInversa = multiplicarMatrizes(inversaMatrizTriangularSuperior,inversaMatrizTriangularInferior);
    }

    public static void calcularColunaMatrizTriangularInferior(int contador, int numColuna, double[][] matrizOriginal, double[][] matrizU, double[][] matrizL){
        for (int i = contador; i < matrizU.length; i++) {
            matrizL[i][numColuna] = (calcularNumerador(numColuna,i,matrizOriginal,matrizU,matrizL))/ matrizU[numColuna][numColuna];

        }

    }

    public static double calcularNumerador( int numColuna, int numLinha, double[][] matrizOriginal, double[][] matrizU, double[][] matrizL ){
        double soma = matrizOriginal[numLinha][numColuna];

        for (int i = 0; i < matrizOriginal.length; i++) {
            if (matrizU[i][numColuna] != 0 && i!=numColuna){
                soma = soma - (matrizL[numLinha][i]*matrizU[i][numColuna]);
            }
        }

        return soma;
    }

    public static void calcularLinhaMatrizTriangularSuperior(int contador,int numLinha,double[][] matrizOriginal,double[][] matrizU, double[][] matrizL){
        for (int i = contador+1; i < matrizU.length; i++) {
            matrizU[numLinha][i] = (calcularNumerador(i,numLinha,matrizOriginal,matrizU,matrizL))/matrizL[numLinha][numLinha];
        }
    }

    public static double[][] criarMatrizIdentidade() {
        double[][] identidade = new double[LINHAS_MATRIZES][COLUNAS_MATRIZES];
        for (int i = 0; i < LINHAS_MATRIZES; i++) {
            identidade[i][i] = 1;
        }
        return identidade;
    }

    public static void inversaMatrizTriangularInferior(double[][]matrizTrinagularInferior, double[][]identidade, int coluna, double[][] inversaMatrizTriangularInferior, int i){
        int j;
        double soma;
        inversaMatrizTriangularInferior[i][coluna] = ((identidade[i][coluna])/matrizTrinagularInferior[i][i]);
        i++;
        while (i < LINHAS_MATRIZES) {
            soma = 0;
            j = 0;
            while (j < i) {
                soma += (inversaMatrizTriangularInferior[j][coluna] * matrizTrinagularInferior[i][j]);
                j++;
            }
            soma = identidade[i][coluna] - soma;
            soma /= matrizTrinagularInferior[i][j];
            inversaMatrizTriangularInferior[i][coluna] = soma;
            i++;
        }
    }

    public static void preencherDiagonalPrincipalCom1(double[][] inversaMatrizTriangularSuperior){
        for (int i = 0; i < LINHAS_MATRIZES; i++) {
            inversaMatrizTriangularSuperior[i][i] = 1;
        }
    }

    public static void inversaMatrizTriangularSuperior(double[][]matrizTrinagularSuperior, double[][]identidade, int coluna, double[][] inversaMatrizTriangularSuperior, int i){
        int j;
        double soma;
        i--;
        while(i >= 0){
            soma = 0;
            j = LINHAS_MATRIZES - 1;
            while (j > i) {
                soma += (inversaMatrizTriangularSuperior[j][coluna] * matrizTrinagularSuperior[i][j]);
                j--;
            }
            inversaMatrizTriangularSuperior[i][coluna] = identidade[i][coluna] - soma;
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
