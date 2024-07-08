import java.util.*;
import java.io.*;

public class SmallSolve {

    public static void main (String[] args) {
	GraphNode u, v;
	SmallerDrawMaze display;
	int delay = 0;
	BufferedReader in;
	String line;


	if (args.length != 1 && args.length != 2)
	    System.out.println("Usage: java Solve labyrithFile OR java Solve labyrinthFile speed");
	else {
	    if (args.length == 2) delay = Integer.parseInt(args[1]);
	    display = new SmallerDrawMaze(args[0]);
	    

	    
	    try {
	    	in = new BufferedReader(new InputStreamReader(System.in));

		Maze theMaze = new Maze(args[0]);
		
	    	System.out.println("Press RET to continue");
	    	line = in.readLine();		

		Iterator solution = theMaze.solve();

		if (solution != null) {
		    if (solution.hasNext()) u = (GraphNode)solution.next();
		    else return;
		    while (solution.hasNext()) {
			v = (GraphNode)solution.next();
			Thread.sleep(delay);
			display.drawEdge(u,v);
			u = v;
		    }
		}
		else {
		    System.out.println("No solution was found");
		    System.out.println("");
		}


		System.out.println("Press RET to finish");
	        line = in.readLine();

	    }
	    catch (MazeException e) {
		System.out.println("Error reading maze file");
	    }
	    catch (IOException inex) {
		System.out.println("Error reading from keyboard");
	    }
	    catch (Exception ex) {
		System.out.println(ex.getMessage());
	    }

	    display.dispose();
	    System.exit(0);
	}
    }
}
