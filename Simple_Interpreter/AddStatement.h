#ifndef ADD_STATEMENT_INCLUDED
#define ADD_STATEMENT_INCLUDED

#include "Statement.h"
#include <string>

class AddStatement: public Statement 
{
private:
	std::string variableName;
	std::string valStr;

public:
	AddStatement(std::string tempName, std::string tempVal);
	virtual ~AddStatement();
	virtual void execute(ProgramState* state, std::ostream &outf);
};

#endif