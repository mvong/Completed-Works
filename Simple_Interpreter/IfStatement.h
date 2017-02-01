#ifndef IF_STATEMENT_INCLUDED
#define IF_STATEMENT_INCLUDED

#include "Statement.h"
#include <string>

class IfStatement: public Statement 
{
private:
	std::string variableName;
	int val;
	std::string op;
	std::string cond;
	int lineNum;


public:
	IfStatement(std::string, std::string, int, int);
	virtual ~IfStatement();
	virtual void execute(ProgramState* state, std::ostream &outf);
};

#endif