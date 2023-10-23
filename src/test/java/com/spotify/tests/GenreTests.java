package com.spotify.tests;

import com.spotify.data.TestData;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;

public class GenreTests extends BaseAnnotations {
    @Test
    public static void verifyAvailableGenres(){
        RestAssured.basePath="/recommendations/available-genre-seeds";
        given().header("Authorization", "Bearer " + token)
                .when().get()
                .then().statusCode(200)
                .body("genres", hasItems(TestData.genres()))
                .body("genres", not(hasItems("disco polo")));
    }
}
