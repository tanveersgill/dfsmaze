package recursion;

//A java implementation of DFS for maze solving

import java.util.*;

public class dfs {
	
	static Scanner scan = new Scanner(System.in);
	static int startY = 1, startX = 1, finalY = 1, finalX = 1;
	static boolean valid = false;
	
	public static void main(String[] args) {
		int decision = 3; //by default, 3 means the user wants to exit the program. it later changes when the user picks what to do with the program
		intro(); //show the intro screen
		
		do {
			decision = mainMenu(); // the main menu allows the user to (1) solve a maze, (2) see the guide, or (3) exit; it returns an int value accordingly
			
			if (decision == 1) { //if user wants to solve a maze
				char[][] maze = mazeSelect(); //lets user pick a maze
			
				maze = mazeSolveType(maze , maze.length - 1 , maze[0].length - 1); //lets user pick how they want to set start/end points
			
				findPath(startY , startX , finalY , finalX,  maze ); //the recursive method to solve the maze
			
				if (!valid) { //the boolean 'valid' ONLY becomes true if a solution is found in the base case of the recursive method
					pressEnter();
					System.out.println("we looked far and wide, but there is no solution to be found!"); //notify the user that a solution is not possible
				}
			} else if (decision == 2) { //if the user wants to see the guide
				guide();
			} else { //if user wants to exit
				System.out.println("we're sorry to see you leave. goodbye!");
			}
			if (decision != 3) {
				System.out.println("returning you to the main menu . . .");
				pressEnter();
			}
			
			valid = false; //in the case that the maze was solved, valid is set back to false so the solver can be used again
		} while (decision != 3); //loop runs as long as the user does not enter three in the main menu
			
		
	}
	
	public static void intro() {
		System.out.println("\t\t\t\t   welcome to");
		System.out.println("––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––");
		System.out.println("                                                       .     .d88888b.  ");
		System.out.println("                                                      .8.    d88`  Y8b  ");
		System.out.println("                                                             888        ");
		System.out.println(".d8bd888bd8b.   888888b.  d8888888b  .d888b.          d8b  88888888  .db   db."); 
		System.out.println("d88Y`Y8Y`Y88b       `88b       888  d8P   Y8b         888    888`    888   888");
		System.out.println("888  888  888  .d8888888     888    88888888Y (88888) 888    888     888   888");
		System.out.println("888       888  88    888   88*      Y8b               888    888     Y8b   888"); 
		System.out.println("888       888  `Y8888888  d8888888P  `Y8888P          Y87    Y87      `Y888888");
		System.out.println("                                                                           888");
		System.out.println("d.                                                                        .88P");
		System.out.println("`Y88888888888888888888888888888888888888888888888888888888888888888888888888P`");
		
		System.out.println();
		System.out.println("a recursive maze solver \nby tanveer gill");
		System.out.println("ics4u @ maple high school \n(2021-22 semester 2) \nms. katsman");
		System.out.println("–––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––");
		pressEnter(); //Logo for program
		
	}
	
	public static int mainMenu () {
		int choice = 0;
		System.out.println("[main menu]");
		System.out.println("1. solve a maze");
		System.out.println("2. how to use");
		System.out.println("3. exit");
		//Options to choose from
		
		do {
			System.out.println("what would you like to do? :");
			choice = scan.nextInt();
			if (choice < 1 || choice > 3) {
				System.out.println("invalid input; enter again"); 
			} //Loop to ensure valid input 
			
		} while (choice < 1 || choice > 3); //Loop to ensure valid input by user
		
		if (choice == 1) {
			return 1;
		} else if (choice == 2) {
			return 2;
		} else {
			return 3;
		} //choices to either solve a maze or access the guide/instructions, or exit
	}
	
