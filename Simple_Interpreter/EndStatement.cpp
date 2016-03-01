#include "EndStatement.h" 
#include <iostream>
using namespace std;

EndStatement::EndStatement() {}

EndStatement::~EndStatement() {}

/* Terminates the program immediately.
*/

void EndStatement::execute(ProgramState* state, ostream& outf) 
{
	state->setC(-1);
}