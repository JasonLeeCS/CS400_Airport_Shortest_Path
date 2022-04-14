// --== CS400 Project Three File Header ==--
// Name: Jason Lee
// Email: jlee967@wisc.edu 
// Team: Blue
// Group: CI
// TA: C
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.invoke.MethodHandles;
import java.util.Hashtable;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.Test;
//import org.junit.platform.console.ConsoleLauncher;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
public class FlightAppTests
{
    public static void main(String[] args) throws Exception
    {
        String className = MethodHandles.lookup().lookupClass().getName();
        String classPath = System.getProperty("java.class.path").replace(" ", "\\ ");
        String[] arguments = new String[] {
          "-cp",
          classPath,
          "--include-classname=.*",
          "--select-class=" + className };
//        ConsoleLauncher.main(arguments);
    }
    // Data Wrangler Code Tests
    /**
     * Test method that tests if the size of the AirportVertex Hashtable is correct after loading airports.csv
     */
    @Test
    public void dataWrangler_sizeOfAirportHashtableTest() {
        FlightDataLoader loader = new FlightDataLoader();
        Hashtable<String, AirportVertex> airportHashtable = loader.loadAirportFile("D:\\Eclipse\\eclipse-workspace\\ProjectThree\\csvFolder/airports.csv");

        if (airportHashtable == null || airportHashtable.size() == 0) {
            fail();
        }

        assertEquals(10, airportHashtable.size());
    }

    /**
     * Test method that tests if the size of the FlightEdge ArrayList is correct after loading flights.csv
     */
    @Test
    public void dataWrangler_sizeOfFlightListTest() {
        FlightDataLoader loader = new FlightDataLoader();
        Hashtable<String, AirportVertex> airportHashtable = loader.loadAirportFile("D:\\Eclipse\\eclipse-workspace\\ProjectThree\\csvFolder/airports.csv");
        List<FlightEdge> flights = loader.loadFlightFile("D:\\Eclipse\\eclipse-workspace\\ProjectThree\\csvFolder/flights.csv", airportHashtable);

        if (airportHashtable == null || flights == null || airportHashtable.size() == 0 ||
                flights.size() == 0) {
            fail();
        }

        assertEquals(28, flights.size());
    }

    /**
     * Method that tests if loading an incorrect csv file returns a null AirportVertex Hashtable
     */
    @Test
    public void dataWrangler_loadingWrongFilePathTest() {
        FlightDataLoader loader = new FlightDataLoader();
        Hashtable<String, AirportVertex> airportHashtable = loader.loadAirportFile("D:\\Eclipse\\eclipse-workspace\\ProjectThree\\csvFolder/wrong.csv");

        if (airportHashtable != null) {
            fail();
        }
    }
 // Back End Developer Tests
    private FlightDataBackEnd graph;
    /**
    * Checks that the ContainsVertex Method is working properly.
    */
   @Test
   public void backendDeveloper_testContainVertex() {
       Hashtable<String, AirportVertex> airports = new Hashtable<String, AirportVertex>();
       //insert Airports into the graph
       AirportVertex chicago = new AirportVertex("OHL", "Chicago");
       AirportVertex newyork = new AirportVertex("NY", "NewYork");
       AirportVertex Rockford = new AirportVertex("RF", "Rockford");
       AirportVertex LosAngeles = new AirportVertex("LA", "LosAngeles");
       airports.put("OHL", chicago );
       airports.put("NY", newyork );
       airports.put("RF", Rockford );
       airports.put("LA", LosAngeles);


       graph = new FlightDataBackEnd(airports);

       // insert Airport Distances
       graph.insertEdge(Rockford, chicago, 100);
       graph.insertEdge(chicago,LosAngeles,1000);
       graph.insertEdge(Rockford,LosAngeles,1250);
       graph.insertEdge(Rockford,newyork,800);
       graph.insertEdge(newyork,LosAngeles,2500);
       assertTrue(graph.containsVertex("RF"));

   }

