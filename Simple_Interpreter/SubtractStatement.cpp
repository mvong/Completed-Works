#include "SubtractStatement.h"
#include <iostream>
#include <sstream>
#include <cmath>

using namespace std;

SubtractStatement::SubtractStatement(string tempName, string tempVal)
{
	variableName = tempName;
	valStr = tempVal;
}

SubtractStatement::~SubtractStatement() {}

/* Subtracts an existing variable's value from the given variable's value or an integer 
   from the given variable's value.
*/

void SubtractStatement::execute(ProgramState* state, ostream &outf) 
{
	map<string, int>* tempM = state->getMap();
	if(tempM->find(valStr) == tempM->end()) {
		stringstream ss(valStr);
		int temp;
		ss >> temp;
		(*tempM)[variableName] -= temp;
	}
	else {
		(*tempM)[variableName] -= (*tempM)[valStr];
	}
	int temp = state->getC();
	state->setC(++temp);
}