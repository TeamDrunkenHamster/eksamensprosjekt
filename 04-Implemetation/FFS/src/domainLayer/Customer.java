package domainLayer;

public class Customer extends Person {
  private String firstName;
  private String lastName;
  private int badStanding;
  
  public String getFirstName() {
    return firstName;
  }
  public void setFirstName( String firstName ) {
    this.firstName = firstName;
  }
  public String getLastName() {
    return lastName;
  }
  public void setLastName( String lastName ) {
    this.lastName = lastName;
  }
  public int getBadStanding() {
    return badStanding;
  }
  public void setBadStanding( int badStanding ) {
    this.badStanding = badStanding;
  }
  @Override
  public String toString() {
    return "Customer [firstName=" + firstName + ", lastName=" + lastName + ", badStanding=" + badStanding + ", toString()=" + super.toString() + "]";
  }

  
}
