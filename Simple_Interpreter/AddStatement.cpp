#include "AddStatement.h"
#include <iostream>
#include <sstream>
#include <cmath>
using namespace std;

AddStatement::AddStatement(string tempName, string tempVal) {
	variableName = tempName;
	valStr = tempVal;
}

AddStatement::~AddStatement() {}

/* Adds either two variables together or an integer to an existing variable.
*/

void AddStatement::execute(ProgramState* state, ostream& outf) 
{
	map<string, int>* tempM = state->getMap();
	if(tempM->find(valStr) == tempM->end()) {
		stringstream ss(valStr);
		int temp;
		ss >> temp;
		(*tempM)[variableName] += temp;
	}
	else {
		(*tempM)[variableName] += (*tempM)[valStr];
	}
	// Move on to the next line.
	int temp = state->getC();
	state->setC(++temp);
}