package newChess;

public class ChessBoard{
	
	private int[][] chessBoard = newBoard();
	private int step = 0;
	
	
	//The initialization board
	public int[][] newBoard(){
		int[][] newBoard = new int[][]{
				{2,3,6,5,1,5,6,3,2},
				{0,0,0,0,0,0,0,0,0},
				{0,4,0,0,0,0,0,4,0},
				{7,0,7,0,7,0,7,0,7},
				{0,0,0,0,0,0,0,0,0},

				{0,0,0,0,0,0,0,0,0},
				{14,0,14,0,14,0,14,0,14},
				{0,11,0,0,0,0,0,11,0},
				{0,0,0,0,0,0,0,0,0},
				{9,10,13,12,8,12,13,10,9},
			};
		return newBoard;
	}
	
	//get the chess board
	public int[][] getChessBoard(){
		return chessBoard;
	}
	
	//getStep
	public int getStep(){
		return step;
	}
	
	public void setStep(int step){
		this.step = step;
	}

	public void cleanChessBoard(){
		chessBoard = newBoard();
	}
	
	
	//update the chess board
	public void updateChessBoard(int startJ, int startI, int endJ, int endI){
		chessBoard[endI][endJ] = chessBoard[startI][startJ];
		chessBoard[startI][startJ] = 0;
		step++;
		System.out.println("Update Succeed!");
	}
	
	public void updateEndPiece(int I,int J, int num){
		chessBoard[I][J] =  num;
	}
	
	
}