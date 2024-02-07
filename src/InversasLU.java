public class InversasLU {
    static final int LINHAS_MATRIZES = 4;
    static final int COLUNAS_MATRIZES = 4;
    public static void main(String[] args) {

        double[][] matrizTriangularSuperior = {{1,9,4,2}, {0,1,4,5}, {0,0,1,9}, {0,0,0,1}};
        double[][] matrizTriangularInferior = {{7,0,0, 0}, {5, 4, 0, 0}, {1,5,3, 0}, {3, 4, 3, 2}};
        double[][] inversaMatrizTriangularInferior = new double[LINHAS_MATRIZES][COLUNAS_MATRIZES];
        double[][] inversaMatrizTriangularSuperior = new double[LINHAS_MATRIZES][COLUNAS_MATRIZES];
        double[][]identidade =  criarMatrizIdentidade();
        int iInferior = 0;
        int iSuperior = LINHAS_MATRIZES -1;
        for(int coluna = 0; coluna < LINHAS_MATRIZES ;coluna++) {
            inversaMatrizTriangularInferior(matrizTriangularInferior, identidade, coluna, inversaMatrizTriangularInferior, iInferior);
            iInferior++;
        }

        preencherInversaMatrizTriangularSuperiorCom1(inversaMatrizTriangularSuperior);
        for (int coluna = 1; coluna < LINHAS_MATRIZES ; coluna ++){
            inversaMatrizTriangularSuperior(matrizTriangularSuperior, identidade, coluna, inversaMatrizTriangularSuperior, iSuperior);
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

    public static void preencherInversaMatrizTriangularSuperiorCom1(double[][] inversaMatrizTriangularSuperior){
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
}
