# Backend Configuratie
Voor het starten van de backend moeten eerst wat configuratie propertjes worden veranderd, ga hiervoor naar `src/main/resources/application.properties`

`admin.email` en `admin.password` kunnen veranderd worden voor de default admin account.

`spring.datasource` properties moeten aangepast worden voor de connectie met de database.
Bij `url` moet een connectie string worden toegevoegd naar een database en onder `username` en `password` de daarbij horende credentials.

# Backend build & run
Voor het builden van de applicatie gebruik dit command: `gradle build`

Dit bouwt een jar file, deze komt te staan in de `src/build/libs/ folder`. Om deze jar te runnen gebruik dit command: `java -jar <JAR NAME>.jar`