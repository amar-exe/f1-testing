package ba.edu.ibu;

public class App {
  public static void main(String[] args) {
    DriverModel driverModel = ApiGetter.getDrivers2022();
    for (DriverModel.StandingsList standingsList : driverModel.StandingsTable.StandingsLists) {
      for (DriverModel.DriverStandings driverStandings : standingsList.DriverStandings) {
        System.out.println(driverStandings.Driver.givenName + " " + driverStandings.Driver.familyName + ": " + driverStandings.points + " points");
      }
    }
  }
}
