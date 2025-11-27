# Automation Test Framework

A comprehensive Java-based automation testing framework designed for both UI and API testing using BDD (Behavior-Driven Development) with Cucumber, TestNG, Selenium WebDriver, and REST Assured.

## Features

- **BDD with Cucumber**: Write test scenarios in Gherkin syntax for clear, readable test cases.
- **UI Testing**: Selenium WebDriver integration for browser automation.
- **API Testing**: REST Assured for API validation.
- **Dependency Injection**: Google Guice for managing dependencies and shared state.
- **Thread-Safe Context**: Custom `TestContext` utility for maintaining test data across steps in parallel executions.
- **Cross-Browser Support**: Configurable WebDriver setup with Chrome (easily extensible to other browsers).
- **Soft Assertions**: Accumulate assertions and report failures at the end of scenarios.
- **Retry Mechanism**: Automatic retry of failed test scenarios with configurable retry count and detailed logging.
- **Logging**: Integrated logging with SLF4J and Logback for detailed test execution logs. Logs are saved per test scenario in the `logs/` folder at the project root.
- **Test Reporting**: Generates Allure reports for comprehensive test results, including automatic screenshot attachments and API request/response details.
- **Screenshot Capture**: Full-page screenshots captured and attached to Allure reports for each test scenario.

## Prerequisites

- **Java**: JDK 17 or higher
- **Maven**: 3.6 or higher
- **Chrome Browser**: For UI tests (WebDriverManager handles driver setup automatically)
- **Internet Connection**: For downloading dependencies and WebDriver binaries

## Project Structure

```
ProjectAutoTestFramework/
├── pom.xml                                    # Maven configuration
├── README.md                                  # This file
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── automation/
│   │   │           └── framework/
│   │   │               ├── config/           # Configuration classes (PropertiesLoader, TestModule, etc.)
│   │   │               ├── driver/           # WebDriver management (DriverManager)
│   │   │               ├── pages/            # Page Object Model classes
│   │   │               ├── utils/            # Utility classes (TestContext, AssertionUtils, Retry, etc.)
│   │   └── resources/                        # Main resources (if any)
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── automation/
│       │           └── framework/
│       │               ├── runners/          # Test runners (BaseTestNGCucumberTests, APIRunner, UIRunner)
│       │               └── steps/            # Step definitions (APISteps, UISteps)
│       └── resources/
│           ├── config/                       # Environment-specific properties
│           ├── features/                     # Cucumber feature files
│           │   ├── api/                      # API test features
│           │   └── ui/                       # UI test features
│           └── logback.xml                   # Logging configuration
├── target/                                   # Build output (compiled classes, reports)
├── logs/                                     # Test execution logs (per scenario)
└── allure-results/                           # Allure test results
```

## Setup Instructions

1. **Clone or Download the Project**:
   ```
   git clone <repository-url>
   cd ProjectAutoTestFramework
   ```

2. **Install Dependencies**:
   ```
   mvn clean install
   ```
   This will download all required dependencies and compile the project.

3. **Configure Environment Properties**:
   - Update properties files in `src/test/resources/config/` for different environments (dev, qa, etc.).
   - Example: `dev.properties` might contain:
     ```
     ui.endpoint=https://example.com
     api.endpoint=https://api.example.com
     ```

## Writing Tests

### UI Tests
- Feature files go in `src/test/resources/features/ui/`
- Step definitions in `src/test/java/com/automation/framework/steps/ui/UISteps.java`
- Use Page Object Model in `src/main/java/com/automation/framework/pages/`

### API Tests
- Feature files in `src/test/resources/features/api/`
- Step definitions in `src/test/java/com/automation/framework/steps/api/APISteps.java`

### Using TestContext
Store and retrieve test data across steps:
```java
// In a step definition
TestContext.set("userId", "12345");

// In another step
String userId = TestContext.get("userId", String.class);
```

## Running Tests

### Run All Tests
```
mvn test
```

### Run Specific Test Suite
- **API Tests Only**:
  ```
  mvn test -Dtest=APIRunner
  ```
- **UI Tests Only**:
  ```
  mvn test -Dtest=UIRunner
  ```

### Run with Specific Tags
```
mvn test -Dcucumber.filter.tags="@SmokeTest"
```

### Generate Allure Reports
1. Run tests
2. Generate report:
   ```
   mvn allure:report
   ```
3. Serve report:
   ```
   mvn allure:serve
   ```
   Allure reports include detailed test results, logs, automatically attached screenshots, and API request/response details for comprehensive debugging.

### View Test Logs
After running tests, individual log files for each test scenario are created in the `logs/` folder. Each log file is named after the scenario (e.g., `Handle multiple windows 01.log`). These logs contain detailed execution information for debugging. Note: The `logs/` folder is tracked in Git, but individual log files are ignored.

