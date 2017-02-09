Problem Statement
-----------------------------------

National geographic wants an application for maintaining the records of different species and capture various details about those species in the system. They read the success stories of various large scale products and came across the term “micro-service” at numerous places and they had this epiphany that micro-services is the way. They have come to you and are adamant about having the micro-servcies architecture. To start with they want you to write a micro-service which handles the registry for different species of birds. 

After discussions in your team and considering various options you decide to use spring boot as a framework for this micro service and mongo db as the data source for this service.

You decide to expose restful endpoints for the apis you will need on this micro-servcie. 

The specs for these apis can be found at https://gist.github.com/sebdah/265f4255cb302c80abd4

------------------------------------------




Required tools/software

* java 8 sdk (verify by command -> javac -version)
* maven >= 3.3.9 (verify by command ->  mvn --version)
* mongodb running on standard port 27017

* To run the test -> mvn test
* To start the application -> ./mvnw  spring-boot:run


Once the application is started you can try these commands.

* create command

curl -v -i -X POST -H 'Content-Type: application/json' -d '{"name":"birdName","family":"birdFamily","continents":["europe","asia"],"visibility":true,"added":"2016-02-28"}' http://localhost:8080/birds

* get all

curl -X GET http://localhost:8080/birds

* get details for one bird

curl -i -X GET http://localhost:8080/birds/{id}

* delete

curl -i -X DELETE http://localhost:8080/birds/{id}



