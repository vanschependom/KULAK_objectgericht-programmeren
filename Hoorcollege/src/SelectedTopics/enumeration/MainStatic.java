package SelectedTopics.enumeration;

public class MainStatic {

    public static final String MONDAY = "Monday";
    public static final String TUESDAY = "Tuesday";
    //etc.

    public static void main(String[] args) {
        String today = MONDAY;

        //Problem: can put any string in variable "today"
        today = "banana";
    }

}

