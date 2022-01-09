# Vaadin Tutorial Solutions for Vaadin 22

This project is the solutions to the Vaadin 14 tutorials adapted to run in Vaadin 22 environment.

Using Spring, then do `mvn spring-boot:run`

To prepare for production:
`mvn install -Pproduction`

Then dockerize

`docker build -t vaadin-docker .`

`docker run -ti -p 8090:8080 vaadin-docker`