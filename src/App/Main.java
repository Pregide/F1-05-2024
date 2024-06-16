package App;

import java.io.File;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import App.exception.NoTravelFoundException;
import fr.ulille.but.sae_s2_2024.Lieu;
import fr.ulille.but.sae_s2_2024.ModaliteTransport;
import graph.TypeCout;

public class Main {
    public final static Scanner sc = new Scanner(System.in);
    private static Plateforme p = null;
    private static int choice = 0;

    public static void main(String[] args) {  
        //Jeu de données a utilisé    
        try {
            p = new Plateforme(CSVUtil.importCSV(Main.selectFile()));
        } catch (InputMismatchException | IndexOutOfBoundsException e) {
            System.out.println("Wrong Input : Default File Selected");
            p = new Plateforme(CSVUtil.importCSV());
        }

        //Select du lieu de départ
        Lieu depart;
        try {
            System.out.println(System.getProperty("line.separator") + "Choisissez un Lieu de départ possible :");
            depart = Main.getLieu();
        } catch (InputMismatchException | IndexOutOfBoundsException e){
            System.out.println("Wrong Input : Default selection 1");
            depart = p.getLieu(0);
        }

        //Selection du lieu d'arrivée
        Lieu arrive;
        try {
            System.out.println(System.getProperty("line.separator") + "Choisissez un Lieu d'arrivée possible :");
            arrive = Main.getLieu();
        } catch (InputMismatchException | IndexOutOfBoundsException e){
            System.out.println("Wrong Input : Default selection 1");
            arrive = p.getLieu(0);
        }

        //Choix Modalité(s)
        List<ModaliteTransport> listModa;
        try {
            listModa = Main.choixModa();
        } catch (InputMismatchException | IndexOutOfBoundsException e){
            System.out.println("Wrong Input : Default selection 1");
            listModa = Main.tousModa();
        }

        //Choix critère(s)
        List<TypeCout> listCrit;
        try {
            listCrit = Main.choixCout();
        } catch (InputMismatchException | IndexOutOfBoundsException e){
            System.out.println("Wrong Input : Default selection 1");
            listCrit = Main.tousCout();
        }
        p.changeCritère(listCrit);

        //limitation
        int limit = 1000;
        try {
            System.out.print(System.getProperty("line.separator") + "Définissez une limite :");
            limit = sc.nextInt();
        } catch (InputMismatchException e) {
        }

        //Afficher trajet
        try {
            System.out.println(System.getProperty("line.separator") + "Chemin possible :");
            for (String str : p.trajet(depart, arrive, listModa, 5, limit)) {
                System.out.println(str);
            }
            
        } catch (NoTravelFoundException e){
            e.getMessage();
        }

        //Enregistrer le trajet
    }

    private static File selectFile(){
        File[] files = new File(CSVUtil.DEFAULT_PATH).listFiles();
        System.out.println("Voici la liste des fichiers ce trouvant dans le dossier : " + System.getProperty("user.dir") + System.getProperty("file.separator") + CSVUtil.DEFAULT_PATH);
        CSVUtil.afficheListFiles(files);
        System.out.println(System.getProperty("line.separator") + "Veuillez indiquer l'indice de votre fichier");
        System.out.print(">>> ");
        return files[sc.nextInt()-1];
    }

    private static Lieu getLieu(){
        System.out.println(p.afficheListLieu());
        System.out.print(">>> ");
        choice = sc.nextInt()-1;
        System.out.println("Lieu choisi : " + p.getLieu(choice));
        return p.getLieu(choice);
    }

    private static List<ModaliteTransport> choixModa(){
        System.out.println(System.getProperty("line.separator") +"Choisissez la/les modalité(s) à utiliser :");
        System.out.println("1) All");
        int i=2;
        for (ModaliteTransport moda : ModaliteTransport.values()) {
            System.out.println(i + ") " + moda);
            i++;
        }
        System.out.print(">>> ");
        choice = sc.nextInt();
        if(choice == 1) return Main.tousModa();
        ArrayList<ModaliteTransport> res = new ArrayList<>();
        res.add(ModaliteTransport.values()[choice-2]);
        System.out.println("Saisissez -1 pour quitter ou une autre modalité");
        System.out.print(">>> ");
        choice = sc.nextInt();
        if(choice == -1) return res;
        res.add(ModaliteTransport.values()[choice-2]);
        return res;
    }

    private static List<ModaliteTransport> tousModa(){
        ArrayList<ModaliteTransport> res = new ArrayList<>();
        for (ModaliteTransport moda : ModaliteTransport.values()) {
            res.add(moda);
        }
        return res;
    }

    private static List<TypeCout> tousCout(){
        ArrayList<TypeCout> res = new ArrayList<>();
        for (TypeCout cout : TypeCout.values()) {
            res.add(cout);
        }
        return res;
    }

    private static List<TypeCout> choixCout(){
        System.out.println(System.getProperty("line.separator") + "Choisissez le critère prioritère");        
        int i=1;
        for (TypeCout cout : TypeCout.values()) {
            System.out.println(i + ") " + cout);
            i++;
        }
        i=5;
        System.out.print(">>> ");
        choice = sc.nextInt();
        i -= choice;
        ArrayList<TypeCout> res = new ArrayList<>();
        res.add(TypeCout.values()[choice-1]);
        System.out.println("Saisissez le deuxième critère");
        System.out.print(">>> ");
        choice = sc.nextInt();
        i -= choice;
        res.add(TypeCout.values()[choice-1]);
        res.add(TypeCout.values()[i]);
        return res;
    }
}
