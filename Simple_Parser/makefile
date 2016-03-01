all: llisttest, stackint

llistint.o: llistint.cpp llistint.h
	g++ -Wall -g -c llistint.cpp -o llistint.o

parse: stackint.o llistint.o parse.cpp
	g++ -Wall -g stackint.o llistint.o parse.cpp -o parse

stackint.o: stackint.cpp stackint.h
	g++ -Wall -g -c stackint.cpp -o stackint.o

llisttest: llisttest.cpp llistint.o
	g++ -Wall -g llisttest.cpp llistint.o -I /usr/local/opt/gtest/include/ -l gtest -l gtest_main -pthread -o $@

.PHONY: tests

tests: llisttest
	./llisttest
	-@echo "---All Tested!---"