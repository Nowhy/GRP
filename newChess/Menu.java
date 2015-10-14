package newChess;
import java.util.HashMap;


public class Menu {
    
	private HashMap<Integer,String> moveStep = new HashMap<Integer, String>();
	private ChessBoard board;
	
	public Menu(ChessBoard board){
		this.board = board;
	}
	
	public void commandRecord(String input){
		moveStep.put(board.getStep(), input);
	}
	
	public void undoStep(){
		if(moveStep.size() == 0){
			System.out.println("No step to undo.");
			return;
		}
		
		int maxStep = moveStep.size();
		String command = moveStep.get(maxStep);
		String[] subString = command.split(" ");
		
		int startJ = Integer.parseInt(subString[1]);
		int startI = Integer.parseInt(subString[2]);
		int endJ = Integer.parseInt(subString[4]);
		int endI = Integer.parseInt(subString[5]);
		int end = Integer.parseInt(subString[6]);
		board.updateChessBoard(endJ, endI, startJ, startI);
		if(end != 0){
			board.updateEndPiece(endI, endJ, end);
		}
		board.setStep(maxStep-1);
		moveStep.remove(maxStep);
	}
	
	public void newGame(){
		board.cleanChessBoard();
		board.setStep(0);
		moveStep.clear();
	}
	
	public void saveBoard(){
		System.out.println("Have'n Achieved Now! To be continue...");
		//Save board and size and moveSteps...
		//Use current time
		//Let User input a name
		//Can show all board
	}
	
}
