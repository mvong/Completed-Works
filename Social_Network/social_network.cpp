/*
social_network.cpp

Author: Mark Vong
Email: mvong@usc.edu
Description: Contains the main function that utilizes the User and 
Network class functions to mimic a social network. The file contains 
menu options for the user to choose which action they would like to 
proceed with in terms of adding a friend, deleting a friend, or merely 
printing out the user network or a user's friendslist. 

*/
#include <iostream>
#include "network.h"
#include "user.h"
#include <iomanip>

using namespace std; 
// Main function
int main(int argc, char *argv[])
{  // Checks to see if at least 2 arguments are given
   if(argc < 2) {
      cerr << "Error: Please input a text file" << endl;
   }
   // New network object
   Network myNetwork;
   // Reads in textfile given in the command line
   myNetwork.read_friends(argv[1]);
   // Initiliazes the user and friend strings used in functions
   string user_firstname, user_lastname, user_fullname;
   int user_birthyear, user_zipcode;
   string friend_firstname, friend_lastname, friend_fullname;
   // Initiliaze user input variable
   int option = 0;
   // While true, enter loop
   while(true) {
      // Menu options
      cout << "Choose 1, give fullname, birth year, zip code to add user";
      cout << endl;
      cout << "Choose 2, provide both users' full names to add users";
      cout << endl;
      cout << "Choose 3, give both users' full names to remove friends";
      cout << endl;
      cout << "Choose 4 to print a list of users and their info";
      cout << endl;
      cout << "Choose 5, give a user's full name to print user's friends";
      cout << endl; 
      cout << "Choose 6, provide a text file name to output network";
      cout << endl; 
      cout << "Choose 7, provide two user names to print shortest path";
      cout << endl;
      cout << "Choose 8 to print disjointed users list.";
      cout << endl;
      cout << "Choose 9, enter a user to view suggested friends.";
      cout << endl;
      cout << ">" << " ";
      // User input option
      cin >> option;
      // If option 1, add new user to network database
      if(option == 1) {
         cin >> user_firstname >> user_lastname;
         user_fullname = user_firstname + " " + user_lastname;
         cin >> user_birthyear;
         cin >> user_zipcode;
         myNetwork.add_user(user_fullname, user_birthyear, user_zipcode);
      }
      // If option 2, create friend connection between two users
      else if(option == 2) {
         cin >> user_firstname >> user_lastname;
         user_fullname = user_firstname + " " + user_lastname;
         cin >> friend_firstname >> friend_lastname;
         friend_fullname = friend_firstname + " " + friend_lastname;
         // Check to see if user exists
         if(myNetwork.get_id(user_fullname) == -1)
            cout << user_fullname << " doesn't exist." << endl;
         // Check to see if friend exists
         else if(myNetwork.get_id(friend_fullname) == -1) 
            cout << friend_fullname << " doesn't exist." << endl;
         // Both exist, add friends
         else
            myNetwork.add_connection(user_fullname, friend_fullname);
      }
      // If option 3, remove friend connection between two users
      else if(option == 3) {
         cin >> user_firstname >> user_lastname;
         user_fullname = user_firstname + " " + user_lastname;
         cin >> friend_firstname >> friend_lastname;
         friend_fullname = friend_firstname + " " + friend_lastname;
         // Check to see if user exists
         if(myNetwork.get_id(user_fullname) == -1)
            cout << user_fullname << " doesn't exist." << endl;
         // Check to see if friend exists
         else if(myNetwork.get_id(friend_fullname) == -1) 
            cout << friend_fullname << " doesn't exist." << endl;
         // Both exist, remove friend connection
         else
            myNetwork.remove_connection(user_fullname, friend_fullname);
     }
     // If option 4, display network database in terminal 
     else if(option == 4) {
         // Table header
         cout << myNetwork.get_userVec().size() << endl;
         cout << "ID" << '\t';
         cout << "Name" << setw(16);
         cout << "Year" << setw(7);
         cout << "Zip" << endl;
         cout << "=====================================" << endl;
         for(int i = 0; i < myNetwork.get_userVec().size(); i++) {
            cout << myNetwork.get_userVec()[i].get_id() << "." << '\t';
            cout << myNetwork.get_userVec()[i].get_name() << '\t';
            cout << myNetwork.get_userVec()[i].get_year() << '\t';
            cout << myNetwork.get_userVec()[i].get_zip()
            << endl;
         }
     }
     // If option 5, display user's friendslist
     else if(option == 5) {
         cin >> user_firstname >> user_lastname;
         user_fullname = user_firstname + " " + user_lastname;
         int ID = myNetwork.get_id(user_fullname);
         // Check to see if user exists
         if(ID == -1) {
            cout << "Error:" << user_fullname << " does not exist" << endl;
         }
         // User exists, find friends
         else {
            int userID = myNetwork.get_id(user_fullname);
            int numF = myNetwork.get_userVec()[userID].get_friend().size();
            int numNet = myNetwork.get_userVec().size();
            // Table Header
            cout << "ID" << '\t';
            cout << "Name" << setw(16);
            cout << "Year"<< setw(7);
            cout << "Zip" << endl;
            cout << "=====================================" << endl;
            // Find friend ID
            for(int i = 0; i < numF; i++) {
               int fID = myNetwork.get_userVec()[userID].get_friend()[i];
               // Compare friend ID to user ID's and print matching ones
               for(int j = 0; j < numNet; j++) {
                  if(fID == myNetwork.get_userVec()[j].get_id()) { 
                     cout << myNetwork.get_userVec()[fID].get_id() 
                     << '\t';
                     cout << myNetwork.get_userVec()[fID].get_name() 
                     << '\t';
                     cout << myNetwork.get_userVec()[fID].get_year() 
                     << '\t';
                     cout << myNetwork.get_userVec()[fID].get_zip() 
                     << endl;
                  }
               }
            }    
         }    
     }
     // If option 6, write out network database to textfile given
     else if(option == 6) {
         cin >> argv[1];
         myNetwork.write_friends(argv[1]);
     }
     
     else if(option == 7) {
         cin >> user_firstname >> user_lastname;
         user_fullname = user_firstname + " " + user_lastname;
         cin >> friend_firstname >> friend_lastname;
         friend_fullname = friend_firstname + " " + friend_lastname;
         int fromID = myNetwork.get_id(user_fullname);
         int toID = myNetwork.get_id(friend_fullname);
         vector<int> friends = myNetwork.shortest_path(fromID, toID);
         if(friends.size() == 0) {
            cout << "None" << endl;
         }
         else {
            cout << "Distance: " << friends.size() - 1 << endl;
         }   
         for(unsigned int i = 0; i < friends.size(); i++) {
            int me = friends[i];
            cout << myNetwork.get_userVec()[me].get_name();
            if(i<friends.size()-1) {
               cout << " -> ";
            }
         }
         cout << endl;
         
     }
     else if(option == 8) {
         vector<vector<int> > totalGroups = myNetwork.groups();
         for(unsigned int i = 0; i < totalGroups.size(); i++) {
            cout << "Set " << i+1 << " => ";
            for(unsigned int j = 0; j < totalGroups[i].size(); j++) {
               int buf = totalGroups[i][j];
               cout << myNetwork.get_userVec()[buf].get_name();
               if(j != totalGroups[i].size()-1) {
                  cout << ", ";
               }
            }
            cout << endl;
         }
         cout << endl;
     }
     else if(option == 9) {
         cin >> user_firstname >> user_lastname;
         user_fullname = user_firstname + " " + user_lastname;
         if(myNetwork.get_id(user_fullname) == -1) {
            cout << "Error: No User Found" << endl;
         }
         else {
            int userID = myNetwork.get_id(user_fullname);
            int score;
            vector<int> suggestedF = myNetwork.suggest_friends(userID, score);
            cout << "The suggested friend(s) is/are:" << endl;
            for(unsigned int i = 0; i < suggestedF.size(); i++) {
               int fID = suggestedF[i];
               cout << '\t' << myNetwork.get_userVec()[fID].get_name() << 
               '\t' << "Score: " << score << endl;
            }
         }
     }    
     // If (option < 1 || option > 9), end loop
     else
         break;
  }            
 return 0;
}