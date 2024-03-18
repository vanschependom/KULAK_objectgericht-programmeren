package SelectedTopics.enumeration;

public class MainEnum {

    public static void printDayInfo(Day day) {
        switch (day) {
            case MONDAY:
                System.out.println("Mondays are bad.");
                break;

            case FRIDAY:
                System.out.println("Fridays are better.");
                break;

            case SATURDAY: case SUNDAY:
                System.out.println("Weekends are best.");
                break;

            default:
                System.out.println("Midweek days are so-so.");
                break;
        }
    }


    public static void main(String[] args) {

        Day today = Day.MONDAY;
        //enums are type-safe
        //Day otherDay = "banana"; //Compiler error!
        //Day otherDay = "MONDAY"; //Compiler error!
        //Day otherDay = Month.FEBRUARY; //Compiler error!

        printDayInfo(today);


        Planet mars = Planet.MARS;
        System.out.println(mars);
        System.out.println(mars.getDescription() + " has radius " + mars.getRadius());
        System.out.println(Planet.EARTH.getRadius());


    }
}
