package SelectedTopics.sealed.record;

public sealed interface VehicleInterface permits CarRecord {

    //public String getRegistrationNumber();
    public String registrationNumber();

}
