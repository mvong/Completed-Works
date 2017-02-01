#ifndef GO_TO_STATEMENT
#define GO_TO_STATEMENT

#include "Statement.h"

class GoToStatement: public Statement 
{
private:
	int lineNum;

public:
	GoToStatement(int tempNum);
	virtual ~GoToStatement();
	virtual void execute(ProgramState* state, std::ostream &outf);
};

#endif