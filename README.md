# Projet de Gestion de Comptes avec CQRS
Ce projet implémente le modèle **CQRS (Command Query Responsibility Segregation)** pour la gestion de comptes, avec **des événements pour la Création de Compte, Activation, Crédit et Débit**.
Il propose des API REST permettant d'interagir avec les comptes, et utilise un **Event Store** pour suivre toutes les modifications et activités sur chaque compte.

## Aperçu du Projet
Le projet gère les opérations typiques de gestion de compte à travers une approche CQRS, séparant **les commandes (Création, Activation, Crédit, Débit)** de la lecture des données, qui s'appuie sur l’Event Store pour refléter l’état actuel des comptes.

### Fonctionnalités :
**1. Création de Compte :** Initialisation d'un compte avec un identifiant unique et un solde initial.  
**2. Activation de Compte :** Activation du compte après sa création.  
**3. Crédit de Compte :** Ajout de fonds au compte.  
**4. Débit de Compte :** Retrait de fonds si le solde le permet.  
**5. Event Store :** Journalisation de chaque événement lié aux changements de compte.  

--- 

## Vue d'Ensemble des Endpoints
Le projet utilise plusieurs endpoints REST, accessibles via **Postman**, pour gérer les actions sur les comptes. Ces endpoints sont décrits ci-dessous, avec des images pour illustrer les requêtes et réponses réussies.

### Prérequis
* **Postman :** Pour interagir avec les endpoints.
* **Base de Données MySQL :** Nommée bank pour stocker les événements liés aux comptes.
* **Event Store :** Pour stocker et récupérer les événements des comptes.

### Structure du Projet
* **Commandes :** Les actions qui modifient l’état, comme **la création, l’activation, le crédit et le débit d’un compte**.
* **Requêtes (Queries) :** Récupèrent **l’état actuel** des comptes en interrogeant l’Event Store.
* **Event Store :** Stocke tous **les événements** liés aux actions de compte.

---

## Utilisation

**1. Création de Compte**
* **Endpoint :** POST /account/create
* **Description :** Crée un compte avec un solde initial spécifié.

![image](https://github.com/user-attachments/assets/c7885e4c-6a6e-4ff0-987b-740b6dfbcdaa)

**2. Journal d'Événements dans la Base de Données MySQL**
* **Description :** Après la création d’un compte, l'événement est enregistré dans la base de données MySQL sous la table **domain_name_entry**, en journalisant **l'événement "AccountCreated"** et tous les autres événements associés au compte.

![image](https://github.com/user-attachments/assets/d79281a7-a4a8-45dc-906f-0077faacef4f)

**3. Event Store - Événements de Création et d'Activation de Compte**
* **Endpoint :** GET /eventStore
* **Description :** Récupère les événements dans l'Event Store, affichant **l'événement AccountCreated suivi de AccountActivated**.

![image](https://github.com/user-attachments/assets/22e93a90-8b26-4c4c-afc6-aed85e868da8)

**4. Crédit de Compte**
* **Endpoint :** POST /account/credit
* **Description :** Crédite le compte d'un montant spécifié.

![image](https://github.com/user-attachments/assets/34b7b797-b3e1-442e-88bc-7372382c78bc)
![image](https://github.com/user-attachments/assets/a7a77b3f-8f6a-4624-8f8b-c054908ea800)

**5. Débit de Compte**
* **Endpoint :** POST /account/debit
* **Description :** Débite le compte d'un montant spécifié.

![image](https://github.com/user-attachments/assets/2b9dd8a8-def4-4729-9965-98342ec60933)
![image](https://github.com/user-attachments/assets/6d9897d5-e5a4-4987-b0dd-45d58f2e4f6b)

**6. Débit Échoué en Raison d’un Solde Insuffisant**
* **Endpoint :** POST /account/debit
* **Description :** Tente de débiter un montant supérieur au solde actuel. La requête échoue en raison de fonds insuffisants.

![image](https://github.com/user-attachments/assets/346f7024-735c-42cd-b9b9-1b99612d6377)
![image](https://github.com/user-attachments/assets/87656085-6546-4f82-928b-911c387a85a2)

**7. Création de Comptes Multiples**
* **Description :** Démontre la création de plusieurs comptes, enregistrée dans la table account de MySQL.

![image](https://github.com/user-attachments/assets/4b53be5a-b1c7-4014-a3cb-f5b675f32235)
![image](https://github.com/user-attachments/assets/435afa5c-0551-47ed-9371-7d2267490c38)

---

### Structure de la Base de Données
* **Base de Données :** bank
  * **Tables :**
     * **Account** : Stocke les informations des comptes.
     * **domain_name_entry** : Enregistre chaque événement de compte, tel que la création, l'activation, le crédit et le débit

## Conclusion
Ce projet de gestion de comptes avec CQRS démontre les principes de la conception basée sur les événements pour gérer les comptes et enregistrer les événements.
Chaque opération sur un compte génère un événement, qui est stocké dans l’Event Store et la base de données MySQL, fournissant un historique détaillé et assurant la cohérence dans l’application.







