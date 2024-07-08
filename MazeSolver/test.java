import java.util.*;

public class test {

    public static void main(String args[]) {

        Maze maze;
        try {

            maze = new Maze("maze0.txt");
            Iterator it = maze.solve();
            while (it.hasNext()) {
                GraphNode node = (GraphNode) it.next();
                System.out.println(node.getName());
                System.out.println("\n");

            }

        }

        catch (Exception e) {

            System.out.println(e);

        }

    }

}