package App;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
        } catch (FileNotFoundException e){
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            System.out.println("Un problème est survenu avec le fichier, vérifier son contenu puis réessayer.");
            e.printStackTrace();
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
}