	public static char[][] mazeSelect () {
		int choice = 0;
		
		System.out.println("here are the mazes available to solve:");
		
		System.out.println("[easy] maze 1 :"); //easy maze (example 1)
		
		char [][] maze1 = {{'–' , '–' , '–' , '–' , '–' , '–' ,'–' , '–' , '–' , '–' , '–' , '–' } , 
						{'|' , ' ' , 'x' , ' ' , ' ' , ' ' , 'x' , ' ' , ' ' , ' ' , ' ' , '|'} , 
						{'|' , ' ' , 'x' , ' ' , ' ' , ' ' , 'x' , ' ' , 'x' , 'x' , 'x' , '|'} , 
						{'|' , ' ' , 'x' , 'x' , 'x' , 'x' , 'x' , ' ' , 'x' , ' ' , ' ' , '|'} , 
						{'|' , ' ' , ' ' , ' ' , ' ' , ' ' , 'x' , ' ' , 'x' , ' ' , ' ' , '|'} , 
						{'|' , ' ' , 'x' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , '|'} , 
						{'|' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , 'x' , 'x' , 'x' , '|'} , 
						{'|' , ' ' , ' ' , ' ' , 'x' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , '|'} , 
						{'|' , 'x' , 'x' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , '|'} , 
						{'|' , ' ' , ' ' , ' ' , 'x' , 'x' , ' ' , 'x' , 'x' , 'x' , ' ' , '|'} , 
						{'|' , 'x' , 'x' , 'x' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , '|'} , 
						{'–' , '–' , '–' , '–' , '–' , '–' ,'–' , '–' , '–' , '–' , '–' , '–' }};
		
		
		printarr(0,0,maze1);
		
		System.out.println("[medium] maze 2 :"); //medium maze (example 2)
		
		char [][] maze2 = {{'–' , '–' , '–' , '–' , '–' , '–' ,'–' , '–' , '–' , '–' , '–' , '–', '–' , '–' , '–' , '–' , '–' , '–' ,'–' , '–' , '–' , '–' , '–'},
						{'|' , ' ' , 'x' , 'x' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , 'x' , 'x' , 'x' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , '|'},
						{'|' , ' ' , ' ' , ' ' , ' ' , 'x' , 'x' , 'x' , ' ' , 'x' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , 'x' , 'x' , 'x' , 'x' , 'x' , 'x' , '|'},
						{'|' , 'x' , 'x' , 'x' , ' ' , 'x' , ' ' , ' ' , ' ' , 'x' , 'x' , 'x' , 'x' , 'x' , 'x' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , '|'},
						{'|' , 'x' , ' ' , ' ' , ' ' , 'x' , 'x' , 'x' , ' ' , 'x' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , 'x' , 'x' , 'x' , 'x' , ' ' , 'x' , '|'},
						{'|' , 'x' , 'x' , 'x' , ' ' , 'x' , ' ' , ' ' , ' ' , 'x' , ' ' , 'x' , 'x' , 'x' , 'x' , ' ' , 'x' , 'x' , 'x' , 'x' , ' ' , 'x' , '|'},
						{'|' , ' ' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , ' ' , ' ' , ' ' , 'x' , ' ' , ' ' , ' ' , 'x' , 'x' , ' ' , 'x' , '|'},
						{'|' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , ' ' , '|'},
						{'|' , ' ' , ' ' , 'x' , ' ' , 'x' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , ' ' , 'x' , 'x' , 'x' , '|'},
						{'|' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , 'x' , 'x' , ' ' , 'x' , 'x' , ' ' , ' ' , ' ' , ' ' , '|'},
						{'|' , ' ' , 'x' , ' ' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , 'x' , 'x' , ' ' , 'x' , 'x' , ' ' , '|'},
						{'–' , '–' , '–' , '–' , '–' , '–' ,'–' , '–' , '–' , '–' , '–' , '–', '–' , '–' , '–' , '–' , '–' , '–' ,'–' , '–' , '–' , '–' , '–'}};

		printarr(0,0,maze2);
		
		System.out.println("[hard] maze 3 :"); //hard maze (example 3)
		
		char[][] maze3 = {{'–' , '–' , '–' , '–' , '–' ,'–' , '–' , '–' , '–' , '–' , '–', '–' , '–' , '–' , '–' , '–' , '–' ,'–' , '–' , '–' , '–' , '–' , '–' , '–' , '–' , '–' , '–' , '–' ,'–' , '–' , '–' , '–' , '–' , '–', '–' , '–' , '–' , '–' , '–' , '–' ,'–' , '–'},
					{'|' , ' ' , 'x' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , 'x' , ' ' , 'x' , 'x' , 'x' , ' ' , 'x' , 'x' , 'x' , 'x' , 'x' , 'x' , ' ' , 'x' , 'x' , 'x' , 'x' , 'x' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , 'x' , 'x' , ' ' , 'x'  ,'|'},
					{'|' , ' ' , ' ' , 'x' , 'x' , 'x' , 'x' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , 'x' , ' ' , ' ' , 'x' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , ' ' , 'x' , ' ' , 'x'  ,'|'},
					{'|' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , 'x' , 'x' , 'x' , 'x' , 'x' , 'x' , 'x' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x'  ,'|'},
					{'|' , ' ' , 'x' , 'x' , 'x' , 'x' , ' ' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , ' ' , ' ' , 'x' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , 'x' , ' ' , ' ' , 'x' , ' ' , 'x' , ' ' , ' ' , ' ' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , ' ' , ' ' , 'x'  ,'|'},
					{'|' , ' ' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , ' ' , 'x' , 'x'  ,'|'},
					{'|' , 'x' , ' ' , 'x' , ' ' , ' ' , 'x' , ' ' , 'x' , ' ' , ' ' , ' ' , 'x' , ' ' , ' ' , ' ' , 'x' , ' ' , 'x' , ' ' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , ' ' , 'x' , ' '  ,'|'},
					{'|' , 'x' , ' ' , 'x' , ' ' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , ' ' , 'x' , 'x'  ,'|'},
					{'|' , 'x' , ' ' , 'x' , ' ' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , ' ' , 'x' , 'x' , 'x' , 'x' , 'x' , ' ' , ' ' , ' ' , ' ' , 'x'  ,'|'},
					{'|' , ' ' , ' ' , 'x' , ' ' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , ' ' , 'x' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , 'x'  ,'|'},
					{'|' , ' ' , 'x' , 'x' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , 'x' , ' ' , 'x' , 'x' , 'x' , 'x' , 'x' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , 'x' , 'x' , ' ' , 'x' , ' ' , ' ' , 'x' , ' ' , 'x'  ,'|'},
					{'|' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , ' ' , 'x' , ' ' , ' ' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x'  ,'|'},
					{'|' , ' ' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , ' ' , 'x' , ' ' , 'x' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x'  ,'|'},
					{'|' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x'  ,'|'},
					{'|' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , ' ' , 'x' , ' ' , ' ' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , ' ' , 'x' , ' ' , 'x' , ' ' , ' ' , 'x' , ' ' , 'x'  ,'|'},
					{'|' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , 'x' , 'x' , ' ' , ' ' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x'  ,'|'},
					{'|' , ' ' , 'x' , 'x' , ' ' , 'x' , 'x' , 'x' , ' ' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , ' ' , ' ' , 'x' , ' ' , ' ' , ' ' , ' ' , 'x' , ' ' , 'x' , ' ' , ' ' , 'x' , ' ' , ' '  ,'|'},
					{'|' , ' ' , 'x' , 'x' , ' ' , 'x' , 'x' , ' ' , ' ' , 'x' , 'x' , ' ' , 'x' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , 'x' , 'x' , 'x' , 'x' , ' ' , 'x' , 'x' , 'x' , 'x' , ' ' , 'x' , 'x' , 'x' , 'x' , 'x' , ' '  ,'|'},
					{'|' , ' ' , 'x' , 'x' , ' ' , 'x' , ' ' , ' ' , 'x' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , ' ' , ' ' , ' ' , 'x' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , 'x' , ' ' , ' ' , ' ' , ' ' , ' ' , 'x' , ' '  ,'|'},
					{'|' , ' ' , 'x' , 'x' , ' ' , 'x' , 'x' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , 'x' , 'x' , 'x' , 'x' , ' ' , 'x' , 'x' , 'x' , 'x' , 'x' , 'x' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , 'x' , 'x' , 'x' , 'x' , 'x' , ' '  ,'|'},
					{'|' , ' ' , ' ' , ' ' , ' ' , 'x' , ' ' , ' ' , ' ' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , ' ' , 'x' , 'x' , ' ' , ' ' , ' ' , ' ' , 'x' , ' ' , 'x' , ' ' , 'x' , 'x' , 'x' , ' ' , ' ' , ' ' , 'x' , ' '  ,'|'},
					{'–' , '–' , '–' , '–' , '–' ,'–' , '–' , '–' , '–' , '–' , '–', '–' , '–' , '–' , '–' , '–' , '–' ,'–' , '–' , '–' , '–' , '–' , '–' , '–' , '–' , '–' , '–' , '–' ,'–' , '–' , '–' , '–' , '–' , '–', '–' , '–' , '–' , '–' , '–' , '–' ,'–' , '–'}};
		
		printarr(0,0,maze3);
		
		do {
			System.out.println("enter a number for the maze you would like to use:");
			choice = scan.nextInt();
			if (choice < 1 || choice > 3) {
				System.out.println("invalid input; please pick a number from 1-3 that corresponds to one of the maze options");
			}
			
		} while (choice < 1 || choice > 3); //Loop to ensure valid input is inputted by user (appropriate choice)
		
		//displays which specific maze the user has chosen
		if (choice == 1) {
			System.out.println("you chose the following maze: ");
			printarr(0,0,maze1);
			return maze1;
		} else if (choice == 2) {
			System.out.println("you chose the following maze: ");
			printarr(0,0,maze2);
			return maze2;
		} else if (choice == 3){
			System.out.println("you chose the following maze: ");
			printarr(0,0,maze3);
			return maze3;
		}
		
		return maze1;
		
	}
	
	public static void printarr(int columnNumber, int rowNumber, char[][]mazearr){ //recursive method to print the 2D array (maze)
		
		//Checks if the rows do not exceed the maze
		if(rowNumber<mazearr.length) {
			if (columnNumber <mazearr[rowNumber].length) { //if the column is less than the number of columns (meaning all columns are traversed) 
				System.out.print(mazearr[rowNumber][columnNumber]); //Prints the specific coordinates in the array
				printarr(columnNumber+1, rowNumber, mazearr); //Will print all columns in specific row
			}
			else {
				System.out.println("");
				columnNumber=0; //Resets column coordinates to 0 in order to print the next rows
				printarr(columnNumber, rowNumber+1, mazearr); //Will traverse through all the rows
			}
		}
		else {
		
		}
	
	}

	public static void pressEnter() { //makes input easier to follow
		System.out.println("type [any key + enter] to continue");
		String temp = scan.next();
	}
	
	public static void guide() { // introduction to mazes and the coordinate system used to represent the indexes of the maze
		System.out.println("welcome to the guide! here you will find all the information necessary to use the maze solving tool.\n"
				+ "before we begin, a 'maze' is defined as a network of paths and hedges through which one has to find a way out.\n");
		pressEnter();
		System.out.println("\nfor the purposes of this program, maze walls are defined as the character 'x', while paths are seen as blank spaces\n\n"
				+ "in order to differentiate between the many positions on the maze, we will use a coordinate system, defined as follows;\n"
				+ "maze rows are numbered (top to bottom) from 1 --> the number of rows in the maze\n"
				+ "maze columns are numbered (left to right) from 1 --> the number of columns in the maze\n\n"
				+ "NOTE: in counting positions, we disregard any row/column containing a border character (i.e. not an x or path)\n");
		pressEnter();
		System.out.println("for example, take the following maze:\n");
		System.out.println("–––––– \n|axxx| \n|  xb| \n|x xx| \n|   c| \n––––––");
		
		pressEnter();
		System.out.println("the straight lines are border walls, the x's are inner walls, and spaces are open pathways.");
		
		pressEnter();
		System.out.println("furthermore, the positions of points a, b, and c on this maze will be:");
		System.out.println("point a --> row 1 & column 1");
		System.out.println("point b --> row 2 & column 4");
		System.out.println("point c --> row 4 & column 4\n\n");
		
		pressEnter();
		System.out.println("as you can see, borders do not count; else point a would have been row 2 and column 2.");
		
		pressEnter();
		//End of introduction to maze
	}
	
	public static char[][] mazeSolveType(char[][] maze , int maxY , int maxX) { //will return an updated maze with the desired start and end points in it
		int choice = 0;
		System.out.println("[maze start/end point configuration]\n"
				+ "1. use pre-set start and end points\n"
				+ "2. set your own start and end points\n");
		
		//the following do-while loop ensures that the user enters a valid option
		do {
			System.out.println("enter a number for your preference:");
			choice = scan.nextInt();
			if (choice < 1 || choice > 2) {
				System.out.println("invalid input; please pick a number from 1-2 that corresponds to one of the options");
			}
			
		} while (choice < 1 || choice > 2);
		
		//if the user chose the number 1, then the default start and end points will be used (top left and bottom right)
		if (choice == 1) {
			
			System.out.println("the starting point will be at [row 1, column 1], and the ending point will be at [row " + (maze.length - 2) + ", column " + (maze[0].length - 2) + "].");
			maze[1][1] = 'S'; //the starting point is marked with an S
			
			finalY = maze.length - 2;
			finalX = maze[0].length - 2;
			
		} else if (choice == 2) { //this condition is used in case the user wants to enter their own points
			boolean invalid = true;
			//the first do while loop takes in starting coordinates, and ensures that they are valid
			do {
				System.out.println("enter a starting row number: ");
				startY = scan.nextInt();
				System.out.println("enter a starting column number: ");
				startX = scan.nextInt();
				//make sure that the numbers are within the boundaries of the 2d array and are not outer borders
				if (startY >= 1 && startY <= maze.length - 2 && startX >= 1 && startX <= maze[0].length - 2 && maze[startY][startX] == ' ') {
					invalid = false;
					maze[startY][startX] = 'S';
					
				} else {
					System.out.println("invalid position; please ensure that your chosen row/column combination (1) does not correspond to a wall and (2) is within the maze");
				}
			} while (invalid);
			
			invalid = true;
			
			//the same process as above for ending coordinates
			do {
				System.out.println("enter a ending row number: ");
				finalY = scan.nextInt();
				System.out.println("enter a ending column number: ");
				finalX = scan.nextInt();
				
				if (finalY >= 1 && finalY <= maze.length - 2 && finalX >= 1 && finalX <= maze[0].length - 2 && maze[finalY][finalX] == ' ') { //making sure that the selected end coordinates are a blank space
					invalid = false;
					
				} else {
					System.out.println("invalid position; please ensure that your chosen row/column combination (1) does not correspond to a wall and (2) is within the maze");
				}
			} while (invalid);
		
		} 
		maze[finalY][finalX] = 'E';
		System.out.println("the maze will look like this: ");
		printarr(0,0,maze); //display the user the maze they chose, with the start/end points they chose
		maze[finalY][finalX] = ' '; //put an empty space at the end coordinate to allow the findPath method to recognize the path easier
		return maze; 
		//finally, the maze is returned with its starting/ending points
	}
	
	public static char[][] makeSolution(char[][] solvedMaze){ //converts a marked path in a maze to another 2D array with only the path in it, excluding walls and borders
		
		char[][] solutionPath = new char[solvedMaze.length - 1][solvedMaze[0].length - 1];
		for (int i = 0; i < solvedMaze.length - 1; i++) {
			for (int j = 0; j < solvedMaze[i].length - 1; j++) {
				if(solvedMaze[i][j] == 'm') { //if the current index in the original maze is marked, put a marker in the solution array
					solutionPath[i][j] = 'X';
				} else {
					solutionPath[i][j] = ' '; //for any other character in the original maze, put in a space
				}
			}
			
		}
		return solutionPath; //the array with just the solved path is returned.
	}
		
	public static boolean findPath(int indexY, int indexX , int endY , int endX , char[][] maze) { //take in the coordinates of the current position, as well as the ending coordinates and the maze being solved.
		
		
		//base case; if the end is reached (i.e. the current coordinates match the desired end ones
		if ((indexY == finalY) && (indexX == finalX)) {
			
			maze[indexY][indexX] = 'm'; //mark the final spot with an 'm'
			
			char[][] solutionPath = makeSolution(maze); //call on the makeSolution method, using the solved and marked maze as a template to create a new array with the solution.
			
			System.out.println("the maze was solved!");
			pressEnter();
			System.out.println("the solution: ");
			printarr(0,0,solutionPath); //use the recursive print method to display the solution
			pressEnter();
			valid = true; //this boolean only becomes true if the maze has a solution; if not, we know no solution exists
			return valid;
			
		} else { //the general case
			maze[indexY][indexX] = 'm'; //mark the current position as "visited"
			
			if (maze[indexY - 1][indexX] == ' ') { //go up
				
				findPath(indexY-1, indexX , finalY , finalX , maze);
				
			} 
			if (maze[indexY+1][indexX] == ' ') { //go down
				
				findPath(indexY + 1, indexX , finalY , finalX,  maze);
			
			}
			if (maze[indexY][indexX + 1] == ' ') { //go right
			
				findPath(indexY, indexX+1 , finalY , finalX ,  maze);
			
			}
			
			if (maze[indexY][indexX - 1] == ' ') { //go left
				
				findPath(indexY, indexX-1 , finalY , finalX ,  maze);
			
			}
			maze[indexY][indexX] = ' '; //if none of the above are possible (cannot move in any direction), the position is unmarked, no longer being 'm'
			return valid;
				
		}
		
	}

}
