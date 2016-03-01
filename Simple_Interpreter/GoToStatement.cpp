#include "GoToStatement.h"

using namespace std;

GoToStatement::GoToStatement(int tempNum)
{
	lineNum = tempNum;
}

GoToStatement::~GoToStatement() {}

/* Skips to given line number and outputs error message if line does not exist.
*/

void GoToStatement::execute(ProgramState* state, ostream &outf) {
	if(lineNum < state->getnumLines()){
		state->setC(lineNum);
	}
	else if (lineNum >= state->getnumLines()) {
		outf << "Illegal Jump Instruction" << endl;
		state->setC(-1);
	}
}