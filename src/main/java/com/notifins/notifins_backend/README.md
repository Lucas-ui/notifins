Domain coeur de l'application, là ou il ya les entités c'est-à-dire la logique pure de l'app
Application c'est ce qui orchestre la logique métier, les services ou use cases => utilise et dépend du domain
Infrastructure tout ce qui touche à ce qui est à l'extérieur, repositories JPA, bdd => se sert de application ou domain
Interface, couche frontale c'est à dire les controllers REST, l'UI, ...

Exemple de flux :
Requete http arrive dans le controller (interface)
le controller appele le service (dans application)
le service fait apppel aux entités / logique métier (domain)
si besoin, service demande à infrastructure d'accéder à la BDD
=> les données remontent à travers les couches jusqu'au controller pour etre retournées
