package com.spotify.tests;

import com.spotify.data.TestData;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PlaylistTests extends BaseAnnotations{

    @BeforeClass
    public void setBasePath(){RestAssured.basePath="/playlists";}

    @Test(dataProvider = "playlists", dataProviderClass = TestData.class)
    public static void getNameAndLengthOfPlaylist(String id, String name, int numberOfTracks, int duration){
        given().header("Authorization", "Bearer " + token)
                .when().get(id).then().statusCode(200)
                .body("name", equalTo(name))
                .body("tracks.total", equalTo(numberOfTracks));
    }

    @Test(dataProvider = "playlists", dataProviderClass = TestData.class)
    public static void verifyLengthOfPlaylistInMinutes(String id, String name, int numberOfTracks, int duration){
        String response = given().header("Authorization", "Bearer " + token)
                .param("limit", 50)
                .when().get(id+"/tracks").then().statusCode(200).extract().asString();

        JsonPath jsonPath = new JsonPath(response);

        // get number of response pages as one page has limitation up to 50 objects
        int noOfPages= (int)Math.ceil(jsonPath.getDouble("total")/jsonPath.getDouble("limit"));
        int offset = 0;
        int sumOfDuration=0;

        // iterate over every page to calculate the length of playlist by adding duration of every track
        for(int i=0; i<noOfPages; i++){
            String specificResponse = given().header("Authorization", "Bearer " + token)
                    .param("limit", 50)
                    .param("offset", offset)
                    .when().get(id+"/tracks").then().statusCode(200).extract().asString();
            JsonPath jsonPath2 = new JsonPath(specificResponse);
            int itemsOnPage = jsonPath2.getInt("items.size()");
            for(int j=0; j<itemsOnPage; j++){
                sumOfDuration = sumOfDuration + jsonPath2.getInt("items["+j+"].track.duration_ms");
            }
            offset = offset +50;
        }

        long totalTime = Math.round((sumOfDuration/1000)/60);
        Assert.assertTrue(totalTime <= duration+1);
        Assert.assertTrue(totalTime >= duration-1);
    }
}
