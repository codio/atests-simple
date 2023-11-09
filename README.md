
## Quick start
1. Clone the git repo — git clone https://github.com/codio/atests-simplegit .git 
2. Install java [jdk](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
3. Install [maven](http://maven.apache.org/download.cgi)
4. In src/test/resources/config.properties file set teacher (teacher should be already added to organization) and students (this example needs 4 test students, no matter already added to org, or new) credentials, like:
```
teacher.login = loginhere
teacher.email = email@adress.here
teacher.firstName = namehere
teacher.lastName = lastnamehere
teacher.password = passwordhere

student1.login = student1loginhere
... and so on
```
5. First run src/test/java/helpers/COneCreateCourseHelper (`mvn clean -Dtest=COneCreateCourseHelper test`). It will create test course
6. To run all tests go to project's root and on command line run `mvn clean test` or import project as maven module to IDE and run as junit tests
7. To see results of tests in Allure go to project's root and on command line run `mvn allure:serve`

## Run tests
It's possible to specify properties (such as environment, runType etc) in src/test/resources/config.properties file.
By default tests run locally on prod in Chrome browser
Also some properties can be specified as arguments in command line
To run tests from command line go to project's root and use commands in terminal:
- `mvn clean test` - runs all tests with default config
- `mvn clean test -DbaseUrl="prod"` - runs tests on prod. Acceptable values: prod
- `mvn clean test -Dbrowser="firefox"` - runs tests with firefox browser. Browser should be installed in OS. Acceptable values: chrome, firefox
- `mvn clean test -Dheadless="true"` - runs tests in headless mode. Acceptable values: true, false
- `mvn clean -Dtest=COneVariablesPageTryItOneByOneTest test` - runs all tests from LoginTest class
- `mvn clean -Dtest=*OneByOne* test` - runs tests from classes whose name contains "Login"
- `mvn clean -Dtest=COneVariablesPageTryItOneByOneTest#secondTryIt test` - runs secondTryIt test from COneVariablesPageTryItOneByOneTest class
- `mvn clean -Dgroups="content, assignment" test` - runs all tests with `@Tag("course")` and all tests with `@Tag("project")`

[Table](https://docs.google.com/spreadsheets/d/1u3yHkny4RLEJS2v31S35cGjmaQUZtZe4GdhUfSwBalU/edit?usp=sharing) of available tests with tags

More info about running tests by maven [there](https://maven.apache.org/surefire-archives/surefire-2.22.1/maven-surefire-plugin/examples/single-test.html)

## Code style checks

### Auto-formatting

A code could be formatted in automatic way with Maven command:

```bash
mvn spotless:apply
```

Formatting could be checked with Maven command:

```bash
mvn spotless:check
```

### Additional style checks

Additional rules could be checked with Maven command:

```bash
mvn checkstyle:checkstyle
```

If this command fails, tests can't be run by maven (e.g. by `mvn clean test` command). To fix it `mvn spotless:apply` should be run. Sometimes manual fixing needed