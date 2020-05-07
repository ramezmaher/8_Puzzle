import java.util.LinkedList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	private class CompareBoard implements Comparable<CompareBoard> {
		private int cost;
		private Board board;
		private CompareBoard(Board b) {
			cost=b.hamming()+b.manhattan();
			this.board=b;
		}
		@Override
		public int compareTo(CompareBoard b) {
			Board bo = b.getBoard();
			int altCost=bo.hamming()+bo.manhattan();
			if(cost<altCost)
				return -1;
			if(cost>altCost)
				return 1;
			return 0;
		}
		public boolean equals(Object y) {

			return this.board.equals(y);
		}
		private Board getBoard() {
			return this.board;
		}
	}

	private Board twin;
	private MinPQ<CompareBoard> queue;
	private MinPQ<CompareBoard> twinQueue;
	private boolean solvable;
	private LinkedList<Board> path;
	// find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
    	if (initial==null)
    		throw new IllegalArgumentException();
    	queue = new MinPQ<CompareBoard>();
    	twinQueue = new MinPQ<CompareBoard>();
    	path = new LinkedList<Board>();
    	queue.insert(new CompareBoard(initial));
    	twinQueue.insert(new CompareBoard(initial.twin()));
    	while(true) {
    	   path.add(queue.delMin().getBoard());
    	   if(path.getLast().isGoal()) {
    		   solvable=true;
    		   break;
    	   }
    	   for (Board b: path.getLast().neighbors()) {
    		   if(queue.isEmpty())
    			   queue.insert(new CompareBoard(b));
    		   else if(!qContains(b))
    			   queue.insert(new CompareBoard(b));
    	   }
    	   twin=twinQueue.delMin().getBoard();
    	   if(twin.isGoal()) {
    		   solvable=false;
    		   break;
    	   }
    	   for(Board b: twin.neighbors()) {
    		   if(twinQueue.isEmpty())
    			   twinQueue.insert(new CompareBoard(b));
    		   else if(!tqContains(b))
    			   queue.insert(new CompareBoard(b));
    	   }
    	}
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
    	return solvable;
    }

    // min number of moves to solve initial board
    public int moves() {
    	return path.size();
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution(){
    	return path;
    }    

    // test client (see below) 
    public static void main(String[] args) {
    	In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
    private boolean qContains(Board board) {
    	for(CompareBoard b: queue)
    		if(b.getBoard().equals(board))
    			return true;
    	return false;
    }
    private boolean tqContains(Board board) {
    	for(CompareBoard b: twinQueue)
    		if(b.getBoard().equals(board))
    			return true;
    	return false;
    }
}
