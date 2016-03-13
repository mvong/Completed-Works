/* 
mazeio.cpp

Author: Mark Vong 

Short description of this file: Contains the read_maze and print_maze 
functions which reads in the inputted maze and prints it out in the
terminal.

*/

#include <iostream>
#include "mazeio.h"

using namespace std;

/*************************************************
 * read_maze:
 * Read the maze from cin into a dynamically allocated array.
 * 
 * Return the pointer to that array.
 * Return NULL (a special address) if there is a problem, 
 * such as integers for the size not found.
 *
 * We also pass in two pointers to integers. Fill 
 * the integers pointed to by these arguments
 * with the number of rows and columns 
 * read (the first two input values).
 *
 *************************************************/
char** read_maze(int* rows, int* cols) {
   int row, col;
   // Inputs the number of rows and columns
   cin >> row >> col; 
   // If input fails, return NULL
   if(cin.fail()) {
      return NULL;
   }
   // Dynamically allocated maze array with rows and columns
   char** maze = new char*[row];
   for(int i=0; i<row; i++) {
      maze[i] = new char[col];
   }
   // Inputs the maze
   for(int i=0; i<row; i++) {
      for (int j=0; j<col; j++) {
      cin >> maze[i][j];
      }
   }
   // Pointers to the rows and columns equals to the row and column value    
   *rows = row;
   *cols = col;
   
   return maze;   
}

/*************************************************
 * Print the maze contents to the screen in the
 * same format as the input (rows and columns, then
 * the maze character grid).
 *************************************************/
void print_maze(char** maze, int rows, int cols) {
   // Outputs the number of rows and columns
   cout << rows << " " << cols << endl;
   // Prints maze
   for(int i=0; i<rows; i++) {
      for(int j=0; j<cols; j++) {
         cout << maze[i][j]; 
      }
      cout << endl;
   }
}