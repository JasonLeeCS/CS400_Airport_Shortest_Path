// --== CS400 Project Three File Header ==--
// Name: Darren Seubert
// Email: dpseubert@wisc.edu 
// Team: Blue
// Group: CI
// TA: Harper
// Lecturer: Florian Heimerl
// Notes to Grader:
/**
 * Interface that represents a FlightEdge
 * 
 * @author Darren Seubert
 */
interface FlightEdgeInterface {
    public AirportVertex getOrigin();
    public int getCost();
    public AirportVertex getTarget();
    public void setOrigin(AirportVertex origin);
    public void setCost(int cost);
    public void setTarget(AirportVertex target);
}
/**
 * Class that represents a FlightEdge, implmenting FlightEdgeInterface
 * 
 * @author Darren Seubert
 */
public class FlightEdge implements FlightEdgeInterface {
  private AirportVertex origin;
  private AirportVertex target;
  private int cost;

  /**
   * Constructor for a FlightEdge
   * 
   * @param origin AirportVertex where the edge is starting from
   * @param cost Cost of how much it takes to get from origin to target
   * @param target AirportVertex where the edge is going to
   */
  public FlightEdge(AirportVertex origin, int cost, AirportVertex target) {
      this.origin = origin;
      this.cost = cost;
      this.target = target;
  }

  /**
   * Getter method for the origin
   * 
   * @return Origin of the FlightEdge
   */
  @Override
  public AirportVertex getOrigin() {
      return origin;
  }

  /**
   * Getter method for the cost
   * 
   * @return Cost of the FlightEdge
   */
  @Override
  public int getCost() {
      return cost;
  }

  /**
   * Getter method for the target
   * 
   * @return Target of the FlightEdge
   */
  @Override
  public AirportVertex getTarget() {
      return target;
  }

  /**
   * Setter method for the origin
   * 
   * @param origin Value to set the origin to
   */
  @Override
  public void setOrigin(AirportVertex origin) {
      this.origin = origin;
  }

  /**
   * Setter method for the cost
   * 
   * @param cost Value to set the cost to
   */
  @Override
  public void setCost(int cost) {
      this.cost = cost;
  }

  /**
   * Setter method for the target
   * 
   * @param target Value to set the target to
   */
  @Override
  public void setTarget(AirportVertex target) {
      this.target = target;
  }
}
                                 