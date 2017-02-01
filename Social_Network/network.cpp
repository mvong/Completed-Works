/*
network.cpp

Author: Mark Vong
Email: mvong@usc.edu
Description: Contains the Network class functions that read in elements
from a text file, write elements to a text file and various accessor 
methods to access the private user vector. 

*/
#include <iostream>
#include "network.h"
#include <fstream>
#include <string>
#include <sstream>
#include <deque>
#include <vector>

using namespace std;
// Default Constructor
Network::Network() {  
}
// Destructor
Network::~Network() {
}
// Read friends function that to import elements from textfile
int Network::read_friends(const char *filename) {
   ifstream infile(filename);
   int numUsers;
   int userId, userYear, userZip, userFriends;
   string userName;
   string temp;
   if(infile.fail()) 
      return -1;
   else {
      infile >> numUsers;
      for(int i = 0; i < numUsers; i++) {
         infile >> userId; // gets user ID
         infile.ignore();
         getline(infile, userName); // gets user name
         userName.erase(0,1); // deletes space
         infile >> userYear; // gets user's birth year
         infile >> userZip; // gets user's zipcode
         add_user(userName, userYear, userZip); // adds new user to network
         getline(infile, temp); // get friend list
         getline(infile, temp); // get friend list
         stringstream ss(temp); // initializes the stringstream
         while (ss >> userFriends) {
            userVec[i].add_friend(userFriends);   
         }   
      }  
   }
   infile.close(); // input file close
   return 0;
}
// Write network database of users and friends to textfile
int Network::write_friends(const char *filename) {
   ofstream outfile(filename);
   if(outfile.fail()) { // check if file exists
      return -1;
   }
      outfile << get_usernum() << endl;
      for(unsigned int i = 0; i < userVec.size(); i++) {
         outfile << userVec[i].get_id() << endl;
         outfile << '\t' << userVec[i].get_name() << endl;
         outfile << '\t' << userVec[i].get_year() << endl;
         outfile << '\t' << userVec[i].get_zip() << endl;
         outfile << '\t'; 
         for(unsigned int j = 0; j < userVec[i].get_friend().size(); j++){
            outfile << userVec[i].get_friend()[j] << " ";
         } 
         outfile << endl; 
      }
   
   outfile.close(); // file close
   return 0;
}
// Adds user to the network database
void Network::add_user(string username, int yr, int zipcode) {
   User newUser(userVec.size(), username, yr, zipcode);
   userVec.push_back(newUser);
}
// Creates friend connection between two users
int Network::add_connection(string name1, string name2) {
   int id1, id2;
   id1 = get_id(name1);
   id2 = get_id(name2);
   if(id1 != -1 || id2 != -1) { // Add friends
      userVec[id2].add_friend(id1);
      userVec[id1].add_friend(id2);
      return 0;
   }
   return -1;  
}
// Removes friend connection between two users
int Network::remove_connection(string name1, string name2) {
   int id1, id2;
   id1 = get_id(name1);
   id2 = get_id(name2);
   if(id1 != -1 || id2 != -1) { // Delete friends
      userVec[id2].delete_friend(id1);
      userVec[id1].delete_friend(id2);
      return 0;
   } 
   return -1;
}
// Gets the id of the user with a given username
int Network::get_id(string username) {
   for(unsigned int i = 0; i < userVec.size(); i++) {
      if(username == userVec[i].get_name())
         return userVec[i].get_id();
   }
   return -1;
}
// Gets the user vector's size
int Network::get_usernum() {
   return userVec.size();
}
// Returns the user vector
vector<User> Network::get_userVec() {
   return userVec;
}
// Returns shortest path between two users
vector<int> Network::shortest_path(int from, int to) {
   deque<int> mutuals;
   mutuals.push_back(from);
   int temp;
   userVec[from].depth = 0;
   vector<int> friends; 
   vector<int> tempVec;
   // Initialize all users' predecessor IDs and depths
   for(unsigned int i = 0; i < userVec.size(); i++) {
      userVec[i].predecessor = -1;
      userVec[i].depth = -1; 
   }
   // Checks for shortest path 
   while(!mutuals.empty()) {
      temp = mutuals.front();
      mutuals.pop_front();
      for(unsigned int i = 0; i < userVec[temp].get_friend().size(); i++) {
         int friendID = userVec[temp].get_friend()[i];
         if(userVec[friendID].depth == -1) {
            userVec[friendID].predecessor = temp;
            userVec[friendID].depth = userVec[temp].depth+1;
            mutuals.push_back(friendID);
            if(friendID == to) {
               tempVec.push_back(friendID);
               for(unsigned int j = 0; j <= userVec[friendID].depth; j++) {
                  int num = userVec[tempVec[j]].predecessor;
                  tempVec.push_back(num); 
               }
               while(!tempVec.empty()) {
                  friends.push_back(tempVec.back());
                  tempVec.pop_back();
               }
               return friends;      
            }
         } 
      }         
   }
   return friends;
}

