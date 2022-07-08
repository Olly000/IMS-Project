# ims

ims is an inventory management system that allows the user to create, read, update and delete 
customers, items and orders from a MySQL database.  
  
The application is written in Java and MySQL, with Maven being used as a dependency management and build tool. For testing JUnit is used for unit testing, and running the test suite; and Mockito is used for
integration testing.  

It is accessed through a simple command line interface, with available options being displayed as
text above the command line prompt.  For this reason further operating instructions are largely unnecessary.

## Getting Started

### Installation

This application can currently be run in two ways:  
1. By downloading the file ims-0.0.1-jar-with-dependencies.jar from the builds folder, navigating to the 
file's location on your computer in the command line, then executing the command:  
  
  
`java -jar ims-0.0.1-jar-with-dependencies.jar  `
  
This runs a compiled build of the application with all dependencies included.  

2. Alternatively fork and clone this repository, then open in your IDE of choice. The application can be started by 
running the Runner class script found at:

`src/main/java/com/qa/ims/Runner.java`  
  


        

### Dependencies

The dependencies required to build and run the application are detailed in the 
`pom.xml` file.  
  
Additionally, users require the Java 15 JDK and MySQL installed on their local machine.

### Running the tests

Unfortunately, it has not been possible to create a working CI pipeline that automatically
runs the unit and integration tests.  However, the tests can be run by installing the application
using option 2 above and running all the tests in the IDE.  

Test coverage is currently 61% of lines, a summary can be found in the `additional documents` 
folder, along with a report of test results derived from the current build version

## Additional documentation  

The `additional documents` folder contains the following documentation for the project:

* ERDs and UML diagrams from the start and end of the project  
* Project risk assessment
* Powerpoint slides for the end of project presentation
* Test reports -  results of individual tests and the test coverage summary from the current build

Further details of the project build can be found on the Jira board for the project at:   
`https://olly000.atlassian.net/jira/software/projects/IP/boards/4/backlog`  



### Issues/TODOs

* Resolve Java versioning issue with automated test running in order to create a working CI pipeline
* Add validation to ItemDAO.amendStockLevel() so that orders with quantities greater than the number in stock
cannot be created
* Complete the Extension to CRUD functionality sprint to add useful functions to application
* Implement user system to introduce greater security and to allow user activity tracking for alterations to the 
database


## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Versioning

We use [SemVer](http://semver.org/) for versioning.

## Authors

* **Oliver Stockman** - *Initial project set-up and skeleton created by* - [JHarry444](https://github.com/JHarry444)

## License

This project is licensed under the MIT license - see the [LICENSE.md](LICENSE.md) file for details 



## Acknowledgments

Thanks to Christopher Yiangou, Andrew McCall and my fellow students in the 22JuneEnable1 cohort for
assistance and support in building this application.