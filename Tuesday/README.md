## Flow 2, first week (38)
<br/>
<br/>
1) One to One – Unidirectional<br/>  
 ##### Make sure you understand everything that changed in the Customer and (if any) in the Address class. Regenerate the schema and investigate the generated tables. Observe the location of the foreign key  

###### * Foreign key ligger i customer.address_id og refererer til address.id  
<br/>
<br/>

2) One to One – Bidirectional
##### Run the project and investigate the generated tables (the foreign key). Is there any difference compared to the previous exercise. If not explain why.

###### * Der var ingen forskel - det betyder ikke noget på databaseniveau om relationen er uni- eller bidirectional, da tables bare kan joines på foreign key. I javakoden betyder det noget - når relationen er unidirectional kender address-objektet ikke noget til customer, men når relationen er bidirectional er der en reference til customer-objektet i address-objektet. 
<br/>
<br/>

3) OneToMany (unidirectional)
##### How many tables were generated? Explain the purpose of the tables.

###### * Der blev genereret et ekstra table. Customer_Address er relations-tabellen mellem Customer og Address.
<br/>
<br/>

4) OneToMany (bidirectional)
##### Observe the generated code, especially where we find the mappedBy value. Explain.

###### * MappedBy findes nu hos Customer-objektet, før har den være på Address-objektet. Det er den fordi owning side er Address. I databasen er det Address der har foreign key med reference til Customer (omvendt fra de forrige opgaver). Ved en-til-mange, ligger foreign key hos den der er mange af, hvilket her er address, for at undgå at Customer skal pege på flere Address pr. række. 
<br/>
<br/>

5) Many To Many (bidirectional)
##### How can we implement ManyToMany relationships in an OO-language like Java? How can we implement ManyToMany relationships in a Relational Database? Make sure you understand everything that changed in the Customer and (if any) in the Address class. Regenerate the schema and investigate the generated tables. Observe the location of the foreign key

###### * Ved hjælp af reference-tabeller mellem mange-til-mange entiteterne/objekterne, samt ved at implementere lister af de referede objekter hos det objekter der skal kende til det og omvent. 

###### * Der bliver oprettet et Customer table og et Address tabel, samt et Customer_Address reference tabel. Customer_Address reference tabel har foreign key til både Customer og Address table og er det table der muliggør mange-til-mange relationen. 


