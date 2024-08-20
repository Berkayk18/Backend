# HULP Start Repository

Bij deze de start-repository voor de HULP opdracht.

In deze repository start een lege Spring-boot applicatie. Deze staat net 1 directory dieper dan je gewend 
bent. Dat kun je voor nu negeren, maar zorgt er voor dat de overstap in week 3 net iets prettiger loopt.


Na opstarten kun je alvast kijken op

* http://localhost:8080/actuator voor allerhande debug info (heel soms handig)

[//]: # (* http://localhost:8080/h2-console voor je database.)
  
### Databases

Het volgende command start de postgres databases op in een docker container.

```
docker compose up -d
```

In de [Docker compose](./docker-compose.yml) kan je de instellingen van de database aanpassen.
- In de variable `POSTGRES_USER` geef je de gebruikersnaam op die je wilt gebruiken voor alle databases.
- In de variable `POSTGRES_PASSWORD` geef je het wachtwoord op dat je wilt gebruiken voor alle databases.
- In de variable `DB_APP_USER` geef je de gebruikersnaam op die je wilt gebruiken voor alle databases.
- In de variable `DB_APP_PASSWORD` geef je het wachtwoord op dat je wilt gebruiken voor alle databases.
- In de variable `DB_BASE_NAMES` geef je de namen van de databases op die je wilt aanmaken.

Er wordt voor elke database aangegeven in de `DB_BASE_NAMES` ook een test database aangemaakt.
De namen van de databases zijn als volgt opgebouwd:
- `DB_USER`_`DB_BASE_NAMES`
- `DB_USER`_`DB_BASE_NAMES`-test

Mocht je de database names aanpassen, zorg er dan ook voor dat je `application.properties` aanpast.










