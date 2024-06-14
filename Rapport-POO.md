---
title: Rapport Saé 201-202 
autor:
> Thomas Smeeckaert
> Milan Theron
> Ylies Ertam
date: 18-06-2024
---

# V1

La première version de cette application doit répondre aux éxigences suivante :
- Récupération des données.
- Vérification des données.
- Calculer les meilleurs voyages possible.
- Exclure les voyages qui dépasse la limite donner par l'utilisateur.

Grace au 2 package suivant nous avons pus satisfaire ces éxigences.

Voici le diagrame UML de cette application pour la V1
![Diagram UML V1](res/Images/Diagram_complet_UML_V1.png)

## Package App


### Plateforme.java

![Class Plateforme.java](res/Images/Class_Plateforme.png)

La classe principale du projet, regroupant tout les paramètres importants et utiles à la constitution du graphe.
   
### Voyageur.java

![Class Voyageur.java](res/Images/Class_Voyageur.png)

Un voyageur est identifiable via un numéro automatique,
il a un lieu de départ et un lieu d'arrivé pour décrire son trajet "favorie".  
Nous pouvons redéfinir ces lieux en fonction du besoin de l'utilisateur.

## Package graph

### MonLieu.java

![Class MonLieu.java](res/Images/Class_MonLieu.png)

La class MonLieu est l'implémentation de l'interface Lieu venant de la librérie **sae_s2_2024.jar**.
Un Lieu est constitué que d'un nom qui n'est pas senser changé.

La methode *equals()* ce base sur le nom pour comparé 2 villes.

La methode *toString()* nous renvoie l'affichage tel que : < Modalité > --> < depart >,< arrivé >

### MonTroncon.java

![Class MonTroncon.java](res/Images/Class_MonTroncon.png)

La class MonTroncon est l'implémentation de l'interface Trancon venant de la librérie **sae_s2_2024.jar**.
Un Troncon est constitué d'un Lieu de départ, un Lieu d'arrivé, d'une Moadalité de Transport et d'une table associative des types de coûts avec leurs valeurs passer en paramètre lors de ça création.  
Les lieux ainsi que la modalité sont des constantes. Si un troncon doit être changer par sa modalité ou son lieu (départ ou arrivé) il devra être supprimé initialement et ré-instancié.

La methode *toString()* nous renvoie l'affichage tel que : < Modalité > --> < depart >,< arrivé >


# V2

### CSVUtil.java

![Class CSVUtil.java]()

Cette classe a pour unique but de gérer toute actions liées aux fichiers, à savoir le chargement des fichiers via les methodes *"importCSV()"* et *"importCSV(File)"*
Le methode *"afficheListFile(File[])"* prend en paramètre un tableau de *File*, provenant du main elle doit simplement afficher tout les fichiers disponible dans un répertoir prédéfinie.  
Ce répertoir est le **res** puis le sous répertoir **Data** dans ce dépot git.

# V3