   /**
    * Checks that the ContainsEdge Method is working properly.
    */
   @Test
   public void backendDeveloper_testContainEdge() {
       Hashtable<String, AirportVertex> airports = new Hashtable<String, AirportVertex>();
       //insert Airports into the graph
       AirportVertex chicago = new AirportVertex("OHL", "Chicago");
       AirportVertex newyork = new AirportVertex("NY", "NewYork");
       AirportVertex Rockford = new AirportVertex("RF", "Rockford");
       AirportVertex LosAngeles = new AirportVertex("LA", "LosAngeles");
       airports.put("OHL", chicago );
       airports.put("NY", newyork );
       airports.put("RF", Rockford );
       airports.put("LA", LosAngeles);


       graph = new FlightDataBackEnd(airports);

       // insert Airport Distances
       graph.insertEdge(Rockford, chicago, 100);
       graph.insertEdge(chicago,LosAngeles,1000);
       graph.insertEdge(Rockford,LosAngeles,1250);
       graph.insertEdge(Rockford,newyork,800);
       graph.insertEdge(newyork,LosAngeles,2500);
       assertTrue(graph.containsEdge("RF", "LA"));
   }
   /**
    * Checks the ordered sequence of data within vertices from the Airports 
    * RF to LA.
    */
   @Test
   public void backendDeveloper_testShortestPath() {
       Hashtable<String, AirportVertex> airports = new Hashtable<String, AirportVertex>();
       //insert Airports into the graph
       AirportVertex chicago = new AirportVertex("OHL", "Chicago");
       AirportVertex newyork = new AirportVertex("NY", "NewYork");
       AirportVertex Rockford = new AirportVertex("RF", "Rockford");
       AirportVertex LosAngeles = new AirportVertex("LA", "LosAngeles");
       airports.put("OHL", chicago );
       airports.put("NY", newyork );
       airports.put("RF", Rockford );
       airports.put("LA", LosAngeles);


       graph = new FlightDataBackEnd(airports);

       // insert Airport Distances
       graph.insertEdge(Rockford, chicago, 100);
       graph.insertEdge(chicago,LosAngeles,1000);
       graph.insertEdge(Rockford,LosAngeles,1250);
       graph.insertEdge(Rockford,newyork,800);
       graph.insertEdge(newyork,LosAngeles,2500);

       assertTrue(graph.shortestPath("RF", "LA").toString().equals(
           "[RF, OHL, LA]"
       ));
   }
   /**
    * Checks the shortest distance between vertices from the Airports 
    * RF to LA.
    */
   @Test
   public void backendDeveloper_testPathCost() {
       Hashtable<String, AirportVertex> airports = new Hashtable<String, AirportVertex>();
       //insert Airports into the graph
       AirportVertex chicago = new AirportVertex("OHL", "Chicago");
       AirportVertex newyork = new AirportVertex("NY", "NewYork");
       AirportVertex Rockford = new AirportVertex("RF", "Rockford");
       AirportVertex LosAngeles = new AirportVertex("LA", "LosAngeles");
       airports.put("OHL", chicago );
       airports.put("NY", newyork );
       airports.put("RF", Rockford );
       airports.put("LA", LosAngeles);


       graph = new FlightDataBackEnd(airports);

       // insert Airport Distances
       graph.insertEdge(Rockford, chicago, 100);
       graph.insertEdge(chicago,LosAngeles,1000);
       graph.insertEdge(Rockford,LosAngeles,1250);
       graph.insertEdge(Rockford,newyork,800);
       graph.insertEdge(newyork,LosAngeles,2500);
       assertTrue(graph.getPathCost("RF", "LA")==1100);
   }
    
    
    // Front End Developer Tests
    /*
     * Test to make sure JavaFX Front end method storeAirport works with null inputs.
     */
    @Test
    public void frontEnd_test_storeAirports()
    {
            FlightDataFrontEnd fe = new FlightDataFrontEnd();
            boolean test = false;
            String s1 = null;
            String s2 = null;
            try
            {
                List<String> ls = fe.StoreAirports(s1, s2);
                test = true;
            }
            catch(NoSuchElementException e)
            {
                System.out.println("Front end handled errors correct");
            }
            assertFalse(test);

    }

    /*
     * Test to make sure JavaFX Front end method costAirport works with null inputs.
     */
    @Test
    public void frontEnd_test_costAirports()
    {
            FlightDataFrontEnd fe = new FlightDataFrontEnd();
            boolean test = false;
            String s1 = null;
            String s2 = null;
            try
            {
                int cost = fe.costAirports(s1, s2);
                test = true;

            }
            catch(NoSuchElementException e)
            {
                System.out.println("Front end handled errors correct");
            }
            assertFalse(test);

    }
    /*
     * Test to make sure JavaFX Front end method createFlight works with null inputs.
     */
    @Test
    public void frontEnd_test_createFlight()
    {
            FlightDataFrontEnd fe = new FlightDataFrontEnd();
            boolean test = false;
            Pane p = null;
            Label lb = null;
            try
            {
                fe.createFlight(p, lb);
                test = true;
            }
            catch(NoSuchElementException e)
            {
                System.out.println("Front end handled errors correct");
            }
            assertFalse(test);

    }

    // Integration Manager Tests
    @Test
    public void IMTestForDW() // Test make sures that the file is loaded correctly and the first value is correct
    {
        FlightDataLoader loader = new FlightDataLoader();
        Hashtable<String, AirportVertex> airportHashTable = loader.loadAirportFile("D:\\Eclipse\\eclipse-workspace\\ProjectThree\\csvFolder/airports.csv");
        List <FlightEdge> flights = null;
        try
        {
          flights = loader.loadFlightFile("D:\\Eclipse\\eclipse-workspace\\ProjectThree\\csvFolder/flights.csv", airportHashTable);
        }
        catch(Exception e)
        {
          fail();
        }
        assertEquals(flights.get(0).getCost(), 2);
        

    }
    
    /*
     *  Test to make sure back end object can be instantiated and that AirportVertices 
     *  can be properly inserted.
     */
    @Test
    public void IMTestForBackEnd() // Test to make sure back end object can be instantiated and that AirportVertexes
    {
      Hashtable <String, AirportVertex> airportHashTable = new Hashtable <String, AirportVertex>();

      FlightDataBackEnd graph = new FlightDataBackEnd(airportHashTable);
      
      AirportVertex StLouis = new AirportVertex("STL", "St Louis");
      AirportVertex LosAngeles = new AirportVertex("LAX", "Los Angeles");
      AirportVertex Denver = new AirportVertex("DEN", "Denver");
      
      airportHashTable.put("STL", StLouis);
      airportHashTable.put("LAX", LosAngeles);
      airportHashTable.put("DEN", Denver);
      
      assertTrue(graph.containsVertex("STL"));
      assertTrue(graph.containsVertex("LAX"));
      assertTrue(graph.containsVertex("DEN"));

      
    }
    
    @Test
    public void IMTestForFrontEnd() // Test make sures that the frontend is not null and can be instantiated
    {      
      try
      {
        FlightDataFrontEnd frontEndTester = new FlightDataFrontEnd();
        if(frontEndTester == null)
        {
          fail("Frontend was not instantiated");
        }
      }
      catch(Exception e)
      {
        fail("Fail: instantiation failed");
      }
    }
}