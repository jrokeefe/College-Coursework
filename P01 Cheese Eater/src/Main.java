//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           P01 Cheese Eater
// Files:           N/A
// Course:          CS 300 Spring 2018
//
// Author:          Jack O'Keefe
// Email:           jokeefe2@wisc.edu
// Lecturer's Name: Dahl
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    Hank Freyberg
// Partner Email:   hfreyberg@wisc.edu
// Lecturer's Name: Dahl
// 
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   _X__ Write-up states that pair programming is allowed for this assignment.
//   __X_ We have both read and understand the course Pair Programming Policy.
//   __X_ We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully 
// acknowledge and credit those sources of help here.  Instructors and TAs do 
// not need to be credited here, but tutors, friends, relatives, room mates 
// strangers, etc do.  If you received no outside help from either type of 
// source, then please explicitly indicate NONE.
//
// Persons:         n/a
// Online Sources:  n/a
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
import java.util.Random;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Random randGen = new Random();
		Scanner scnr = new Scanner(System.in);
		final int numberOfWalls = 20;
		final int ROOM_HEIGHT = 10;
		final int ROOM_WIDTH = 20;
		int mouseX = 0;
		int mouseY = 0;
		int steps;
		char move;
		int cheese = 0;
		boolean eat;
		String ending = "Thank you for running the Cheese Eater simulation.";
		String userInput;
		int[] mousePosition = new int[2];
		char[][] room = new char[ROOM_HEIGHT][ROOM_WIDTH];
		int [][] cheesePositions = new int [10][2];
		placeWalls(room, numberOfWalls, randGen);
		placeCheeses(cheesePositions, room, randGen);
		boolean same;
		for(int i = 0; i < cheesePositions.length; ++i) {
			same = true;
			boolean cheesePosition;
			while(same) {
				cheesePosition = true;
				int randomX = randGen.nextInt(room[0].length);
				int randomY = randGen.nextInt(room.length);
				for(int j = 0; j < cheesePositions.length; ++j) {
					if (cheesePositions[j][0] != randomX && cheesePositions[j][1] != randomY) {
						cheesePosition = false;
					}
				}
				if (room[randomY][randomX] == '.' && !cheesePosition) {
					mouseX = randomX;
					mouseY = randomY;
					same = false;
				}
			}
		}
		boolean run = true;
		boolean error;
		while (run) {
			System.out.println("Welcome to the Cheese Eater simulation.");
			for (int i = 0; i < 39; ++i) {
				System.out.print("=");
			}
			System.out.println();
			System.out.print("Enter the number of steps for this simulation to run: ");
			steps = scnr.nextInt();
			scnr.nextLine();
			System.out.println();
			for (int i = 0; i < steps; ++i) {
				error = true;
				while (error) {
					System.out.println("The mouse has eaten " + cheese + " cheese!");
					printRoom(room, cheesePositions, mouseX, mouseY);
					System.out.print("Enter the next step you'd like the mouse to take (WASD): ");
					userInput = scnr.nextLine();
					move = userInput.charAt(0);
					System.out.println();
					mousePosition = moveMouse(mouseX, mouseY, room, move);
					if (mousePosition != null) {
						error = false;
						mouseX = mousePosition[0];
						mouseY = mousePosition[1];
						eat = tryToEatCheese(mouseX, mouseY, cheesePositions);
						if (eat) {
						cheese += 1;
						}
					}
				}
			}
			run = false;
		}
		System.out.println("The mouse has eaten " + cheese + " cheese!");
		printRoom(room, cheesePositions, mouseX, mouseY);
		for(int i = 0; i < ending.length(); ++i) {
			System.out.print("=");
		}
		System.out.println();
		System.out.print(ending);
		scnr.close();
	}
	
	public static void placeWalls(char[][] room, int numberOfWalls, Random randGen) {
		for(int i = 0; i < numberOfWalls; ++i) {
			boolean same = true;
			while(same) {
				int randomX = randGen.nextInt(room[0].length);
				int randomY = randGen.nextInt(room.length);
				if (room[randomY][randomX] != '#') {
					room[randomY][randomX] = '#';
					same = false;
				}
			}
		}
		for(int i = 0; i < room.length; ++i) {
			for(int j = 0; j < room[0].length; ++j) {
				if (room[i][j] != '#') {
					room[i][j] = '.';
				}
			}
		}
	}
	public static void placeCheeses(int[][] cheesePositions, char[][] room, Random randGen) {
		boolean same = true;
		boolean cheese;
		int randomX;
		int randomY;
		while (same) {
			randomX = randGen.nextInt(room[0].length);
			randomY = randGen.nextInt(room.length);
			if (room[randomY][randomX] != '#') {
				cheesePositions[0][0] = randomX;
				cheesePositions[0][1] = randomY;
				same = false;
			}
		}
		for(int i = 1; i < cheesePositions.length; ++i) {
			same = true;
			while(same) {
				cheese = true;
				randomX = randGen.nextInt(room[0].length);
				randomY = randGen.nextInt(room.length);
				for(int j = 0; j < cheesePositions.length; ++j) {
					if (cheesePositions[j][0] == randomX && cheesePositions[j][1] == randomY) {
						cheese = false;
					}
				}
				if (room[randomY][randomX] != '#' && cheese) {
					cheesePositions[i][0] = randomX;
					cheesePositions[i][1] = randomY;
					same = false;
				}
			}
		}
	}
	public static int[] moveMouse(int mouseX, int mouseY, char[][] room, char move) {
		int[] mousePosition = new int[2];
		if (move == 'w' || move == 'W') {
			if (mouseY - 1 < 0) {
				System.out.println("WARNING: Mouse cannot move outside the room.");
				return null;
			}
			else if (room[mouseY - 1][mouseX] == '#') {
				System.out.println("WARNING: Mouse cannot move into wall.");
				return null;
			}
			else {
				mouseY = mouseY - 1;
			}	
		}
		else if (move == 'A' || move == 'a') {
			if (mouseX - 1 < 0) {
				System.out.println("WARNING: Mouse cannot move outside the room.");
				return null;
			}
			else if (room[mouseY][mouseX - 1] == '#') {
				System.out.println("WARNING: Mouse cannot move into wall.");
				return null;
			}
			else {
				mouseX = mouseX - 1;
			}
		}
		else if (move == 'S' || move == 's') {
			if (mouseY + 1 > (room.length - 1)) {
				System.out.println("WARNING: Mouse cannot move outside the room.");
				return null;
			}
			else if (room[mouseY + 1][mouseX] == '#') {
				System.out.println("WARNING: Mouse cannot move into wall.");
				return null;
			}
			else {
				mouseY = mouseY + 1;
			}
		}
		else if (move == 'D' || move == 'd') {
			if (mouseX + 1 > (room[0].length - 1)) {
				System.out.println("WARNING: Mouse cannot move outside the room.");
				return null;
			}
			else if (room[mouseY][mouseX + 1] == '#') {
				System.out.println("WARNING: Mouse cannot move into wall.");
				return null;
			}
			else {
				mouseX = mouseX + 1;
			}
		}
		else {
			System.out.println("WARNING: Didn’t recognize move command: " + move);
			return null;
		}
		mousePosition[0] = mouseX;
		mousePosition[1] = mouseY;
		return mousePosition;
	}
	public static void printRoom(char[][] room, int[][] cheesePositions, int mouseX, int mouseY) {
		for (int i = 0; i < room.length; ++ i) {
			for (int j = 0; j < room[0].length; ++ j) {
				boolean cheese = false;
				for (int k = 0; k < cheesePositions.length; ++ k) {
						if (j == cheesePositions[k][0] && i == cheesePositions[k][1]) {
							cheese = true;
						}
				}
				if (i == mouseY && j == mouseX) {
					System.out.print("@");
				}
				else if (cheese) {
					System.out.print("%");
				}
				else {
					System.out.print(room[i][j]);
				}
			}
		System.out.println();
		}
	}
	public static boolean tryToEatCheese(int mouseX, int mouseY, int[][] cheesePositions) {
		boolean cheese = false;
		for (int i = 0; i < cheesePositions.length; ++i) {
			if (cheesePositions[i][0] == mouseX && cheesePositions[i][1] == mouseY) {
				cheese = true;
				cheesePositions[i][0] = -1;
				cheesePositions[i][1] = -1;
			}
		}
		return cheese;
	}
}
