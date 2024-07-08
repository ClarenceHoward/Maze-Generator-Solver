import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Maze {

    // Instance Variables
    private Graph graph;
    private int length, width, coins, entrance, exit;

    // Class Initializer
    public Maze(String inputFile) throws MazeException {

        BufferedReader input;

        try {

            // Initialize buffer with input file.
            input = new BufferedReader(new FileReader(inputFile));

            input.readLine(); // Disgard first line of file.

            // Take constants from file headers.
            width = Integer.parseInt(input.readLine());
            length = Integer.parseInt(input.readLine());
            coins = Integer.parseInt(input.readLine());

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

                String mazeLine = input.readLine();

                // Horizontal edges. Only on even line of input.

                for (int i = 0; i < mazeLine.length(); i++) {

                    // Check for start.
                    if (mazeLine.charAt(i) == 's') {

                        entrance = i / 2 + (row * width);
                    }

                    // Check for exit.
                    if (mazeLine.charAt(i) == 'x') {

                        exit = i / 2 + (row * width);
                    }

                    // Only add edges if odd number.
                    if (i % 2 != 0 && i != 0) {

                        char iChar = mazeLine.charAt(i);

                        // Check type of edge and set input variables accordingly.
                        switch (iChar) {

                            case 'w':
                                continue;

                            case 'c':
                                edgeLabel = "corridor";
                                edgeKey = 0;
                                break;

                            default:
                                edgeLabel = "door";
                                edgeKey = Character.getNumericValue(iChar);
                        }

                        // Catch GraphException when calling getNode() and insertEdge().
                        try {

                            // get edges for input to insertEdge().
                            pre = graph.getNode(((i / 2) + (row * width)));
                            post = graph.getNode((((i / 2) + 1) + (row * width)));

                            graph.insertEdge(pre, post, edgeKey, edgeLabel);
                        } catch (Exception e) {

                            System.out.println(e + "1");
                        }
                    }
                }

                // Vertical Edges only for odd lines of input.
                mazeLine = input.readLine();
                if (mazeLine == null) {
                    break;
                }

                char jChar;

                for (int j = 0; j < mazeLine.length(); j += 2) {

                    jChar = mazeLine.charAt(j);

                    switch (jChar) {

                        case 'w':
                            continue;

                        case 'c':
                            edgeLabel = "corridor";
                            edgeKey = 0;
                            break;

                        default:
                            edgeLabel = "door";
                            edgeKey = Character.getNumericValue(jChar);
                    }

                    // Catch any Graph Exceptions when calling get and insert.
                    try {

                        // Get the pre and post nodes of the edge which are the ones
                        // directly above it and below it in the row, but since row only increments
                        // every other line
                        // pre = row * width + index in row
                        pre = graph.getNode(((j / 2) + (width * (row))));
                        post = graph.getNode(((j / 2) + (width * (row + 1))));

                        graph.insertEdge(pre, post, edgeKey, edgeLabel);
                    }

                    catch (Exception e) {

                        System.out.println(e + "2");
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
    private Stack<GraphNode> recursiveSearch(GraphNode node, int coins, Stack path) {

        // Start by pushing node onto stack and marking it.
        path.push(node);
        node.mark(true);

        // Precaution for methods used that throw exceptions.
        try {

            // Base Case.
            if (node == graph.getNode(exit)) {

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
                    if (edge.getType() <= coins) {

                        // If the edge is a door, pass coins subtract door cost.
                        path = recursiveSearch(edge.secondEndpoint(), (coins - edge.getType()), path);

                        // If the recursive search returns a path to exit, break.
                        GraphNode top = (GraphNode) path.peek();
                        if (top == graph.getNode(exit)) {
                            break;
                        }
                    }
                }
            }

            // After leaving the loop that tries all the edges, one may have found a path to
            // the exit
            // or may have found no valid path. If no valid path found the current node must
            // me popped of the path
            // stack and marked false.
            GraphNode top = (GraphNode) path.peek();
            if (top != graph.getNode(exit)) {
                path.pop();
                node.mark(false);
            }

        } catch (Exception e) {

            System.out.println(e);
        }

        // return to sender.
        return path;

    }

    public Iterator<GraphNode> solve() {

        // This just calls helper function recursiveSolve. Recursive solve will return
        // either a path to the exit, or an empty stack.
        // If stack is empty, this function return null. Otherwise it returns the stack
        // as an iterator.

        Stack<GraphNode> path = new Stack<GraphNode>();

        try {

            recursiveSearch(graph.getNode(entrance), coins, path);
        }

        catch (Exception e) {

            System.out.println(e);
        }

        if (path.empty() == true) {
            return null;
        }

        Iterator<GraphNode> foundPath = path.iterator();

        return foundPath;

    }

}