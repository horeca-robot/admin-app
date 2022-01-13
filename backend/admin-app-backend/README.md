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
RIGS Admin Backend Applicatie Deployment


## Backend Dockerfile

In de backend applicatie is een Dockfile met de configuratie om de applicatie in een docker container te draaien. Belangrijk hierbij is dat in de Dockerfile staat dat de applicatie op port 8080 zal gaan luisteren op inkomende verzoeken als een andere port wordt aangegeven dan moet diezelfde poort ook in de Dockerfile komen te staan anders zal de applicatie niet bereikbaar zijn.

Voor de rest zorgt de Dockerfile er voor dat de applicatie gebouwd wordt voor productie.

Om de een docker image te maken voor het front-end moet je eerst Docker installeren, dat kan hier.

Als docker geïnstalleerd is voer je de volgende commando uit in een terminal/CMD in de root folder van het project waar de Dockerfile zich bevindt.

Commando: docker build -t rigs-backend .

De naam rigs-backend kan veranderd woorden naar iets anders

LET OP DE PUNT OP HET EINDE IS HEEL BELANGRIJK ANDERS WORD DE IMAGE NIET GEMAAKT!.


# API Documentatie
[Postman link](https://documenter.getpostman.com/view/13694729/UVXgMd6x#efb4951c-5b0d-4de8-81f5-a95c7b1bf6bc)