  CREATE TABLE projectsDto (
  projectId INT NOT NULL AUTO_INCREMENT,
  description VARCHAR(255) NOT NULL ,
  dateAdded DATETIME,
  countOfDevelopers INT,
  PRIMARY KEY (projectId)
);