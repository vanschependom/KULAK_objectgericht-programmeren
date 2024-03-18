package SelectedTopics.record;

public record RecordPerson(String name, int yearOfBirth) {
//public SelectedTopics.record RecordPerson(String name, int yearOfBirth, String zodiacSign) {

    //Instance fields are not allowed in records!
    //private String address;

    //Additional constructor
    /*
    public RecordPerson(String name, int yearOfBirth) {
       this(name, yearOfBirth, "Unknown");

        //Must call the canonical constructor via this(), the code below is not sufficient!
        //this.name = name;
        //this.yearOfBirth = yearOfBirth;
        //this.zodiacSign = "Unknown";
    }
     */


    //Can add additional instance or static methods
    public void printHello() {
        System.out.println("Hello from " + name);
    }

}
