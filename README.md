# Repte 4: CRUD for two Basic Models
The project follows the guideline from [Lliga IT Academy Repte 4](https://nuwe.io/challenge/repte-4-operacions-crud).
It's a project made with Java and Spring Boot that executes CRUD operations on two Basic Models. I choose **Author** and **Book** as models with no relations within them.
The application works well (excepting for not-found bugs).


## Structure
The project's structure contains the *models*, the *controller*s, the *repositories* and *middleware*. The middleware is responsible for checking some parameters in the request. For example, when updating the name of a book the *middleware/BookHandler.java* checks first if the *name* parameter exists. 
Usually is good practice to separate the *model* for the database and the *object* to work within the program. But as only one database is used and no extra operations are needed or performed into the models I prefered to use the *model* to work with.
There are two controllers, one for the Book CRUD and another for the Author CRUD.


## Databases
The application allows to choose between two databases. To select one or the other use the **applitacion.properties** and change the profile name.
- MySQL 
  - File: application-mysql.properties
  - Profile: mysql
- H2
  - File: application-h2.prperties
  - Profile: h2


## Testing
There are various tests provided in the project. There are tests for the repositories and for the controllers. In addition, a Postman Collection is provided.
The application is fully tested and works well.

## License
[MIT](https://choosealicense.com/licenses/mit/)




