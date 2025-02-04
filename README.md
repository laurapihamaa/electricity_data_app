# Instructions for running the application

The application is runnable with a docker compose file

1.  On command line under this folder run:

docker compose up --build

The application will be running at: localhost:80

You can also run the application without docker with the following commands:

1. Set up database (docker): 

Run the database by running only the db and adminer from docker compose file

2. Run the backend application:

cd .\backend\v1

mvn spring-boot:run

3. Run the frontend application:
   
cd .\frontend

npm install

npm start 

The application will be running at: localhost:3000


# Some notes about the application

# Architecture

The backend is designed in the MVC-typish approach. I chose this style mainly for modularity as I could have clear separation of tasks with the controller handling the requests, services handling any business logic and the frontend is simply for displaying the contents. The question wheter the service layer was actually needed can be valid as the application is relatively small and the business logic handling could be done in the controller as well.
I still decided to create the service layer as I felt like adding all of the business logic to the controller would have made it too complex. But I guess that this is a personal preference.

# Implementation

I decided to go with the approach that most of the data querying would be performed straight through the SQL-quering which meant in my case that searching, pagination and ordering (mostly)
was handled directly in a sql-query. The main reason behind this was the efficiency of querying the table and retrieving only necessary data. 

The cons with this approach was having more complexity in the SQL-queries and to avoid that I decied to also implement some order handling and pagination by code while ordering the longest concecutive hours. So I finally ended up with a hybrid-approach with doing the simple logic via SQL queries and more complex logic via code. 

This of course adds complexity as every data is not treated as the same but for me it felt like the best approach with combaining the "best of both worlds" in a way.

# Testing

For the backend I reached 100% test coverage for controller and 90% test coverage for services. I find it sufficient but with more time I would implement more tests.
I decided to create some integration tests to test the functionality of sending requests to database. For the integration tests I decided to use the actual database because most of the tests were only to retrieve data and beacuse it is only a practice assesment it didnt matter that much.

