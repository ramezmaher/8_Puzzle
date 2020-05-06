import java.util.HashSet;
import java.util.Random;

public class Board {
	
	private int[][] board;
	private int dimension;
	private int zeroX,zeroY;
	
    public Board(int[][] tiles) {
    	dimension = tiles.length;
    	board = new int[dimension][dimension];
    	for (int i=0 ; i<dimension ;i++) {
    		for (int j=0 ;j<dimension ;j++) {
    			board[i][j] = tiles[i][j];
    			if (board[i][j] == 0) {
    				zeroX = i;
    				zeroY = j;
    			}
    		}
    	}
    }
                                               
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

    public int dimension() {
    	return dimension;
    }

    public int hamming() {
    	int counter=0,validNum=1;
    	for (int i=0 ; i<dimension ; i++) {
    		for (int j=0 ; j<dimension ; j++) {
    			if (board[i][j] != validNum++)
    				counter++;
    		}
    	}
    	return counter-1;
    }

    public int manhattan() {
    	int counter=0;
    	for(int i=0 ; i<dimension ; i++) {
    		for (int j=0 ; j<dimension ; j++) {
    			if (board[i][j] != 0)
    			counter += getManhattan(board,i,j);
    		}
    	}
    	return counter;
    }

    public boolean isGoal() {
    	if (hamming()==0)
    	    return true;
    	else return false;
    }

    public boolean equals(Object y) {
    	if (this == y)
    		return true;
    	if (y==null)
    		return false;
    	if(getClass() != y.getClass())
    		return false;
    	Board b = (Board)y;
    	if(b.dimension() != this.dimension())
    		return false;
    	if (b.toString().equals(this.toString()))
    		return true;
    	return false;
    }

    public Iterable<Board> neighbors(){
    	HashSet<Board> set = new HashSet<Board>();
    	if(zeroX-1 >= 0) {
    	    board[zeroX][zeroY] = board[zeroX-1][zeroY];
    	    board[zeroX-1][zeroY] = 0;
    	    set.add(new Board(board));
    	    board[zeroX-1][zeroY] = board[zeroX][zeroY];
    	    board[zeroX][zeroY] = 0;
    	}
    	if(zeroX+1 < dimension) {
    		board[zeroX][zeroY] = board[zeroX+1][zeroY];
    	    board[zeroX+1][zeroY] = 0;
    	    set.add(new Board(board));
    	    board[zeroX+1][zeroY] = board[zeroX][zeroY];
    	    board[zeroX][zeroY] = 0;
    	}
        if(zeroY-1 >= 0) {
        	board[zeroX][zeroY] = board[zeroX][zeroY-1];
    	    board[zeroX][zeroY-1] = 0;
    	    set.add(new Board(board));
    	    board[zeroX][zeroY-1] = board[zeroX][zeroY];
    	    board[zeroX][zeroY] = 0;
    	}
    	if(zeroY+1 < dimension) {
    		board[zeroX][zeroY] = board[zeroX][zeroY+1];
    	    board[zeroX][zeroY+1] = 0;
    	    set.add(new Board(board));
    	    board[zeroX][zeroY+1] = board[zeroX][zeroY];
    	    board[zeroX][zeroY] = 0;
    	}
    	return set;
    }

    public Board twin() {
    	Random r = new Random();
    	int x1 = r.nextInt(dimension);
    	int y1 = r.nextInt(dimension);
    	while (board[x1][y1] == 0) {
    		y1=r.nextInt(dimension);
    	}
    	int x2 = r.nextInt(dimension);
    	int y2 = r.nextInt(dimension);
    	while (board[x2][y2]==0 || board[x2][y2]==board[x1][y1] ) {
    		x2=r.nextInt(dimension);
    	}
    	int dummy = board[x1][y1];
    	board[x1][y1]=board[x2][y2];
    	board[x2][y2]=dummy;
    	Board b = new Board(board);
    	board[x2][y2]=board[x1][y1];
    	board[x1][y1]=dummy;
    	return b;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
    	int[][] a = new int[3][3];
    	int count =0 ;
    	a[0][0]=8;
    	a[0][1]=1;
    	a[0][2]=3;
    	a[1][0]=4;
    	a[1][1]=0;
    	a[1][2]=2;
    	a[2][0]=7;
    	a[2][1]=6;
    	a[2][2]=5;
    	Board b = new Board(a);
    	System.out.println(b.toString());
    	System.out.println(b.twin().toString());
    	
    }
    
    
    private int getManhattan(int[][] a,int i,int j) {
    	
    	int x = (a[i][j]-1)/dimension;
    	int y = (a[i][j]-1)%dimension;
    	
    	x = (int)Math.abs(x-i);
    	y = (int)Math.abs(y-j);
    	
    	return x+y;
    }
}
