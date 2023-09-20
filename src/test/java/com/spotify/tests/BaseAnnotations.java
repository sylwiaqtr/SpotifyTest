package com.spotify.tests;

import io.restassured.RestAssured;
import jdk.jshell.spi.ExecutionControl;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
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
