//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: P01 Cheese Eater
// Files: N/A
// Course: CS 300, Spring, and 2018
//
// Author: Monica Schmidt
// Email: meschmidt6@wisc.edu
// Lecturer's Name: Alexi Brooks
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name: Benjamin Carpenter
// Partner Email: blcarpenter@wisc.edu
// Lecturer's Name: Alexi Brooks
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
// ___ Write-up states that pair programming is allowed for this assignment. X
// ___ We have both read and understand the course Pair Programming Policy. X
// ___ We have registered our team prior to the team registration deadline. X
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates
// strangers, etc do. If you received no outside help from either type of
// source, then please explicitly indicate NONE.
//
// Persons: Benjamin Carpenter
// Online Sources: (identify each URL and describe their assistance in detail)
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.util.Random;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Scanner scr = new Scanner(System.in);
        char[][] room = new char[10][20];
        Random randGen = new Random();
        int NUM_WALLS = room[0].length;
        int[][] cheesePositions = new int[10][2];
        placeWalls(room, NUM_WALLS, randGen);
        placeCheeses(cheesePositions, room, randGen);
        int mouseX = randGen.nextInt(room[0].length);
        int mouseY = randGen.nextInt(room.length);
        int j = 0;
        while (j < cheesePositions.length) {
            // Checks to make sure the mouse x and y are not in the places of walls and cheese.
            if (cheesePositions[j][1] == mouseY && cheesePositions[j][0] == mouseX
                            || room[mouseY][mouseX] == '#') {
                do {
                    mouseX = randGen.nextInt(room[0].length);
                    mouseY = randGen.nextInt(room.length);
                } while (cheesePositions[j][1] == mouseY && cheesePositions[j][0] == mouseX
                                || room[mouseY][mouseX] == '#');
            }
            j++;
        }

        System.out.println("Welcome to the Cheese Eater simulation.");
        System.out.println("=======================================");
        System.out.println("Enter the number of steps for this simulation to run:");
        int steps = scr.nextInt();
        scr.nextLine();
        System.out.println();
        int cheeseCount = 0;
        int i = 0;
        // Prints out the map and cheese eaten if the user inputs zero
        System.out.println("The mouse has eaten " + cheeseCount + " cheese!");
        printRoom(room, cheesePositions, mouseX, mouseY);
        // Loops steps number of times that is given by the user
        while (i < steps) {
            System.out.println("Enter the next step you'd like the mouse to take (WASD): ");
            char move = scr.next().charAt(0);
            int[] newCoordinates = moveMouse(mouseX, mouseY, room, move);
            // If statement to see if null was returned during move mouse
            if (newCoordinates != null) {
                // This changes the coordinates of the mouse
                mouseX = newCoordinates[0];
                mouseY = newCoordinates[1];
                boolean cheeseEaten = tryToEatCheese(mouseX, mouseY, cheesePositions);
                // Increments cheese count every time true is returned by cheeseEaten
                if (cheeseEaten == true) {
                    cheeseCount++;
                }
            
                i++;
            }
            System.out.println("The mouse has eaten " + cheeseCount + " cheese!");
            printRoom(room, cheesePositions, mouseX, mouseY);
            System.out.println();
        }
        scr.close();
        System.out.println("==================================================");
        System.out.println("Thank you for running the Cheese Eater simulation.");
    }

    /*
     * This method places the walls and empty characters into the room array This creates the room
     * size and amount of walls within the room. It creates the barriers for the simulation.
     */
    public static void placeWalls(char[][] room, int numberOfWalls, Random randGen) {
        // Places the empty space char in the array
        for (int row = 0; row < room.length; row++) {
            for (int col = 0; col < room[0].length; col++) {
                room[row][col] = '.';
            }
        }
        // Places walls are random places in the array by overriding
        // the empty space char with a wall char
        int i = 0;
        while (i < numberOfWalls) {
            int row = randGen.nextInt(room[0].length);
            int col = randGen.nextInt(room.length);
            // checks to see if there is a wall already at this space
            if (room[col][row] == '#') {
                // Do while to check and see if a wall char is already at that space
                // If there is a wall it generates more random numbers until there is
                // and empty space character
                do {
                    row = randGen.nextInt(room[0].length);
                    col = randGen.nextInt(room.length);
                } while (room[col][row] == '#');
            }
            room[col][row] = '#';
            i++;
        }
    }

    /*
     * This method create random coordinates for cheeses to be placed in the room It uses random to
     * create x and y positions. It also checks to see if there is already a wall at the random
     * coordinates. If there is it creates new coordinates It also makes sure no cheeses are placed
     * in the exact same spots. This method creates the goal of the cheese eater simulation.
     */
    public static void placeCheeses(int[][] cheesePositions, char[][] room, Random randGen) {
        // While loop to create the right amount of cheeses
        int i = 0;
        while (i < cheesePositions.length) {
            int col = randGen.nextInt(room[0].length); // 0-19
            int row = randGen.nextInt(room.length); // 0-9
            // Another while loop to check if there is a wall or a cheese already
            // at the coordinate generated.
            int j = 0;
            while (j < cheesePositions.length) {
                // Use or and do while statement to check for a wall or a cheese
                // just in case it creates more errors after correcting the first one.
                if (cheesePositions[j][1] == row && cheesePositions[j][0] == col
                                || room[row][col] == '#') { // checks each index for cheeses or
                    // walls
                    do {
                        col = randGen.nextInt(room[0].length); // picks new random location
                        row = randGen.nextInt(room.length);

                    } while (cheesePositions[j][1] == row && cheesePositions[j][0] == col
                                    || room[row][col] == '#'); // repeats until unique location
                    // found
                }
                j++;
            }

            cheesePositions[i][1] = row;
            cheesePositions[i][0] = col;
            i++;
        }
    }

    /*
     * This method displays the "gameBoard" with the mouse, cheese, walls, and empty spaces. It has
     * a priority list of 1. Mouse 2. Cheese 3. Walls 4. Empty Space
     */
    public static void printRoom(char[][] room, int[][] cheesePositions, int mouseX, int mouseY) {
        // creates a new array to hold all of the characters for the display
        char[][] gameBoard = new char[room.length][room[0].length];
        // put the contents of the room array into the gameBoard array
        for (int row = 0; row < room.length; row++) {
            for (int col = 0; col < room[0].length; col++) {
                gameBoard[row][col] = room[row][col];
            }
        }
        // While loop to run through cheesePositions
        int i = 0;
        while (i < cheesePositions.length) {
            // Grabs the x and y coordinates
            int row = cheesePositions[i][0];
            int col = cheesePositions[i][1];
            // Checks to see if the sentinel position is listed
            if (row == -1) {
                // Skips the allocation of the sentinel position
                i++;
            } else {
                // Puts a percent at the correct coordinates given by cheesePositions
                gameBoard[col][row] = '%';
                i++;
            }
        }
        // Places the mouse into the gameBoard array
        gameBoard[mouseY][mouseX] = '@';
        // Prints the board to the screen
        for (int row = 0; row < room.length; row++) {
            for (int col = 0; col < room[0].length; col++) {
                System.out.print(gameBoard[row][col]);
            }
            System.out.println();
        }
    }

    /*
     * This method creates new coordinate for the mouse. It evaluates which way the mouse should
     * move according to user input. It then returns an array with the new coordinates for the
     * mouse. It also displays different errors depending on if the user tried to go off the board
     * or into the walls.
     */
    public static int[] moveMouse(int mouseX, int mouseY, char[][] room, char move) {
        int[] mouseMove = new int[2];
        // Checks to see if w was inputed in by the user
        if (move == 'w' || move == 'W') {
            // Since w signifies up I subtract one from the y coordinate to facilitate up
            mouseY -= 1;
            // checks to see if the mouse went of the board
            if (mouseY == -1) {
                // prints and error and returns null
                System.out.println("WARNING: Mouse cannot move outside the room.");
                return null;
                // checks to see if the mouse went into a wall
            } else if (room[mouseY][mouseX] == '#') {
                // prints and error and returns null
                System.out.println("WARNING: Mouse cannot move into wall.");
                return null;
            } else {
                mouseMove[0] = mouseX;
                mouseMove[1] = mouseY;
                return mouseMove;
            }
            // Checks to see if w was inputed in by the user
        } else if (move == 'a' || move == 'A') {
            // Since a signifies left I subtract one from the x coordinate to facilitate left
            mouseX -= 1;
            // checks to see if the mouse went of the board
            if (mouseX == -1) {
                // prints and error and returns null
                System.out.println("WARNING: Mouse cannot move outside the room.");
                return null;
                // checks to see if the mouse went into a wall
            } else if (room[mouseY][mouseX] == '#') {
                // prints and error and returns null
                System.out.println("WARNING: Mouse cannot move into wall.");
                return null;
            } else {
                // Changes the coordinates of the mouse in order to move the mouse in main
                mouseMove[0] = mouseX;
                mouseMove[1] = mouseY;
                return mouseMove;
            }
            // Checks to see if s was inputed in by the user
        } else if (move == 's' || move == 'S') {
            // Since s signifies down I add one from the y coordinate to facilitate down
            mouseY += 1;
            // checks to see if the mouse went of the board
            if (mouseY == room.length) {
                // prints and error and returns null
                System.out.println("WARNING: Mouse cannot move outside the room.");
                return null;
                // checks to see if the mouse went into a wall
            } else if (room[mouseY][mouseX] == '#') {
                // prints and error and returns null
                System.out.println("WARNING: Mouse cannot move into wall.");
                return null;
                // Changes the coordinates of the mouse in order to move the mouse in main
            } else {
                mouseMove[0] = mouseX;
                mouseMove[1] = mouseY;
                return mouseMove;
            }
        } else if (move == 'd' || move == 'D') {
            // Since d signifies right I add one from the x coordinate to facilitate right
            mouseX += 1;
            // checks to see if the mouse went of the board
            if (mouseX == room[0].length) {
                // prints and error and returns null
                System.out.println("WARNING: Mouse cannot move outside the room.");
                return null;
                // checks to see if the mouse went into a wall

            } else if (room[mouseY][mouseX] == '#') {
                // prints and error and returns null
                System.out.println("WARNING: Mouse cannot move into wall.");
                return null;
                // Changes the coordinates of the mouse in order to move the mouse in main
            } else {
                mouseMove[0] = mouseX;
                mouseMove[1] = mouseY;
                return mouseMove;
            }
        } else {
            System.out.println("WARNING: Didn’t recognize move command: " + move);
        }
        return mouseMove;
    }

    /*
     * This method creates new coordinate for the cheese if the mouse is on a cheese. If the mouse
     * is on a cheese the position of the cheese is changed to -1 -1 and true is returned if not
     * false is returned
     */
    public static boolean tryToEatCheese(int mouseX, int mouseY, int[][] cheesePositions) {
        int i = 0;
        boolean cheeseEaten = false;
        // Loops according to the number of cheeses given by the length
        while (i < cheesePositions.length) {
            // Checks to see if the mouseX and mouseY are the same as a cheese position
            if (cheesePositions[i][0] == mouseX && cheesePositions[i][1] == mouseY) {
                cheesePositions[i][0] = -1;
                cheesePositions[i][1] = -1;
                cheeseEaten = true;
            }
            i++;
        }
        return cheeseEaten;
    }
}

