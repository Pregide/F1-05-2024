package App;

import java.util.Scanner;

import graph.TypeCout;

public class Main {
    public final static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        Voyageur v = new Voyageur(null, null);

        System.out.println("Avant de commencer, assurez vous d'avoir fourni votre csv dans le dossier \"res\"?");

        //ajout une boucle for qui liste tout les fichiers dans res, ex "1) fichier 1 \n 2) fichier 2" etc...

        System.out.println("Veuillez indiquer l'indice de votre fichier");
        int choice = sc.nextInt()-1;
        String fichierName=""; //get fichier name from indice
        Plateforme p=new Plateforme(fichierName); //remplacer fichier name par Scanner du contenue de /res/FichierName.csv
        
        System.out.println("Quelle critère voulez vous privilégié ?");
        System.out.println("1. Pollution");
        System.out.println("2. Temps");
        System.out.println("3. Prix");

        choice = sc.nextInt()-1;
        p.changeCritère(TypeCout.values()[choice]);
        System.out.println("Critère sélectionné : " + TypeCout.values()[choice]);

        System.out.println("\nLieu de depart possible :");
        System.out.println(p.afficheListLieu());
        choice = sc.nextInt()-1;
        System.out.println("Lieu choisi : " + p.getLieu(choice));
        v.setdepart(p.getLieu(choice));

        System.out.println("Lieu d'arrivé possible :");
        System.out.println(p.afficheListLieu());
        choice = sc.nextInt()-1;
        System.out.println("Lieu choisi : " + p.getLieu(choice));
        v.setArrive(p.getLieu(choice));

        System.out.println("\nChemin possible : ");
        System.out.println(p.toString(v, 10));
    }
}
