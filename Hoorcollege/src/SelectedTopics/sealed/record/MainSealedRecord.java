package SelectedTopics.sealed.record;


public class MainSealedRecord {

    public static void main(String[] args) {
        CarRecord carRecord = new CarRecord("123456ABCDEF", 5);
        System.out.println("Registration number: " + carRecord.registrationNumber());
        System.out.println("Number of seats: " + carRecord.numberOfSeats());
        System.out.println("carRecord toString: " + carRecord.toString());
    }

}
