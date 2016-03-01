/*
user.cpp

Author: Mark Vong
Email: mvong@usc.edu
Description: Contains the User class, the various accessor methods to 
access the private data members, and the functions used to add and delete
friends.

*/
#include <iostream>
#include "user.h"
#include <string>
#include <vector> 

using namespace std;

User::User(int user_id, string user_name, int user_year, int user_zip) {
   id = user_id;
   name = user_name;
   year = user_year;
   zip = user_zip;
}

User::~User() {
}

int User::get_id() {
   return id;
}

string User::get_name() {
   return name;
}

int User::get_year() {
   return year;
}

int User::get_zip() {
   return zip;
}

vector<int> User::get_friend() {
   return friendlist;
}

void User::add_friend(int user_id) {
   friendlist.push_back(user_id);
}

void User::delete_friend(int user_id) { 
   for(unsigned int i = 0; i<friendlist.size(); i++) {
      if (friendlist[i] == user_id) {
         friendlist.erase(friendlist.begin()+i);  
      }
   }  
} 
