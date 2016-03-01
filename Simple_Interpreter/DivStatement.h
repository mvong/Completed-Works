#ifndef DIV_STATEMENT_INCLUDED
#define DIV_STATEMENT_INCLUDED

#include "Statement.h"
#include <string>

class DivStatement: public Statement
{
private:
	std::string variableName;
	std::string valStr;
public:
	DivStatement(std::string tempName, std::string tempVal);
	virtual ~DivStatement();
	virtual void execute(ProgramState* state, std::ostream &outf);
};

#endif