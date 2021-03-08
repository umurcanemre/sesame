#About

This is an assesment game for Sesame

#Running

##Building
>cd ${project_directory}

>./mvnw clean install

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

* The data transformation is done in-place, on pre-existing objects.

* The constraints described on POJO's under com.umurcan.sesame.domain package, apply **ONLY** for appointment first structure and are used to validate the structure of the response of appointment system. Since the creation of the doctor first data is the responsibility of this service, that data is validated by unit tests.

