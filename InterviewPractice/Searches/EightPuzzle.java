import java.util.ArrayList;

public class EightPuzzle {
	public static void main(String[] args) {
		Board easyBoard = new Board(new int[][] { {1,2,0}, {4,5,3}, {7,8,6} });
	}

	static class Board {
		public int[][] board;
		private int blankRow;
		private int blankColumn;

		public Board(int[][] board) {
			this.board = new int[3][3];
			for (int y = 0; y < board.length; y++) {
				for (int x = 0; x < board[y].length; x++) {
					if (board[y][x] == 0) {
						this.blankRow = x;
						this.blankColumn = y;
						return;
					}
				}
			}
			throw new Error("Invalid input puzzle to Board constructor");
		}

		@Override
		public String toString() {
			return "";
		}

		public Boolean equals(Board other) {
			if (this.board.length != other.board.length)
				return false;

			for (int y = 0; y < this.board.length; y++) {
				if (this.board[y].length != other.board[y].length)
					return false;

				for (int x = 0; x < this.board[y].length; x++) {
					if (this.board[y][x] != other.board[y][x])
						return false;
				}
			}

			return true;
		}

		public Board clone() {
			Board clone = new Board(this.board);
			return clone;
		}
	}

	static class Move {
		public int x;
		public int y;

		public Move(int x, int y) {
			if (!((x == 0 || x == 1 || x == -1) && (y == 0 || y == 1 || x == -1))) {
				throw new Error("A move must be a 0,1,-1 for both x,y positions.");
			}
			this.x = x;
			this.y = y;
		}
	}


	private Board goalBoard;
	private ArrayList<Move> moves;

	public EightPuzzle() {
		this.goalBoard =  new Board(new int[][] {{1,2,3},{4,5,6},{7,8,0}});
		ArrayList<Move> moves = new ArrayList<>();
		moves.add(new Move(-1,-1));
		moves.add(new Move(0,-1));
		moves.add(new Move(1,-1));
		moves.add(new Move(-1,0));
		moves.add(new Move(1,0));
		moves.add(new Move(-1,1));
		moves.add(new Move(0,1));
		moves.add(new Move(1,1));
		this.moves = moves;
	}

	public ArrayList<Move> breadthFirstSearch() {
		ArrayList<Move> solution = new ArrayList<>();
		return solution;
	}

	public ArrayList<Move> depthFirstSearch() {
		ArrayList<Move> solution = new ArrayList<>();
		return solution;
	}

	public ArrayList<Move> aStarSearch() {
		ArrayList<Move> solution = new ArrayList<>();
		return solution;
	}

	public ArrayList<Move> iterativeDeepeningSearch() {
		ArrayList<Move> solution = new ArrayList<>();
		return solution;
	}
}
