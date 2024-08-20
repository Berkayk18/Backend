### functionaliteit
- Er kan een user worden aangemaakt met een email adres.
- Op het email address zit validatie, deze moet voldoen aan een regex pattern.
- Een User kan een curus volgen, met daarbij de volgende rollen: student, docent, designer.
- Een User kan zich niet 2 keer inschrijven voor dezelfde cursus.
- Op basis van het cursus id kan je alle inschrijvingen van een cursus opvragen.

### Ik heb voornamelijk aandacht besteed aan de volgende onderdelen

#### value object
- validatie van de email toegepast, die niet ontweken of aangepast kan worden door externe klassen.
- regex pattern statisch gedefinieerd in het value object, zodat deze maar een keer gedefinieerd hoeft te worden.

#### error handling
- door gebruik te maken van transactional worden er automatisch rollbacks uitgevoerd bij een exception.
- door exceptions te gooien in de service laag en deze te vangen in de controller met restcontrolleradvice, wordt de error handling gecentraliseerd.
- door gebruik te maken van custom exceptions, kan ik de error messages en status codes specificeren.

#### DTO's
- door gebruik te maken van DTO's wordt de data die ik krijg van de client gecontroleerd en gescheiden van de data die ik opsla in de database.
