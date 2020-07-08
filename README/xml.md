
# Import and export projects and developers in XML with data archiving

## Export projects and developers in XML with data archiving
### Rest layer
1.Export projects.
Method post
```
http://localhost:8088/projects/export/xml
```
2.Export developers.
  Method post
```
http://localhost:8088/developers/export/xml
```

3. Import projects.
Method post
```
http://localhost:8088/projects/import/xml
```
4. Import developers.
  Method post
```
http://localhost:8088/developers/import/xml
```

### Web layer
    
1.Export projects.
Method get
```
http://localhost:8080/projects/export/xml
```
2.Export developers.
  Method get
```
http://localhost:8080/developers/export/xml
```

3. Import projects.
Method post
```
http://localhost:8080/projects/import/xml
```
4. Import developers.
  Method post
```
http://localhost:8080/developers/import/xml
```