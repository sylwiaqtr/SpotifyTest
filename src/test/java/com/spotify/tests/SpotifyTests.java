package com.spotify.tests;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.restassured.RestAssured.*;
import io.restassured.matcher.RestAssuredMatchers.*;
import io.restassured.module.jsv.JsonSchemaValidator.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class SpotifyTests extends BaseAnnotations{




    @Test(dataProvider = "artistsSet", dataProviderClass = TestData.class)
    public static void getArtists(String ids, int sumOfArtists, List<String> names){
        RestAssured.basePath="/artists";
        String response = given()
                .header("Authorization", "Bearer " + token)
                .param("ids", ids)
                .when().get()
                .then().statusCode(200)
                .extract().response().asString();

        JsonPath jsonPath = new JsonPath(response);
        Assert.assertEquals(jsonPath.getInt("artists.size()"), sumOfArtists);
        Assert.assertTrue(jsonPath.getList("artists.name").containsAll(names));
    }

    @Test(dataProvider = "artists", dataProviderClass = TestData.class)
    public static void getAlbumsOfArtist(String id, int numberOfAlbums, int numberOfTracks) {
        RestAssured.basePath="/artists";
        String response = given()
                .header("Authorization", "Bearer " + token)
                .param("limit", 50)
                .param("include_groups", "album")
                .when().get(id +"/albums")
                .then().statusCode(200)
                .extract().response().asString();

        JsonPath jsonPath = new JsonPath(response);
        Assert.assertEquals(jsonPath.getInt("items.size()"), numberOfAlbums);
        int sumOfTracks=0;
        for (int i=0; i<jsonPath.getInt("items.size()"); i++){
            int noOFTracks = jsonPath.get("items["+i+"].total_tracks");
            sumOfTracks += noOFTracks;
        }
        Assert.assertEquals(sumOfTracks, numberOfTracks);
    }

    @Test(dataProvider = "artists", dataProviderClass = TestData.class)

    public static void getArtistTopTracks(String id, int numberOfAlbums, int numberOfTracks) {
        RestAssured.basePath="/artists";
        String response = given()
                .header("Authorization", "Bearer " + token).param("market", "PL")
                .when().get(id+"/top-tracks")
                .then().statusCode(200)
                .extract().response().asString();

        JsonPath jsonPath = new JsonPath(response);
        assertThat(jsonPath.getInt("tracks.size()"), equalTo(10));
        for (int i=0; i<jsonPath.getInt("tracks.size()"); i++){
           assertThat(jsonPath.get("tracks["+i+"].popularity"), lessThanOrEqualTo(100));
           assertThat(jsonPath.get("tracks["+i+"].popularity"), greaterThanOrEqualTo(0));
        }
    }

    @Test
    public static void getGenres(){
        RestAssured.basePath="/recommendations/available-genre-seeds";
     given().header("Authorization", "Bearer " + token)
                .when().get()
                .then().statusCode(200)
             .body("genres", hasItems(TestData.genres()))
             .body("genres", not(hasItems("disco polo")));
    }

    @Test
    public static void searchForPodcast(){
        RestAssured.basePath="/search";
        String response = given().header("Authorization", "Bearer " + token)
                .param("q", "okuniewska")
                .param("type", "show")
                .param("limit", 3)
                .param("market", "PL")
                .when().get().then().statusCode(200)
                .extract().asString();
        JsonPath jsonPath = new JsonPath(response);

        Map<String, String> podcasts = new HashMap<>();
        for (int i=0; i<jsonPath.getInt("shows.items.size()"); i++){
            podcasts.put(jsonPath.getString("shows.items["+i+"].name"), jsonPath.getString("shows.items["+i+"].publisher"));
        }
        assertThat(podcasts, hasEntry("Ja i moje przyjaciółki idiotki", "Tu Okuniewska"));
        assertThat(podcasts, hasEntry("Matka matce", "Joanna Okuniewska"));
        assertThat(podcasts, hasEntry("Tu Okuniewska", "Tu Okuniewska"));
    }

    @Test(dataProvider = "tracks", dataProviderClass = TestData.class)
    public static void getTrack(String id){
        RestAssured.basePath="/tracks";
        given().header("Authorization", "Bearer " + token)
                .when().get(id)
                .then().statusCode(200)
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("gettrack-schema.json"));

    }

    @Test(dataProvider = "invalidTrack", dataProviderClass = TestData.class)
    public static void failToGetTrack(String id){
        RestAssured.basePath="/tracks";
        given().header("Authorization", "Bearer " + token)
                .when().get(id).then().statusCode(400);

    }

    @Test(dataProvider = "albums", dataProviderClass = TestData.class)
    public static void getAlbumTracks(String id){
        RestAssured.basePath="/albums";
        given().header("Authorization", "Bearer " + token)
                .when().get(id+"/tracks").then().statusCode(200)
                .time(lessThan(800L));


    }

    @Test(dataProvider = "playlists", dataProviderClass = TestData.class)
    public static void getPlaylist(String id, String name, int numberOfTracks, int duration){
        RestAssured.basePath="/playlists";
        given().header("Authorization", "Bearer " + token)
                .when().get(id).then().statusCode(200)
                .body("name", equalTo(name))
                .body("tracks.total", equalTo(numberOfTracks));

    }
    @Test(dataProvider = "playlists", dataProviderClass = TestData.class)
    public static void getPlaylistTracks(String id, String name, int numberOfTracks, int duration){
        RestAssured.basePath="/playlists";
        String response = given().header("Authorization", "Bearer " + token)
                .param("limit", 50)
                .when().get(id+"/tracks").then().statusCode(200).extract().asString();
        JsonPath jsonPath = new JsonPath(response);

        int noOfPages= (int)Math.ceil(jsonPath.getDouble("total")/jsonPath.getDouble("limit"));
        int offset = 0;
        int sumOfDuration=0;
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
       Assert.assertEquals(totalTime, duration);

    }


}

