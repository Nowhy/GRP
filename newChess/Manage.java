package newChess;

import java.util.Scanner;


public class Manage{
	
	private ChessBoard chessBoard = new ChessBoard();//Create a new board.
	private int startJ = -1, startI = -1, endJ = -1, endI = -1;//Start(j,i) and end(j,i)
	private Menu menu = new Menu(chessBoard);
	
	private int step = 0;
	private int[][] board = new int[10][9];

	private Scanner reader = new Scanner(System.in);//input reader
	private String input = "";//user input
	
	public void start(){
		
		//Print chess board and get the movement input
		printChessBoard();;
		userMove();
		
		//Continue get input until user type quit.
		while(!input.equals("Quit")){
			
			//check the input format -- update chess board OR give the hint for wrong format
			if(input.equals("Undo")){
				menu.undoStep();
			}else if(input.equals("New Game")){
				menu.newGame();
			}else if(input.equals("Save")){
				menu.saveBoard();//Haven't Achieved Now!!!
			}else if(checkInputFormat() && checkSameSide() && checkGuize()){
				int end =  board[endI][endJ];
				chessBoard.updateChessBoard(startJ, startI, endJ, endI);
				menu.commandRecord(input +" " + end);
			}else{
				if(!checkGuize()){
					System.out.println("Please Obey Chess Rule!");
				}
				System.out.println("Press enter to continue..........");
				reader.nextLine();
			}
			
			//Print chess board and get the movement input
			printChessBoard();;
			userMove();
		}
		System.out.println("Quiting....");
	}
	

	//print chess board
	public void printChessBoard(){
		step = chessBoard.getStep();
		board = chessBoard.getChessBoard();
		
		System.out.println("This is your chess board (Total step: " + step + "):");
		System.out.println("   ------------------------------"
					+ "-----------------------------------------------");
			
		for(int i = 0; i < 10; i++){
			System.out.print("" + i + " |\t ");
			for(int j = 0; j < 9; j++){
				System.out.print("" + board[i][j]+ "\t");
			}
			System.out.print("|\n");
			if(i == 4){
				System.out.println();
			}
		}
		
		System.out.println("   ---------------------------------"
				+ "--------------------------------------------");
		System.out.println("   \t 0\t 1\t 2\t 3\t 4\t 5\t 6\t 7\t 8\t"); 
		if(step % 2 == 0){
			System.out.println("Move Red Chess"); 
		}else{
			System.out.println("Move Black Chess");
		}
	    
	}
	
	
	//Let user give a input, quit for stop project.
	public void userMove(){
		
		//reset start(j,i) and end(j,i) to initialize number
		startJ = -1;
		startI = -1;
		endJ = -1;
		endI = -1;
		
		//get input
		System.out.println("Type your movement:\n" + 
				"Move Format: Move x y to x y\n" +
				"Type \"Undo\" for canceling\n" +
				"Type \"Save\" for saving chess board\n" +
				"Type \"New Game\" for opening a new game\n" +
				"Type \"Quit\" for quitting");
		input = reader.nextLine();
	}
	
	
	//check whether the format of input is correct
	public boolean checkInputFormat(){
		if(input.length() < 6){
			return false;
		}
		else{
		String[] subString = input.split(" ");
		
		//if it is suit for "Move X X to X X"
		if(subString.length == 6 && subString[0].equals("Move") && subString[3].equals("to")){
			
			//if it is suit for "Move int int to int int"
			try{
				startJ = Integer.parseInt(subString[1]);
				startI = Integer.parseInt(subString[2]);
				endJ = Integer.parseInt(subString[4]);
				endI = Integer.parseInt(subString[5]);
				
				//if it is suit for "Move [0,8] [0,9] to [0,8] [0,9]"
				if(checkInputRange(startJ, 0, 8) && checkInputRange(startI, 0, 9) 
						&& checkInputRange(endJ, 0, 8) && checkInputRange(endI, 0, 9)){
					return true;
				}
				System.out.println("Invalid! The range of x is [0,8], the range of y is [0,9].");
				
			}catch(NumberFormatException e){
				System.out.println("Invalid! Please check x and y are integer numbers!");
			}
			
		}else{
			System.out.println("Invalid! Please check your input format!");
		}
		
		return false;
		}
	}
	
	
	//To check whether a number is in [lowBoarder, highBoarder] 
	private boolean checkInputRange(int checkedNum, int lowBoarder, int highBoarder){
		if(checkedNum >= lowBoarder && checkedNum <= highBoarder){
			return true;
		}
		return false;
	}
	
