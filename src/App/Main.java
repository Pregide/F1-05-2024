package App;

import java.io.File;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import App.exception.NoTravelFoundException;
import graph.TypeCout;

public class Main {
    public final static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        Voyageur v = new Voyageur(null, null);
        Plateforme p;
        int choice;


        File[] files = new File(CSVUtil.DEFAULT_PATH).listFiles();
        ArrayList<String> data;
        System.out.println("Voici la liste des fichiers ce trouvant dans le dossier : " + System.getProperty("user.dir") + System.getProperty("file.separator") + CSVUtil.DEFAULT_PATH);
        CSVUtil.afficheListFiles(files);
        try {
            System.out.println("\nVeuillez indiquer l'indice de votre fichier");
            data = CSVUtil.importCSV(files[sc.nextInt()-1]);
        } catch (InputMismatchException e) {
            System.out.println("Wrong Input Match : Default file selected");
            data = CSVUtil.importCSV();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Wrong Index : Default file selected");
            data = CSVUtil.importCSV();
        }
        
        try {
            p = new Plateforme(data);
        } catch (NullPointerException e){
            p = new Plateforme(new ArrayList<>());
        }
        
        try {
            
            System.out.println("\nQuelle critère voulez vous privilégié ?");
            System.out.println("1. Pollution");
            System.out.println("2. Temps");
            System.out.println("3. Prix");

            choice = sc.nextInt()-1;
            p.changeCritère(TypeCout.values()[choice]);
            System.out.println("Critère sélectionné : " + TypeCout.values()[choice]);
        } catch (InputMismatchException e){
            System.out.println("Wrong Input Format : Default selection 1");
            p.changeCritère(TypeCout.values()[0]);
        } catch (IndexOutOfBoundsException e){
            System.out.println("Wrong Index : Default selection 1"); e.getMessage();
            p.changeCritère(TypeCout.values()[0]);
        }

        try {
            System.out.println("\nLieu de depart possible :");
            System.out.println(p.afficheListLieu());
            choice = sc.nextInt()-1;
            System.out.println("Lieu choisi : " + p.getLieu(choice));
            v.setdepart(p.getLieu(choice));
        } catch (InputMismatchException e){
            System.out.println("Wrong Input Format : Default selection 1");
            v.setdepart(p.getLieu(0));
        } catch (IndexOutOfBoundsException e){
            System.out.println("Wrong Index : Default selection 1");
            v.setdepart(p.getLieu(0));
        }

        try {
            System.out.println("Lieu d'arrivé possible :");
            System.out.println(p.afficheListLieu());
            choice = sc.nextInt()-1;
            System.out.println("Lieu choisi : " + p.getLieu(choice));
            v.setArrive(p.getLieu(choice));
        } catch (InputMismatchException e){
            System.out.println("Wrong Input Format : Default selection 1");
            v.setArrive(p.getLieu(0));
        } catch (IndexOutOfBoundsException e){
            System.out.println("Wrong Index : Default selection 1"); e.getMessage();
            v.setArrive(p.getLieu(0));
        }        

        try {
            System.out.println("\nChemin possible : ");
            System.out.println(v.trajet(p.getGraphe(), 3));
        } catch (NoTravelFoundException e){}
    }
}