// Returns vector of disjointed groups
vector<vector<int> > Network::groups() {
   for(unsigned int i = 0; i < userVec.size(); i++) {
      userVec[i].depth = -1;
   }
   deque <int> tempVec;
   int tempnum;
   vector <vector<int> > totalGroup;
   // Go through users to check for individual groups
   for(unsigned int i = 0; i < userVec.size(); i++) {
      if(userVec[i].depth == -1) {
         vector <int> anothGroup;
         userVec[i].depth = 0;
         tempVec.push_back(i);
         while(!tempVec.empty()) {
            tempnum = tempVec.front();
            anothGroup.push_back(tempnum);
            tempVec.pop_front();
            int friendVec = userVec[tempnum].get_friend().size();
            for(unsigned int j = 0; j < friendVec; j++) {
               int fID = userVec[tempnum].get_friend()[j];
               if(userVec[fID].depth == -1) {
                  userVec[fID].depth = userVec[tempnum].depth+1;
                  tempVec.push_back(fID);
               }
            }
         }
         totalGroup.push_back(anothGroup);
      }     
   }
   return totalGroup;
}
// Return vector of suggested friends of users
vector<int> Network::suggest_friends(int who, int& score) {
   vector<int> tempF;
   vector<int> suggestF; 
   int max_score = 0;
   // Initialize users' scores
   for(unsigned int i = 0; i < userVec.size(); i++) {
      userVec[i].score = 0;
   }
   // Check if user's friends are user's friends' friends 
   for(unsigned int i = 0; i < userVec[who].get_friend().size(); i++) {
      // User's friends
      int userF = userVec[who].get_friend()[i];
      for(unsigned int j = 0; j < userVec[userF].get_friend().size(); j++) {
         // User's friends' friends
         int userF2 = userVec[userF].get_friend()[j];
         int same = 0;
         for(unsigned int k = 0; k < userVec[who].get_friend().size(); k++) {
            int userF3 = userVec[who].get_friend()[k];
            if(userF2 == userF3 || userF2 == who) {
               same = 1;
            } 
         }
         for(unsigned int l = 0; l < tempF.size(); l++) {
            if(tempF[l] == userF2) {
               same = 1;
               userVec[userF2].score++;  
            }
         } 
         if(same == 0) {
            userVec[userF2].score++;
            tempF.push_back(userF2);
         }
          
      }
   }
   for(unsigned int m = 0; m < tempF.size(); m++) {
      if(max_score < userVec[tempF[m]].score) {
         max_score = userVec[tempF[m]].score;
      }
   }
   score = max_score;
   for(unsigned int i = 0; i < tempF.size(); i++) {
      if(userVec[tempF[i]].score == score) {
         suggestF.push_back(tempF[i]);
      }
   }
   return suggestF;
}