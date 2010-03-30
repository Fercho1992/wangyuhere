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

	Queens(const int n): l(*this, n*n, 0, 1)
	{
		size = n;
		Matrix<IntVarArray> m(l, n, n);
		// Constraints for rows and columns
		const int s = 1;
		for (int i=0; i<n; i++) 
		{
			linear(*this, m.row(i), IRT_EQ, s);
			linear(*this, m.col(i), IRT_EQ, s);
		}
		
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
			linear(*this, d1y, IRT_LQ, s);
			linear(*this, d2y, IRT_LQ, s);
			linear(*this, d3y, IRT_LQ, s);
			linear(*this, d4y, IRT_LQ, s);
		}

		branch(*this, l, INT_VAR_SIZE_MIN, INT_VAL_MIN);
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
