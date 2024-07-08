import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Maze {

    // Instance Variables
    private Graph graph;
    private int length, width, coins, entrance, exit;

    // Class Initializer
    public Maze(int length, int width) throws MazeException {

        try {

            // Take constants from file headers.
            this.width = length;
            this.length = width;
            coins = 0;
            // Generate graph with the total number of node.
            graph = new Graph(length * width);

            // Add edges
            /*
             * For each row of the maze there is a loop that add the edges between the nodes
             * and below the nodes in that order. The node numbers are calculated by
             * position in row(i or j)
             * added to the row number multiplied by width of row.
             */
            // Temp variables used for input when calling insertEdge().
            String edgeLabel;
            int edgeKey;
            GraphNode pre, post;

            for (int row = 0; row < length; row++) {

                // Horizontal edges. Only on even line of input.

                for (int i = 0; i < width - 1; i++) {

                    // Catch GraphException when calling getNode() and insertEdge().
                    try {

                        // get edges for input to insertEdge().
                        pre = graph.getNode(((i + (row * width))));
                        post = graph.getNode((((i + 1) + (row * width))));

                        graph.insertEdge(pre, post, 0, "corridor");
                    } catch (Exception e) {

                        System.out.println(e + "1");
                    }

                }

                // Vertical Edges only for odd lines of input.

                if (row < length - 1) {

                    for (int j = 0; j < width; j++) {

                        // Catch any Graph Exceptions when calling get and insert.
                        try {

                            // Get the pre and post nodes of the edge which are the ones
                            // directly above it and below it in the row, but since row only increments
                            // every other line
                            // pre = row * width + index in row
                            pre = graph.getNode(((j) + (width * (row))));
                            post = graph.getNode(((j) + (width * (row + 1))));

                            graph.insertEdge(pre, post, 0, "corridor");
                        }

                        catch (Exception e) {

                            System.out.println(e + "2");
                        }
                    }
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    Graph getGraph() {

        return graph;
    }

    // Helper function for solve().
    private Stack<GraphEdge> recursiveSearch(GraphNode node, Stack path) {

        // Start by pushing node onto stack and marking it.

        node.mark(true);

        // Precaution for methods used that throw exceptions.
        try {

            // Base Case.
            if (path.size() == ((width * length) - 1)) {

                return path;

            }

            // If node is not exit get all the adjacent edges.
            Iterator<GraphEdge> edges = graph.incidentEdges(node);

            // For Each edge of node if it is not marked an does not require more keys that
            // one has left,
            // Call itself of the opposite(second) endpoint of node.
            while (edges.hasNext()) {

                GraphEdge edge = edges.next();
                if (!edge.secondEndpoint().isMarked()) {

                    path.push(edge);
                    edge.mark(true);

                    // If the edge is a door, pass coins subtract door cost.
                    path = recursiveSearch(edge.secondEndpoint(), path);

                    // If the recursive search returns a path to exit, break.

                    if (path.size() == ((width * length) - 1)) {
                        break;
                    }

                }
            }

            // After leaving the loop that tries all the edges, one may have found a path to
            // the exit
            // or may have found no valid path. If no valid path found the current node must
            // me popped of the path
            // stack and marked false.

            if (path.size() != ((width * length) - 1)) {

            }

        } catch (Exception e) {

            System.out.println(e);
        }

        // return to sender.
        return path;

    }

    public Iterator<GraphEdge> solve() {

        // This just calls helper function recursiveSolve. Recursive solve will return
        // either a path to the exit, or an empty stack.
        // If stack is empty, this function return null. Otherwise it returns the stack
        // as an iterator.

        Stack<GraphEdge> path = new Stack<GraphEdge>();

        try {

            recursiveSearch(graph.getNode(10), path);
        }

        catch (Exception e) {

            System.out.println(e);
        }

        if (path.empty() == true) {
            return null;
        }

        Iterator<GraphEdge> foundPath = path.iterator();

        return foundPath;

    }

}