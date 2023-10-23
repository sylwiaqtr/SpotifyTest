package com.spotify.tests;

import com.spotify.data.TestData;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TrackTests extends BaseAnnotations{

    @BeforeClass
    public void setBasePath(){RestAssured.basePath="/tracks";}

    @Test(dataProvider = "tracks", dataProviderClass = TestData.class)
    public static void verifyGetTrackJsonSchema(String id){
        given().header("Authorization", "Bearer " + token)
                .when().get(id)
                .then().statusCode(200)
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("gettrack-schema.json"));

    }

    @Test(dataProvider = "invalidTrack", dataProviderClass = TestData.class)
    public static void failToGetTrack(String id){
        given().header("Authorization", "Bearer " + token)
                .when().get(id).then().statusCode(400);

    }
}
