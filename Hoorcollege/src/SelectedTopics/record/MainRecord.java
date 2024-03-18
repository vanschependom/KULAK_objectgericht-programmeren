package SelectedTopics.record;

public class MainRecord {

    public static void main(String[] args) {
        Person person = new Person("Alex", 2000);

        //Default constructor not allowed
        //Person pers = new Person();

        System.out.println("Person name: " + person.getName());
        System.out.println("Person toString: " + person);


        RecordPerson recordPerson = new RecordPerson("Barbara", 2001);
        System.out.println("Recordperson name: " + recordPerson.name());
        System.out.println("Recordperson toString: " + recordPerson);
        recordPerson.printHello();


        /*
        RecordPerson recordPerson2 = new RecordPerson("Carol", 2002, "Aquarius");
        System.out.println("Recordperson2 name: " + recordPerson2.name());
        System.out.println("Recordperson2 toString: " + recordPerson2);
        */

    }
}
