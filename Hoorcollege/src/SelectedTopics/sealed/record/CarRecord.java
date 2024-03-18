package SelectedTopics.sealed.record;

//No need to declare final since Records are implicitly final!
public record CarRecord(String registrationNumber, int numberOfSeats) implements VehicleInterface {


    //Alternative:

    //@Override
    //public String getRegistrationNumber() {
      //  return registrationNumber;
    //}

    //public int getNumberOfSeats() {
      //  return numberOfSeats;
    //}

}
