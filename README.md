# Agenda Condivisa - Backend Infrastructure

## Descrizione del Progetto
Questo repository contiene l'infrastruttura di backend distribuita per il progetto "Agenda Condivisa". Il sistema è basato su un'architettura a microservizi scalabile, resiliente e guidata dagli eventi (event-driven), progettata per gestire utenti, gruppi, calendari e notifiche automatiche in un ambiente cloud-ready.

Il backend implementa pattern enterprise per garantire la separazione delle responsabilità, la tolleranza ai guasti e la sicurezza stateless.

## Architettura e Microservizi
Il sistema è composto da servizi indipendenti containerizzati che comunicano tramite REST e Message Broker:

* **API Gateway (Spring Cloud Gateway)**: Punto di accesso unico. Gestisce il routing, la sicurezza centralizzata, il filtraggio dei token JWT e la resilienza tramite Circuit Breaker.
* **User Service (IAM)**: Gestisce l'Identity and Access Management, la registrazione, l'autenticazione (BCrypt) e le gerarchie tra utenti e gruppi.
* **Calendar Service**: Motore core per la gestione degli eventi. Include uno scheduler interno per il monitoraggio delle scadenze e la pubblicazione di eventi asincroni.
* **Notification Service**: Microservizio asincrono che consuma messaggi dal broker e gestisce l'invio delle notifiche agli utenti.
* **Agent Service**: Modulo integrato per l'assistenza smart basata su intelligenza artificiale.
* **Message Broker (RabbitMQ)**: Spina dorsale per la comunicazione asincrona tra i servizi, garantendo il disaccoppiamento tra la creazione di eventi e l'invio di notifiche.

## Funzionalità Avanzate (Key Features)

### 1. Resilienza e Fault Tolerance
* **Circuit Breaker (Resilience4j)**: Implementato sul Gateway per prevenire fallimenti a catena. In caso di downtime di un servizio a valle, il sistema risponde con logiche di *fallback* (503 Service Unavailable personalizzate).
* **Fail-fast & Timeouts**: Configurazione di timeout granulari per connessione e risposta per ottimizzare l'uso delle risorse.

### 2. Comunicazione Event-Driven
* **Architettura Asincrona**: Utilizzo di RabbitMQ (Exchange/Queues) per gestire i promemoria degli eventi.
* **Scheduled Tasks**: Task pianificati che analizzano il database e notificano gli utenti in tempo reale senza bloccare il thread principale.

### 3. Sicurezza Stateless
* **JWT Centralizzato**: Validazione dei token a livello di Gateway con propagazione dell'identità sicura verso i microservizi interni.
* **Cifratura**: Protezione delle credenziali sensibili tramite hashing BCrypt.

### 4. Database & Persistence
* **Multi-Schema Strategy**: Utilizzo di PostgreSQL con schemi isolati (`user_schema`, `calendar_schema`) per simulare un ambiente multi-database.
* **Database Versioning**: Gestione evolutiva dello schema tramite **Flyway**.

## Stack Tecnologico
* **Linguaggio**: Java 17 / 21
* **Core Framework**: Spring Boot 3.3.0, Spring Cloud Gateway, Spring Data JPA
* **Messaggistica**: RabbitMQ
* **Resilienza**: Resilience4j
* **Database**: PostgreSQL
* **Sicurezza**: JJWT (Java JWT), Spring Security
* **Mapping & Utility**: MapStruct, Lombok
* **Containerizzazione**: Docker & Docker Compose

## Testing & Qualità del Codice
Il sistema adotta una strategia di testing a più livelli:
* **Unit Testing**: JUnit 5 e Mockito per la validazione della logica di business nei Service Layer (copertura su User e Calendar service).
* **Integration Testing**: Setup predisposto per **Testcontainers** e **MockMvc** per testare il reale dialogo con il database PostgreSQL in ambienti isolati.
* **Architettura Layered**: Rigorosa separazione tra Controller, Service e Repository con l'uso di DTO per lo scambio dati esterno.

## Stato del Progetto
* **Backend**: 🟢 Completo e Testato.
* **Infrastruttura**: 🟢 Dockerizzata e pronta per il deployment.
* **Prossimo Step**: Sviluppo del Frontend (React) e integrazione API.

---
*Ultimo aggiornamento: Aprile 2026*