// --== CS400 Project Three File Header ==--
// Name: Darren Seubert
// Email: dpseubert@wisc.edu 
// Team: Blue
// Group: CI
// TA: Harper
// Lecturer: Florian Heimerl
// Notes to Grader:
import java.util.LinkedList;
/**
 * Interface that represents a AirportVertex
 * 
 * @author Darren Seubert
 */
interface AirportVertexInterface {
    public String getAbbreviation();
    public String getName();
    public LinkedList<FlightEdge> getFlightEdges();
    public void setAbbreviation(String abbreviation);
    public void setName(String name);
}
/**
 * Class that represents a AirportVertex, implmenting AirportVertexInterface
 * 
 * @author Darren Seubert
 */
public class AirportVertex implements AirportVertexInterface {
    private String abbreviation;
    private String name;
    private LinkedList<FlightEdge> flightEdges;
    /**
     * Constructor for AirportVertex
     * 
     * @param abbreviation Abbreviation representation of the airport
     * @param name Name of the airport
     */
    public AirportVertex(String abbreviation, String name) {
        this.abbreviation = abbreviation;
        this.name = name;
        flightEdges = new LinkedList<FlightEdge>();
    }
    /**
     * Getter method for the abbreviation
     * 
     * @return Abbreviation of the AirportVertex
     */
    @Override
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * Getter method for the name
     * 
     * @return Name of the AirportVertex
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Getter method for the FlightEdges LinkedList
     * 
     * @return FlightEdges LinkedList
     */
    @Override
    public LinkedList<FlightEdge> getFlightEdges() {
        return flightEdges;
    }

    /**
     * Setter method for the abbreviation
     * 
     * @param abbreviation Value to set the abbreviation to
     */
    @Override
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    /**
     * Setter method for the name
     * 
     * @param name Value to set the name to
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }
}