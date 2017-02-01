#ifndef RETURN_STATEMENT_INCLUDED
#define RETURN_STATEMENT_INCLUDED

#include "Statement.h"

class ReturnStatement: public Statement
{
private:
	int lineNum;

public:
	ReturnStatement();
	virtual ~ReturnStatement();
	virtual void execute(ProgramState* state, std::ostream& outf);
};

#endif