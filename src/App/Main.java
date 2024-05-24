package App;

import java.util.Scanner;

import graph.TypeCout;

public class Main {
    public final static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        Plateforme p = new Plateforme(new String[]{"villeA;villeB;Train;60;1.7;80",
                                                    "villeB;villeD;Train;22;2.4;40",
                                                    "villeA;villeC;Train;42;1.4;50",
                                                    "villeB;villeC;Train;14;1.4;60",
                                                    "villeC;villeD;Avion;110;150;22",
                                                    "villeC;villeD;Train;65;1.2;90"});
        Voyageur v = new Voyageur(null, null);
        
        System.out.println("Quelle critère voulez vous privilégié ?");
        System.out.println("1. Pollution");
        System.out.println("2. Temps");
        System.out.println("3. Prix");

        int choice = sc.nextInt()-1;
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
