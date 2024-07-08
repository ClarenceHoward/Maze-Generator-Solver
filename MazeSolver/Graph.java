import java.util.*;

public class Graph implements GraphADT {

    // Instance variables
    private GraphNode nodeArray[];
    private ArrayList<ArrayList<GraphEdge>> edgeArray; // Used as an adjaceny list.

    // Class initializer.
    public Graph(int n) {

        int size = n;
        nodeArray = new GraphNode[size];

        // Initialize the array of nodes, used to mark visited or not, with the number
        // of nodes in the maze.
        for (int i = 0; i < size; i++) {

            nodeArray[i] = new GraphNode(i);
        }

        // Initialize the array used as an adjaceny list with the number of nodes in the
        // maze.
        edgeArray = new ArrayList<>(size);

        // Initialize each node in adjacency list with an empty array list that will
        // house all edges of said node.
        for (int i = 0; i < size; i++) {

            edgeArray.add(new ArrayList<>());
        }

    }

    public void insertEdge(GraphNode nodeu, GraphNode nodev, int type, String label) throws GraphException {

        try {

            // Check if both nodes exist before inserting edge.
            GraphNode checkNode = nodeArray[nodeu.getName()];
            if (checkNode == null) {
                throw new GraphException("Node not in Graph");
            }

            checkNode = nodeArray[nodev.getName()];
            if (checkNode == null) {
                throw new GraphException("Node not in Graph");
            }

        }

        catch (Exception e) {

            throw new GraphException("Node not in Graph");

        }

        // Initialize new edge to insert.
        GraphEdge newEdge = new GraphEdge(nodeu, nodev, type, label);

        edgeArray.get(nodeu.getName()).add(newEdge);

        // Flip the nodes in the edge around so one can travel both ways between nodes
        // and add it once more.
        newEdge = new GraphEdge(nodev, nodeu, type, label);

        edgeArray.get(nodev.getName()).add(newEdge);
    }

    public GraphNode getNode(int u) throws GraphException {

        GraphNode getNode = nodeArray[u];

        if (getNode == null) {
            throw new GraphException("Node not in Graph");
        }

        return getNode;
    }

    public Iterator incidentEdges(GraphNode u) throws GraphException {

        ArrayList<GraphEdge> checkList;

        try {

            // Get the array list in the adjaceny list at the index that corresponds to node
            // u.
            // If u not in list, out of bounds will be thrown.
            checkList = edgeArray.get(u.getName());
        }

        // Catch out of bounds and throw custom GraphException
        catch (Exception e) {
            throw new GraphException("Node not in graph.");
        }

        // If it is null there are no edges for u.
        if (checkList == null || checkList.isEmpty()) {

            return null;
        }

        // Create iterator.
        Iterator<GraphEdge> iterator = checkList.iterator();

        return iterator;
    }

    public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {

        // Start by getting an adjaceny list.
        Iterator<GraphEdge> adj = incidentEdges(u);

        // Then do a linear seach for desired edge. Since the maximum number of edges is
        // 4, a binary search is not needed.
        while (adj.hasNext()) {
            GraphEdge found = adj.next();
            if (v.getName() == found.secondEndpoint().getName()) {
                return found;
            }
        }
        throw new GraphException("Edge does not exist");

    }

    public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {

        // Since edges are added bidirectionally, one can check if an edge exist by
        // simply getting the edge in either configuration {u,v} or {v,u}.
        GraphEdge edge = getEdge(u, v);

        if (edge == null) {
            return false;
        } else {
            return true;
        }

    }

}