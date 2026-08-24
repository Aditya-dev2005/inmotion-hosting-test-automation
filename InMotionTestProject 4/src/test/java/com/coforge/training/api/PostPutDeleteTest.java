package com.coforge.training.api;

import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * PostPutDeleteTest
 * - Demonstrates POST, PUT, DELETE with REST Assured.
 * - Uses JSONPlaceholder which accepts and echoes back payloads.
 *
 * Concepts covered:
 *   1. POST with JSON body using JSONObject
 *   2. Validating 201 Created response
 *   3. PUT to update a resource
 *   4. DELETE and verify 200/204
 *   5. Wrong HTTP verb test (negative)
 */
public class PostPutDeleteTest extends ApiBaseTest {

    // -------------------------------------------------------
    // TC_API_011 - POST /posts - Create a new post
    // -------------------------------------------------------
    @Test(groups = {"api", "smoke"}, description = "POST /posts creates a new resource")
    public void verifyPostRequestCreatesResource() {
        // Build JSON body using JSONObject (as taught in training)
        JSONObject requestBody = new JSONObject();
        requestBody.put("title",  "InMotion Hosting Test Post");
        requestBody.put("body",   "This post was created by REST Assured automation.");
        requestBody.put("userId", 1);

        Response response = given()
                .spec(requestSpec)
                .header("Content-Type", "application/json")
                .body(requestBody.toJSONString())
        .when()
                .post(postsEndpoint);

        int statusCode = response.getStatusCode();
        System.out.println("POST Status Code: " + statusCode);
        System.out.println("Response Body: " + response.asString());

        // JSONPlaceholder returns 201 for POST
        Assert.assertEquals(statusCode, 201,
                "Expected 201 Created but got: " + statusCode);
    }

    // -------------------------------------------------------
    // TC_API_012 - POST and verify returned body fields
    // -------------------------------------------------------
    @Test(groups = {"api", "regression"}, description = "POST /posts - verify response body fields")
    public void verifyPostResponseBodyFields() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("title",  "Coforge Training Project");
        requestBody.put("body",   "Automation test from Coforge training at InMotion.");
        requestBody.put("userId", 5);

        given()
                .spec(requestSpec)
                .body(requestBody.toJSONString())
        .when()
                .post(postsEndpoint)
        .then()
                .statusCode(201)
                .body("title",  equalTo("Coforge Training Project"))
                .body("userId", equalTo(5))
                .body("id",     notNullValue());
    }

    // -------------------------------------------------------
    // TC_API_013 - PUT /posts/{id} - Update an existing post
    // -------------------------------------------------------
    @Test(groups = {"api", "regression"}, description = "PUT /posts/1 updates the resource")
    public void verifyPutRequestUpdatesResource() {
        JSONObject updateBody = new JSONObject();
        updateBody.put("id",     1);
        updateBody.put("title",  "Updated Title via REST Assured");
        updateBody.put("body",   "Updated body content.");
        updateBody.put("userId", 1);

        given()
                .spec(requestSpec)
                .body(updateBody.toJSONString())
                .pathParam("id", 1)
        .when()
                .put(postsEndpoint + "/{id}")
        .then()
                .statusCode(200)
                .body("title", equalTo("Updated Title via REST Assured"));
    }

    // -------------------------------------------------------
    // TC_API_014 - DELETE /posts/{id}
    // -------------------------------------------------------
    @Test(groups = {"api", "regression"}, description = "DELETE /posts/1 deletes the resource")
    public void verifyDeleteRequest() {
        given()
                .spec(requestSpec)
                .pathParam("id", 1)
        .when()
                .delete(postsEndpoint + "/{id}")
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(204)));
    }

    // -------------------------------------------------------
    // TC_API_015 - Negative: Send GET to a POST-only endpoint
    // -------------------------------------------------------
    @Test(groups = {"api", "regression"},
          description = "Sending wrong HTTP verb - GET to POST resource")
    public void verifyWrongHttpVerbReturnsError() {
        // JSONPlaceholder is lenient, but this pattern demonstrates the concept.
        // On a real API (e.g., InMotion API) this would return 405 Method Not Allowed.
        Response response = given()
                .spec(requestSpec)
        .when()
                .get(postsEndpoint + "/9999999"); // non-existent resource with GET

        int statusCode = response.getStatusCode();
        System.out.println("Wrong verb test - Status Code: " + statusCode);
        System.out.println("Response Body: " + response.body().asString());

        // Verify it is not a 2xx success for a truly missing resource
        Assert.assertTrue(statusCode == 404 || statusCode >= 400,
                "Expected 4xx error for non-existent resource, got: " + statusCode);
    }

    // -------------------------------------------------------
    // TC_API_016 - POST with incomplete data (negative)
    // -------------------------------------------------------
    @Test(groups = {"api", "regression"},
          description = "POST with empty body - should handle gracefully")
    public void verifyPostWithEmptyBody() {
        Response response = given()
                .spec(requestSpec)
                .body("{}")
        .when()
                .post(postsEndpoint);

        System.out.println("Empty POST Status Code: " + response.getStatusCode());
        System.out.println("Response: " + response.asString());
        // JSONPlaceholder accepts empty body (201). On a real API this would be 400.
        // Demonstrate we can inspect the result either way.
        Assert.assertTrue(
                response.getStatusCode() >= 200 && response.getStatusCode() < 500,
                "Unexpected server error for empty POST body."
        );
    }
}
