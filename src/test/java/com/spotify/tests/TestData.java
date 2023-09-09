package com.spotify.tests;

import org.testng.annotations.DataProvider;

import java.util.List;

public class DataProviders {

    @DataProvider(name="artistsSet")
    public Object[][] artistsData(){
        List<String> artistsNames1 = List.of("Muse", "Phil Collins", "The Darkness", "The Raconteurs", "The Police");
        List<String> artistsNames2 = List.of("M83", "Daft Punk", "Fleetwood Mac");

        return new Object[][] {
                {"12Chz98pHFMPJEknJQMWvI,4lxfqrEsLX6N1N4OCSkILp,5r1bdqzhgRoHC3YcCV6N5a,4wo1267SJuUfHgasdlfNfc,5NGO30tJxFlKixkPSgXcFE", 5, artistsNames1},
                {"4tZwfgrHOc3mvqYlEYSvVi,63MQldklfxkjYDoUE4Tppz,08GQAI4eElDnROBrJRGE0X", 3, artistsNames2},
                };
    }

    @DataProvider(name="artists")
    public Object[][] artists(){

        return new Object[][] {
                {"12Chz98pHFMPJEknJQMWvI", 13, 180},
                {"4lxfqrEsLX6N1N4OCSkILp", 22, 384},
                {"5r1bdqzhgRoHC3YcCV6N5a", 11, 148},
                {"4wo1267SJuUfHgasdlfNfc", 5, 69},
                {"5NGO30tJxFlKixkPSgXcFE", 9, 129},
                {"4tZwfgrHOc3mvqYlEYSvVi", 14, 225},
                {"63MQldklfxkjYDoUE4Tppz", 17, 243},
                {"08GQAI4eElDnROBrJRGE0X", 32, 743},
        };
    }
}
