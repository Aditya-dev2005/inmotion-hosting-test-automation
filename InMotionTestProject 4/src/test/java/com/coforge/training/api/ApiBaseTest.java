package com.coforge.training.api;

import com.coforge.training.utils.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;

import static org.hamcrest.Matchers.lessThan;

/**
 * ApiBaseTest
 * - Sets up shared RequestSpec and ResponseSpec for all API tests.
 * - Reads base URI from config.properties.
 */
public class ApiBaseTest {

    protected RequestSpecification requestSpec;
    protected ResponseSpecification responseSpec;

    protected String baseUri;
    protected String usersEndpoint;
    protected String postsEndpoint;
    protected String commentsEndpoint;

    @BeforeClass
    public void apiSetUp() {
        baseUri          = ConfigReader.get("api.base.uri");
        usersEndpoint    = ConfigReader.get("api.users.endpoint");
        postsEndpoint    = ConfigReader.get("api.posts.endpoint");
        commentsEndpoint = ConfigReader.get("api.comments.endpoint");

        requestSpec = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectResponseTime(lessThan(5000L))
                .log(LogDetail.ALL)
                .build();

        RestAssured.requestSpecification = requestSpec;
    }
}
