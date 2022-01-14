# Vaadin Sample App for Vaadin 22

This project is the combined solutions to the Vaadin 14 with Spring-Boot tutorials adapted to run in Vaadin 22 environment and added Auth0 universal login.

To use Auth0 universal login, see auth-default.properties and `cp` to auth0.properties.

### Login View Options
- Auth0LoginView.class delegates to Auth0 universal login with Vaadin Spring Servlet
- LoginView does VaadinSession Login and has sample user tokens.

First time: `mvn clean compile install`

Run with `mvn spring-boot:run`

# Production

To prepare for production:
`mvn install -Pproduction`

Test resulting jar
`java -jar target/project-base-1.0-SNAPSHOT.jar`

Then dockerize

`docker build -t vaadin-docker .`

Test it locally on port 8080

`docker run -ti -p 8090:8080 vaadin-docker`

# Production to Google Cloud

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

