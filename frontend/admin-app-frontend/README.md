# Frontend Configuratie
Voor het starten van de backend moeten eerst wat configuratie propertjes worden veranderd, ga hiervoor naar `.env.development` en `.env.production`

`VUE_APP_API_BASE_URL` moet worden aangepast naar de base URL van de backend.

# Frontend setup & run
Voor setup & run zijn twee commands nodig.
1. `npm install`
1. `npm run serve`


# Front-end Dockerfile 

In de front-end applicatie is een Dockfile met de configuratie om de applicatie in een docker container te draaien. Belangrijk hierbij is dat in de Dockerfile staat dat de applicatie op port 8081 zal gaan luisteren op inkomende verzoeken als er in de package.json bestand een andere port wordt aangegeven dan moet diezelfde poort ook in de Dockerfile komen te staan anders zal de applicatie niet bereikbaar zijn. 

Voor de rest zorgt de Dockerfile er voor dat de applicatie gebouwd wordt voor productie. 

Om de een docker image te maken voor het front-end moet je eerst Docker installeren, dat kan [hier](https://docs.docker.com/get-docker/). 

Als docker geïnstalleerd is voer je de volgende commando uit in een terminal/CMD in de root folder van het project waar de Dockerfile zich bevindt. 

Commando: <span style="color:#000080">docker build -t rigs-front-end .</span> 

De naam <span style="color:#000080">rigs-front-end</span>  kan veranderd woorden naar iets anders 

<span style="color:red">LET OP DE PUNT OP HET EINDE IS HEEL BELANGRIJK ANDERS WORD DE IMAGE NIET GEMAAKT!.</span> 