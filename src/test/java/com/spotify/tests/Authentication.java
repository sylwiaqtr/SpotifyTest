package com.spotify.tests;

import com.spotify.util.LoadCredentials;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.util.Base64;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;


public class Authentication {

    public static final String clientID = LoadCredentials.loadProperty("clientid");
    public static final String clientSecret = LoadCredentials.loadProperty("clientsecret");


    public static String getToken(){
        String encodeToString = Base64.getEncoder().encodeToString((clientID+":"+clientSecret).getBytes());
        RestAssured.baseURI = "https://accounts.spotify.com/api/token";
        String response = given()
                .header("Authorization", "Basic " + encodeToString)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .param("grant_type", "client_credentials")
                .when().post().then().extract().response().asString();
        JsonPath js = new JsonPath(response);
        return js.getString("access_token");
    }

}
