import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Queue;

public class EightPuzzle {
	public static void main(String[] args) {
		EightPuzzle runner = new EightPuzzle();

		Board easyBoard = new Board(new int[][] { {1,2,0}, {4,5,3}, {7,8,6} });
		System.out.println(easyBoard);

		ArrayList<Move> recRes = runner.rec(easyBoard);
		System.out.println(recRes.toString());
	}

	static class Board {
		public static final int width = 3;
		public static final int height = 3;
		public int[][] board;
		private int blankRow;
		private int blankColumn;

		public Board(int[][] board) {
			boolean seenAzero = false;
			this.board = new int[this.height][this.width];
			for (int y = 0; y < board.length; y++) {
				for (int x = 0; x < board[y].length; x++) {
					this.board[y][x] = board[y][x];
					if (board[y][x] == 0) {
						if (seenAzero)
							throw new Error("Can only have one zero to input board constructor.");

						this.blankRow = x;
						this.blankColumn = y;
						seenAzero = true;
					}
				}
			}
		}

		@Override
		public String toString() {
			String res = "[";
			for (int y = 0; y < this.board.length; y++) {
				res += "[";
				for (int x = 0; x < this.board[y].length; x++) {
					res += this.board[y][x] + ",";
				}
				res = res.substring(0,res.length() - 1);
				res += "]";
			}
			return res + "]";
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

		public Boolean isValidMove(Move move) {
			int newBlankRow = this.blankRow + move.x;
			int newBlankColumn = this.blankColumn + move.y;
			return newBlankRow >= 0 && newBlankRow < width && newBlankColumn >= 0 && newBlankColumn < height;
		}

		public void makeMove(Move move) {
			if (!this.isValidMove(move))
				throw new Error("Trying to make an invalid move on a board.");

			int x = this.blankRow;
			int y = this.blankColumn;
			int newX = x + move.x;
			int newY = y + move.y;

			int tmp = this.board[newY][newX];
			this.board[newY][newX] = this.board[y][x];
			this.board[y][x] = tmp;
		}
	}

	static class Move {
		public int x;
		public int y;

		public Move(int x, int y) {
			if (!((x == 0 || x == 1 || x == -1) && (y == 0 || y == 1 || y == -1))) {
				throw new Error("A move must be a 0,1,-1 for both x,y positions: (" + x + "," + y + ").");
			}
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return "(" + this.x + "," + this.y + ")";
		}
	}

	static class State {
		public Board board;
		public int depth;
		public ArrayList<Move> moves;
		public ArrayList<Board> visited;

		public State(Board board, ArrayList<Move> moves, ArrayList<Board> visited, int depth) {
			this.board = board;
			this.moves = moves;
			this.depth = depth;
			this.visited = visited;
		}
	}


	private Board goalBoard;
	private ArrayList<Move> possibleMoves;

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
		this.possibleMoves = moves;
	}

	private ArrayList<Move> getPossibleMoves(Board board) {
		ArrayList<Move> result = new ArrayList<>();

		for (Move move : this.possibleMoves) {
			if (board.isValidMove(move))
				result.add(move);
		}

		return result;
	}

	private ArrayList<Move> cloneMoveList(ArrayList<Move> moves) {
		ArrayList<Move> res = new ArrayList<>();

		for (Move move : moves)
			res.add(new Move(move.x, move.y));

		return res;
	}

	private ArrayList<Board> cloneBoardList(ArrayList<Board> boards) {
		ArrayList<Board> res = new ArrayList<>();

		for (Board board : boards)
			res.add(board.clone());

		return res;
	}

	public Boolean boardListContains(ArrayList<Board> boards, Board board) {
		for (Board b : boards) {
			if (b.equals(board))
				return true;
		}
		return false;
	}

	public ArrayList<Move> rec(Board initBoard) {
		ArrayList<Board> visited = new ArrayList<>();
		return recHelper(initBoard, new ArrayList<Move>(), visited);
	}

	public ArrayList<Move> recHelper(Board curBoard, ArrayList<Move> movesSoFar, ArrayList<Board> visited) {
		if (! boardListContains(visited, curBoard)) {
			if (curBoard.equals(goalBoard))
				return movesSoFar;

			visited.add(curBoard);

			for (Move m : this.possibleMoves) {
				Board b = curBoard.clone();
				if (b.isValidMove(m)) {
					b.makeMove(m);

					ArrayList<Move> newMoves = cloneMoveList(movesSoFar);
					newMoves.add(m);

					ArrayList<Move> res = recHelper(b, newMoves, visited);
					if (res != null)
						return res;
				}
			}
		}

		return null;
	}

	/*
		 public ArrayList<Move> bfs(Board initBoard, int limit) {
		 State initState = new State(initBoard, new ArrayList<Move>(), new ArrayList<Board>(), 0);
		 Queue<State> states = new LinkedList<>();
		 states.add(initState);

		 while (states.size() > 0) {
		 State head = states.poll();

		 if (head.depth > limit)
		 continue;

		 if (head.board.equals(goalBoard))
		 return head.moves;

		 else {
		 for (Move move : getPossibleMoves(head.board)) {
		 Board boardClone = head.board.clone();
		 boardClone.makeMove(move);
		 ArrayList<Move> newMoves = (ArrayList<Move>)head.moves.clone();
		 newMoves.add(move);
		 ArrayList<Board> visitedClone = (ArrayList<Board>)head.visited.clone();
		 visitedClone.add(boardClone.clone());
		 states.add(new State(boardClone, newMoves, visitedClone, head.depth + 1));
		 }
		 }
		 }

		 return null;
		 }
		 */

	public ArrayList<Move> dfs() {
		return null;
	}

	public ArrayList<Move> astar() {
		return null;
	}

	public ArrayList<Move> ids() {
		return null;
	}
}