	//check start point and end point are valid
	private boolean checkSameSide(){
		if(!checkInputFormat()){
			return false;
		}
		else{
		step = chessBoard.getStep();
		boolean b1,b2;
		board = chessBoard.getChessBoard();
		if(step % 2 == 0){
			 b1 = checkInputRange(board[startI][startJ], 1, 7);
			 b2 = checkInputRange(board[endI][endJ], 1, 7);
		}
		else{
			 b1 = checkInputRange(board[startI][startJ], 8, 14);
			 b2 = checkInputRange(board[endI][endJ], 8, 14);
		}
		if(!b1){
			System.out.println("Please choose your side chess !");
		}
		if(b2){
			System.out.println("Please not choose your side chess !");
		}
		return (b1 && !b2);
		}
	}
	
	
	private boolean checkGuize(){
		if(checkSameSide()){
			return false;
		}
		else{
		board = chessBoard.getChessBoard();
		int start = board[startI][startJ];
		boolean b1 = false, b2 = false;
		switch(start){
		   case 1://hongshuai
			   return !(endI>2||endJ<3||endJ>5) && !(Math.abs(startJ-endJ)+Math.abs(startI-endI)>1);
		   case 8://heijiang
			   return !(endI<7||endJ>5||endJ<3) && !(Math.abs(startJ-endJ)+Math.abs(startI-endI)>1); 
		   case 2://hongche
	       case 9://heiche
	    	   if(startJ == endJ){
	    		    if(Math.abs(startI - endI)==1){
	    		    	b2 = true;
	    		    	break;
	    		    }else if(startI < endI-1){
						for(int i = startI + 1; i < endI; i++){
							b2 = (board[i][startJ] == 0);
							if(!b2)break;
						}
					}
					else{
						for(int i = endI + 1; i < startI; i++){
							b2 = (board[i][startJ] == 0);
							if(!b2)break;
						}
					}
				}
				else{
					if(Math.abs(startJ - endJ)==1){
	    		    	b2 = true;
	    		    	break;
	    		    }else if(startJ < endJ - 1){
						for(int j = startJ + 1; j < endJ; j++){
							b2 = (board[startI][j] == 0);
							if(!b2)break;
						}
					}
					else{
						for(int j= endJ + 1; j < startJ; j++){
							b2 = (board[startI][j] == 0);
							if(!b2)break;
						}
					}
				}
	    	   return !(startJ != endJ && startI != endI)&&b2;
	       case 3://hongma
	       case 10://heima
	    	   int i = 0,j = 0;
	    	   if(endI-startI==2){
					i=startI+1;
					j=startJ;
	    	   }
	    	   else if(startI-endI==2){
					i=startI-1;
					j=startJ;
	    	   }
	    	   else if(endJ-startJ==2){
					i=startI;
					j=startJ+1;
	    	   }
	    	   else if(startJ-endJ==2){
					i=startI;
					j=startJ-1;
	    	   }
	    	   return Math.abs(endI-startI) * Math.abs(endJ-startJ)==2 && !(board[i][j] != 0);
	       case 4://hongpao
	       case 11://heipao	
	    	   if(board[endI][endJ] == 0){//zou
					if(startJ == endJ){
						if(Math.abs(startI - endI)==1){
			    		    	b1 = true;
			    		    	break;
						}else if(startI < endI-1){
							for(i = startI + 1; i < endI; i++){
								b1 = (board[i][startJ] == 0);
								if(!b1)break;
							}
						}else{
							for(i = endI + 1; i < startI; i++){
								b1 = (board[i][startJ] == 0);
								if(!b1)break;
							}
						}
					}
					else{
						 if(Math.abs(startJ - endJ)==1){
			    		    	b1 = true;
			    		    	break;
						 }else if(startJ < endJ){
							for(j = startJ + 1; j < endI; j++){
								b1 = (board[startI][j] == 0);
								if(!b1)break;
							}
						}else{
							for(j = endJ + 1; j < startJ; j++){
								b1 = (board[startI][j] == 0);
								if(!b1)break;
							}
						}
					}
				}
	    	   else{//chi
					int count=0;
					if(startJ == endJ){
						if(startI < endI){
							for(i=startI+1;i<endI;i++){
								if(board[i][startJ]!=0){
									count++;
								}
							}
							b1 = !(count != 1);
						}
						else{
							for(i=endI+1;i<startI;i++){
								if(board[i][startJ] != 0){
									count++;
								}
							}
							b1 = !(count != 1);
						}
					}
					else{
						if(startJ<endJ){
							for(j=startJ+1;j<endJ;j++){
								if(board[startI][j]!=0){
									count++;
								}
							}	
							b1 = !(count != 1);
						}
						else{
							for(j=endJ+1;j<startJ;j++){
								if(board[startI][j] != 0){
									count++;
								}
							}
							b1 = !(count != 1);
						}
					}
				}
			   return 	!(startJ!=endJ && startI!=endI) && b1;	   
	       case 5://hongshi
	    	   return (endI>2||endJ<3||endJ>5) && !(Math.abs(startI-endI) != 1 || Math.abs(endJ-startJ) != 1);
	       case 12://heishi
	    	   return (endI>7||endJ<3||endJ>5) && !(Math.abs(startI-endI) != 1 || Math.abs(endJ-startJ) != 1);   
	       case 6://hongxiang
	    	   return !(endI>4) && (Math.abs(startI-endI) == 2 || Math.abs(startJ-endJ) == 2) && (board[(startI+endI)/2][(startJ+endJ)/2] == 0);
	       case 13://heixiang
	    	   return !(endI<5) && (Math.abs(startI-endI) == 2 || Math.abs(startJ-endJ) == 2) && (board[(startI+endI)/2][(startJ+endJ)/2] == 0);   
	       case 7://hongbing
				return !(endI < startI) && !(startI < 5 && startI == endI) && !(endI - startI + Math.abs(endJ - startJ) > 1);
	       case 14://heizu
				return !(endI > startI) && !(startI > 4 && startI == endI) && !(startI - endI + Math.abs(endJ - startJ) > 1);
	       default:
	    	   System.out.println("Invalid move!");
	    	   break;
		}
		return true;
		}
	}
	
}