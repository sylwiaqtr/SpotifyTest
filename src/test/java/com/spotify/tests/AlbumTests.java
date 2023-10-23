package com.spotify.tests;

import com.spotify.data.TestData;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class AlbumTests extends BaseAnnotations{

    @Test(dataProvider = "albums", dataProviderClass = TestData.class)
    public static void timeOfGettingAlbumTracks(String id){
        RestAssured.basePath="/albums";
        given().header("Authorization", "Bearer " + token)
                .when().get(id+"/tracks").then().statusCode(200)
                .time(lessThan(800L));
    }
}
