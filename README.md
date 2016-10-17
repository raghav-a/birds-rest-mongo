# birds registry (https://gist.github.com/sebdah/265f4255cb302c80abd4)

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



