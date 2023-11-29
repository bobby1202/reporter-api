# Reporter Application

The Reporter Application is responsible for handling requests related to the reporting functionality of incident data.


## Getting Started

Clone the repository:

   ```bash
   git clone https://github.com/bobby1202/reporter-api.git
   ```

## Running the application
Run the following commands:

- mvn clean install (Build's the Application)
- mvn spring-boot:run (Run's the application)

Resource endpoints:
-------------------
Use API tools like Post Man to test the end point

***For Generating aggregated incidents report***

`POST` `http://localhost:3026/api/dashboard`
