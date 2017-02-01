// LetStatement.cpp:
#include "LetStatement.h"
#include <iostream>
#include <utility>
using namespace std;

LetStatement::LetStatement(string variableName, int value)
	: m_variableName( variableName ), m_value( value )
{}

LetStatement::~LetStatement() {

}

// The LetStatement version of execute() should make two changes to the
// state of the program:
//
//    * set the value of the appropriate variable
//    * increment the program counter
void LetStatement::execute(ProgramState* state, ostream &outf)
{	
	map<string, int>* tempM = state->getMap();
	if((tempM->find(m_variableName)) == (tempM->end())) 
	{
		tempM->insert(make_pair(m_variableName, m_value));
	}
	else 
	{
		(*tempM)[m_variableName] = m_value;
	}
	int temp = state->getC();
	state->setC(++temp);

}


