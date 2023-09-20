package com.spotify.tests;

import org.testng.annotations.DataProvider;

import java.time.Duration;
import java.util.List;

public class TestData {

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
                {"08GQAI4eElDnROBrJRGE0X", 33, 761},
        };
    }

    @DataProvider(name="tracks")
    public Object[][] tracks(){
        return new Object[][] {
                {"1prZ0pr6XoRCxcrC3MCL0M"},
                {"4gvea7UlDkAvsJBPZAd4oB"},
                {"5psbwXbjlWCqmLVzIqif7Q"},
                {"4d6eqRtpDX7tydHJGDZUBQ"},
                {"2sgBTNHz9ckmqj3rx3ez4M"}

        };
    }

    @DataProvider(name="albums")
    public Object[][] albums(){
        return new Object[][] {
                {"0lw68yx3MhKflWFqCsGkIs"},
                {"6Ar5HxNWXtvraqs7FI7bYq"},
                {"1VpqO2dBJdAS3YAimXlhEM"},
                {"1cM3r0WQZWNkCpEbmFjLln"},
                {"5bqtZRbUZUxUps8mrO9tGY"}

        };
    }
    @DataProvider(name="playlists")
    public Object[][] playlists(){
        return new Object[][] {
                {"37i9dQZF1DX9LbdoYID5v7", "The 2000s Indie Scene", 75, 286},
                {"37i9dQZF1DX9GRpeH4CL0S", "Essential Alternative", 225, 878},
                {"37i9dQZF1DX98f0uoU1Pcs", "Noisy", 124, 425},
                {"37i9dQZF1DWZryfp6NSvtz", "All New Rock", 50, 169},
                {"37i9dQZF1DWXRqgorJj26U", "Rock Classics", 200, 882}

        };
    }

    @DataProvider(name="invalidTrack")
    public Object[][] invalidTrack(){
        return new Object[][]{
                {"1rprZ0pr6XoRCxcrC3MCL0M"}
        };
    }


    public static String[] genres(){
        return new String[]{ "acoustic",
                "afrobeat",
                "alt-rock",
                "alternative",
                "ambient",
                "anime",
                "black-metal",
                "bluegrass",
                "blues",
                "bossanova",
                "brazil",
                "breakbeat",
                "british",
                "cantopop",
                "chicago-house",
                "children",
                "chill",
                "classical",
                "club",
                "comedy",
                "country",
                "dance",
                "dancehall",
                "death-metal",
                "deep-house",
                "detroit-techno",
                "disco",
                "disney",
                "drum-and-bass",
                "dub",
                "dubstep",
                "edm",
                "electro",
                "electronic",
                "emo",
                "folk",
                "forro",
                "french",
                "funk",
                "garage",
                "german",
                "gospel",
                "goth",
                "grindcore",
                "groove",
                "grunge",
                "guitar",
                "happy",
                "hard-rock",
                "hardcore",
                "hardstyle",
                "heavy-metal",
                "hip-hop",
                "holidays",
                "honky-tonk",
                "house",
                "idm",
                "indian",
                "indie",
                "indie-pop",
                "industrial",
                "iranian",
                "j-dance",
                "j-idol",
                "j-pop",
                "j-rock",
                "jazz",
                "k-pop",
                "kids",
                "latin",
                "latino",
                "malay",
                "mandopop",
                "metal",
                "metal-misc",
                "metalcore",
                "minimal-techno",
                "movies",
                "mpb",
                "new-age",
                "new-release",
                "opera",
                "pagode",
                "party",
                "philippines-opm",
                "piano",
                "pop",
                "pop-film",
                "post-dubstep",
                "power-pop",
                "progressive-house",
                "psych-rock",
                "punk",
                "punk-rock",
                "r-n-b",
                "rainy-day",
                "reggae",
                "reggaeton",
                "road-trip",
                "rock",
                "rock-n-roll",
                "rockabilly",
                "romance",
                "sad",
                "salsa",
                "samba",
                "sertanejo",
                "show-tunes",
                "singer-songwriter",
                "ska",
                "sleep",
                "songwriter",
                "soul",
                "soundtracks",
                "spanish",
                "study",
                "summer",
                "swedish",
                "synth-pop",
                "tango",
                "techno",
                "trance",
                "trip-hop",
                "turkish",
                "work-out",
                "world-music"};
    }
}
