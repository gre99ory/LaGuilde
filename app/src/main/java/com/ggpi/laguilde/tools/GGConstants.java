package com.ggpi.laguilde.tools;

public class GGConstants {

    public class ServiceCode {
        public static final int HOME = 1;
    }
    // web service url constants
    public class ServiceType {
        public static final String SRVURL = "http://laguilde-jeux.fr/media/events/";
    }
    public int CHANNEL_ID = 1976;


    public class Paths {
        public static final String URL_EVENT_IMG = "http://laguilde-jeux.fr/modules/gg_events/img/";
    }

    public class Api {

        // private static final String ROOT_URL = "http://192.168.101.1/HeroApi/v1/Api.php?apicall=";
        private static final String ROOT_URL = "http://laguilde-jeux.fr/ggPiApi/api/api.php?apicall=";

        public static final String MARKET_URL = "market://details?id=com.ggpi.laguilde";

        /*
        public static final String URL_CREATE_HERO = ROOT_URL + "createhero";
        public static final String URL_READ_HEROES = ROOT_URL + "getheroes";
        public static final String URL_UPDATE_HERO = ROOT_URL + "updatehero";
        public static final String URL_DELETE_HERO = ROOT_URL + "deletehero&id=";
        */

        public static final String URL_READ_VERSION = ROOT_URL + "version";
        public static final String URL_ADD_REG = ROOT_URL + "addreg";
        public static final String URL_DEL_REG = ROOT_URL + "delreg";

        public static final String URL_GET_EVENTS = ROOT_URL + "getevents";

        public static final String URL_MAGIC_GET_POINTS = "https://www.wizards.com/Magic/PlaneswalkerPoints/JavaScript/GetPointsSummary/";


    }
    /*
    // webservice key constants
    public class Params {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String COUNTRY = "country";
        public static final String CITY = "city";
    }
    */


    /*
    {
        "status":"true",
        "message":"Data fetched successfully!",
        "data":
            [
                {
                    "id":"1",
                    "name":"Roger Federer",
                    "country":"Switzerland",
                    "city":"Basel",
                    "imgURL":"https:\/\/demonuts.com\/Demonuts\/SampleImages\/roger.jpg"
                 },
                 {
                    "id":"
     */
}