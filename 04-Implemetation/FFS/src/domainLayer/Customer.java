package domainLayer;

public class Customer extends Person {
	private String firstName;
	private String lastName;
	private String CPR;
	private boolean badStanding;

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
	public String getCPR() {
		return CPR;
	}
	public void setCPR(String cPR) {
		CPR = cPR;
	}
	public boolean getBadStanding() {
		return badStanding;
	}
	public void setBadStanding( boolean i ) {
		this.badStanding = i;
	}
	@Override
	public String toString() {
		return "Customer [firstName=" + firstName + ", lastName=" + lastName + ", badStanding=" + badStanding + ", toString()=" + super.toString() + "]";
	}
}
