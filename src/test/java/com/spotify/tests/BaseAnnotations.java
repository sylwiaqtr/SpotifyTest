package com.spotify.tests;

import com.spotify.util.Authentication;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

public class BaseAnnotations {

    public static String token;
    @BeforeSuite(alwaysRun = true)
    public void getToken(){
        token = Authentication.getToken();
    }

    @BeforeTest()
    public void baseURI(){
        RestAssured.baseURI = "https://api.spotify.com/v1";
    }


}
