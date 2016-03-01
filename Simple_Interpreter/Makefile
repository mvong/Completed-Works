all: bin/facile

bin/ProgramState.o: ProgramState.cpp ProgramState.h bin/.dirstamp 
	g++ -Wall -g -c ProgramState.cpp -o bin/ProgramState.o

bin/LetStatement.o: LetStatement.cpp LetStatement.h bin/.dirstamp 
	g++ -Wall -g -c LetStatement.cpp -o bin/LetStatement.o

bin/EndStatement.o: EndStatement.cpp EndStatement.h bin/.dirstamp 
	g++ -Wall -g -c EndStatement.cpp -o bin/EndStatement.o

bin/PrintStatement.o: PrintStatement.cpp PrintStatement.h bin/.dirstamp 
	g++ -Wall -g -c PrintStatement.cpp -o bin/PrintStatement.o

bin/PrintAllStatement.o: PrintAllStatement.cpp PrintAllStatement.h bin/.dirstamp 
	g++ -Wall -g -c PrintAllStatement.cpp -o bin/PrintAllStatement.o

bin/AddStatement.o: AddStatement.cpp AddStatement.h bin/.dirstamp 
	g++ -Wall -g -c AddStatement.cpp -o bin/AddStatement.o

bin/SubtractStatement.o: SubtractStatement.cpp SubtractStatement.h bin/.dirstamp 
	g++ -Wall -g -c SubtractStatement.cpp -o bin/SubtractStatement.o

bin/MultiStatement.o: MultiStatement.cpp MultiStatement.h bin/.dirstamp 
	g++ -Wall -g -c MultiStatement.cpp -o bin/MultiStatement.o

bin/DivStatement.o: DivStatement.cpp DivStatement.h bin/.dirstamp 
	g++ -Wall -g -c DivStatement.cpp -o bin/DivStatement.o

bin/GoToStatement.o: GoToStatement.cpp GoToStatement.h bin/.dirstamp 
	g++ -Wall -g -c GoToStatement.cpp -o bin/GoToStatement.o

bin/IfStatement.o: IfStatement.cpp IfStatement.h bin/.dirstamp 
	g++ -Wall -g -c IfStatement.cpp -o bin/IfStatement.o

bin/GoSubStatement.o: GoSubStatement.cpp GoSubStatement.h bin/.dirstamp 
	g++ -Wall -g -c GoSubStatement.cpp -o bin/GoSubStatement.o

bin/ReturnStatement.o: ReturnStatement.cpp ReturnStatement.h bin/.dirstamp 
	g++ -Wall -g -c ReturnStatement.cpp -o bin/ReturnStatement.o

bin/stackint.o: stackint.cpp stackint.h bin/.dirstamp 
	g++ -Wall -g -c stackint.cpp -o bin/stackint.o

bin/llistint.o: llistint.cpp llistint.h bin/.dirstamp 
	g++ -Wall -g -c llistint.cpp -o bin/llistint.o

bin/facile: Statement.h bin/ReturnStatement.o bin/GoSubStatement.o bin/IfStatement.o bin/GoToStatement.o bin/DivStatement.o bin/MultiStatement.o bin/SubtractStatement.o bin/AddStatement.o bin/PrintAllStatement.o bin/PrintStatement.o bin/ProgramState.o bin/LetStatement.o bin/EndStatement.o bin/llistint.o bin/stackint.o Facile.cpp bin/.dirstamp 
	g++ -Wall -g bin/ReturnStatement.o bin/GoSubStatement.o bin/IfStatement.o bin/GoToStatement.o bin/DivStatement.o bin/MultiStatement.o bin/SubtractStatement.o bin/AddStatement.o bin/PrintAllStatement.o bin/PrintStatement.o bin/llistint.o bin/stackint.o bin/ProgramState.o bin/LetStatement.o bin/EndStatement.o Facile.cpp -o bin/facile

.PHONY: clean
clean:
	rm -rf bin

bin/.dirstamp:
	mkdir -p bin
	touch bin/.dirstamp