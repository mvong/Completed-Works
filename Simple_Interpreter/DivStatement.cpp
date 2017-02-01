#include "DivStatement.h"
#include <iostream>
#include <sstream>
#include <cmath>
#include <ctype.h>

using namespace std;

DivStatement::DivStatement(string tempName, string tempVal)
{
	variableName = tempName;
	valStr = tempVal;
}

DivStatement::~DivStatement() {}

/* Divides an existing variable by either another existing variable or integer.
   Outputs error message if variable is being divide by a variable that does not 
   exist or is an integer equal to 0.
 */

void DivStatement::execute(ProgramState* state, ostream &outf) 
{
	map<string, int>* tempM = state->getMap();
	int temp = state->getC();
	if(tempM->find(valStr) == tempM->end()) {
		if(isdigit(valStr[0])) {
			if(valStr == "0") {
				outf << "Divide by zero exception" << endl;
				state->setC(-1);
			}
			else {
				stringstream ss(valStr);
				int tempNum;
				ss >> tempNum;
				(*tempM)[variableName] /= tempNum;
				state->setC(++temp);
			}
		}
		else {
			outf << "Divide by nonexistent variable" << endl;
			state->setC(-1);
		}
	}
	
	else if(tempM->find(valStr) != tempM->end()) {
		if((*tempM)[valStr] == 0) {
			outf << "Divide by zero exception" << endl;
			state->setC(-1);
		}
		else {
			(*tempM)[variableName] /= (*tempM)[valStr];	
			state->setC(++temp);
		}
	}
}