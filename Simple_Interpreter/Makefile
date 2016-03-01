all: facile

ProgramState.o: ProgramState.cpp ProgramState.h 
	g++ -Wall -g -c ProgramState.cpp -o ProgramState.o

LetStatement.o: LetStatement.cpp LetStatement.h
	g++ -Wall -g -c LetStatement.cpp -o LetStatement.o

EndStatement.o: EndStatement.cpp EndStatement.h
	g++ -Wall -g -c EndStatement.cpp -o EndStatement.o

PrintStatement.o: PrintStatement.cpp PrintStatement.h
	g++ -Wall -g -c PrintStatement.cpp -o PrintStatement.o

PrintAllStatement.o: PrintAllStatement.cpp PrintAllStatement.h
	g++ -Wall -g -c PrintAllStatement.cpp -o PrintAllStatement.o

AddStatement.o: AddStatement.cpp AddStatement.h
	g++ -Wall -g -c AddStatement.cpp -o AddStatement.o

SubtractStatement.o: SubtractStatement.cpp SubtractStatement.h
	g++ -Wall -g -c SubtractStatement.cpp -o SubtractStatement.o

MultiStatement.o: MultiStatement.cpp MultiStatement.h
	g++ -Wall -g -c MultiStatement.cpp -o MultiStatement.o

DivStatement.o: DivStatement.cpp DivStatement.h
	g++ -Wall -g -c DivStatement.cpp -o DivStatement.o

GoToStatement.o: GoToStatement.cpp GoToStatement.h
	g++ -Wall -g -c GoToStatement.cpp -o GoToStatement.o

IfStatement.o: IfStatement.cpp IfStatement.h
	g++ -Wall -g -c IfStatement.cpp -o IfStatement.o

GoSubStatement.o: GoSubStatement.cpp GoSubStatement.h
	g++ -Wall -g -c GoSubStatement.cpp -o GoSubStatement.o

ReturnStatement.o: ReturnStatement.cpp ReturnStatement.h
	g++ -Wall -g -c ReturnStatement.cpp -o ReturnStatement.o



stackint.o: stackint.cpp stackint.h
	g++ -Wall -g -c stackint.cpp -o stackint.o

llistint.o: llistint.cpp llistint.h
	g++ -Wall -g -c llistint.cpp -o llistint.o

facile: Statement.h ReturnStatement.o GoSubStatement.o IfStatement.o GoToStatement.o DivStatement.o MultiStatement.o SubtractStatement.o AddStatement.o PrintAllStatement.o PrintStatement.o ProgramState.o LetStatement.o EndStatement.o llistint.o stackint.o Facile.cpp 
	g++ -Wall -g ReturnStatement.o GoSubStatement.o IfStatement.o GoToStatement.o DivStatement.o MultiStatement.o SubtractStatement.o AddStatement.o PrintAllStatement.o PrintStatement.o llistint.o stackint.o ProgramState.o LetStatement.o EndStatement.o Facile.cpp -o facile

