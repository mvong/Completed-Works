#include "GoSubStatement.h"

using namespace std;

GoSubStatement::GoSubStatement(int tempL) {
	lineNum = tempL;
}

GoSubStatement::~GoSubStatement() {}

/* Skips to the given line number but stores the skipped line in stack to be
  returned to with RETURN statement.
 */

void GoSubStatement::execute(ProgramState* state, ostream& outf) 
{
	StackInt stack = state->getStack();
	int currL = state->getC();
	if(lineNum > 0 && lineNum < state->getnumLines()) {
		stack.push(currL);
		state->setC(lineNum);
		state->setStack(stack);
	}
	else {
		outf << "Illegal jump instruction" << endl;
		state->setC(-1);
	}
}