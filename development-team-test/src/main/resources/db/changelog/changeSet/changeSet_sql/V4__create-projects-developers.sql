CREATE TABLE projects_developers (
 projectId INT NOT NULL ,
 developerId INT NOT NULL,
PRIMARY KEY (projectId, developerId),
CONSTRAINT FK_Projects FOREIGN KEY (projectId)
REFERENCES projects (projectId) ON DELETE CASCADE,
CONSTRAINT FK_Developers FOREIGN KEY (developerId)
REFERENCES developers (developerId) ON DELETE CASCADE
);