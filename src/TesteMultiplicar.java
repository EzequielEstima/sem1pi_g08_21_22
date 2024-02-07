public class TesteMultiplicar {

    public static void main(String[] args) {
        double[] arrA = {35.4,76.9,141.7,229.0,338.1,468.1,618.4};
        double[] arrB = {};
        double[] arrEsperado = {21,14,10,15,14,25,17};
        double erro = calcularErro(arrA,arrEsperado);
        System.out.println(erro);
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

    public static double calcularErro (double[] arr1, double[] arrEsperado){
        double soma = 0;
        for (int i = 0; i < arr1.length; i++) {
            soma = soma + diferencaEntreDoisDias(arrEsperado[i],arr1[i]);
        }
        return soma;
    }

    public static double diferencaEntreDoisDias(double num1, double num2){
        double diff = num1-num2;
        diff = Math.abs(diff);
        return diff;
    }
}
