package com.spotify.tests;

import com.spotify.data.TestData;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;

public class SearchTests extends BaseAnnotations{



    @Test(dataProvider = "podcasts", dataProviderClass = TestData.class)
    public static void searchForPodcast(String searchItem, Map results){
        RestAssured.basePath="/search";
        String response = given().header("Authorization", "Bearer " + token)
                .param("q", searchItem)
                .param("type", "show")
                .param("limit", 3)
                .param("market", "PL")
                .when().get().then().statusCode(200)
                .extract().asString();

        JsonPath jsonPath = new JsonPath(response);

        Map<String, String> actualSearchResults = new HashMap<>();
        int numberOfItems = jsonPath.getInt("shows.items.size()");

        // iterate over search results to map shows and authors
        for (int i=0; i<numberOfItems; i++){
            actualSearchResults.put(jsonPath.getString("shows.items["+i+"].name"), jsonPath.getString("shows.items["+i+"].publisher"));
        }

        // assert that test data from data provider are included in test results
        for(Object key : results.keySet()){
            assertThat(actualSearchResults, hasEntry(key, results.get(key)));
        }
    }
}
