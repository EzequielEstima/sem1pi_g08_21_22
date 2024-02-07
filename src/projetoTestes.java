import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class projetoTestes {
    static Scanner ler = new Scanner(System.in);
    public static void main(String[] args) throws FileNotFoundException {
        boolean sairDocumento;
        boolean sairPrograma = false;
        do {
            System.out.println("Deseja (escreva o número da funcionalidade):");
            System.out.println("1 - Introdução de Ficheiros");
            System.out.println("2 - Analizar e vizualizar os dados de COVID-19 num intervalo de tempo.");
            System.out.println("3 - Comparar os dados de COVID-19 de dois intervalos de tempo.");
            System.out.println("4 - sair.");
            int funcionalidade = lerFuncionalidade();

            switch (funcionalidade){
                case 1:
                    System.out.println("nao sei");

                    break;
                case 2:
                    System.out.println("ja sei");
                    break;
                case 3:
                    System.out.println("ya meu");
                    break;
                case 4:
                    sairPrograma = true;
            }

        }while (!sairPrograma);
    }

    public static int lerFuncionalidade (){
        int funcionalidade = ler.nextInt();
        while (funcionalidade!=1 && funcionalidade!=2 && funcionalidade!=3 && funcionalidade!=4){
            System.out.println("Funcionalidade não existe. Por forvor insira novamente.");
            funcionalidade = ler.nextInt();
        }
        return funcionalidade;
    }

    public static String lerStringVoltarMenuInicial(){
        String string = ler.nextLine();
        while (!string.equals("s") && !string.equals("n")){
            System.out.println("Resposta não conhecida. Por forvor insira novamente.");
            string = ler.nextLine();
        }
        return string;
    }
}
