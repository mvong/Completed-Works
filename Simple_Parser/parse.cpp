#include <iostream>
#include <fstream>
#include "stackint.h"
#include <string>
#include <ctype.h>
#include <cmath>
#include <stdlib.h>
#include <sstream>

using namespace std;

int main(int argc, const char* argv[]) {
	if(argc < 2) {
		cout << "Please input file" << endl;
	}
	else 
	{
		ifstream input(argv[1]);
		StackInt stack;
		StackInt tempStack;
		string temp;
		stringstream ss;
		// Boolean variable to keep track of numbers that have been shifted and need to be added
		bool addC = false;
		// Boolean variable to keep track of numbers that have been shifted and need to be multiplied
		bool multC = false;
		// Boolean variable to keep track of malformed equations
		bool malformed = false;
		// Open parentheses value
		const int OPEN_PAREN = -1;
		// Shift left value
		const int shiftLeft = -3;
		// Shift right value
		const int shiftRight = -4;
		// Add operator
		const int addOpp = -5;
		// Multiplication operator
		const int multOpp = -6;
		// Stores the end product
		int tempNum = -7;
		// Temp variable to store integers
		int finalNum = -8;
		// Value to keep track of which equations were malformed
		const int invalid = -9;
		// Temp variable to store input integer
		int tempD = 0;
		// Temp variable to store input integer
		int D = 0;
		// Keeps track of number of open parentheses
		int oParen = 0;
		// Keeps track of number of open parentheses
		int clParen = 0;
		// While input file contains equations
		while(!input.fail()) {
			getline(input, temp);
			oParen = 0;
			clParen = 0;
			D = 0;
			tempD = 0;
			malformed = false;
			addC = false;
			multC = false;
			tempNum = -7;
			finalNum = -8;
			// Clear stack
			while(!tempStack.empty()) {
				tempStack.pop();
			}
			if(!input.fail()) {
				// Iterate through string
				for(unsigned int i = 0; i < temp.length(); i++) {
					// Push open paren to tempStack
					if(temp[i] == '(') {
						tempStack.push(OPEN_PAREN);
						oParen++;
					}
					// Push shift right to tempStack
					else if(temp[i] == '>') {
						tempStack.push(shiftRight);
					}
					// Push shift left to tempStack
					else if(temp[i] == '<') {
						tempStack.push(shiftLeft);
					}
					// Push add opp to tempStack
					else if(temp[i] == '+') {
						tempStack.push(addOpp);
					}
					// Push multiplication opp to tempStack
					else if(temp[i] == '*') {
						tempStack.push(multOpp);
					}
					// Once first closed paren is reached, do operations
					else if(temp[i] == ')') {
						// Counter to keep track of number of closed paren
						clParen++;
						// While tempStack is not empty, do operations
						while(!tempStack.empty()) {
							// Check if top of stack is integer, store in temp variable if it is
							if(tempStack.top() >= 0) {
								tempNum = tempStack.top();
								tempStack.pop();
							}
							// Check if top of stack is add opp, pop and check what the next item on the stack is
							else if(tempStack.top() == addOpp) {
								tempStack.pop();
								addC = true;
								// If next item is an integer
								if(tempStack.top() >= 0) {
									// Check if it has already been used
									if(finalNum == -8) {
										finalNum = tempStack.top();
										tempStack.pop();
										// Check if add opp and mult opp are in same equation, if so then it is malformed
										if(tempStack.top() == multOpp) {
											malformed = true;
											stack.push(invalid);
										}
									}
									// Check if the numb should be shifted left before being added
									if(tempStack.top() == shiftLeft) {
										tempStack.pop();
										finalNum *= 2;
										// Check if next item on stack is add opp or mult opp, follow operations
										if(tempStack.top() == addOpp || tempStack.top() == multOpp) {
											tempNum += finalNum;
											finalNum = -8;
											addC = false;
										}
										// If next item on stack is an integer after the shift, equation is malformed
										else if(tempStack.top() >= 0) {
											malformed = true;
											stack.push(invalid);
										}
									}
									// Check if the numb should be shifted right before being added
									else if(tempStack.top() == shiftRight) {
										tempStack.pop();
										finalNum /= 2;
										// Check if next item on stack is add opp or mult opp, follow operations
										if(tempStack.top() == addOpp || tempStack.top() == multOpp) {
											tempNum += finalNum;
											finalNum = -8;
											addC = false;
										}
										// Check if next item on stack is an integer, if it is then equation is malformed
										else if(!tempStack.empty() && tempStack.top() >= 0) {
											malformed = true;
											stack.push(invalid);
										}
									}
									// if no shifts then add
									else {
										if(finalNum >= 0) {
											tempNum += finalNum;
											finalNum = -8;
											addC = false;
										}
										else{
											tempNum += tempStack.top();
											tempStack.pop();
											addC = false;
											if(tempStack.top() == multOpp) {
												malformed = true;
												stack.push(invalid);
											}
										}
									}
								}
								// if next item on stack is not an integer, equation is malformed
								else {
									stack.push(invalid);
									malformed = true;
								}
							}
							// Check if next item on stack is multiplication operator
							else if(tempStack.top() == multOpp) {
								tempStack.pop();
								multC = true;
								// Check if next item is an integer
								if(tempStack.top() >= 0) {
									if(finalNum == -8) {
										finalNum = tempStack.top();
										tempStack.pop();
										// Check if item after integer is add operator, if it is then equation is malformed
										if(tempStack.top() == addOpp) {
											malformed = true;
											stack.push(invalid);
										}
									}
									// Check if item should be shifted left before being multiplied
									if(tempStack.top() == shiftLeft) {
										tempStack.pop();
										finalNum *= 2;
										// If item after shift is add or multiplication, do operation
										if(tempStack.top() == addOpp || tempStack.top() == multOpp) {
											tempNum *= finalNum;
											finalNum = -8;
											multC = false;
										}
										// if next item after shift is an integer, equation is malformed
										else if(!tempStack.empty() && tempStack.top() >= 0) {
											malformed = true;
											stack.push(invalid);
										}
									}
									// Check if item should be shifted right before being multiplied
									else if (tempStack.top() == shiftRight){
										tempStack.pop();
										finalNum /= 2;
										// If item after shift is add or multiplication, do operation
										if(tempStack.top() == addOpp || tempStack.top() == multOpp) {
											tempNum *= finalNum;
											finalNum = -8;
											multC = false;
										}
										// If next item after shift is an integer, equation is malformed
										else if(!tempStack.empty() && tempStack.top() >= 0) {
											malformed = true;
											stack.push(invalid);
										}
									}	
									// else just multiply							
									else {
										if(finalNum >= 0) {
											tempNum *= finalNum;
											finalNum = -8;
											multC = false;
										}
										else{
											tempNum *= tempStack.top();
											tempStack.pop();
											multC = false;
											// If item after multiplication is an add operator, equation is malformed
											if(tempStack.top() == addOpp) {
												malformed = true;
												stack.push(invalid);
											}
										}
									}
								}
								else {
									stack.push(invalid);
									malformed = true;
								}
							}
							// Number needs to be shifted left
							else if(tempStack.top() == shiftLeft) {
								tempStack.pop();
								// Check if item after shift is an integer, malformed equation if it is
								if(!tempStack.empty() && tempStack.top() >= 0) {
									malformed = true;
									stack.push(invalid);
								}
								// If the number has not been altered, do operation
								else if(finalNum == -8) {
									tempNum *= 2;
								}
								// Shift already changed number
								else {
									finalNum *= 2;
								}
							}
							// Number needs to be shifted right
							else if(tempStack.top() == shiftRight) {
								tempStack.pop();
								// Check if item after shift is an integer, malformed if it is
								if(!tempStack.empty() && tempStack.top() >= 0) {
									malformed = true;
									stack.push(invalid);
								}
								// If number has not been altered, shift
								else if(finalNum == -8) {
									tempNum /= 2;
								}
								// Shift already changed number
								else {
									finalNum /= 2;
								}
							}
							// End of one equation
							else if(tempStack.top() == OPEN_PAREN) {
								tempStack.pop();
								// Check if numbers need to be multiplied after one was shifted
								if(tempNum >= 0 && finalNum >= 0 && multC == true) {
									tempNum *= finalNum;
									finalNum = -8;
									multC = false;
								}
								// Check if numbers need to be added after one was shifted
								else if (tempNum >= 0 && finalNum >= 0 && addC == true) {
									tempNum += finalNum;
									finalNum = -8;
									addC = false;
								}
							}
						}
					}
					// If char is an integer, push integer to tempStack
					else if(isdigit(temp[i])) {
						ss << temp[i];
						ss >> tempD;
						ss.clear();
						if(D >= 10) {
							tempD = D;
						}
						D = tempD;
						if(isdigit(temp[i+1]) && tempD >= 0) {
							D = tempD * 10;
							ss << temp[i+1];
							ss >> tempD;
							D += tempD;
							ss.clear();
						}
						else {
							tempStack.push(D);
							D = 0;
							tempD = 0;
						}
					}
				}
				// Check if number of open parentheses are equal to number of closed parentheses, malformed if unequal
				if(oParen != clParen) {
					stack.push(invalid);
					malformed = true;
				}
				// If tempStack is empty and not malformed, push evaluated integer to stack
				if(tempStack.empty() && !malformed) {
					stack.push(tempNum);
				}
			}
			
		}
		// Print results
		while(!stack.empty()) {
			if(stack.top() >= 0) {
				cout << stack.top() << endl;
				stack.pop();
			}
			else if(stack.top() == invalid) {
				cout << "Malformed" << endl;
				stack.pop();
			}
		}
		input.close();
	}
	return 0;
}