# Using jetty server

Goto Project folder and execute  
    
    mvn clean install -P jetty
    
1. Create links to war files 
```
 /abaryliuk-development-team/development-team-rest-app/target/rest_app_DT.war
```
```
 /home/alexey/dev/abaryliuk-development-team/development-team-web-app/target/web_app_DT.war
```
in the directory jetty server
 ```
/webapps
 ```

[source link]( https://www.baeldung.com/deploy-to-jetty "click")

2. Changing localhost at 
 ```
/etc/jetty-http.xml
 ```

3. Start the Jetty server from the Jetty folder

 ```
 java -jar start.jar 
 ```
4. The application is available at

 ```
http://localhost:8088/jetty_war/projects
 ```

- if you need, you can correct the name "jetty_war" in the folder 
 ```
/webapps/web_app_DT.xml
 ```



