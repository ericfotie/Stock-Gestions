🛒 Microservices E-Commerce System

Architecture microservices développée avec Spring Boot 3, Kafka, PostgreSQL et Swagger (OpenAPI).

Ce projet simule un système simple de gestion de commandes avec communication asynchrone entre services.

🏗️ Architecture

Le projet contient 3 microservices :

1️⃣ User-Service

Gestion des utilisateurs

Publication d’un événement CommandSubmittedEvent vers Kafka

2️⃣ Commande-Service

Gestion des commandes

Écoute l’événement CommandSubmittedEvent

Envoie un événement ProductCheckEvent vers Product-Service

Écoute ProductAvailabilityEvent

Met à jour le statut de la commande

3️⃣ Product-Service

Gestion des produits

Vérifie le stock

Réduit le stock si disponible

Envoie ProductAvailabilityEvent vers Commande-Service

🔄 Communication Kafka
📌 Flux global :

User-Service → command-topic

Commande-Service → product-check-topic

Product-Service → product-availability-topic

Commande-Service met à jour le statut :

CONFIRMED si stock disponible

REJECTED si stock insuffisant

🛠️ Technologies utilisées

Java 17+

Spring Boot 3

Spring Data JPA

PostgreSQL

Apache Kafka

Spring Kafka

Lombok

Swagger / OpenAPI (Springdoc)

🗄️ Bases de données

Chaque service possède sa propre base :

Service	Base de données
User-Service	UserDB
Commande-Service	CmdDB
Product-Service	ProductDB
⚙️ Configuration
📌 Kafka (localhost)
spring.kafka.bootstrap-servers=localhost:9092
📌 Topics utilisés
command-topic
product-check-topic
product-availability-topic
