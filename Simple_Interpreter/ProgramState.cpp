#include <iostream>
#include "ProgramState.h"

using namespace std;

ProgramState::ProgramState(int numLines) {
	m_numLines = 0;
	counter = 1;
	m_numLines = numLines;
	items = new map<string, int>(); 
}

ProgramState::~ProgramState() {
	items->clear();
	delete items;
}

int ProgramState::getC() {
	return counter;
}

int ProgramState::getnumLines() {
	return m_numLines;
}

StackInt ProgramState::getStack() {
	return stack;
}

void ProgramState::setC(int C) {
	counter = C;
}

void ProgramState::setnumLines(int L) {
	m_numLines = L;
}

void ProgramState::setStack(StackInt tempS) {
	stack = tempS;
}

map<string, int>* ProgramState::getMap() {
	return items;
}

