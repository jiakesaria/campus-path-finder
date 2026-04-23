// === CS400 File Header Information ===
// Name: Jia Bhavesh Kesaria
// Email: kesaria@wisc.edu
// Group and Team: <your group name: two letters, and team color>
// Group TA: <name of your group's ta>
// Lecturer: Florian
// Notes to Grader: <optional extra notes>

import java.util.PriorityQueue;
import java.util.List;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Arrays; 
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
        extends BaseGraph<NodeType, EdgeType>
        implements GraphADT<NodeType, EdgeType> {

    /**
     * While searching for the shortest path between two nodes, a SearchNode
     * contains data about one specific path between the start node and another
     * node in the graph. The final node in this path is stored in its node
     * field. The total cost of this path is stored in its cost field. And the
     * predecessor SearchNode within this path is referened by the predecessor
     * field (this field is null within the SearchNode containing the starting
     * node in its node field).
     *
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost
     * SearchNode has the highest priority within a java.util.PriorityQueue.
     */
    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;

        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }

        public int compareTo(SearchNode other) {
            if (cost > other.cost)
                return +1;
            if (cost < other.cost)
                return -1;
            return 0;
        }
    }

    /**
     * Constructor that sets the map that the graph uses.
     */
    public DijkstraGraph() {
        super(new PlaceholderMap<>());
    }

    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations. The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *                                or when either start or end data do not
     *                                correspond to a graph node
     */
     protected SearchNode computeShortestPath(NodeType start, NodeType end) {
        
        if (!this.containsNode(start) || !this.containsNode(end)) {
                throw new NoSuchElementException("Start or end node not in the graph");
        }
        //Getting actual start and end nodes 
        Node startNode = this.nodes.get(start);
        Node endNode = this.nodes.get(end);
    
        //Initiaizing priority queue for tracking nodes
        PriorityQueue<SearchNode> pq = new PriorityQueue<>();
        PlaceholderMap<Node, Node> visited = new PlaceholderMap<>();

        pq.add(new SearchNode(startNode, 0, null)); 

        while(!pq.isEmpty()) {
                SearchNode current = pq.poll(); // Gives the node with lowest cost 

                if (current.node.equals(endNode)) {
                        return current;
                }
                
                //Skip if node already visited 
                if (visited.containsKey(current.node)) {
                        continue;
                }

                visited.put(current.node, current.node);

                for (Edge edge : current.node.edgesLeaving) {
                        // Skip if destination node has been visited
                         if (visited.containsKey(edge.successor)) {
                                 continue;
                         }

                        double totalCost = current.cost + edge.data.doubleValue(); //Calc>
                        pq.add(new SearchNode(edge.successor, totalCost, current));
                }
        }

       throw new NoSuchElementException("No path exists from " + start + " to " + end); //Incase all the nodes has been exhausted and we haven't found the end 
    }

    
    /**
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value. This list of data values starts with the start
     * value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shorteset path. This
     * method uses Dijkstra's shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) {
        SearchNode endNode = computeShortestPath(start, end); //Shortest Path computed using our helper method
	LinkedList<NodeType> path = new LinkedList<>();

	for (SearchNode current = endNode; current != null; current = current.predecessor) {
		path.addFirst(current.node.data);
	}
        return path;
   }

    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path freom the node containing the start data to the node containing the
     * end data. This method uses Dijkstra's shortest path algorithm to find
     * this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     */
    public double shortestPathCost(NodeType start, NodeType end) {
        SearchNode endNode = computeShortestPath(start, end);
        return endNode.cost;  
    }

   /**
    * Constructs and returns a sample directed, weighted graph  used in lecture examples.
    *
    * @return a pre-defined DijkstraGraph with nodes and edges
    */
    private DijkstraGraph<String, Double> lectureGraph() {
	DijkstraGraph<String, Double> graph = new DijkstraGraph<>();

	// Adding nodes
	graph.insertNode("A");
        graph.insertNode("B");
	graph.insertNode("C");
	graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("G");
        graph.insertNode("H");
	// Adding edges
	graph.insertEdge("A", "B", 4.0);
	graph.insertEdge("A", "C", 2.0);
	graph.insertEdge("A", "E", 15.0);
	graph.insertEdge("B", "D", 1.0);
	graph.insertEdge("B", "E", 10.0);
	graph.insertEdge("C", "D", 5.0);
	graph.insertEdge("D", "E", 3.0);
	graph.insertEdge("D", "F", 0.0);
	graph.insertEdge("F", "H", 4.0);
	graph.insertEdge("F", "D", 2.0);
	graph.insertEdge("G", "H", 4.0);

	return graph;
    }
    // TODO: implement 3+ tests in step 4.1

   /** 
    * Tests the shortest path from node A to node E.
    */
    @Test
    public void test1() {
	DijkstraGraph<String, Double> graph = lectureGraph();

	// Test shortest path from A to E
	List<String> path = graph.shortestPathData("A", "E");
        double  cost = graph.shortestPathCost("A", "E");

	// Expected path: A -> B -> D -> E with cost (4 + 1 + 3 =) 8
	List<String> expectedPath = Arrays.asList("A", "B", "D", "E");
	double expectedCost = 8.0;

	Assertions.assertEquals(expectedPath, path);
	Assertions.assertEquals(expectedCost, cost);
    }

    /** 
    * Tests the shortest path from node A to node H.
    */
    @Test
    public void test2() {
        DijkstraGraph<String, Double> graph = lectureGraph();

        // Test shortest path from A to H
        List<String> path = graph.shortestPathData("A", "H");
        double cost = graph.shortestPathCost("A", "H");

        // Expected path: A -> B -> D -> F -> H  with cost (4 + 1 + 0 + 4 =) 9
        List<String> expectedPath = Arrays.asList("A", "B", "D", "F", "H");
        double expectedCost = 9.0;

       Assertions.assertEquals(expectedPath, path);
       Assertions.assertEquals(expectedCost, cost);
    }

   /** 
    *Tests that no path exists from node H to node A in a directed graph.
    */
    @Test
    public void test3() {
	DijkstraGraph<String, Double> graph = lectureGraph();

	 // Test that no path exists from H to A
	try {
		graph.shortestPathData("H", "A");
		Assertions.fail("Expected NoSuchElementException, but not thrown for path from H to A");
	} catch (NoSuchElementException e) {
            // Expected outcome; Test passes
        }
   }
}
