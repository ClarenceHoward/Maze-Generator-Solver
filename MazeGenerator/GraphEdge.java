public class GraphEdge {

    // Instance Variables
    private GraphNode firstEndpoint;
    private GraphNode secondEndpoint;
    private int type;
    private String label;
    private boolean mark = false;

    // Class initializer.
    public GraphEdge(GraphNode u, GraphNode v, int type, String label) {

        firstEndpoint = u;
        secondEndpoint = v;
        this.type = type;
        this.label = label;
    }

    // Only getters and setters below here.
    public void mark(boolean t) {
        mark = t;
    }

    public boolean isMarked() {
        return mark;
    }

    public GraphNode firstEndpoint() {

        return firstEndpoint;
    }

    public GraphNode secondEndpoint() {

        return secondEndpoint;
    }

    public int getType() {

        return type;
    }

    public void setType(int type) {

        this.type = type;
    }

    public String getLabel() {

        return label;
    }

    public void setLabel(String newLabel) {

        this.label = newLabel;
    }

}