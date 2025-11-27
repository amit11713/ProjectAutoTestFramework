package com.automation.framework.steps.api;

import com.automation.framework.steps.BaseSteps;
import com.automation.framework.utils.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.After;
import io.restassured.response.Response;
import io.qameta.allure.Allure;
import static io.restassured.RestAssured.given;

/**
 * API Step definitions with soft assertions.
 */
public class APISteps extends BaseSteps {

    private Response response;

    @After
    public void tearDown() {
        logger.info("Cleaning up TestContext after API test");
        TestContext.cleanup();
    }

    @Given("I have the API endpoint for users")
    public void iHaveTheAPIEndpointForUsers() {
        logger.info("Setting up API endpoint for users");
        String baseUri = getProperty("api.endpoint", "https://jsonplaceholder.typicode.com");
        requestSpec.baseUri(baseUri); // Example API
        requestSpec.relaxedHTTPSValidation(); // In case of self-signed certificates
        // Store base URI in context
        TestContext.set("baseUri", baseUri);
    }

    @When("I send a GET request to retrieve user data")
    public void iSendAGETRequestToRetrieveUserData() {
        logger.info("Sending GET request to /users/1");
        String baseUri = TestContext.get("baseUri", String.class);
        String requestUrl = baseUri + "/users/1";
        String requestDetails = "Method: GET\nURL: " + requestUrl + "\nPayload: N/A (GET request)";

        response = given(requestSpec)
                .when()
                .get("/users/1");

        // Attach request details to Allure
        Allure.addAttachment("Request Details", "text/plain", requestDetails, ".txt");

        // Attach response details to Allure
        String responseDetails = "Status Code: " + response.getStatusCode() + "\nStatus Line: " + response.getStatusLine() + "\nBody:\n" + response.getBody().asString();
        Allure.addAttachment("Response Details", "text/plain", responseDetails, ".txt");

        // Store response in context for use in other steps
        TestContext.set("apiResponse", response);
    }

    @Then("I should receive a valid response with status 200")
    public void iShouldReceiveAValidResponseWithStatus200() {
        logger.info("Verifying response status and content");
        // Retrieve response from context
        Response apiResponse = TestContext.get("apiResponse", Response.class);
        softAssert.assertNotNull(apiResponse, "API response should be available");
        softAssert.assertEquals(apiResponse.getStatusCode(), 200, "Status code should be 200");
        softAssert.assertTrue(apiResponse.getBody().asString().contains("id"), "Response should contain user data");
        softAssert.assertAll(); // Report failures at end
    }
}