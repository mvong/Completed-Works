#include "llistint.h"
#include <cstdlib>
#include <stdexcept>

LListInt::LListInt()
{
  head_ = NULL;
  tail_ = NULL;
  size_ = 0;
}

LListInt::~LListInt()
{
  clear();
}

bool LListInt::empty() const
{
  return size_ == 0;
}

int LListInt::size() const
{
  return size_;
}

/* Function is used to insert a node at the beginning, end and middle of a linked list. 
   Will return if the given index does not exist.
 */
void LListInt::insert(int loc, const int& val)
{
    Item* newNode = new Item;
    newNode->val = val;
    Item* tempNode = getNodeAt(loc);
    // Function ends if position does not exist.
    if(loc > size_ || loc < 0) {
      return;
    }
    // If list is empty, create new node.
    else if(size_ == 0) {
      newNode->next = NULL;
      newNode->prev = NULL;
      head_ = newNode;
      tail_ = newNode;
      size_++;
    }
    // Add to start of the list.
    else if(loc == 0) {
      newNode->next = head_;
      head_->prev = newNode;
      newNode->prev = NULL;
      head_ = newNode;
      size_++;
    }
    // Add to end of the list.
    else if(loc == size_) {
      tail_->next = newNode;
      newNode->prev = tail_;
      newNode->next = NULL;
      tail_ = newNode;
      size_++;
    }
    // Add to middle of the list.
    else {
      newNode->next = tempNode;
      newNode->prev = tempNode->prev;
      tempNode->prev->next = newNode;
      tempNode->prev = newNode;
      size_++;
    }
}

/* This function is responsible for removing a node from a specified node.
   Function will end if the position called at does not exist.
 */
void LListInt::remove(int loc)
{
  Item* tempNode = getNodeAt(loc);
  Item* curr = NULL;
  // Return if position does not exist.
  if(loc > size_ || loc < 0) {
    return;
  }
  // If single noded list, delete node and point head and tail to NULL.
  else if(size_ == 1) {
    tail_ = NULL;
    head_ = NULL;
    delete tempNode;
    size_--;
  }
  // Delete from the start of the list.
  else if(loc == 0) {
    curr = head_;
    head_ = head_->next;
    delete curr;
    head_->prev = NULL;
    size_--;
  }
  // Delete from the end of the list.
  else if(loc == size_-1) {
    curr = tail_;
    tail_ = tail_->prev;
    delete curr;
    tail_->next = NULL;
    size_--;

  }
  // Delete from the middle of the list.
  else{
    tempNode->prev->next = tempNode->next;
    tempNode->next->prev = tempNode->prev;
    delete tempNode;
    size_--;
  }

}

void LListInt::set(int loc, const int& val)
{
  Item *temp = getNodeAt(loc);
  temp->val = val;
}

int& LListInt::get(int loc)
{
  if(loc < 0 || loc >= size_){
    throw std::invalid_argument("bad location");
  }
  Item *temp = getNodeAt(loc);
  return temp->val;
}

int const & LListInt::get(int loc) const
{
  if(loc < 0 || loc >= size_){
    throw std::invalid_argument("bad location");
  }
  Item *temp = getNodeAt(loc);
  return temp->val;
}

void LListInt::clear()
{
  while(head_ != NULL){
    Item *temp = head_->next;
    delete head_;
    head_ = temp;
  }
  tail_ = NULL;
  size_ = 0;
}


/* The function is responsible for returning a pointer to the node at the 
   given index. If position does not exist, return NULL.
 */
LListInt::Item* LListInt::getNodeAt(int loc) const
{
  
  Item* curr = NULL;
  curr = head_;
  // If empty list, return pointer to list.
  if(curr == NULL) {
    return curr;
  }
  // If position does not exist, return NULL.
  else if(loc < 0 || loc > size_) {
    return NULL;
  }
  else {
    // Traverse through linked list and return the index matching the given position.
    for(int i = 0; i < size_; i++) {
      if(i == loc) {
        return curr;
      }
      else{
        curr = curr->next;
      }
    }
  }
  return 0;
}
  /**
   * Copy Constructor (deep copy) 
   */
LListInt::LListInt(const LListInt& other) {
  head_ = NULL;
  tail_ = NULL;
  size_ = 0;
  Item* temp = other.head_;
  for(int i = 0; i < other.size_; i++) {
    this->insert(i, temp->val);
    temp = temp->next;
  }
}

  /**
   * Assignment Operator (deep copy)
   */
LListInt& LListInt::operator=(const LListInt& other) {
  if (this == &other) {
    return *this;
  }
  else if (this != &other) {
    Item* otherPtr = other.head_;
    this->clear();
    for(int i = 0; i < other.size_; i++) {
      this->insert(i, otherPtr->val);
      otherPtr = otherPtr->next;
    }
  }
  return *this;
}

  /**
   * Concatentation Operator (other should be appended to the end of this)
   */
LListInt LListInt::operator+(const LListInt& other) const {
  LListInt newItem(*this);
  Item* tempItem = other.head_;
  for(int i = 0; i < other.size_; i++) {
      newItem.insert(i, tempItem->val);
      tempItem = tempItem->next;
  }
  return newItem;

}

  /**
   * Access Operator
   */
int const & LListInt::operator[](int position) const {
  Item* tempItem = this->head_;
  for(int i = 0; i < this->size(); i++) {
    if(i == position) {
      break;
    }
    else {
      tempItem = tempItem->next;
    }
  }
  return tempItem->val;
}

