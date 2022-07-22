# Introduction
This project is an utilized the Twitter REST API in order to send requests to either post, look up or delete a tweet. The application takes user input via the command line and converts it into a request which is sent to Twitter using their REST API. Twitter then returns the Tweet object that was either posted, looked up, or deleted and sends it back to the user. This project uses the Twitter REST API, HTTP Client, maven, Docker, the Spring framework, as well as many other Java libraries.

# Quick Start
Before using the application, you will need to get the tokens associated with your account and set them up as enviornment variables by doing the following:
```
export consumerToken=[yourConsumerToken]
export consumerSecret=[yourConsumerSecret]
export accessToken=[yourAccessToken]
export tokenSecret=[yourTokenSecret]
```
After the enviornment variables have been set, you can run the application using the following command:
```
docker run --rm -e consumerToken=${consumerToken} -e consumerSecret=${consumerSecret} \
-e accessToken=${accessToken} -e tokenSecret=${tokenSecret} humdan123/twitter \
post|show|delete [options]
```

# Design
## UML diagram
![UML Diagram](./assets/uml.png)

## App/Main
This layer is in charge of processing the command line inputs sent in by the user and to send the entered information to the right method in the controller layer. This layer also makes sure the user specified either post, show, or delete in their command.
## Controller
The controller processes the specific command the user sent in and makes sure all the arguments needed to run the specified command are included e.g. if the user is posting a tweet, an id, a longitude and latitude need to be defined. Once it has ensured that all the requirements are met it sends the command into the service layer.
## Service
The service layer handles any business logic, e.g. a tweet cannot exceed 140 characters, or longitude and latitude must be valid etc. After it has ensured that, it passes the values into the TwitterDao.
## DAO
The DAO layer is the data access layer and is responsible for constructing the tweet and a URI that it can send to the Twitter REST API by using the HttpHelper class. This layer is also responsible for parsing through the JSON string that gets returned and construting a simplified tweet object out of it.
## Models
The Tweet model is a simplified version of Twitter's tweet object. It includes the following fields:
```
String createdAt
BigInteger id
String idString
String text
Coordinates coordinates
int retweetCount
int favoriteCount
boolean favorited
boolean retweeted
```
Where Coordinates is class that records the coordinates of the tweet.
## Spring
All the dependencies were handled using SpringBoot. Using the componentscan method, the springboot class finds all the dependencies of the classes in the project and injects them where they are needed.

# Test
Every class has its own integration test using JUnit. Junit was used to give sample tweets and see if the information was posted, retreived or deleted successfully. Every class also had its own unit test which was done using JUnit and Mockitos. Mockitos was used to mock the response from another layer, e.g. in the Controller integration test, the Service layer was mocked. This ensured that our app runs independent of if Twitter is working or not.

## Deployment
Maven was used to build an uber jar of the project and then Docker was used to make an image out of that uber jar. The image was then posted on dockerhub so that anyone can download it and run it without worrying about the dependencies. 

# Improvements
- Add hashtag and user mentioning capabilities
- Add functionality to display all user tweets at once
- Add the ability to search for a tweet by its text