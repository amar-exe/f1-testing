package ba.edu.ibu;

import java.util.List;

public class DriverModel {

        String xmlns;
        String series;
        String url;
        String limit;
        String offset;
        String total;
        StandingsTable StandingsTable;


    class StandingsTable {
        String season;
        List<StandingsList> StandingsLists;
    }

    class StandingsList {
        String season;
        String round;
        List<DriverStandings> DriverStandings;
    }

    class DriverStandings {
        String position;
        String positionText;
        String points;
        String wins;
        Driver Driver;
        List<Constructor> Constructors;
    }

    class Driver {
        String driverId;
        String permanentNumber;
        String code;
        String url;
        String givenName;
        String familyName;
        String dateOfBirth;
        String nationality;
    }

    class Constructor {
        String constructorId;
        String url;
        String name;
        String nationality;
    }

}


