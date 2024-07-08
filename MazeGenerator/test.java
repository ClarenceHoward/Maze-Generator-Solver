import java.io.FileWriter;
import java.util.*;

public class test {

    public static void main(String args[]) {
        int length = Integer.parseInt(args[0]);
        int width = Integer.parseInt(args[1]);
        Maze maze;
        try {

            maze = new Maze(length, width);
            Iterator it = maze.solve();
            // while (it.hasNext()) {
            // GraphEdge node = (GraphEdge) it.next();
            // System.out.println(node.firstEndpoint().getName() + "-" +
            // node.secondEndpoint().getName());
            // System.out.println("\n");

            // }
            Graph graph = maze.getGraph();
            String string = "3\n" + length + "\n" + width + "\n0\n";
            for (int i = 0; i < length; i++) {

                for (int j = 0; j < width - 1; j++) {

                    if (i == 0 && j == 0) {
                        string += "s";
                    } else if (i == length - 1 && j == width - 2) {
                        string += "x";

                    }

                    else {

                        string += "o";
                    }

                    if (graph.getEdge(graph.getNode((i * width) + j), graph.getNode((i * width) + j + 1)).isMarked()
                            || graph.getEdge(graph.getNode((i * width) + j + 1), graph.getNode((i * width) + j))
                                    .isMarked()) {
                        string += "c";
                    } else {
                        string += "w";
                    }
                }

                string += "o\n";
                if (i == length - 1) {
                    break;
                }
                for (int j = 0; j < width; j++) {

                    if (graph.getEdge(graph.getNode((i * width) + j), graph.getNode(((i + 1) * width) + j)).isMarked()
                            || graph.getEdge(graph.getNode(((i + 1) * width) + j), graph.getNode((i * width) + j))
                                    .isMarked()) {
                        string += "c";
                    } else {
                        string += "w";
                    }
                    if (j == width - 1) {
                        break;
                    }
                    string += "w";
                }
                string += "\n";
            }
            FileWriter writer = new FileWriter(length + "x" + width + "Maze.txt");
            writer.write(string);
            writer.close();

        }

        catch (Exception e) {

            System.out.println(e);

        }

    }

}