/* 
 * File:   main.cpp
 * Author: yuwang
 *
 * Created on May 5, 2010, 12:34 AM
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <gecode/int.hh>
#include <gecode/search.hh>
#include <gecode/minimodel.hh>

#include "nooverlap.h"

using namespace Gecode;

class SquarePacking : public Space {
protected:
    IntVarArray x;
    IntVarArray y;
    IntVar size;
public:

    SquarePacking(const int n, bool use_nooverlap = false) : x(*this, n, 0, n*(n + 1) / 2 - 1),
    y(*this, n, 0, n*(n + 1) / 2 - 1),
    size(*this, sqrt(n * (n + 1)*(2 * n + 1) / 6), n*(n + 1) / 2) {

        int w = n * (n + 1) / 2;

        // Restrict position according to square size
        for (int i = 0; i < n - 1; i++) {
            post(*this, x[i] <= size - (n - i));
            post(*this, y[i] <= size - (n - i));
        }

        if (use_nooverlap) {
            IntVarArgs xva(n), yva(n);
            IntArgs wa(n), ha(n);
            for (int i = 0; i < n; i++) {
                xva[i] = x[i];
                yva[i] = y[i];
                wa[i] = n - i;
                ha[i] = n - i;
            }
            nooverlap(*this, xva, wa, yva, ha);
        } else {
            // Squares do not overlap
            for (int i = 0; i < n; i++)
                for (int j = i + 1; j < n; j++)
                    post(*this, tt((x[i] + (n - i) <= x[j]) || (x[j] + (n - j) <= x[i]) ||
                        (y[i] + (n - i) <= y[j]) || (y[j] + (n - j) <= y[i])));
        }

        // the sum of the sizes of the squares occuping space at column x or row y must be less than or equal to size.
        IntArgs sa(n);
        for (int i = 0; i < n; i++) {
            sa[i] = n - i;
        }
        BoolVarArgs b(n);
        for (int cx = 0; cx < w; cx++) {
            for (int i = 0; i < n; i++) {
                b[i].init(*this, 0, 1);
                dom(*this, x[i], cx - (n - i) + 1, cx, b[i]);
            }
            linear(*this, sa, b, IRT_LQ, size);
        }

        for (int cy = 0; cy < w; cy++) {
            for (int i = 0; i < n; i++) {
                b[i].init(*this, 0, 1);
                dom(*this, y[i], cy - (n - i) + 1, cy, b[i]);
            }
            linear(*this, sa, b, IRT_LQ, size);
        }

        // constrain 2.2
        post(*this, 2 * x[0] <= size - n);
        post(*this, 2 * y[0] <= size - n);

        // constrain 4.1
        int table_size[44];
        for (int i = 0; i < 44; i++) {
            table_size[i] = i + 2;
        }
        int table_generic[44];
        for (int i = 0; i < 44; i++) {
            if (table_size[i] == 2) {
                table_generic[i] = 1;
            } else if (table_size[i] == 3 || table_size[i] == 4) {
                table_generic[i] = 2;
            } else if (table_size[i] >= 5 && table_size[i] <= 8) {
                table_generic[i] = 3;
            } else if (table_size[i] >= 9 && table_size[i] <= 11) {
                table_generic[i] = 4;
            } else if (table_size[i] >= 12 && table_size[i] <= 17) {
                table_generic[i] = 5;
            } else if (table_size[i] >= 18 && table_size[i] <= 21) {
                table_generic[i] = 6;
            } else if (table_size[i] >= 22 && table_size[i] <= 29) {
                table_generic[i] = 7;
            } else if (table_size[i] >= 30 && table_size[i] <= 34) {
                table_generic[i] = 8;
            } else if (table_size[i] >= 35 && table_size[i] <= 44) {
                table_generic[i] = 9;
            } else if (table_size[i] == 45) {
                table_generic[i] = 10;
            }
        }

        for (int i = 0; i < 44; i++) {
            if (n >= table_size[i]) {
                post(*this, x[n - table_size[i]] != table_generic[i]);
                post(*this, y[n - table_size[i]] != table_generic[i]);
                if (table_size[i] == 34) {
                    post(*this, x[n - 34] != 9);
                    post(*this, y[n - 34] != 9);
                }
            }

        }

        // constrain 4.2
        post(*this, x[n - 1] == n * (n + 1) / 2 - 1);
        post(*this, y[n - 1] == n * (n + 1) / 2 - 1);

        branch(*this, size, INT_VAL_MIN);
        branch(*this, x, INT_VAR_SIZE_MIN, INT_VAL_MIN);
        branch(*this, y, INT_VAR_SIZE_MIN, INT_VAL_MIN);

    }

    SquarePacking(bool share, SquarePacking& s) : Space(share, s) {
        x.update(*this, share, s.x);
        y.update(*this, share, s.y);
        size.update(*this, share, s.size);

    }

    virtual Space* copy(bool share) {
        return new SquarePacking(share, *this);
    }

    void print(void) const {
        std::cout << "Size: " << size << std::endl;
        int n = x.size();
        for (int i = 0; i < n - 1; i++) {
            std::cout << "S" << n - i << ": " << x[i] << ", " << y[i];
            std::cout << std::endl;
        }
    }
};

void runSquarePacking(int n, bool use_nooverlap) {
    SquarePacking * sp = new SquarePacking(n, use_nooverlap);
    DFS<SquarePacking> e(sp);
    delete sp;
    if (SquarePacking * s = e.next()) {
        s->print();
        delete s;
    }
}

/*
 * 
 */
int main(int argc, char** argv) {

    std::cout<<"Using the Nooverlap propagator: "<<std::endl;
    runSquarePacking(10, true);
    std::cout<<"\nNot using the Nooverlap propagator: "<<std::endl;
    runSquarePacking(10, false);
    return (EXIT_SUCCESS);
}

