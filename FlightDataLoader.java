// --== CS400 Project Three File Header ==--
// Name: Darren Seubert
// Email: dpseubert@wisc.edu 
// Team: Blue
// Group: CI
// TA: Harper
// Lecturer: Florian Heimerl
// Notes to Grader:
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
/**
 * Interface that represents the FlightDataLoader
 * 
 * @author Darren Seubert
 */
interface FlightDataLoaderInterface {
    public Hashtable<String, AirportVertex> loadAirportFile(String csvFilePath);
    public List<FlightEdge> loadFlightFile(String csvFilePath, Hashtable<String, AirportVertex> airportHashtable);
}
/**
 * Class that loads Flight Data from csv files to the program, implements FlightDataLoaderInterface
 * 
 * @author Darren Seubert
 */
public class FlightDataLoader implements FlightDataLoaderInterface {
    /**
     * Method that loads in a airport csv file and hashes said airports into a hashtable
     * 
     * @param csvFilePath The file path to the csv file that is to be loaded
     * @return A hashtable containing the loaded in AirportVertex's
     */
  @Override
  public Hashtable<String, AirportVertex> loadAirportFile(String csvFilePath) {
      Hashtable<String, AirportVertex> hashtable = new Hashtable<String, AirportVertex>();
      List<AirportVertex> list = new ArrayList<AirportVertex>();
      String line = "";
      int abbreviationIndex = -1;
      int nameIndex = -1;

      try {
          File file = new File(csvFilePath);

          if (!file.exists()) {
              throw new FileNotFoundException("File at Path " + csvFilePath +
                  " Could Not Be Found");
          }

          BufferedReader br = new BufferedReader(new FileReader(csvFilePath));

          if ((line = br.readLine()) != null) {
              String[] line1 = line.split(",");

              for (int i = 0; i < line1.length; i++) {
                  if (line1[i].equals("\"abbreviation\"") || line1[i].equals("abbreviation")) {
                      abbreviationIndex = i;
                  } else if (line1[i].equals("\"name\"") || line1[i].equals("name")) {
                      nameIndex = i;
                  } else {
                      br.close();
                      throw new FileNotFoundException("CSV File is Not the Accepted Type");
                  }
              }
          }
          if (abbreviationIndex == -1 || nameIndex == -1) {
            br.close();
            throw new FileNotFoundException("CSV File is Not the Accepted Type");
        }

        String[] val;
        String abbreviation;
        String name;
        AirportVertex values;
        while ((line = br.readLine()) != null) {
            val = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

            abbreviation = val[abbreviationIndex].replaceAll("\"","").trim();
            name = val[nameIndex].replaceAll("\"","").trim();

            values = new AirportVertex(abbreviation, name);
            list.add(values);
        }

        br.close();
    } catch (FileNotFoundException e1) {
        e1.printStackTrace();
        return null;
    } catch (IOException e2) {
        e2.printStackTrace();
        return null;
    }

    for (AirportVertex airport : list) {
        hashtable.put(airport.getAbbreviation(), airport);
    }

    return hashtable;
}
  /**
   * Method that loads in a flight csv file and returns a list of flght edges
   * 
   * @param csvFilePath The file path to the csv file that is to be loaded
   * @return A list of FlightEdge's
   */
  @Override
  public List<FlightEdge> loadFlightFile(String csvFilePath, Hashtable<String, AirportVertex> airportHashtable) {
      List<FlightEdge> list = new ArrayList<FlightEdge>();
      String line = "";
      int originIndex = -1;
      int costIndex = -1;
      int destinationIndex = -1;

      try {
          File file = new File(csvFilePath);

          if (!file.exists()) {
              throw new FileNotFoundException("File at Path " + csvFilePath +
                  " Could Not Be Found");
          }

          BufferedReader br = new BufferedReader(new FileReader(csvFilePath));

          if ((line = br.readLine()) != null) {
              String[] line1 = line.split(",");

              for (int i = 0; i < line1.length; i++) {
                  if (line1[i].equals("\"origin\"") || line1[i].equals("origin")) {
                      originIndex = i;
                  } else if (line1[i].equals("\"cost\"") || line1[i].equals("cost")) {
                      costIndex = i;
                  } else if (line1[i].equals("\"destination\"") || line1[i].equals("destination")) {
                      destinationIndex = i;
                  } else {
                      br.close();
                      throw new FileNotFoundException("CSV File is Not the Accepted Type");
                  }
              }
          }
          if (originIndex == -1 || costIndex == -1 || destinationIndex == -1) {
            br.close();
            throw new FileNotFoundException("CSV File is Not the Accepted Type");
        }

        String[] val;
        String originString;
        int cost;
        String destinationString;
        AirportVertex origin;
        AirportVertex destination;
        FlightEdge values;
        while ((line = br.readLine()) != null) {
            val = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

            originString = val[originIndex].replaceAll("\"","").trim();
            origin = airportHashtable.get(originString);

            cost = Integer.parseInt(val[costIndex].replaceAll("\"",""));

            destinationString = val[destinationIndex].replaceAll("\"","").trim();
            destination = airportHashtable.get(destinationString);

            values = new FlightEdge(origin, cost, destination);
            list.add(values);
        }

        br.close();
    } catch (FileNotFoundException e1) {
        e1.printStackTrace();
        return null;
    } catch (IOException e2) {
        e2.printStackTrace();
        return null;
    }

    return list;
}
}
