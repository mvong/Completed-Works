// PrintStatement.cpp:
#include "PrintStatement.h"


#include <iostream>
using namespace std;

PrintStatement::PrintStatement(string variableName) 
{
	m_variableName = variableName;
}

PrintStatement::~PrintStatement() {}

/* Prints given variable's integer value or 0 if it doesn't exist.
*/

void PrintStatement::execute(ProgramState * state, std::ostream &outf)
{
	map<string, int>* tempM = state->getMap();
	outf << (*tempM)[m_variableName] << endl;
	int temp = state->getC();
	state->setC(++temp);

}


