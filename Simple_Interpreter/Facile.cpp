// Facile.cpp
#include "Statement.h"
#include "LetStatement.h"
#include "EndStatement.h"
#include "PrintStatement.h"
#include "PrintAllStatement.h"
#include "AddStatement.h"
#include "SubtractStatement.h"
#include "MultiStatement.h"
#include "DivStatement.h"
#include "GoToStatement.h"
#include "IfStatement.h"
#include "GoSubStatement.h"
#include "ReturnStatement.h"
#include <vector>
#include <string>
#include <sstream> 
#include <fstream>
#include <cstdlib>
#include <iostream>
#include <cctype>

using namespace std;

// parseProgram() takes a filename as a parameter, opens and reads the
// contents of the file, and returns an vector of pointers to Statement.
void parseProgram(istream& inf, vector<Statement *> & program);

// parseLine() takes a line from the input file and returns a Statement
// pointer of the appropriate type.  This will be a handy method to call
// within your parseProgram() method.
Statement * parseLine(string line);

// interpretProgram() reads a program from the given input stream
// and interprets it, writing any output to the given output stream.
// Note:  you are required to implement this function!
void interpretProgram(istream& inf, ostream& outf);

Statement::~Statement() {}

int main()
{
        cout << "Enter Facile program file name: ";
        string filename;
        getline(cin, filename);
        ifstream infile(filename.c_str());
        if (!infile)
        {
                cout << "Cannot open " << filename << "!" << endl;
                return 1;
        }
        interpretProgram(infile, cout);
}



void parseProgram(istream &inf, vector<Statement *> & program)
{
	program.push_back(NULL);
	
	string line;
	while( ! inf.eof() )
	{
		getline(inf, line);
		program.push_back( parseLine( line ) );
	}
}


Statement * parseLine(string line)
{
	Statement * statement;
	stringstream ss;
	string type;
	string var;
	int val;

	ss << line;
	ss >> type;
	
	if ( type == "LET" )
	{
		ss >> var;
		ss >> val;
		// Note:  Because the project spec states that we can assume the file
		//	  contains a syntactically legal Facile program, we know that
		//	  any line that begins with "LET" will be followed by a space
		//	  and then a variable (single character) and then an integer value.
		statement = new LetStatement(var, val);
	}

	else if ( type == "END") 
	{
		statement = new EndStatement();
	}

	else if ( type == "PRINT") {
		ss.clear();
		ss >> var;
		statement = new PrintStatement(var);
	}

	else if ( type == "PRINTALL") 
	{
		statement = new PrintAllStatement();
	}
 
	else if ( type == "ADD") {
		ss.clear();
		ss >> var;
		string tempVal;
		ss >> tempVal;
		statement = new AddStatement(var, tempVal);
	}
	else if ( type == "SUB") {
		ss.clear();
		ss >> var;
		string tempVal;
		ss >> tempVal;
		statement = new SubtractStatement(var, tempVal);
	}
	else if ( type == "MULT") {
		ss.clear();
		ss >> var;
		string tempVal;
		ss >> tempVal;
		statement = new MultiStatement(var, tempVal);
	}
	else if ( type == "DIV") {
		ss.clear();
		ss >> var;
		string tempVal;
		ss >> tempVal;
		statement = new DivStatement(var, tempVal);
	}
	else if ( type == "GOTO") {
		ss.clear();
		ss >> val;
		statement = new GoToStatement(val);
	}
	else if( type == "IF") {
		ss.clear();
		ss >> var;
		string tempOp; 
		ss >> tempOp;
		int tempN;
		ss >> tempN;
		string condSt;
		ss >> condSt;
		ss >> val;
		statement = new IfStatement(var, tempOp, tempN, val);
	}
	else if (type == "GOSUB") {
		ss.clear();
		int tempNum;
		ss >> tempNum;
		statement = new GoSubStatement(tempNum);
	}
	else if (type == "RETURN") {
		ss.clear();
		statement = new ReturnStatement();
	}
	else if (type == ".") {
		statement = new EndStatement();
	}

	return statement;
}


void interpretProgram(istream& inf, ostream& outf)
{
	vector<Statement *> program;
	parseProgram( inf, program );
	ProgramState* state = new ProgramState(program.size());
	unsigned int pSize = program.size();
	for(unsigned int i = 1; i < pSize; i++) 
	{  
		unsigned int temp = state->getC();
		if(temp != i && temp < program.size() && temp > 0) {
			program[temp]->execute(state, outf);
			pSize++;
		}
		else if(temp == i && temp < program.size()) {
			program[i]->execute(state, outf);
		}
		else {
			break;
		}
	}
	for(unsigned int i = 0; i < program.size(); i++) {
		delete program[i];
	}
	delete state;
}

