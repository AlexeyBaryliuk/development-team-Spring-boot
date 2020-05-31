DROP TABLE IF EXISTS projects_developers;
DROP TABLE IF EXISTS projects;
DROP TABLE IF EXISTS developers;


   CREATE TABLE projects (
  projectId INT NOT NULL AUTO_INCREMENT,
  description VARCHAR(255) NOT NULL ,
  dateAdded DATETIME,
  PRIMARY KEY (projectId)
);

CREATE TABLE developers (
  developerId INT NOT NULL AUTO_INCREMENT,
  firstName VARCHAR(255) NOT NULL,
  lastName VARCHAR(255) NOT NULL,
  PRIMARY KEY (developerId)
);


CREATE TABLE projects_developers (
 projectId INT NOT NULL ,
 developerId INT NOT NULL,
PRIMARY KEY (projectId, developerId),
CONSTRAINT FK_Projects FOREIGN KEY (projectId)
REFERENCES projects (projectId) ON DELETE CASCADE,
CONSTRAINT FK_Developers FOREIGN KEY (developerId)
REFERENCES developers (developerId) ON DELETE CASCADE
);