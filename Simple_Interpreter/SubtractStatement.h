#ifndef SUBTRACT_STATEMENT_INCLUDED
#define SUBTRACT_STATEMENT_INCLUDED

#include "Statement.h"
#include <string>

class SubtractStatement: public Statement
{
private:
	std::string variableName;
	std::string valStr;
public:
	SubtractStatement(std::string tempName, std::string tempVal);
	virtual ~SubtractStatement();
	virtual void execute(ProgramState* state, std::ostream &outf);
};

#endif