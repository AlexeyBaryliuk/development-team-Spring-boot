
## Start REST layer

# Using JPA profile
    
1.Start Rest using JPA profile:
    JPA profile starts by default.
    Goto Project folder and execute.  
```
cd development-team-rest-app
mvn clean install

```
2.Start tests using mySql or H2 profiles:
   Changing the pom value in < argline >
```
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-failsafe-plugin</artifactId>
                <configuration>
                    <argLine>-Dspring.profiles.active=mySql</argLine>
                </configuration>
            </plugin>
```

3.The application is available at
```
http://localhost:8088/projects
```


# Using JDBC profile
    
1.Start Rest using JDBC profile:
Goto Project folder and execute  
  
```
cd development-team-rest-app
mvn clean install -P jdbc

```
2.Start tests using mySql or H2 profiles:
   Changing the pom value in < argline >
```
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-failsafe-plugin</artifactId>
                <configuration>
                    <argLine>-Dspring.profiles.active=mySql</argLine>
                </configuration>
            </plugin>
```

3.The application is available at
```
http://localhost:8088/projects
```

## Start WEB layer

1.Goto Project folder and execute  
  
```
cd development-team-web-app
mvn clean install 
```

2.Start tests using mySql or H2 profiles:

   Changing the pom value in < argline >
```
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-failsafe-plugin</artifactId>
                <configuration>
                    <argLine>-Dspring.profiles.active=mySql</argLine>
                </configuration>
            </plugin>
```