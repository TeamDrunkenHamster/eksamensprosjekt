package domainLayer;

import java.util.ArrayList;

public class Person {
  private int id;
  private ArrayList<LoanOffer> offers = new ArrayList<LoanOffer>();
  
  public int getId() {
    return id;
  }

  public void setId( int id ) {
    this.id = id;
  }

  public ArrayList<LoanOffer> getOffers() {
    return offers;
  }

  @Override
  public String toString() {
    return "Person [id=" + id + ", offers=" + offers + "]";
  }

}
