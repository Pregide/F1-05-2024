package App;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class CSVUtil {
    public final static String DEFAULT_PATH = "res" + System.getProperty("file.separator");
    private final static String DEFAULT_FILE = "DefaultData.csv";

    public static ArrayList<String> importCSV(File file){
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            ArrayList<String> data = new ArrayList<String>();
            String str = "";
            while ((str = br.readLine()) != null){
                data.add(str);
            } 
            return data;
        } catch (IOException e) {
            System.out.println("Un problème est survenu avec le fichier, vérifier l'emplacement du fichier et son contenu puis réessayer.");
            System.exit(1);
        } 
        return null;
    }

    public static ArrayList<String> importCSV(){
        return CSVUtil.importCSV(new File(DEFAULT_PATH + DEFAULT_FILE));
    }

    public static void afficheListFiles(File[] files){
        int i=1;
        for(File file : files){
            System.out.println(i + ". " + file.getName());
            i++;
        }
    }

    public static ArrayList<String> selectionFile() {
        File[] files = new File(DEFAULT_PATH).listFiles();
        System.out.println("Voici la liste des fichiers ce trouvant dans le dossier : " + System.getProperty("user.dir") + System.getProperty("file.separator") + DEFAULT_PATH);
        CSVUtil.afficheListFiles(files);
        try {
            System.out.println("\nVeuillez indiquer l'indice de votre fichier");
            return CSVUtil.importCSV(files[Main.sc.nextInt()-1]);
        } catch (InputMismatchException e) {
            System.out.println("Wrong Input Match : Default file selected");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Wrong Index : Default file selected");
        }
        return CSVUtil.importCSV();
    }
}
