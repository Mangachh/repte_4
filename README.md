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

## Endpoints
- ### Author:
    - **POST** host:3000/cbs/author?name={input_name}&surname={input_surname} -> adds an author to the database
    - **GET** host:3000/cbs/author/all -> gets a list of all the authors in the database
    - **GET** host:3000/cbs/author/{author_id} -> gets an author by it's id, if exists
    - **PUT** host:3000/cbs/author/{author_id}?name={new_name}&surname={new_surname} -> updates author's Name, surname OR both if exist.
    - **DELETE** host:3000/cbs/author/{author_id} -> deletes the author with the id provided, if exists 
  
- ### Book:
    - **POST** host:3000/cbs/book?name={input_name}&isbn={input_isbn} -> adds a book into the database using the *isbn* as primary key
    - **GET** host:3000/cbs/book/all -> gets a list of all the books in the database
    - **GET** host:3000/cbs/book/{book_isbn} -> gets a book by it's isbn, if exists
    - **PUT** host:3000/cbs/book/{book_isbn}?name={new_name} -> updates book's Name if exist.
    - **DELETE** host:3000/cbs/book/{book_isbn} -> deletes the book with the provided isbn, if exists 

## Testing
There are various tests provided in the project. There are tests for the repositories and for the controllers. In addition, a Postman Collection is provided.
The application is fully tested and works well.

## License
[MIT](https://choosealicense.com/licenses/mit/)




