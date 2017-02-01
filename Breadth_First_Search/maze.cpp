/*
maze.cpp

Author: Mark Vong

Short description of this file: This program contains the maze_search
function which searches a given maze path in order to find the optimal
math from start to end. It also replaces the shortest path with stars to
indicate its appearance. In addition, the function also determines
whether or not the maze is valid or not by providing specific return
values. The program also contains the main function that calls
this maze_search function as well as the read_maze and print_maze
functions.
*/

#include <iostream>
#include "mazeio.h"
#include "queue.h"

using namespace std;

// Prototype for maze_search, which you will fill in below.
int maze_search(char**, int, int);

// main function to read, solve maze, and print result
int main() {
   int rows, cols, result;
   char** mymaze;
   
   // Calls the read_maze function.
   mymaze = read_maze(&rows, &cols);
 
   if (mymaze == NULL) {
      cout << "Error, input format incorrect" << endl;
      return 1;
   }
   // Return value of maze_search to determine if the maze is valid.
   result = maze_search(mymaze, rows, cols);
   
   // examine value returned by maze_search and print appropriate output
   if (result == 1) { // path found!
      print_maze(mymaze, rows, cols);
   }
   else if (result == 0) { // no path :(
      cout << "No path could be found!" << endl;
   }
   else {  //result == -1;
      cout << "Invalid maze." << endl;
   }

   // Delete allocated memory from inputted maze.
   for(int i = 0; i < rows; i++){
      delete [] mymaze[i];
   }
   delete [] mymaze;

   return 0;
}

/**************************************************
 * Attempt to find shortest path and return:
 *  1 if successful
 *  0 if no path exists
 * -1 if invalid maze (not exactly one S and one F)
 *
 * If path is found fill it in with '*' characters
 *  but don't overwrite the 'S' and 'F' cells
 *************************************************/
int maze_search(char** maze, int rows, int cols) {
   
   // Declare start and end locations
   Location start, end;
   int startCount = 0;
   int endCount = 0;
   // Initializes the start and end locations
   for(int i=0; i<rows; i++) {
      for(int j=0; j<cols; j++) {
         if(maze[i][j] == 'S') { 
            start.row = i;
            start.col = j;
            startCount++;
         }
         if(maze[i][j] == 'F') {
            end.row = i;
            end.col = j;
            endCount++;
         }     
      }
   }
   // Dynamically allocates the visited array for the visited locations
   bool** vstd = new bool*[rows];
   for(int i=0; i<rows; i++) {
      vstd[i] = new bool[cols];
   }
   // Initializes each array as false to indicate that it hasn't been
   // visited.
   for(int i=0; i<rows; i++) {
      for(int j=0; j<cols; j++) {
         vstd[i][j] = false; 
      }
   }
   // Dynamically allocates a predecessor array to store the previous
   // locations of most optimal path from start to end. 
   Location **pred = new Location*[rows];
   for(int i=0; i<rows; i++) {
      pred[i] = new Location[cols];
   }
   
   // If there is not one start and one end, maze is invalid.
   if(startCount != 1 || endCount != 1) {
      for(int i=0; i<rows; i++){
         delete [] pred[i];
         delete [] vstd[i];
      }
      delete [] pred;
      delete [] vstd;
      return -1; 
   }
   // Create different locations for north, west, south, east
   Location curr, loc;
   // Create a queue
   Queue q(rows*cols);
   // Initialize queue with start location
   q.add_to_back(start);
   // Initialize the visited array as opened because it contains start
   // location.
   vstd[start.row][start.col] = true;
   
   // While loop used to check each direction for the final location,
   // end if queue is empty.
   while(!q.is_empty()) {
      curr = q.remove_from_front();
      // Check if North is in bounds, check for final
      if(curr.row-1 >= 0) {
         if(vstd[curr.row-1][curr.col] == false &&
         maze[curr.row-1][curr.col] == 'F') {
            vstd[curr.row-1][curr.col] = true;
            pred[curr.row-1][curr.col] = curr;
            
         }
         // Not final, add location to back of queue
         else if(vstd[curr.row-1][curr.col] == false && 
         maze[curr.row-1][curr.col] == '.') {
            loc.row = curr.row-1;
            loc.col = curr.col; 
            q.add_to_back(loc);
            vstd[loc.row][loc.col] = true; 
            pred[loc.row][loc.col] = curr;
         }    
      }
      // Check West
      if(curr.col-1 >= 0) {
         if(vstd[curr.row][curr.col-1] == false &&
         maze[curr.row][curr.col-1] == 'F') {
            vstd[curr.row][curr.col-1] = true;
            pred[curr.row][curr.col-1] = curr;
         }
         else if(vstd[curr.row][curr.col-1] == false && 
         maze[curr.row][curr.col-1] == '.') { 
            loc.row = curr.row;
            loc.col = curr.col-1;
            q.add_to_back(loc);
            vstd[loc.row][loc.col] = true;
            pred[loc.row][loc.col] = curr;
         }
      }
      // Check South
      if(curr.row+1 < rows) {
         if(vstd[curr.row+1][curr.col] == false && 
         maze[curr.row+1][curr.col] == 'F') {
            vstd[curr.row+1][curr.col] = true;
            pred[curr.row+1][curr.col] = curr;
         }
         else if(vstd[curr.row+1][curr.col] == false && 
         maze[curr.row+1][curr.col] == '.') {
            loc.row = curr.row+1;
            loc.col = curr.col;
            q.add_to_back(loc);
            vstd[loc.row][loc.col] = true;
            pred[loc.row][loc.col] = curr;
         }
      }
      // Check East
      if(curr.col+1 < cols) {
         if(vstd[curr.row][curr.col+1] == false && 
         maze[curr.row][curr.col+1] == 'F') {
            vstd[curr.row][curr.col+1] = true;
            pred[curr.row][curr.col+1] = curr;
         }
         else if(vstd[curr.row][curr.col+1] == false && 
         maze[curr.row][curr.col+1] == '.') { 
            loc.row = curr.row;
            loc.col = curr.col+1;
            q.add_to_back(loc);
            vstd[loc.row][loc.col] = true;
            pred[loc.row][loc.col] = curr;
         }
      }
   }
   // Delete allocated memory if array is not visited. 
   if(vstd[end.row][end.col] == false) {
      for(int i = 0; i< rows; i++) {
         delete [] pred[i];
         delete [] vstd[i];
      }
      delete [] pred;
      delete [] vstd;
      return 0;
   }
   // Replaces visited arrays on optimal path with '*'
   loc = pred[end.row][end.col];
   while(!(loc.row == start.row && loc.col == start.col)) {
      maze[loc.row][loc.col] = '*';
      loc = pred[loc.row][loc.col];
   }  
   // Delete memory
   for(int i=0; i<rows; i++) {
      delete [] pred[i];
      delete [] vstd[i];
   }
   delete [] pred;
   delete [] vstd;
   
   return 1;
}