package App;

import java.util.ArrayList;
import java.util.Scanner;

import graph.TypeCout;

public class Main {
    public final static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        Voyageur v = new Voyageur(null, null);
        Plateforme p;

        try {
            p = new Plateforme(CSVUtil.selectionFile());
        } catch (NullPointerException e){
            p = new Plateforme(new ArrayList<>());
        }
        
        int choice;
        System.out.println("\nQuelle critère voulez vous privilégié ?");
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

        try {
            System.out.println("\nChemin possible : ");
            System.out.println(v.trajet(p.getGraphe(), 10));
        } catch (NoTravelFindException e){}
    }
}
