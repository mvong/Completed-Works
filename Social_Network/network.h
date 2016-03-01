/*
network.h

Author: Mark Vong
Email: mvong@usc.edu
Description: Contains the Network class that also posseses the prototypes 
for the functions used to read in the elements from the text file, output
elements into a text file, add users to the database, add friends together,
delete friends, get the user's id number, get the size of the user vector 
and the user vector.

*/
#ifndef NETWORK_H
#define NETWORK_H
#include <string>
#include "user.h"
#include <vector>


class Network {
 public:
  // Default Constructor
  Network();
  // Destructor 
  ~Network();
  // Reads in textfile
  int read_friends(const char *filename);
  // Outputs to textfile
  int write_friends(const char *filename);
  // Adds users to network database
  void add_user(std::string username, int yr, int zipcode);
  // Adds two users together
  int add_connection(std::string name1, std::string name2);
  // Removes connection between two users
  int remove_connection(std::string name1, std::string name2);
  // Accessor method to get the id of a user
  int get_id(std::string username);
  // Accessor method to get the size of the user vector
  int get_usernum();
  // Accessor method to access the user network
  std::vector<User> get_userVec();
  // Get the shortest path between users
  std::vector<int> shortest_path(int from, int to);
  // List of components of network
  std::vector<std::vector<int> > groups();
  //Suggested friends with highest score
  std::vector<int> suggest_friends(int who, int& score);

 private:
  // Vector to store users in the network
  std::vector<User>userVec;

};


#endif