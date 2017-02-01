/*
user.h

Author: Mark Vong
Email: mvong@usc.edu
Description: Contains the User class as well as the function prototypes.
Functions range from various accessor methods to the vector used to 
store the user's friends. 

*/
#ifndef USER_H
#define USER_H
#include <vector>
#include <string>


class User {
 public:
   // Constructor
   User(int user_id, std::string user_name, int user_year, int user_zip);
   // Destructor
   ~User();
   // Accessor method to return user id
   int get_id();
   // Accessor method to return user name
   std::string get_name();
   // Accessor method to return user birth year
   int get_year();
   // Accessor method to return user zipcode
   int get_zip();
   // Function to add a friend connection with a user's id
   void add_friend(int user_id);
   // Function to delete friend connection
   void delete_friend(int user_id);
   // Accessor method to return the friend vector
   std::vector<int> get_friend();
   // How far out from initial user
   int depth;
   // Proceeding friend
   int predecessor;
   // Score of user(mutual friends) 
   int score;
 
 // Private data members
 private:
   int id;
   std::string name;
   int year;
   int zip;
   std::vector<int>friendlist; 

};



#endif