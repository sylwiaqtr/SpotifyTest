package com.spotify.tests;

import com.spotify.data.TestData;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ArtistTests extends BaseAnnotations{


    @BeforeClass
    public void setBasePath(){RestAssured.basePath="/artists";}

    @Test(dataProvider = "artistsSet", dataProviderClass = TestData.class)
    public static void getArtistsByIds(String ids, int sumOfArtists, List<String> names){
        String response = given()
                .header("Authorization", "Bearer " + token)
                .param("ids", ids)
                .when().get()
                .then().statusCode(200)
                .extract().response().asString();

        JsonPath jsonPath = new JsonPath(response);
        int actualSumOfArtists = jsonPath.getInt("artists.size()");
        List actualArtistsNames = jsonPath.getList("artists.name");

        Assert.assertEquals(actualSumOfArtists, sumOfArtists);
        Assert.assertTrue(actualArtistsNames.containsAll(names));
    }

    @Test(dataProvider = "artists", dataProviderClass = TestData.class)
    public static void getAlbumsOfOneArtist(String id, int numberOfAlbums, int numberOfTracks) {
        String response = given()
                .header("Authorization", "Bearer " + token)
                .param("limit", 50)
                .param("include_groups", "album")
                .when().get(id +"/albums")
                .then().statusCode(200)
                .extract().response().asString();

        JsonPath jsonPath = new JsonPath(response);
        int actualNumberOfAlbums = jsonPath.getInt("items.size()");

        int sumOfTracks=0;
        for (int i=0; i<actualNumberOfAlbums; i++){
            int noOFTracks = jsonPath.get("items["+i+"].total_tracks");
            sumOfTracks += noOFTracks;
        }

        Assert.assertEquals(actualNumberOfAlbums, numberOfAlbums);
        Assert.assertEquals(sumOfTracks, numberOfTracks);
    }

    @Test(dataProvider = "artists", dataProviderClass = TestData.class)

    public static void getTopTracksOfOneArtist(String id, int numberOfAlbums, int numberOfTracks) {
        String response = given()
                .header("Authorization", "Bearer " + token).param("market", "PL")
                .when().get(id+"/top-tracks")
                .then().statusCode(200)
                .extract().response().asString();

        JsonPath jsonPath = new JsonPath(response);
        int actualNumberOfTopTracks = jsonPath.getInt("tracks.size()");

        for (int i=0; i<actualNumberOfTopTracks; i++){
           assertThat(jsonPath.get("tracks["+i+"].popularity"), lessThanOrEqualTo(100));
           assertThat(jsonPath.get("tracks["+i+"].popularity"), greaterThanOrEqualTo(0));
        }
        assertThat(actualNumberOfTopTracks, equalTo(10));
    }
}

