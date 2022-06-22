# Introduction
This project is an implementation of the grep bash command on Java. It parses through all the files in a given root directory and prints out lines based on a regex defined by the user. The technologies used in this app are core java libraries, lambda functions, streams, and the IntelliJ IDEA IDE. Docker was also used to dockerize the application and uploaded it to DockerHub.

# Quick Start
```
# Download the docker image named humdan123/grep from DockerHub.

# From the root directory of where you want to run the program, build the docker container
docker build -t humdan123/grep .

# Run the docker container
docker run --rm -v `pwd`/data:/data -v `pwd`/log:/log humdan123/grep [regex] [rootDir] [outputFile]
```

# Implemenation
## Pseudocode
The application gets the regex, the root directory, and the output file passed in from the command line. The first thing the application does is save the values using setters. The application lists all the non-directory files in the root directory given using the `listFiles` method which returns it. The files on this list are traversed one by one and all the lines are collected by the `readLines` method. All these lines are then passed to the `containsPattern` method which removes all lines that do not match the given regex. All the lines that match the regex are then printed on the output file using the `writeToFile` method.

## Performance Issue
Depending on the size of the root directory, Java could run into an `OutOfMemory` error due to the usage of lists. A way to fix this issue is to use streams and lambda functions. This way nothing is saved on memory unless it is needed and therefore Java will not encounter the `OutOfMemory` error.

# Test
I created some test cases for each method in the application as well as created some sample files to check for edge cases. I changed the CLI inputs, such as the regex, or the root directory to ensure that the code did not have any bugs.
# Deployment
This program was deployed using Docker. A dockerfile was made and included in the GitHub repository to build the code from scratch on another computer.

# Improvement
1. Only work in streams and remove lists and for loops entirely.
2. Cache data so multiple calls of the same query are more efficient.
3. Add an option to print the results to the command line instead.
