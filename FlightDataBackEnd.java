// --== Project3 BackEnd Header ==--
// Name: Richard Yang
// Email: ryang247@wisc.edu
// TA: Harper
// Lecturer: Florian
// Notes to Grader: none
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.NoSuchElementException;

public class FlightDataBackEnd {
    Hashtable<String, AirportVertex> vertexHashtable;

    /**
     * Constructor
     * 
     * @param vertexHashtable
     */
    FlightDataBackEnd(Hashtable<String, AirportVertex> vertexHashtable) {
        this.vertexHashtable = vertexHashtable;
    }

    /**
     * Insert a new directed edge with a positive edge weight into the graph.
     * 
     * @param source the data item contained in the source vertex for the edge
     * @param target the data item contained in the target vertex for the edge
     * @param weight the weight for the edge (has to be a positive integer)
     * @return true if the edge could be inserted or its weight updated, false 
     *     if the edge with the same weight was already in the graph
     * @throws IllegalArgumentException if either source or target or both are not in the graph, 
     *     or if its weight is < 0
     * @throws NullPointerException if either source or target or both are null
     */
    public boolean insertEdge(AirportVertex source, AirportVertex target, int weight) {
        if(source == null || target == null)
            throw new NullPointerException("Cannot add edge with null source or target");
        AirportVertex sourceVertex = this.vertexHashtable.get(source.getAbbreviation());
        AirportVertex targetVertex = this.vertexHashtable.get(target.getAbbreviation());
        if(sourceVertex == null || targetVertex == null)
            throw new IllegalArgumentException("Cannot add edge with vertices that do not exist");
        if(weight < 0)
            throw new IllegalArgumentException("Cannot add edge with negative weight");
        // handle cases where edge already exists between these verticies
        for(FlightEdge e : sourceVertex.getFlightEdges())
            if(e.getTarget() == targetVertex) {
                if(e.getCost() == weight) return false; // edge already exists
                else e.setCost(weight); // otherwise update weight of existing edge
                return true;
            }
        // otherwise add new edge to sourceVertex
        sourceVertex.getFlightEdges().add(new FlightEdge(sourceVertex, weight, targetVertex));
        return true;
    }
    /**
     * Check if the graph contains a vertex with data item *data*.
     * 
     * @param data the data item to check for
     * @return true if data item is stored in a vertex of the graph, false otherwise
     * @throws NullPointerException if *data* is null
     */
    public boolean containsVertex(String data) {
        if(data == null) throw new NullPointerException("Cannot contain null data vertex");
        return vertexHashtable.containsKey(data);
    }

    /**
     * Check if edge is in the graph.
     * 
     * @param source the data item contained in the source vertex for the edge
     * @param target the data item contained in the target vertex for the edge
     * @return true if the edge is in the graph, false if it is not in the graph
     * @throws NullPointerException if either source or target or both are null
     */
    public boolean containsEdge(String source, String target) {
        if(source == null || target == null) throw new NullPointerException("Cannot contain edge adjacent to null data");
        AirportVertex sourceVertex = vertexHashtable.get(source);
        AirportVertex targetVertex = vertexHashtable.get(target);
        if(sourceVertex == null) return false;
        for(FlightEdge e : sourceVertex.getFlightEdges())
            if(e.getTarget() == targetVertex)
                return true;
        return false;
    }

    /**
     * Path objects store a discovered path of vertices and the overal distance of cost
     * of the weighted directed edges along this path. Path objects can be copied and extended
     * to include new edges and verticies using the extend constructor. In comparison to a
     * predecessor table which is sometimes used to implement Dijkstra's algorithm, this
     * eliminates the need for tracing paths backwards from the destination vertex to the
     * starting vertex at the end of the algorithm.
     */
    protected class Path implements Comparable<Path> {
        public AirportVertex start; // first vertex within path
        public int distance; // sumed weight of all edges in path
        public List<String> dataSequence; // ordered sequence of data from vertices in path
        public AirportVertex end; // last vertex within path

        /**
         * Creates a new path containing a single vertex.  Since this vertex is both
         * the start and end of the path, it's initial distance is zero.
         * @param start is the first vertex on this path
         */
        public Path(AirportVertex start) {
            this.start = start;
            this.distance = 0;
            this.dataSequence = new LinkedList<>();
            this.dataSequence.add(start.getAbbreviation());
            this.end = start;
        }
        /**
         * This extension constructor makes a copy of the path passed into it as an argument
         * without affecting the original path object (copyPath). The path is then extended
         * by the Edge object extendBy.
         * @param copyPath is the path that is being copied
         * @param extendBy is the edge the copied path is extended by
         */
        public Path(Path copyPath, FlightEdge extendBy) {
            this.start = copyPath.start;
            this.distance = copyPath.distance + extendBy.getCost();
            this.dataSequence = new LinkedList<>();

            for (int i = 0; i < copyPath.dataSequence.size(); i++) {
                this.dataSequence.add(copyPath.dataSequence.get(i));
            }

            this.dataSequence.add(extendBy.getTarget().getAbbreviation());
            this.end = extendBy.getTarget();
        }