## Configuration

- **WebDriver**: Configured in `DriverManager.java` with Chrome options. Modify for headless mode or other browsers.
- **Logging**: Configured via `logback.xml` in `src/test/resources/`
- **TestNG**: Parallel execution can be configured in `testng.xml` or via Maven Surefire plugin.
- **Retry Mechanism**: Configure retry count in `src/test/resources/config/global.properties` using the `retry.count` property (default: 0). Failed scenarios will be retried up to this number of times with detailed logging for each retry attempt.

## Retry Mechanism

The framework includes an automatic retry mechanism for failed test scenarios:

- **Configuration**: Set the maximum retry count in `global.properties` with `retry.count=2` (or any desired number)
- **Implementation**: Uses TestNG's `IRetryAnalyzer` interface implemented in `Retry.java`
- **Logging**: Each retry attempt is logged with scenario name and attempt number
- **Thread Safety**: Retry count is maintained per thread using `ThreadLocal` for parallel execution support
- **Integration**: All test runners extend `BaseTestNGCucumberTests` which applies the retry analyzer to all scenarios

When a scenario fails, it will be automatically retried up to the configured maximum attempts. Logs will show retry attempts and final failure status.

## Test Execution Configuration (testng.xml)

The framework uses `testng.xml` for orchestrating test execution, which provides centralized control over test suites and execution parameters.

### Why testng.xml is Needed

- **Suite Organization**: Defines logical test suites and groups related tests together
- **Parallel Execution Control**: Configures thread counts and parallel execution modes (`methods`, `classes`, `tests`)
- **Selective Test Execution**: Allows running specific test classes or groups without code changes
- **Maven Integration**: Works seamlessly with Maven Surefire plugin for CI/CD pipelines
- **TestNG Features**: Leverages TestNG's built-in suite configuration capabilities

### Pros of Using testng.xml

- **Flexibility**: Easy to modify test execution without touching code
- **CI/CD Friendly**: Standard approach for test automation in enterprise environments
- **Grouping**: Can create different suites (smoke, regression, integration) using the same code
- **Parallel Control**: Fine-grained control over parallel execution parameters
- **TestNG Ecosystem**: Compatible with TestNG listeners, reporters, and other extensions

### Cons of Using testng.xml

- **Additional File**: One more configuration file to maintain
- **Maven Coupling**: Tightly coupled with Maven Surefire plugin configuration
- **Learning Curve**: Requires understanding of TestNG XML syntax

### Alternative: Removing testng.xml

If you prefer to eliminate `testng.xml`, here's how to do it:

#### What to Update:

1. **pom.xml**: Modify the Surefire plugin configuration:
   ```xml
   <configuration>
       <parallel>methods</parallel>
       <threadCount>5</threadCount>
       <includes>
           <include>**/*Runner.java</include>
       </includes>
       <!-- Remove suiteXmlFiles configuration -->
   </configuration>
   ```

2. **Test Runners**: Ensure runners have proper TestNG annotations for parallel execution if needed.

#### Cons of Removing testng.xml:

- **Less Flexibility**: Harder to create different test suites without code changes
- **Parallel Configuration**: Parallel settings must be managed in pom.xml or code
- **CI/CD Complexity**: May require more complex Maven commands for different test scenarios
- **TestNG Features Loss**: Limited access to TestNG's suite-level configurations

### Recommendation

**Keep testng.xml** for production use as it provides better control and flexibility for test execution management. The current setup with `testng.xml` is industry-standard and well-suited for enterprise test automation frameworks.

## Key Classes

- **BaseSteps**: Abstract base class with injected dependencies (WebDriver, SoftAssert, etc.)
- **BaseTestNGCucumberTests**: Abstract base class for Cucumber TestNG runners with retry analyzer configuration
- **TestContext**: Thread-safe utility for sharing data across steps
- **DriverManager**: Manages WebDriver instances with ThreadLocal
- **PropertiesLoader**: Loads environment-specific configurations
- **Retry**: TestNG retry analyzer implementation for automatic test retries with configurable count
- **ScreenshotUtils**: Utility for capturing full-page or viewport screenshots and attaching them to Allure reports

## Best Practices

- Use Page Object Model for UI tests to separate logic from test steps.
- Leverage `TestContext` for passing data between steps instead of instance variables.
- Write descriptive Gherkin scenarios.
- Use soft assertions for non-blocking validations.
- Clean up resources in `@After` hooks.

## Troubleshooting

- **WebDriver Issues**: Ensure Chrome is installed and WebDriverManager can download the driver.
- **Port Conflicts**: If running multiple instances, ensure no port conflicts.
- **Memory Issues**: For large test suites, increase JVM memory: `mvn test -Xmx2g`

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make changes and add tests
4. Run tests: `mvn test`
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For issues or questions, please create an issue in the repository or contact the development team.