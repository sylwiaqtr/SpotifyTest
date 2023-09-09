package com.spotify.tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserTest {

    public static String token;

    @BeforeSuite
    public void getToken(){
        token = Authentication.getToken();

    }
    @BeforeGroups


    @Test(dataProvider = "artists", dataProviderClass = DataProviders.class, groups = {"artists"})
    public static void getArtists(String ids, int sumOfArtists, List<String> names){

        RestAssured.baseURI = "https://api.spotify.com/v1/artists";
        String response = given()
                .header("Authorization", "Bearer " + token)
                .param("ids", ids)
                .when().get()
                .then().statusCode(200).log().all()
                .extract().response().asString();

        JsonPath jsonPath = new JsonPath(response);
        Assert.assertEquals(jsonPath.getInt("artists.size()"), sumOfArtists);
        Assert.assertTrue(jsonPath.getList("artists.name").containsAll(names));
    }

    @Test(groups = {"artists"})
    public static void getAlbumsOfArtist()
    {
        RestAssured.baseURI = "https://api.spotify.com/v1/artists";
        String response = given()
                .header("Authorization", "Bearer " + token)
                .when().get("4lxfqrEsLX6N1N4OCSkILp/albums")
                .then().statusCode(200).log().all()
                .extract().response().asString();

        JsonPath jsonPath = new JsonPath(response);
        assertThat(jsonPath.getInt("items.size()"), equalTo(20));
        int sumOfTracks=0;
        for (int i=0; i<jsonPath.getInt("items.size()"); i++){
            int noOFTracks = jsonPath.get("items["+i+"].total_tracks");
            sumOfTracks += noOFTracks;
        }
        assertThat(sumOfTracks, equalTo(348));
    }

    @Test(groups = {"artists"})
    public static void getArtistTopTracks()
    {
        RestAssured.baseURI = "https://api.spotify.com/v1/artists";
        String response = given()
                .header("Authorization", "Bearer " + token).param("market", "PL")
                .log().all()
                .when().get("4lxfqrEsLX6N1N4OCSkILp/top-tracks")
                .then().log().all()
                .extract().response().asString();

        JsonPath jsonPath = new JsonPath(response);
        assertThat(jsonPath.getInt("tracks.size()"), equalTo(10));
        for (int i=0; i<jsonPath.getInt("tracks.size()"); i++){
           assertThat(jsonPath.get("tracks["+i+"].popularity"), lessThanOrEqualTo(101));
            assertThat(jsonPath.get("tracks["+i+"].popularity"), greaterThanOrEqualTo(0));
        }
    }

    @Test(groups = {"genres"})
    public static void getGenres(){
        RestAssured.baseURI="https://api.spotify.com/v1/recommendations/available-genre-seeds";
     given().header("Authorization", "Bearer " + token)
                .when().get()
                .then().statusCode(200)
             .body("genres", hasItems("punk", "rock", "indie", "industrial"));
    }

    @Test(groups = {"search"})
    public static void searchForPodcast(){
        RestAssured.baseURI="https://api.spotify.com/v1/search";
        given().header("Authorization", "Bearer " + token)
                .param("q", "Okuniewska")
                .param("type", "show")
                .when().get().then().statusCode(200)
                .body("shows.items.findAll {it.total_episodes= 127} .description", containsString("Tu Okuniewska"));
    }

    @Test(groups = {"tracks"})
    public static void getTrack(){

        RestAssured.baseURI="https://api.spotify.com/v1/tracks";
        given().header("Authorization", "Bearer " + token)
                .when().get("1prZ0pr6XoRCxcrC3MCL0M").then().statusCode(200)
                .log().all();

    }

    @Test(groups = {"tracks"})
    public static void failToGetTrack(){

        RestAssured.baseURI="https://api.spotify.com/v1/tracks";
        given().header("Authorization", "Bearer " + token)
                .when().get("1rprZ0pr6XoRCxcrC3MCL0M").then().statusCode(400)
                .log().all();

    }

    @Test(groups = {"albums"})
    public static void getAlbumTracks(){

        RestAssured.baseURI="https://api.spotify.com/v1/albums";
        given().header("Authorization", "Bearer " + token)
                .when().get("6WaIQHxEHtZL0RZ62AuY0g/tracks").then().statusCode(200)
                .log().all();

    }

    @Test(groups = {"playlists"})
    public static void getPlaylist(){

        RestAssured.baseURI= "https://api.spotify.com/v1/playlists/";
        given().header("Authorization", "Bearer " + token)
                .when().get("37i9dQZF1DX9LbdoYID5v7").then().statusCode(200)
                .log().all();

    }
    @Test(groups = {"playlists"})
    public static void getPlaylistTracks(){

        RestAssured.baseURI= "https://api.spotify.com/v1/playlists";
        given().header("Authorization", "Bearer " + token)
                .when().get("37i9dQZF1DX9LbdoYID5v7/tracks").then().statusCode(200)
                .log().all();

    }




}

