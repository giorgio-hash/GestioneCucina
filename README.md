# Gestione Cucina
Componente del sistema [ServeEasy](https://github.com/giorgio-hash/ServeEasy.git).

Il microservizio Gestione Cucina si occupa di implementare i seguenti casi d'uso del gruppo cucina:

UC-5 “visualizzare lista ordini”, dettagliato:
- UC-5.1 : visualizzazione ordini per postazione, il cuoco deve visualizzare gli ordini destinati alla sua postazione

UC-6 “gestione preparazione ordini”, dettagliato:
- UC-6.1 : notifica preparazione ordine, il cuoco deve segnalare la presa in carico dell’ordine
- UC-6.2 : notifica completamento ordine, il cuoco deve segnalare il completamento dell’ordine così da passare al successivo
- UC-6.3 : gestione priorità postazione, il cuoco può modificare la priorità di un certo ingrediente così da ridurre la pressione su una certa postazione o, viceversa, per aumentarne il traffico. In tal modo può manualmente agire sulla gestione del traffico verso la cucina.

Nello specifico quindi GestioneCucina risolve gli use case del gruppo “cucina”, espone le chiamate destinate ai 
dispositivi di cucina. Questo servizio conterrà un sistema a code, dove l’ordine in arrivo verrà classificato ed 
inserito in base al suo ingrediente principale. Gli ordini verranno gestiti dalle postazioni della cucina 
seguendo una politica FIFO.

La comunicazione con gli altri microservizi avviene tramite Message Broker come segue:
- Il microservizio [GestioneComanda](https://github.com/giorgio-hash/GestioneComanda) comunica verso GestioneCucina tramite il topic Kafka SendOrderEvent.
- Il microservizio [GestioneCucina](https://github.com/giorgio-hash/GestioneCucina) comunica verso GestioneComanda tramite il topic Kafka NotifyPrepEvent.

La comunicazione con il databse avviene tramite un adapter JPA

Il microservizio è stato implementato seguendo lo stile architetturale esagonale, seguendo lo schema port/adapter,
per questo motivo viene strutturato in 3 parti:

- ### Interface
    Adattatori ponte tra il mondo esterno e il core del sistema, consentono al microservizio di comunicare con altre applicazioni, servizi o dispositivi esterni in modo indipendente dall'implementazione interna del sistema stesso. Sono presenti i seguenti Interface Adapter:
    - EventControllers: SubOrderAdapter, permette la ricezione di messaggi tramite message broker dal microservizio GestioneComanda;
    - HTTPControllers: RestApiCucina, permette di esporre API verso l'esterno.

- ### Domain
  Definisce gli oggetti, le entità e le operazioni che sono pertinenti al problema che il microservizio gestisce.

- ### Infrastructure:
  Adattatori ponte tra il core del sistema e l'infrastruttura esterna, gestendo le chiamate e le operazioni necessarie per accedere e utilizzare le risorse infrastrutturali.     Sono presenti i seguenti Infrastructure Adapters:
    - Repository: JPADBAdapter per la comunicazione con il database;
    - MessageBroker: PubOrderAdapter per l'invio di messaggi sul topic verso il microservizio di GestioneComanda.

## Start
Apri Docker Desktop, apri un terminale e vai alla root directory del progetto e digita:
```shell
docker compose up
```
Manda in run il microservizio usando qualsiasi IDE oppure tramite Maven Wrapper con la seguente istruzione:
```shell
./mvnw clean install
./mvnw spring-boot:run
```

## User Interface

### Kafdrop
[Kafdrop](https://github.com/obsidiandynamics/kafdrop) è un'interfaccia utente Web per visualizzare i topic di Kafka
e sfogliare i gruppi dei consumers.
Lo strumento visualizza informazioni circa: brokers, topics, partitions, consumers, e consente di visualizzare i messaggi.

Apri un browser e vai all'indirizzo http://localhost:9000.

### phpMyAdmin
[phpMyAdmin](https://www.phpmyadmin.net/) è un'applicazione web che consente di amministrare un database MariaDB tramite un qualsiasi browser.

Apri un browser e vai all'indirizzo http://localhost:3307.

## API
Il microservizio GestioneCucina espone 3 API verso l'esterno per mezzo dell'adattarore RestApiCucina in HTTPControllers, 
per la documentazione si rimanda alla documentazione completa via documenter.getpostman: https://documenter.getpostman.com/view/32004409/2sA3JF9iav

## Test
E' possibile usufruire delle API per verificare il corretto funzionamento del sistema
via [Postman](https://web.postman.co//) tramite l'API all'indirizzo http://localhost:8080/...

### Test dei topic Kafka:
- ### Command Line Producer
    Utilizzare il seguente comando per pubblicare sul topic specificato un messaggio
    ```shell
    docker exec --interactive --tty broker kafka-console-producer --bootstrap-server broker:9092 --topic "sendOrderEvent"
    ```
    ```shell
    docker exec --interactive --tty broker kafka-console-producer --bootstrap-server broker:9092 --topic "notifyPrepEvent"
    ```

- ### Command Line Consumer
    Utilizzare il seguente comando per restare in ascolto sul topic specifico
    ```shell
    docker exec --interactive --tty broker kafka-console-consumer --bootstrap-server broker:9092 --topic "sendOrderEvent" --from-beginning
    ```
    ```shell
    docker exec --interactive --tty broker kafka-console-consumer --bootstrap-server broker:9092 --topic "notifyPrepEvent" --from-beginning
    ```
### Test di integrazione e unità
E' possibile eseguire i test di integrazione e di unità tramite il Maven Wrapper, che è uno strumento che consente di eseguire i comandi Maven senza dover avere Maven installato globalmente sul sistema, tramite l'istruzione:
```shell
 ./mvnw clean verify
 ```

## Analisi Statica
### Checkstyle
In questo progetto è integrato un tool per l'analisi statica:
[Apache Maven Checkstyle Plugin](https://maven.apache.org/plugins/maven-checkstyle-plugin/index.html)
Per generare il sito del progetto e i report eseguire:
```shell
 ./mvnw site
 ```
I file di report si troveranno in ```target/site```, in particolare il file di interesse è
```checkstyle.html``` che è possibile aprire tramite un qualsiasi browser.

Per poter personalizzare le regole è possibile modificare il file ```checkstyle.xml``` e seguire le indicazioni
dei commenti in apertura.

Il report è disponibile grazie a Github pages della repository ServeEasy al seguente link: [checkstyle.html](https://giorgio-hash.github.io/ServeEasy/Report/GestioneCucina/site/checkstyle.html)

### Script Python
Inoltre è presente uno script python per generare i file csv e i grafici associati ai report, per poterlo avviare
è necessario avere python installato sulla propria macchina ed eseguire il seguente comando
per installare le librerie necessarie:
```shell
 pip install -r python/requirements.txt
 ```
Succesivamente eseguire il seguente per poter avviare lo script:
```shell
 python main.py
 ```
I file csv e le immagini png verranno salvati in ```target/output```.
