#ifndef GO_SUB_STATEMENT_INCLUDED
#define GO_SUB_STATEMENT_INCLUDED

#include "Statement.h"

class GoSubStatement: public Statement 
{
private:
	int lineNum;

public:
	GoSubStatement(int);
	virtual ~GoSubStatement();
	virtual void execute(ProgramState* state, std::ostream& outf);
};

#endif