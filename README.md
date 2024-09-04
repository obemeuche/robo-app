ABOUT PROJECT:
1. Project is on the Main branch.
2. Program is written in Java 8.
3. Program runs on port 8080.
4. Persisted questions and answers provided in a faq.json file
5. Used a H2 database to simulate permanent storage. This was done to achieve future storing the question 
   and answers in a permanent storage.  
6. Replace database properties with a persistent database in the application.properties file.
7. Project allows containerization as it includes a dockerfile and docker-compose.yaml file. 
8. Replace database properties in the docker-compose.yaml file.

Design pattern used:
SINGLETON DESIGN PATTERN (BENEFITS):
1. Single Instance: It guarantees that a class has only one instance. This can be
   useful when you want to control access to a shared resource, in this case, the persisted
   questions and answers in the faq.json file.

2. Global Point of Access: The Singleton provides a single point of access to
   its instance, making it easy to manage and control the use of the instance across
   the application.


KEY DECISIONS:
1. Used a javascript script engine provided by java to carry out the arithmetic computation for the CLI application. 
   This made me stick with Java 8 as it was removed versions after 8. To utilize the script engine in other java versions, 
   you need to use the GraalVM by adding the below dependencies in your pom.xml:

   <dependency>
        <groupId>org.graalvm.js</groupId>
        <artifactId>js</artifactId>
        <version>23.0.0</version>
   </dependency>

   <dependency>
        <groupId>org.graalvm.js</groupId>
        <artifactId>js-scriptengine</artifactId>
        <version>23.0.0</version>
   </dependency>
   