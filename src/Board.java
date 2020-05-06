
public class Board {
	
	private int[][] board;
	private int dimension;
	 // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
    	board = tiles;
    	dimension = tiles.length;
    }
                                           
    // string representation of this board
    public String toString() {
    	StringBuilder s = new StringBuilder();
    	s.append(dimension);
    	s.append(System.getProperty("line.separator"));
    	for (int i=0 ; i<dimension ; i++) {
    		for(int j=0 ; j<dimension ; j++) {
    			s.append(board[i][j]);
    			s.append(" ");
    		}
    		s.append(System.getProperty("line.separator"));
    	}
    	return s.toString();
    }

    // board dimension n
    public int dimension() {
    	return dimension;
    }

    // number of tiles out of place
    public int hamming() {
    	return 0;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
    	return 0;
    }

    // is this board the goal board?
    public boolean isGoal() {
    	return false;
    }

    // does this board equal y?
    public boolean equals(Object y) {
    	return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors(){
    	return null;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
    	return null;
    }

    // unit testing (not graded)
    public static void main(String[] args) {}
}
