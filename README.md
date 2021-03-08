#About

This is an assesment game for Sesame

#Running

##Tests
>cd ${project_directory}

>./mvnw test

##Server
>cd ${project_directory}

>./mvnw spring-boot:run

#Endpoints
After running the server go to [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

* /doctors       -> Post method to create a new player account

#Things to consider

* Due to replacement nature of the service, to describe the current state of the data, name of the first object layer has been used

ie. :  The remote service that supplies the appointment data is called the appointment service and data we fetch is appointments. Once the transformation is applied and the data is turned to doctor -> location -> appointment structure, it mendioned as doctor data. This applies to file namings too.

