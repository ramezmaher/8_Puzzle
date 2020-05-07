import java.util.LinkedList;
import edu.princeton.cs.algs4.MinPQ;

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
    	int[][] a = new int[3][3];
    	int count =0 ;
    	a[0][0]=0;
    	a[0][1]=1;
    	a[0][2]=4;
    	a[1][0]=3;
    	a[1][1]=2;
    	a[1][2]=5;
    	a[2][0]=7;
    	a[2][1]=8;
    	a[2][2]=6;
    	Board b = new Board(a);
    	Solver s = new Solver(b);
    	System.out.println(s.isSolvable());
    	for (Board bo: s.solution()) {
    		System.out.println(bo.toString());
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
