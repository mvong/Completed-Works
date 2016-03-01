
#ifndef PRINT_STATEMENT_INCLUDED
#define PRINT_STATEMENT_INCLUDED

#include "Statement.h"
#include <iostream>
#include <string>

class PrintStatement: public Statement
{
private:
	std::string m_variableName;
	
public:
	PrintStatement(std::string variableName);
	virtual ~PrintStatement();
	virtual void execute(ProgramState * state, std::ostream &outf);
};

#endif

