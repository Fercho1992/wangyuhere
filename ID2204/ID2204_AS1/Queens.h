#pragma once
#include <gecode/int.hh>
#include <gecode/search.hh>
#include <gecode/minimodel.hh>

class Queens : public Space
{
protected:
	int size;
	IntVarArray l;
public:

	Queens(const int n, IntVarBranch op=INT_VAR_SIZE_MIN): l(*this, n*n, 0, 1)
	{
		size = n;
		Matrix<IntVarArray> m(l, n, n);
		// Constraints for rows and columns
		const int s = 1;
		for (int i=0; i<n; i++) 
		{
			post(*this, sum(m.row(i)) == s);
			post(*this, sum(m.col(i)) == s);
		}
		
		// for all the diagonals
		for (int j=2; j<=n; j++) {
			IntVarArgs d1y(j);
			IntVarArgs d2y(j);
			IntVarArgs d3y(j);
			IntVarArgs d4y(j);
			for (int i = j;i--;) {
				d1y[i] = m(j-i-1,i);
				d2y[i] = m(n+i-j, i);
				d3y[i] = m(j-i-1,n-1-i);
				d4y[i] = m(n+i-j, n-1-i);
			}
			post(*this, sum(d1y) <= s);
			post(*this, sum(d2y) <= s);
			if(j!=n) post(*this, sum(d3y) <= s);
			if(j!=n) post(*this, sum(d4y) <= s);
		}

		branch(*this, l, op, INT_VAL_MIN);
	}

	Queens(bool share, Queens& s) : Space(share, s) 
	{
		size = s.size;
		l.update(*this, share, s.l);
	}

	~Queens(void)
	{
	}

	virtual Space*
		copy(bool share) {
			return new Queens(share,*this);
	}

	/// Print solution
	virtual void
		print(std::ostream& os) const {
			os << "queens\t";
			for (int i = 0; i < l.size(); i++) {

				os << l[i] << " ";
				if ((i+1) % size == 0)
					os << std::endl << "\t";
			}
			os << std::endl;
	}
};
