# Backend Architectuur
De backend is opgesplitst in drie lagen;
1. Controller laag - Hier komen de HTTP requests binnen.
2. Service laag - Hier in zit de logic van de applicatie.
3. Repository laag - Hierin communiceerd de applicatie met de database.
![alt text](RIGS_Admin_Component_Diagram.png)

# Backend Configuratie
Voor het starten van de backend moeten eerst wat configuratie propertjes worden veranderd, ga hiervoor naar `src/main/resources/application.properties`

`admin.email` en `admin.password` kunnen veranderd worden voor de default admin account.

`spring.datasource` properties moeten aangepast worden voor de connectie met de database.
Bij `url` moet een connectie string worden toegevoegd naar een database en onder `username` en `password` de daarbij horende credentials.

# Backend build & run
Voor het builden van de applicatie gebruik dit command: `gradle build`

Dit bouwt een jar file, deze komt te staan in de `src/build/libs/ folder`. Om deze jar te runnen gebruik dit command: `java -jar <JAR NAME>.jar`

# Docker
RIGS Admin Applicatie Deployment


## Front-end Dockerfile

In de front-end applicatie is een Dockfile met de configuratie om de applicatie in een docker container te draaien. Belangrijk hierbij is dat in de Dockerfile staat dat de applicatie op port 8081 zal gaan luisteren op inkomende verzoeken als er in de package.json bestand een andere port wordt aangegeven dan moet diezelfde poort ook in de Dockerfile komen te staan anders zal de applicatie niet bereikbaar zijn.

Voor de rest zorgt de Dockerfile er voor dat de applicatie gebouwd wordt voor productie.

Om de een docker image te maken voor het front-end moet je eerst Docker installeren, dat kan hier.

Als docker geïnstalleerd is voer je de volgende commando uit in een terminal/CMD in de root folder van het project waar de Dockerfile zich bevindt.

Commando: docker build -t rigs-front-end .

De naam rigs-front-end kan veranderd woorden naar iets anders

LET OP DE PUNT OP HET EINDE IS HEEL BELANGRIJK ANDERS WORD DE IMAGE NIET GEMAAKT!.

## Backend Dockerfile

In de backend applicatie is een Dockfile met de configuratie om de applicatie in een docker container te draaien. Belangrijk hierbij is dat in de Dockerfile staat dat de applicatie op port 8080 zal gaan luisteren op inkomende verzoeken als een andere port wordt aangegeven dan moet diezelfde poort ook in de Dockerfile komen te staan anders zal de applicatie niet bereikbaar zijn.

Voor de rest zorgt de Dockerfile er voor dat de applicatie gebouwd wordt voor productie.

Om de een docker image te maken voor het front-end moet je eerst Docker installeren, dat kan hier.

Als docker geïnstalleerd is voer je de volgende commando uit in een terminal/CMD in de root folder van het project waar de Dockerfile zich bevindt.

Commando: docker build -t rigs-backend .

De naam rigs-backend kan veranderd woorden naar iets anders

LET OP DE PUNT OP HET EINDE IS HEEL BELANGRIJK ANDERS WORD DE IMAGE NIET GEMAAKT!.

DockerCompose

Om beide applicaties live te zetten en om er voor te zorgen dat ze met elkaar kunnen communiceren is er gebruik gemaakt van een docker-compose.yml bestand in de root van het project. Niet in de backend of front-end project maar waar beide projecten zitten. De docker-compose.yml zorgt ervoor dat beide applicaties worden gebouwd door op beide docker build -t [naam] . uit te voeren.

In de docker-compose.yml wordt er gebruik gemaakt van een reverse proxy zodat beide applicaties hun eigen host hebben maar toch met elkaar kunnen communiceren. Om de docker-compose.yml uit te voeren moet je de volgende commando uitvoeren in een terminal/CMD.

Commando: docker-compose up --build beide applicaties worden gebouwd en gestart. 

# API Documentatie
[Postman link](https://documenter.getpostman.com/view/13694729/UVXgMd6x#efb4951c-5b0d-4de8-81f5-a95c7b1bf6bc)