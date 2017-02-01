#include "ReturnStatement.h"

using namespace std;

ReturnStatement::ReturnStatement() {}

ReturnStatement::~ReturnStatement() {}

/* ReturnStatement returns the cursor to the line skipped by GOSUB statement. 
   This line is then executed by the program.
*/
   
void ReturnStatement::execute(ProgramState* state, ostream& outf) 
{
	StackInt stackL = state->getStack();
	if(!stackL.empty()) {
		lineNum = stackL.top();
		stackL.pop();
		state->setStack(stackL);
		state->setC(++lineNum);
	}
	else {
		outf << "Nothing to return to!" << endl;
		state->setC(-1);
	}
}