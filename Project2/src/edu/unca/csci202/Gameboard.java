package edu.unca.csci202;

import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

//@Author Kamren Sims
//Contains the intro, identifies a cell using a cell class. Contains what the gameboard is supposed to look like using cells in a 2d array.
//Places the mines at random.

public class Gameboard {

	Random rand = new Random();
	Scanner scan = new Scanner(System.in);
	
	int rows = 8;
	int columns = 8;
	Cell[][] cellBoard = new Cell[rows][columns];
	int numOfMines;
	int found = 0;
	int adjMines;
	boolean invalidAns = true;
	boolean rightAnswers = true;
	
	
	private Stack<Integer> rowS;
	private Stack<Integer> columnS;
	
	//Plays the games welcoming messages, if the user types 'n' then the program ends
	public void gameIntro() {
		System.out.println("Welcome to Minesweeper!");
		System.out.print("Would you like to play a game? (y/n): ");
		String userResponse = scan.nextLine();
		
		while(!userResponse.equalsIgnoreCase("n") && !userResponse.equalsIgnoreCase("y") || userResponse.isEmpty() ) {
			//compares both strings, ignores uppercase/lowercase differences ^
			
			System.out.println("Invalid input, please try again.");
			System.out.print("Would you like to play a game? (y/n): ");
			userResponse = scan.nextLine();
		}
		
		if(userResponse.equalsIgnoreCase("n")) {
			System.out.println("Goodbye!");
			System.exit(0);
		}
	}
	
	
	
	
	//Plants the mines in random spots on the board using a nested loop, and a random number gen, if the number of mines is less then 10 (or the board is empty)
	public void placeMines() {
	  while (numOfMines < 10) {
		for (int i = 0; i < rows;i++) {
		  for (int j = 0; j< columns;j++) {
			if (cellBoard[i][j] == null && numOfMines < 10) {
				int random = rand.nextInt(50);
				if(random < 5) {
					cellBoard[i][j] = new Cell(Cell.mine, false);
					numOfMines++;
				} else {
					continue;
				}
					}
				}
			}
		}
	}
	
	
	//Method that checks the board for adjacent mines, uses to nested loops, one to check for mines, one to check to place adj mines.
	//Uses a try catch statement to make sure the board doesn't go out of bounds, using the built in ArraysIndexOutOfBoundsException
	public void checkAdj() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (cellBoard[i][j].boardSpot != Cell.mine) {
					adjMines = 0;
					
		for (int a = -1; a < 2; a++) {
			for(int b = -1; b < 2; b++) {
				try {
					if (cellBoard[i + a][j + b].boardSpot == Cell.mine) {
						adjMines++;
						cellBoard[i][j].boardSpot = (char)(adjMines + '0');
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					
				}
			}
		}
				}
			}
		}
	}
	
	//Method used to print off the board
   public void Board() {
	   for(int i = 0; i < rows;i++) {
		   for (int j = 0; j < columns; j++) {
			   if(cellBoard[i][j].playerGuess == false) {
				   System.out.print('-' + " ");
			   } else {
				   System.out.print(cellBoard[i][j].boardSpot + " ");
			   }
		   }
		   System.out.println();
	   }
	   System.out.println();
   }
	   
	   //Method used to actually generate the board, checks for adjacent mines using the method above, and places mines using the method above.
	  public void genBoard() {
		  placeMines();
		  
		 for(int i = 0; i < rows; i++) {
			 for (int j = 0; j < columns;j++) {
				if(cellBoard[i][j] == null) {
					cellBoard[i][j] = new Cell(Cell.numbers,false);
				}
		 }
	  }
	 checkAdj();
	  }
   
	  
	  //Method used to reset the gameboard back to its original state
	  public void resetBoard() throws WrongInputException {
		numOfMines = 0;
	    found = 0;
	    adjMines = 0;
		invalidAns = true;
	    rightAnswers = true; 
	   
	    for(int i = 0; i< rows; i++) {
	    	for (int j = 0; j < columns;j++) {
	    		cellBoard[i][j] = null;
	    	}
	    }
	   run();
	  }
	  
	  //Method used to play the game again if the user decides to, calls resetboard method.
	  //If the user enters no then the program ends.
	  public void oneMoreTime() throws WrongInputException{
		  System.out.println("Thank you for playing Minesweeper.");
		  System.out.print("Would you like to play again? (y/n): ");
		  String userResponse = scan.nextLine();
		  
		  while (!userResponse.equalsIgnoreCase("n") && !userResponse.equalsIgnoreCase("y") || userResponse.isEmpty()) {
			  System.out.println("Invalid input, please try again.");
			  System.out.print("Would you like to play again? (y/n): ");
			  userResponse = scan.nextLine();
		  }
		  
		if(userResponse.equalsIgnoreCase("n")) {
			System.out.println("Goodbye!");
			System.exit(0);
		} else {
			resetBoard();
		}
	  }
	  
	  
	  //Method used to peek at the board, asks the user if he would like to peek or not. Lets the user picks a particular cell
	  public void peek() {
		  System.out.print("Would you like to peek? (y/n): ");
		  String userResponse = scan.nextLine();
		
		while(!userResponse.equalsIgnoreCase("n") && !userResponse.equalsIgnoreCase("y") || userResponse.isEmpty() ) {
			System.out.println("Invalid input, please try again.");
			System.out.print("Would you like to peek? (y/n): ");
			userResponse = scan.nextLine();			//Fixed issue where user could type anything and it would basically crash
		}
		 if(userResponse.equalsIgnoreCase("y")) {
			 for(int i = 0; i<rows;i++) {
				 for(int j=0; j<columns;j++) {
					if(cellBoard[i][j].boardSpot != Cell.mine && cellBoard[i][j].playerGuess == false) {
						System.out.print("- ");					
				 } else {
					 System.out.print(cellBoard[i][j].boardSpot + " ");
				 }
			 }
				 System.out.println();
		 }
		System.out.println();
		 }
	}
	  
	  //Method used to run the game
	  public void run() throws WrongInputException{
		  genBoard();
		while(rightAnswers) {
			if(found == numOfMines) {
				System.out.println("You win!");
				break;
			}
			Board();
			peek();
			Guess();
		}
		oneMoreTime();
	  }
	  
	  
	  //Method used to get the user input to guess the row and column, invalid inputs are not accepted and throws a new WrongInputException
	  //Displays whether or not the user entered numbers contain mines or not
	  public void Guess() throws WrongInputException {
		int rowG = 0;
		int colG = 0;
		rowS = new Stack<Integer>();
		columnS = new Stack<Integer>();
		
		
		//User input for Row number, wrong input exception if invalid input
		while(true) {
			System.out.print("Please enter a row number: ");
			
		 try {
			 rowG = Integer.parseInt(scan.nextLine()) - 1;
			 
			if(rowG < 0 || rowG > (rows - 1)) {
				throw new WrongInputException();
			} else {
				break;
			}
		 } catch (Exception e) {
			 System.out.println("Invalid input, please try again.");
		 }
		}
		
		//User input for col number, wrong input exception if invalid input
		while(true) {
			System.out.print("Please enter a column number: ");
			try {
				colG = Integer.parseInt(scan.nextLine()) - 1;
				if(colG < 0 || colG > (columns - 1)) {
					throw new WrongInputException();
				} else {
					break;
				}
			} catch (Exception e) {
				System.out.println("Invalid input, please try again");
			}
		}
		
		System.out.print("Does row " + (rowG + 1) + " and column " + (colG + 1) + " contain a mine? (y/n): ");
		String userResponse = scan.nextLine();  //note
		cellBoard[rowG][colG].playerGuess = true;
		
		//Makes sure there isn't a valid input
		while(!userResponse.equalsIgnoreCase("n") && !userResponse.equalsIgnoreCase("y") || userResponse.isEmpty()) {
			System.out.print("Invalid input,please try again.");
			System.out.print("Does row " + (rowG + 1) + " and column " + (colG + 1) + " contain a mine? (y/n): ");
			userResponse = scan.nextLine();
		}
		
		//User guesses correctly
		if (userResponse.equals("y") && cellBoard[rowG][colG].boardSpot == Cell.mine) {
			found ++;
			
			//User guesses incorrectly below, prompts the user to play again
		}else if (userResponse.equals("n") && cellBoard[rowG][colG].boardSpot == '0') {
			rowS.push(rowG);  //pushes row guess onto rowStack
			columnS.push(colG);  //pushes column guess onto columnStack
			
			while(rowS.size() > 0) {
				expand(rowS.pop(), columnS.pop()); 
			}
		} else if ((userResponse.equals("y") && cellBoard[rowG][colG].boardSpot != Cell.mine)|| (userResponse.equals("n") && cellBoard[rowG][colG].boardSpot == Cell.mine)) {
			System.out.println("Boom! You lose.");
			rightAnswers = false;
		}
	  }
	 
	  
	  
	  //When the user guesses a non-mine location, the block of guessed locations
	  //Expands in all directions until it hits non-zero adjacent mine locations.
	  //@Parameters row, column
	  
	  public void expand(int row, int column) {
		  
		  for(int i = -1; i < 2; i++) {
			  for(int j = -1; j < 2; j++) {
				  try {
					  if(cellBoard[row - i][column - j].boardSpot == '0' && cellBoard[row - i][column - j].playerGuess == false) {
						  rowS.push(row - i);   
						  columnS.push(column - j);   
						  cellBoard[row - i][column - j].playerGuess = true;
					  } else if (cellBoard[row - i][column - j].boardSpot > '0') {
						  cellBoard[row - i][column - j].playerGuess = true;
					  }
				  } catch (ArrayIndexOutOfBoundsException e) {
					  
				  }
			  } //for inside loop
		  } //for outside loop 
	  }  //end of expand method
	  
	  
 }
	
	
	
	
	
	
	
	

