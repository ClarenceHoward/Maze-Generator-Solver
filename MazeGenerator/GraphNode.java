public class GraphNode {

    // Instance variables
    private int name;
    private boolean mark = false;

    // Class constructor initalizes the name but leaves marked as false.
    // As marked represents whether a node has been visited, the starting value must
    // be false.
    public GraphNode(int name) {

        this.name = name;
    }

    // Getters and setters.

    public void mark(Boolean mark) {

        this.mark = mark;
    }

    public boolean isMarked() {

        return mark;
    }

    public int getName() {

        return name;
    }
}