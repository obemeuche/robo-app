Project Overview
1.	Branch: The project is currently on the main branch.
2.	Language: The application is developed using Java 8.
3.	Port: The application operates on port 8080.
4.	Data Persistence: Questions and answers are persisted in the faq.json file.
5.	Database: An H2 database is used to simulate permanent storage, facilitating future storage of questions and answers.
    To transition to a persistent database, update the database properties in the application.properties file.
6.	Containerization: The project supports containerization through the inclusion of a Dockerfile and a docker-compose.yaml file.
    Update the database properties in the docker-compose.yaml file as necessary.
7.	CLI Command: Use the --quit keyword to exit the CLI application.
8.	Architecture: The project follows the Clean Architecture pattern.


Design Pattern
Singleton Design Pattern
Benefits:
1.	Single Instance: Ensures that only one instance of a class exists, which is beneficial for controlling access to shared resources, such as the faq.json file.
2.	Global Access: Provides a single point of access to the instance, simplifying management and control across the application


KEY DECISIONS:
1.	JavaScript Script Engine: A JavaScript script engine is used for arithmetic computations in the CLI application.
      The JavaScript script engine (Nashorn) is included in Java 8 for executing JavaScript code. This engine was removed in Java 15 and later versions.
      To maintain JavaScript execution capabilities in newer Java versions, you should use GraalVM and include the following dependencies in your pom.xml:

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

Trade-off: The application functions as intended with Java 8. For compatibility with newer Java versions, the above dependencies must be added to use GraalVM’s JavaScript engine.

2.	REST Endpoint: A REST endpoint has been exposed to facilitate the addition of questions and answers to permanent storage. This design choice allows for future expansion and flexibility.