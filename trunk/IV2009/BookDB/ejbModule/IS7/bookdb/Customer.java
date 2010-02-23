package IS7.bookdb;

public class Customer implements java.io.Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3560404160156599873L;

	public Customer()
    {
    }

    public Customer(int customernr, String name, String address, String city,
            String country)
    {
        this.customernr = customernr;
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
    }
    /** *private variable for the CustomerNr of this Customer object** */
    private int customernr;
    /** *private variable for the Name of this Customer object** */
    private String name;
    /** *private variable for the Address of this Customer object** */
    private String address;
    /** *private variable for the City of this Customer object** */
    private String city;
    /** *private variable for the Country of this Customer object** */
    private String country;

    /** *Returns the CustomerNr of this Customer object** */
    public int getCustomernr()
    {
        return customernr;
    }

    /** *Returns the Name of this Customer object** */
    public String getName()
    {
        return name;
    }

    /** *Returns the Address of this Customer object** */
    public String getAddress()
    {
        return address;
    }

    /** *Returns the City of this Customer object** */
    public String getCity()
    {
        return city;
    }

    /** *Returns the Country of this Customer object** */
    public String getCountry()
    {
        return country;
    }

    /** *Sets the CustomerNr for this Customer object** */
    public void setCustomernr(int value)
    {
        customernr = value;
    }

    /** *Sets the Name for this Customer object** */
    public void setName(String value)
    {
        name = value;
    }

    /** *Sets the Address for this Customer object** */
    public void setAddress(String value)
    {
        address = value;
    }

    /** *Sets the City for this Customer object** */
    public void setCity(String value)
    {
        city = value;
    }

    /** *Sets the Country for this Customer object** */
    public void setCountry(String value)
    {
        country = value;
    }
}