OpenAPI Documentation: /v3/api-docs
Swagger: /swagger-ui

In the config file of the MySQL Server, change max_allowed_packet from 1M to 10M to allow base64 images of greater scales.
Only change the max_allowed_packet of the server settings, not the UTF-8 settings.