# Vaadin Tutorial Solutions for Vaadin 22

This project is the solutions to the Vaadin 14 tutorials adapted to run in Vaadin 22 environment, then pushed to Google Cloud Platform

Then added Auth0 credentials, (use auth-default.properties and `mv` to auth0.properties).

- Auth0LoginView.class delegates to Auth0 universal login.
- LoginView does VaadinSession Login and has sample user tokens.

Run with `mvn spring-boot:run`

To prepare for production:
`mvn install -Pproduction`

Test resulting jar
`java -jar target/project-base-1.0-SNAPSHOT.jar`

Then dockerize

`docker build -t vaadin-docker .`

Test it locally on port 8080

`docker run -ti -p 8090:8080 vaadin-docker`

Tag image with registry name for Google Cloud Run (GCR) project Id

`docker tag [IMAGE] gcr.io/[PROJECT-ID]/[IMAGE]`

e.g.,  `docker tag 8666b80ba571 gcr.io/vaadintest-337721/8666b80ba571`

If first time, make sure you `gcloud auth login` and `gcloud auth configure-docker`


Push image to GCR

`docker push gcr.io/[PROJECT-ID]/[IMAGE]`

e.g., `docker push gcr.io/vaadintest-337721/8666b80ba571`


Then go to Google Cloud Run and deploy by creating service.

Also, in the YAML tab on Kubernetes service, looking at default port:
`sessionAffinity: None to sessionAffinity: ClientIP`
(https://vaadin.com/learn/tutorials/cloud-deployment/google#_prepare_the_application)

