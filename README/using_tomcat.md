# Using tomcat server

Goto Project folder and execute  
    
    mvn clean install -P tomcat
   
1.  Copy the war file from directory
 ```
 /abaryliuk-development-team/development-team-rest-app/target/rest_app_DT.war
```
 to the tomcat directory
 ```
/webapps
 ```
2.  Copy the war file from directory
 ```
 /home/alexey/dev/abaryliuk-development-team/development-team-web-app/target/web_app_DT.war
```
 to the tomcat directory
 ```
/webapps
 ```
3. Start the tomcat server from the directory

 ```
/bin
 ```

 ```
./startup.sh
 ```

4. The application is available at

 ```
http://localhost:8080/web_app_DT/projects
 ```
5. Undeploy tomcat server from the directory

 ```
/bin
 ```
 ```
./shutdown.sh
 ```

or 

from address

 ```
http://localhost:8080/manager/html
 ```
click undeploy opposite the application 