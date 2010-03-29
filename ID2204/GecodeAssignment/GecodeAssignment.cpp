// GecodeAssignment.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include "SendMoreMoney.h"
#include "SendMostMoney.h"
#include "Sudoku.h"

using namespace Gecode;

void runSendMoreMoney();
void runSendMostMoney();
void runSudoku(int i, IntConLevel option = ICL_DEF);

int _tmain(int argc, _TCHAR* argv[])
{
	runSendMoreMoney();	
	runSendMostMoney();
	const int size = sizeof(examples)/sizeof(examples[0]);
	for(int i = 0; i < size; i++) {
		std::cout<<"=====================================";
		runSudoku(i);
	}
	std::cout<<"\n============Using ICL_DEF============";
	runSudoku(0, ICL_DEF);
	std::cout<<"\n============Using ICL_VAL============";
	runSudoku(0, ICL_VAL);
	std::cout<<"\n============Using ICL_BND============";
	runSudoku(0, ICL_BND);
	std::cout<<"\n============Using ICL_DOM============";
	runSudoku(0, ICL_DOM);
	system("PAUSE");
	return 0;
}

void runSendMoreMoney() {
	std::cout<<"\nSend more money:\n";
	SendMoreMoney* m = new SendMoreMoney;
	DFS<SendMoreMoney> e(m);
	delete m;
	while (SendMoreMoney* s = e.next()) {
		s->print(); delete s;
	}
}

void runSendMostMoney() {
	std::cout<<"\nSend most money:\n";
	SendMostMoney* m = new SendMostMoney;
	BAB<SendMostMoney> e(m);
	delete m;
	while (SendMostMoney* s = e.next()) {
		s->print(); delete s;
	}
}

void runSudoku(int i, IntConLevel option) {
	std::cout<<"\nSudoku puzzle "<<i<<" :\n";
	for(int j = 0; j < 9; j++) 
	{
		for(int k = 0; k < 9; k++) 
		{
			std::cout<<examples[i][j][k]<<" ";
		}
		std::cout<<std::endl;
	}

	std::cout<<"\nSudoku answer "<<i<<" :\n";
	Sudoku* m = new Sudoku(&examples[i][0][0], option);
	DFS<Sudoku> e(m);
	delete m;
	while (Sudoku* s = e.next()) {
		std::cout<<"depth: "<<e.statistics().depth<<std::endl;
		std::cout<<"memory: "<<e.statistics().memory<<std::endl;
		std::cout<<"node: "<<e.statistics().node<<std::endl;
		s->print(); delete s;
	}
}
