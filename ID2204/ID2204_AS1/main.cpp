/* 
 * File:   main.cpp
 * Author: yuwang
 *
 * Created on May 4, 2010, 8:57 PM
 */

#include <stdlib.h>
#include <stdio.h>
#include "Sudoku.h"
#include "Queens.h"

using namespace Gecode;

void runSudoku(int i, IntConLevel option = ICL_DEF);
void runQueens(int n, IntVarBranch op = INT_VAR_SIZE_MIN);
void runQueensGist(int n);

/*
 * 
 */
int main(int argc, char** argv) {

    const int size = sizeof (examples) / sizeof (examples[0]);
    for (int i = 0; i < size; i++) {
        std::cout << "=====================================";
        runSudoku(i);
    }
    std::cout << "\n============Using ICL_DEF============";
    runSudoku(5, ICL_DEF);
    std::cout << "\n============Using ICL_VAL============";
    runSudoku(5, ICL_VAL);
    std::cout << "\n============Using ICL_BND============";
    runSudoku(5, ICL_BND);
    std::cout << "\n============Using ICL_DOM============";
    runSudoku(5, ICL_DOM);

    std::cout << "\n8 Queens solutions:\n";
    runQueens(8);
    std::cout << "\n8 Queens using INT_VAR_SIZE_MAX\n";
    runQueens(8, INT_VAR_SIZE_MAX);

    return (EXIT_SUCCESS);
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

void runQueens(int n, IntVarBranch op) {
	Queens* q = new Queens(n, op);
	DFS<Queens> e(q);
	delete q;
	int i = 0;
	while(Queens* qs = e.next()) {
		i++;
		std::cout<<"depth: "<<e.statistics().depth<<std::endl;
		std::cout<<"memory: "<<e.statistics().memory<<std::endl;
		std::cout<<"node: "<<e.statistics().node<<std::endl;
		qs->print(std::cout);
		delete qs;
	}
	std::cout<<"Number of solution "<<i<<std::endl;
}
