#include "MultiStatement.h"
#include <iostream>
#include <sstream>
#include <cmath>

using namespace std;

MultiStatement::MultiStatement(string tempName, string tempVal)
{
	variableName = tempName;
	valStr = tempVal;
}

MultiStatement::~MultiStatement() {}

/* Multiplies either two existing variables together or an existing variable with
   a given integer.
 */

void MultiStatement::execute(ProgramState* state, ostream &outf) 
{
	map<string, int>* tempM = state->getMap();
	if(tempM->find(valStr) == tempM->end()) {
		stringstream ss(valStr);
		int temp;
		ss >> temp;
		(*tempM)[variableName] *= temp;
	}
	else {
		(*tempM)[variableName] *= (*tempM)[valStr];
	}
	int temp = state->getC();
	state->setC(++temp);
}