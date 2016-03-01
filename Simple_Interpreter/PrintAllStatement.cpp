#include "PrintAllStatement.h"
#include <iostream>

using namespace std;

PrintAllStatement::PrintAllStatement() {}

PrintAllStatement::~PrintAllStatement() {}

/* Prints all variables and their values.
*/

void PrintAllStatement::execute(ProgramState* state, ostream &outf) 
{
	map<string, int>* tempM = state->getMap();
	map<string, int>::iterator it;
	for(it = tempM->begin(); it != tempM->end(); ++it) {
		outf << it->first << " " << it->second << endl;
	}
	int temp = state->getC();
	state->setC(++temp);
}