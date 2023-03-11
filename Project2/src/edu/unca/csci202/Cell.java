package edu.unca.csci202;

//@Author Kamren Sims
//Contains the constructor of a cell, used static characters to keep the value constant
public class Cell {

	
	static char numbers = '0';
	static char mine = 'M';
	
	boolean playerGuess;
	char boardSpot;
	
	public Cell(char boardSpot, boolean playerGuess) {
		this.boardSpot = boardSpot;
		this.playerGuess = playerGuess;
		
		
	}
	
}
