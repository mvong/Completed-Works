#include "IfStatement.h"
#include <iostream>
using namespace std;

IfStatement::IfStatement(string tempN, string tempOp, int tempV, int tempL)
{
	variableName = tempN;
	op = tempOp;
	val = tempV;
	lineNum = tempL;
}

IfStatement::~IfStatement() {}

/* Allows program to skip to given line if the comparison between the given variable and 
   integer value is true. Outputs error message if line is out of bounds.
 */

void IfStatement::execute(ProgramState* state, ostream& outf) 
{
	map<string, int>* tempM = state->getMap();
	int temp = state->getC();
	if(lineNum > 0 && lineNum < state->getnumLines()) {
		if(op == "<") {
			if((*tempM)[variableName] < val) {
				state->setC(lineNum);
			}
			else {
				state->setC(++temp);
			}
		}
		else if(op == "<=") {
			if((*tempM)[variableName] <= val) {
				state->setC(lineNum);
			}
			else {
				state->setC(++temp);
			}
		}
		else if(op == ">") {
			if((*tempM)[variableName] > val) {
				state->setC(lineNum);
			}
			else {
				state->setC(++temp);
			}
		}
		else if(op == ">=") {
			if((*tempM)[variableName] >= val) {
				state->setC(lineNum);
			}
			else {
				state->setC(++temp);
			}
		}
		else if(op == "=") {
			if((*tempM)[variableName] == val) {
				state->setC(lineNum);
			}
			else {
				state->setC(++temp);
			}
		}
		else if(op == "<>") {
			if((*tempM)[variableName] != val) {
				state->setC(lineNum);
			}
			else {
				state->setC(++temp);
			}
		}
	}
	else {
		outf << "Illegal jump instruction" << endl;
		state->setC(-1);
	}
}