# Agenda Condivisa - Backend Infrastructure

## Descrizione del Progetto
Questo repository contiene l'infrastruttura di backend per il progetto "Agenda Condivisa". Si tratta di un sistema distribuito basato su un'architettura a microservizi, progettato per gestire in modo scalabile e sicuro utenti, gruppi, eventi a calendario e integrazioni avanzate.

Il sistema centralizza l'autenticazione e il routing delegando le specifiche logiche di business ai singoli servizi di dominio, garantendo un alto isolamento e una gestione sicura dei dati.

## Architettura e Microservizi
Il backend è composto da servizi indipendenti che comunicano attraverso pattern architetturali moderni:

* **API Gateway**: Punto di ingresso unico per tutti i client. Gestisce il routing dinamico, il bilanciamento del carico e funge da barriera di sicurezza principale, intercettando e validando le richieste prima che raggiungano i servizi a valle.
* **User Service**: Microservizio dedicato all'Identity and Access Management (IAM). Gestisce la registrazione degli utenti, l'autenticazione, la cifratura delle credenziali e le mappature relazionali tra utenti e gruppi di lavoro.
* **Calendar Service**: Motore core per la gestione del tempo. Elabora la creazione di eventi, la gestione degli inviti e l'assegnazione delle risorse, basandosi sull'identità propagata in modo sicuro dal Gateway.
* **Agent Service** *(In Sviluppo)*: Modulo di intelligenza artificiale integrato per l'assistenza smart e la gestione avanzata delle interazioni testuali.

## Funzionalità Principali (Key Features)
* **Sicurezza Centralizzata**: Autenticazione basata su JWT (JSON Web Token) gestita a livello di Gateway. I microservizi interni operano in un perimetro di fiducia, ricevendo l'identità dell'utente in modo trasparente e sicuro ("Zero Trust" approach verso l'esterno).
* **Gestione Identità e Gruppi**: Sistema robusto per la creazione di account e la definizione di ruoli all'interno di gruppi condivisi.
* **Gestione Eventi e Inviti**: Logica di validazione per sovrapposizioni e permessi, garantendo che ogni utente abbia accesso e controllo unicamente sulle proprie risorse o su quelle a cui è esplicitamente invitato.
* **Database Migrations**: Evoluzione dello schema dati tracciata e versionata tramite migrazioni automatizzate per garantire l'integrità strutturale in ogni ambiente.
* **API Strutturate e DTO**: Esposizione di interfacce contrattualizzate. Separazione netta tra il modello dati persistente (Entity) e i dati esposti all'esterno (DTO) tramite mapping automatizzato.

## Stack Tecnologico
* **Linguaggio**: Java 17 / 21
* **Framework**: Spring Boot, Spring Cloud (Gateway), Spring Security, Spring Data JPA
* **Database**: PostgreSQL
* **Strumenti di Sviluppo**: Maven, Lombok, MapStruct
* **Database Versioning**: Flyway
* **Sicurezza**: JJWT (Java JWT), BCrypt
* **Infrastruttura**: Docker (Containerizzazione dei servizi pronti per il Cloud)

## Stato del Progetto
*Attivo - In fase di sviluppo continuo.*
I servizi principali (Gateway, IAM, Calendar) sono operativi e integrati in un flusso di sicurezza end-to-end. Attualmente i lavori sono concentrati sull'integrazione del modulo AI (Agent Service) e sull'orchestrazione containerizzata dell'intero cluster.