package me.cketernity.sudokusolver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
	
	private static final int GRID_SIZE = 9;
	
	public static void main(String[] args) {
		if(args.length > 0) {
			try {
				int[][] board = new int[GRID_SIZE][GRID_SIZE];
				BufferedReader reader = new BufferedReader(new FileReader(args[0]));
				String line;
				int row = 0, col = 0;
				while((line = reader.readLine()) != null) {
					for(int i = 0; i < line.length(); i++) {
						board[row][col] = Integer.parseInt(line.charAt(i) + "");
						col++;
						if(col >= GRID_SIZE) {
							col = 0;
							row++;
						}
					}
				}
				
				startGame(board);
			} catch (FileNotFoundException e) {
				System.out.println("Please enter a valid text file to read from! Solving default board...");
				main(new String[] {});
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		} else {
			int[][] board = {
					{7, 0, 2, 0, 5, 0, 6, 0, 0},
					{0, 0, 0, 0, 0, 3, 0, 0, 0},
					{1, 0, 0, 0, 0, 9, 5, 0, 0},
					{8, 0, 0, 0, 0, 0, 0, 9, 0},
					{0, 4, 3, 0, 0, 0, 7, 5, 0},
					{0, 9, 0, 0, 0, 0, 0, 0, 8},
					{0, 0, 9, 7, 0, 0, 0, 0, 5},
					{0, 0, 0, 2, 0, 0, 0, 0, 0},
					{0, 0, 7, 0, 4, 0, 2, 0, 3}
			};
			
			startGame(board);
		}
	}
	
	private static void startGame(int[][] board) {
		System.out.println("Original board:");
		printBoard(board);
		if(solveBoard(board)) {
			System.out.println("\nSuccessfully solved the board:");
			printBoard(board);
		} else {
			System.out.println("\nFailed to solve the board!");
		}
	}
	
	private static boolean solveBoard(int[][] board) {
		for(int row = 0; row < GRID_SIZE; row++) {
			for(int col = 0; col < GRID_SIZE; col++) {
				if(board[row][col] == 0) {
					for(int num = 1; num <= GRID_SIZE; num++) {
						if(isValidPlacement(board, num, row, col)) {
							board[row][col] = num;
							
							if(solveBoard(board)) {
								return true;
							} else {
								board[row][col] = 0;
							}
						}
					}
					
					return false;
				}
			}
		}
		
		return true;
	}
	
	private static boolean isValidPlacement(int[][] board, int num, int row, int col) {
		return !isNumberInRow(board, num, row) && !isNumberInCol(board, num, col) && !isNumberInBox(board, num, row, col);
	}
	private static boolean isNumberInRow(int[][] board, int num, int row) {
		for(int i = 0; i < GRID_SIZE; i++) {
			if(board[row][i] == num)
				return true;
		}
		
		return false;
	}
	private static boolean isNumberInCol(int[][] board, int num, int col) {
		for(int i = 0; i < GRID_SIZE; i++) {
			if(board[i][col] == num)
				return true;
		}
		
		return false;
	}
	private static boolean isNumberInBox(int[][] board, int num, int row, int col) {
		int boxRow = row - row % 3;
		int boxCol = col - col % 3;
		
		for(int i = boxRow; i < boxRow + 3; i++) {
			for(int j = boxCol; j < boxCol + 3; j++) {
				if(board[i][j] == num)
					return true;
			}
		}
		
		return false;
	}
	
	public static void printBoard(int[][] board) {
		for(int i = 0; i < board.length; i++) {
			if(i % 3 == 0 && i != 0)
				System.out.println("---------------------");
			
			for(int j = 0; j < board[i].length; j++) {
				if(j % 3 == 0 && j != 0)
					System.out.print("| ");
				
				System.out.print(board[i][j] + " ");
			}
			
			System.out.println();
		}
	}

}
