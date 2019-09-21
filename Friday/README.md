
## Flow 2, first week (38)
<br/>
<br/>

##### Explain the rationale behind the topic Object Relational Mapping and the Pros and Cons in using ORM

##### * Pros

###### * Ingen SQL, men objektorienteret JPQL

###### * Nestede/relaterede objekter kan automatisk trækkes med ud

###### * Uafhængig af database-variant (MySQL, PostgreSQL, MSSQL osv)

##### * Cons

###### * Abstraktionsniveauet er højere når der anvendes ORM hvilket kan medføre at det er lettere at miste overblik og kontrol over databasen

###### * Performance, da ORM er generel vil der i mange tilfælde være meget overhead (ekstra ting der udføres som ikke er strengt nødvendige), der vil være meget overhead i forhold til rå SQL der efterfølgende konverteres til objekter i Java. 
<br/>
<br/>

##### Explain the JPA strategy for handling Object Relational Mapping and important classes/annotations involved.
###### * JPA implementationen er vores persistence provider, i vores projekter bruger vi EclipseLink. Mapningen af objekter i Java til tabeller i databasen sker vha. Metadata som oftest er defineret som annotationer i Java Klasserne. Objekter der har disse annotationer defineres som entities. JPA har sit eget query language, JPQL som genererer al SQL.

###### * @Entities, @GeneratedValue, @One/ManyToOne/Many er nok de vigtigste annotationer, men der er mange flere. [Se her for flere](https://www.objectdb.com/api/java/jpa/annotations)
<br/>
<br/>

##### Outline some of the fundamental differences in Database handling using plain JDBC versus JPA
###### * Ved brug af JDBC så skal al SQL skrives selv og manuelt mappes til objekter/modeller i Javakoden. Relationer vil skabe komplekse SQL-queries, som også manuelt skal mappes over i flere objekter efterfølgende. Ved brug af JPA er databasen defineret på baggrund af objekterne (entiteterne). Hele SQL-laget er abstraheret væk, så i Java-applikationen arbejdes der udelukkende med objekter (med tilhørende referencer objekterne måtte have). 

###### * Ved brug af JDBC sker al mapning direkte med SQL og alle objekter skal brydes ned og manuelt mappes til databasen, sammen med relationer objektet måtte have. Ved brug af JPA mister man aldrig objekt-strukturen og reference-strukturen de måtte have. 

