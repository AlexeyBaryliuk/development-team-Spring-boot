# Using jetty plugin

Goto Project folder and execute  
    
    mvn clean install 
    
1.Start Rest using Maven Jetty plugin use:

```
cd development-team-rest-app
mvn jetty:run
```
2.Start Web using Maven Jetty plugin use:
   
```
cd development-team-web-app
mvn jetty:run
```

3.The application is available at
```
http://localhost:8080/projects
```