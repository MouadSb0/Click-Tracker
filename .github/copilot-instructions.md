# ClickTrack — Instructions pour Copilot

## Stack IMPOSÉE (ne JAMAIS dévier)
- Java 17, Maven, packaging WAR
- Spring Core 6.1.x + Spring MVC 6.1.x UNIQUEMENT
- PAS de Spring Boot, PAS de JPA, PAS de Lombok, PAS de MapStruct
- Tomcat 10.1+ (jakarta.servlet, PAS javax.servlet)
- Jackson databind pour JSON (déjà dans Spring MVC)

## Configuration
- Configuration 100% XML : applicationContext.xml, dispatcher-servlet.xml, web.xml
- AUCUNE annotation de stéréotype : pas de @Service, @Repository, @RestController, @Component, @Configuration, @Bean, @ComponentScan
- SEULES annotations autorisées : @RequestMapping, @GetMapping, @PostMapping, @PathVariable, @ResponseBody, @RequestBody
- Beans déclarés par <bean> + <constructor-arg> dans applicationContext.xml
- Injection par constructeur, dépendances vers INTERFACES (ex: LienService, LienRepository)

## Stockage
- En mémoire uniquement, ArrayList dans les repositories
- Thread-safe : synchronized / AtomicInteger / CopyOnWriteArrayList pour la concurrence
- Pas de base de données, pas de fichier

## Architecture
controller → service → repository
- controller : @Controller (XML) + @ResponseBody, mapping HTTP
- service : interface + impl, logique métier
- repository : interface + impl, ArrayList

## Interdictions strictes
- NE PAS ajouter de dépendances Maven non demandées
- NE PAS créer de tests sauf demande explicite
- NE PAS utiliser de records, de var, ou de features hors Java 17 standard sans demande
- NE PAS générer de code Spring Boot
- Répondre en français pour les commentaires, en anglais pour le code

## Modèle à respecter
- Publisher : id (Long), nom, prenom, email, categorie (enum Categorie)
- Lien : id (Long), offre (String), urlDestination (String), clicks (int/AtomicInteger), publisherId (Long)