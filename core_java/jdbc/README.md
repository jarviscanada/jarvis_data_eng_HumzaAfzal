# Introduction
This application utilizes JDBC to access a PostgreSQL database to perform CRUD operations. The Postgres database was hosted on a Docker container that used the postgres:9.6-alpine image. Maven was used as a build manager to build all the dependencies needed.

# Implementaiton
## ER Diagram
![ERDiagram] (./assets/ERDiagram.png)

## Design Patterns
The repository pattern focuses only on single table access per class. In contrast, the DAO pattern allows DOAs to access multiple tables per class. In a DAO pattern, different tables can be joined straight from the database due to their ability to access multiple tables, on the other hand, the repository pattern has to do joins in the code since it can only access one table. The repository pattern allows sharding of database, horizontal scaling, whereas DAOs are better for vertical scaling.

# Test
I would load up test data into the database to test it and call all the methods I wanted to test in the main method in the JDBCExecutor class.