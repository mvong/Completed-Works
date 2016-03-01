#ifndef MULTI_STATEMENT_INCLUDED
#define MULTI_STATEMENT_INCLUDED

#include "Statement.h"
#include <string>

class MultiStatement: public Statement
{
private:
	std::string variableName;
	std::string valStr;
public:
	MultiStatement(std::string tempName, std::string tempVal);
	virtual ~MultiStatement();
	virtual void execute(ProgramState* state, std::ostream &outf);
};

#endif