        /**
         * Allows the natural ordering of paths to be increasing with path distance.
         * When path distance is equal, the string comparison of end vertex data is used to break ties.
         * @param other is the other path that is being compared to this one
         * @return -1 when this path has a smaller distance than the other,
         *         +1 when this path has a larger distance that the other,
         *         and the comparison of end vertex data in string form when these distances are tied
         */
        public int compareTo(Path other) {
            int cmp = this.distance - other.distance;
            if(cmp != 0) return cmp; // use path distance as the natural ordering
            // when path distances are equal, break ties by comparing the string
            // representation of data in the end vertex of each path
            return this.end.getAbbreviation().compareTo(other.end.getAbbreviation());
        }
    }

    /**
     * Uses Dijkstra's shortest path algorithm to find and return the shortest path 
     * between two vertices in this graph: start and end. This path contains an ordered list
     * of the data within each node on this path, and also the distance or cost of all edges
     * that are a part of this path.
     * @param start data item within first node in path
     * @param end data item within last node in path
     * @return the shortest path from start to end, as computed by Dijkstra's algorithm
     * @throws NoSuchElementException when no path from start to end can be found,
     *     including when no vertex containing start or end can be found
     */
    protected Path dijkstrasShortestPath(String start, String end) {
      PriorityQueue<Path> pq = new PriorityQueue<>();
      List<AirportVertex> vistedVertices = new ArrayList<>();
      AirportVertex startingVertex = vertexHashtable.get(start);
      AirportVertex endingVertex = vertexHashtable.get(end);
      if (startingVertex == null || endingVertex == null) { // Throw if vertex isn't in Hashtable
          throw new NoSuchElementException();
      }
      Path startingPath = new Path(startingVertex);
      Path currentPath;
      Path successorPath;
      boolean vertexVisted;
      boolean inPriorityQueue;
      pq.add(startingPath);
      while (!pq.isEmpty()) {
          currentPath = pq.remove();
          if (currentPath.end.getAbbreviation().equals(endingVertex.getAbbreviation())) { // Found the Shortest Path
              return currentPath;
          }
          vistedVertices.add(currentPath.end);
          // For each Edge leaving currentPath
          for (FlightEdge edgeLeaving : currentPath.end.getFlightEdges()) {
              vertexVisted = false;
              successorPath = new Path(currentPath, edgeLeaving);
              for (AirportVertex vertex : vistedVertices) { // Check if successorPath.end is visited
                  if (successorPath.end.getAbbreviation().equals(vertex.getAbbreviation())) {
                      vertexVisted = true;
                      break;
                  }
              }
              if (!vertexVisted) { // If successorPath.end wasn't visited
                  inPriorityQueue = false;
                  for (Path path : pq) {
                      // If Vertex is already in PriorityQueue
                      if (successorPath.end.getAbbreviation().equals(path.end.getAbbreviation())) {
                          inPriorityQueue = true;
                          // If the new found distance is shorter
                          if (successorPath.distance < path.distance) {
                              pq.remove(path);
                              pq.add(successorPath);
                          }
                          break;
                      }
                  }
                  if (!inPriorityQueue) { // If not in PriorityQueue
                      pq.add(successorPath);
                  }
              }
          }
      }
      throw new NoSuchElementException(); // Throw if no path can be found
  }
    /**
     * Returns the shortest path between start and end.
     * Uses Dijkstra's shortest path algorithm to find the shortest path.
     * 
     * @param start the data item in the starting vertex for the path
     * @param end the data item in the destination vertex for the path
     * @return list of data item in vertices in order on the shortest path between vertex 
     * with data item start and vertex with data item end, including both start and end 
     * @throws NoSuchElementException when no path from start to end can be found
     *     including when no vertex containing start or end can be found
     */
    public List<String> shortestPath(String start, String end) {
        return dijkstrasShortestPath(start,end).dataSequence;
    }
    /**
     * Returns the cost of the path (sum over edge weights) between start and end.
     * Uses Dijkstra's shortest path algorithm to find the shortest path.
     * 
     * @param start the data item in the starting vertex for the path
     * @param end the data item in the end vertex for the path
     * @return the cost of the shortest path between vertex with data item start 
     * and vertex with data item end, including all edges between start and end
     * @throws NoSuchElementException when no path from start to end can be found
     *     including when no vertex containing start or end can be found
     */
    public int getPathCost(String start, String end) {
        return dijkstrasShortestPath(start, end).distance;
    }
}