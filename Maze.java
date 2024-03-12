package Maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Maze {

    private boolean animate;

    private char[][] maze;
    private int startRow;
    private int startCol;

    public Maze(String filepath, boolean animate) throws FileNotFoundException{
        maze = readFile(filepath);
        for(int row = 0; row < maze.length; row ++){
            for(int column = 0; column < maze[row].length; column ++){
                if(maze[row][column] == 'S'){
                    startRow = row; 
                    startCol = column;
                }
            }
        }
        System.out.println("Start: "  + startRow + ", " + startCol);
        
    }

    public Maze(String filepath) throws FileNotFoundException{
        this(filepath, false);
    }

   

    /*
     * smallest problem: you are already there, return 0
     * second smallest problem: you are next to it, return smallest problem + 1;
     * third smallest problem; you are two away from it, return 1 + second smallest problem = 1 + 1 + smallest problem
     */
    public int solve(int row, int col){
        if(!isValid(row, col) || maze[row][col] == '.' || maze[row][col] == '#' || maze[row][col] == '@'){
            return -1;
        } 
        if(maze[row][col] == 'E'){
            return 0;
        }


        for(int i = 0; i < 4; i ++){
            int steps = 0;
            if(i == 0){
                maze[row][col] = '@';
                steps = solve(row + 1, col);

            }else if(i == 1){
                maze[row][col] = '@';
                steps = solve(row - 1, col);
            }else if(i == 2){
                maze[row][col] = '@';
                steps = solve(row, col + 1);
            }else if(i == 3){
                maze[row][col] = '@';
                steps = solve(row, col - 1);
            } else {
                steps = 0;
            }
            if(steps != -1){ // if not a dead end, 
                return 1 + steps;
                
            } 
        }
        maze[row][col] = '.';
        System.out.println(arrToString(maze));
        return -1; //dead end
        
    }



    public int solve(){
        return solve(startRow, startCol);
    }
    public boolean isValid(int row, int col){
        return row > -1 && row < maze.length && col > -1 && col < maze[row].length;
    }



    public static void main(String[] args) throws FileNotFoundException{
        // char[][] maze = readFile("maze1.txt");
        // System.out.println(arrToString(maze));
        try{
            Maze m = new Maze("maze1.txt");
            System.out.println(m);
            System.out.println(m.solve());
            System.out.println(m);

        }catch(StackOverflowError e){
            System.out.println("stack overflow");
        }
        
    }






    public static char[][] readFile(String filepath) throws FileNotFoundException{
        char[][] parsed = null;
        // System.out.println(filepath + "/n");
        Scanner sc = new Scanner(new File(filepath));
        int numRows = 0;
        int maxCols = 0;
        while (sc.hasNextLine()) {
            numRows++;
            maxCols = Math.max(maxCols, sc.nextLine().length());
        }
        sc.close();

        parsed = new char[numRows][maxCols];

        sc = new Scanner(new File(filepath)).useDelimiter("");
        int row = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();

            for (int col = 0; col < line.length(); col++) {
                parsed[row][col] = line.charAt(col);
            }

            row ++;
        }
        sc.close();

       

        return parsed;
    }

    public static void printArray(char[][] arr) {
        for (char[] row : arr) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public static String arrToString(char[][] arr) {
        String str = "";
        for (char[] row : arr) {
            for (char c : row) {
                str += c;
            }
            str += "\n";
        }
        return str;
    }

    public String toString(){
        return arrToString(maze);
    }
}
