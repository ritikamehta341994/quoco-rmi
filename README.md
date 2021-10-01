Running the Program
This is a maven project, complete the following steps to run locally:
1. Run mvn clean compile install on root directory
2. Run the broker first using mvn exec:java
3. Run the auldfellas, dodgydrivers and girlpower services in each different terminal using mvn exec:java
4. Run client at the end, using mvn exec:java

Complete the following steps to run on docker:
1. Run mvn clean compile install on root directory
2. Run docker-compose build
3. Run docker-compose up

Description:

1. The broker is responsible for creating the registry
2. The quotation services, ie. auldfellas, dodgydrivers and girlpower are responsible for looking up the registry
3. The client interacts with the broker, and receives the quotation depending upon the quotation services currently registered in the service registry
