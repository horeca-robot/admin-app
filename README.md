De RIGS bestel applicatie is opgesplitst in drie sub projecten zoals hieronder beschreven.
1. Admin applicatie: Hier stelt de restauranteigenaar het menu in.
2. Klant applicatie: Hier besteld de klant zijn eten
3. Medewerker applicatie: Hier komen de bestellingen van de klant binnen zodat de keuken kan beginnen met de bereiding.
      Deze drie projecten communiceren doormiddel van een gedeelde database. Hiervoor is een database library gemaakt om conflicten te voorkomen.

In deze git repo vind je de eerste van de drie applicaties. Deze is weer opgesplitst is twee sub applicaties:
1.	Front-end: Hiervoor is het Vue framework (JavaScript) gebruikt.
2.	Backend: Hiervoor is het Spring framework (Java) gebruikt.
      Opstarten van de applicaties:
      Voor een uitleg voor het opstarten van deze applicaties ga naar de readme files in deze folders:
1.	frontend/admin-app-frontend/ - Voor de frontend
2.	backend/admin-app-backend/ - Voor de backend


OpenAPI Documentation: `/v3/api-docs`

Swagger: `/swagger-ui`

In the config file of the MySQL Server, change max_allowed_packet from 1M to 10M to allow base64 images of greater scales.
Only change the max_allowed_packet of the server settings, not the UTF-8 settings.