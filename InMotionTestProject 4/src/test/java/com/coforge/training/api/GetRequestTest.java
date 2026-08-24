package com.coforge.training.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * GetRequestTest
 * - Demonstrates REST Assured GET requests as taught in training.
 * - Uses JSONPlaceholder (free public REST API) to mimic real API testing.
 *
 * Concepts covered:
 *   1. Simple GET and status code validation
 *   2. Response header validation
 *   3. JSON body extraction with jsonPath()
 *   4. Response time assertion
 *   5. Query parameter usage
 *   6. Path parameter usage
 *   7. BDD-style Given/When/Then chaining
 */
public class GetRequestTest extends ApiBaseTest {

    // -------------------------------------------------------
    // TC_API_001 - Verify GET /users returns 200
    // -------------------------------------------------------
    @Test(groups = {"api", "smoke"}, description = "GET /users returns HTTP 200")
    public void verifyGetUsersStatusCode() {
        RequestSpecification request = RestAssured.given().spec(requestSpec);
        Response response = request.get(usersEndpoint);

        int statusCode = response.getStatusCode();
        System.out.println("Status Code: " + statusCode);

        Assert.assertEquals(statusCode, 200,
                "Expected status code 200 but received: " + statusCode);
    }

    // -------------------------------------------------------
    // TC_API_002 - Verify response status line
    // -------------------------------------------------------
    @Test(groups = {"api", "smoke"}, description = "GET /users status line is 200 OK")
    public void verifyStatusLine() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get(usersEndpoint);

        String statusLine = response.getStatusLine();
        System.out.println("Status Line: " + statusLine);
        Assert.assertTrue(statusLine.contains("200"),
                "Status line does not contain '200': " + statusLine);
    }

    // -------------------------------------------------------
    // TC_API_003 - Validate Content-Type response header
    // -------------------------------------------------------
    @Test(groups = {"api", "regression"}, description = "GET /users Content-Type is application/json")
    public void verifyResponseContentTypeHeader() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get(usersEndpoint);

        String contentType = response.header("Content-Type");
        System.out.println("Content-Type: " + contentType);
        Assert.assertTrue(contentType.contains("application/json"),
                "Content-Type is not JSON. Actual: " + contentType);
    }

    // -------------------------------------------------------
    // TC_API_004 - Print all response headers
    // -------------------------------------------------------
    @Test(groups = {"api", "regression"}, description = "Print all response headers from GET /users")
    public void printAllResponseHeaders() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get(usersEndpoint);

        response.headers().forEach(header ->
                System.out.println(header.getName() + " : " + header.getValue())
        );
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    // -------------------------------------------------------
    // TC_API_005 - Extract and verify JSON body using jsonPath
    // -------------------------------------------------------
    @Test(groups = {"api", "regression"}, description = "Verify user list is not empty")
    public void verifyUserListIsNotEmpty() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get(usersEndpoint);

        List<Object> users = response.jsonPath().getList("$");
        System.out.println("Total users returned: " + users.size());
        Assert.assertFalse(users.isEmpty(), "User list is empty — API returned no users.");
        Assert.assertTrue(users.size() >= 1, "Expected at least 1 user.");
    }

    // -------------------------------------------------------
    // TC_API_006 - Verify specific field in JSON response
    // -------------------------------------------------------
    @Test(groups = {"api", "regression"}, description = "Verify first user's name is present")
    public void verifyFirstUserNameIsNotNull() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get(usersEndpoint);

        String firstName = response.jsonPath().getString("[0].name");
        System.out.println("First user name: " + firstName);
        Assert.assertNotNull(firstName, "First user's name is null.");
        Assert.assertFalse(firstName.isEmpty(), "First user's name is empty.");
    }

    // -------------------------------------------------------
    // TC_API_007 - Path Parameter: GET /users/{id}
    // -------------------------------------------------------
    @Test(groups = {"api", "regression"}, description = "GET single user by ID")
    public void verifySingleUserById() {
        int userId = 1;

        Response response = given()
                .spec(requestSpec)
                .pathParam("id", userId)
                .when()
                .get(usersEndpoint + "/{id}");

        Assert.assertEquals(response.getStatusCode(), 200);
        int returnedId = response.jsonPath().getInt("id");
        System.out.println("Returned user ID: " + returnedId);
        Assert.assertEquals(returnedId, userId,
                "Returned user ID does not match requested ID.");
    }

    // -------------------------------------------------------
    // TC_API_008 - Query Parameter: GET /posts?userId=1
    // -------------------------------------------------------
    @Test(groups = {"api", "regression"}, description = "GET posts by userId query parameter")
    public void verifyPostsByQueryParam() {
        Response response = given()
                .spec(requestSpec)
                .queryParam("userId", 1)
                .when()
                .get(postsEndpoint);

        Assert.assertEquals(response.getStatusCode(), 200);
        List<Object> posts = response.jsonPath().getList("$");
        System.out.println("Posts for userId=1: " + posts.size());
        Assert.assertFalse(posts.isEmpty(), "No posts returned for userId=1.");
    }

    // -------------------------------------------------------
    // TC_API_009 - BDD-style Given/When/Then with Hamcrest matchers
    // -------------------------------------------------------
    @Test(groups = {"api", "regression"}, description = "BDD-style GET /posts validation")
    public void verifyPostsWithBddStyle() {
        given()
                .spec(requestSpec)
        .when()
                .get(postsEndpoint)
        .then()
                .statusCode(200)
                .contentType("application/json")
                .body("$", hasSize(greaterThan(0)))
                .body("[0].title", notNullValue())
                .body("[0].body",  notNullValue());
    }

    // -------------------------------------------------------
    // TC_API_010 - Negative: GET /users/{id} with invalid ID → 404
    // -------------------------------------------------------
    @Test(groups = {"api", "regression"}, description = "GET with invalid userId returns 404")
    public void verifyInvalidUserIdReturns404() {
        given()
                .spec(requestSpec)
                .pathParam("id", 9999)
        .when()
                .get(usersEndpoint + "/{id}")
        .then()
                .statusCode(404);
    }
}
