package SelectedTopics.nested;


public class OuterClass {

    private String outerField = "Outer field";
    private static String staticOuterField = "Static outer field";

    private static void printTestStatic() {
        System.out.println("Test static");
    }

    private void printHelloNonStatic() {
        System.out.println("Hello non-static");
    }

    private class InnerClass {
        private int number = 13;

        private void accessMembers() {
            System.out.println(outerField);
            System.out.println(staticOuterField);
            printTestStatic();
            printHelloNonStatic();
        }
    }

    private static class StaticNestedClass {
        private void accessMembers(OuterClass outer) {
            // Compiler error: Cannot make a static reference to the non-static
            //     field outerField
            // System.out.println(outerField);
            System.out.println(outer.outerField);
            System.out.println(staticOuterField);
            printTestStatic();
            //printHelloNonStatic(); //Compiler error
            outer.printHelloNonStatic();
        }

        private static void printTest() {
            System.out.println("Printing test");
        }
    }

    public static void main(String[] args) {
        System.out.println("Inner class:");
        System.out.println("------------");
        OuterClass outerObject = new OuterClass();
        OuterClass.InnerClass innerObject = outerObject.new InnerClass();
        innerObject.accessMembers();

        System.out.println(innerObject.number);

        System.out.println("\nStatic SelectedTopics.nested class:");
        System.out.println("--------------------");
        StaticNestedClass staticNestedObject = new StaticNestedClass();
        staticNestedObject.accessMembers(outerObject);

        StaticNestedClass.printTest();

    }
}