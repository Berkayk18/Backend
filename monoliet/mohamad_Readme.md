
In de CourseService heb ik CRUD-operaties toegepast op cursussen, inclusief het toevoegen en ophalen van cursusmateriaal, evenals het toevoegen van modules.

Daarnaast heb ik geen CRUD-usecases toegepast om het aantal cursussen per periode of per studierichting op te halen.

De domeinlogica van Course en CourseMaterial omvat nu functionaliteiten zoals het toevoegen van materiaal of modules die al bestaan, het ophalen van materiaal en modules, en het controleren op de hoeveelheid, met als eis dat het maximum 10 materialen per cursus of 10 modules per materiaal is.

Aggregate Root: Ik heb de Aggregate Root toegepast door Course als root te definiëren om materiaal aan te maken en op te halen, en materiaal om modules toe te voegen.

Zowel Course als Module maken gebruik van objectwaarden waarop ik validatie van eigenschappen toepas.

Er zijn zowel unit- als integratietests geschreven, inclusief een verhalende test.

De service maakt ook gebruik van een service van een collega om meldingen op te halen per cursus.

Als laatste heb ik de CI/CD-pipeline voor ons team opgezet, waardoor automatische integratie- en deploymentprocessen mogelijk zijn. 