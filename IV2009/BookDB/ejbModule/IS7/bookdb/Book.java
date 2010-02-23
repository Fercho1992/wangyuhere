package IS7.bookdb;

public class Book implements java.io.Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7236801713718167322L;

	public Book()
    {
    }

    public Book(int booknr, String title, int price, int year)
    {
        this.booknr = booknr;
        this.title = title;
        this.price = price;
        this.year = year;
    }
    /** *private variable for the BookNr of this Book object** */
    private int booknr;
    /** *private variable for the Title of this Book object** */
    private String title;
    /** *private variable for the Price of this Book object** */
    private int price;
    /** *private variable for the Year of this Book object** */
    private int year;

    /** *Returns the BookNr of this Book object** */
    public int getBooknr()
    {
        return booknr;
    }

    /** *Returns the Title of this Book object** */
    public String getTitle()
    {
        return title;
    }

    /** *Returns the Price of this Book object** */
    public int getPrice()
    {
        return price;
    }

    /** *Returns the Year of this Book object** */
    public int getYear()
    {
        return year;
    }

    /** *Sets the BookNr for this Book object** */
    public void setBooknr(int value)
    {
        booknr = value;
    }

    /** *Sets the Title for this Book object** */
    public void setTitle(String value)
    {
        title = value;
    }

    /** *Sets the Price for this Book object** */
    public void setPrice(int value)
    {
        price = value;
    }

    /** *Sets the Year for this Book object** */
    public void setYear(int value)
    {
        year = value;
    }
}
