/* 
 * File:   main.cpp
 * Author: yuwang
 *
 * Created on May 5, 2010, 12:34 AM
 */

#include <stdlib.h>
#include <gecode/int.hh>
#include <gecode/search.hh>
#include <gecode/minimodel.hh>

using namespace Gecode;

class SquarePacking : public Space {
protected:
    IntVarArray x;
    IntVarArray y;
    IntVar size;
public:

    SquarePacking(const int n) : x(*this, n, 1, n*(n - 1) / 2), y(*this, n, 1, n*(n - 1) / 2), size(*this, 1, n*(n + 1) / 2) {
        // Restrict position according to square size
        for (int i = 0; i < n; i++) {
            rel(*this, x[i], IRT_LQ, size - (n - i));
            rel(*this, y[i], IRT_LQ, size - (n - i));
        }

        // Squares do not overlap
        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++)
                post(*this, tt((x[i] + (n-i) <= x[j]) || (x[j] + (n-j) <= x[i]) ||
                    (y[i] + (n-i) <= y[j]) || (y[j] + (n-j) <= y[i])));
    }
};

/*
 * 
 */
int main(int argc, char** argv) {

    return (EXIT_SUCCESS);
}

