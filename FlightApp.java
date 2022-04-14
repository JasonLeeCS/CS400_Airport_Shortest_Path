// --== CS400 Project Three File Header ==--
// Name: Jason Lee
// Email: jlee967@wisc.edu 
// Team: Blue
// Group: CI
// TA: C
// Lecturer: Florian Heimerl
// Notes to Grader:
import java.util.Hashtable;
import java.util.List;
public class FlightApp {
    public static void main(String[] args) {
        FlightDataLoader loader = new FlightDataLoader();
        Hashtable<String, AirportVertex> airportHashtable = loader.loadAirportFile("D:\\Eclipse\\eclipse-workspace\\ProjectThree\\csvFolder/airports.csv");
        List<FlightEdge> flights = loader.loadFlightFile("D:\\Eclipse\\eclipse-workspace\\ProjectThree\\csvFolder/flights.csv", airportHashtable);
        if (airportHashtable == null || flights == null || airportHashtable.size() == 0 ||
                flights.size() == 0) {
            System.out.println("Error in FlightDataLoader.java");
            return;
        }
        FlightDataBackEnd engine = new FlightDataBackEnd(airportHashtable);
        for (FlightEdgeInterface flightEdge : flights) {
            engine.insertEdge((AirportVertex) flightEdge.getOrigin(), (AirportVertex) flightEdge.getTarget(), flightEdge.getCost());
        }
        FlightDataFrontEnd ui = new FlightDataFrontEnd();
        ui.run(engine);
    }
}