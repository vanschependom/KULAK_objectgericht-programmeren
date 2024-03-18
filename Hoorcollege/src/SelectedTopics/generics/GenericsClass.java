package SelectedTopics.generics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class GenericsClass {

    //Note: Number is a superclass of Integer, Double, Long,...
    public static double sum(List<? extends Number> numberList) {
        double sum = 0.0;
        //Now we know that elements of the list will be a Number
        for (Number n : numberList) sum += n.doubleValue();
        //for (Number n : numberList) sum += n.intValue(); //intValue() takes floor of Doubles
        return sum;
    }

    //Note: Number is a superclass of Integer, Double, Long,...
    //Alternative using type parameter instead of wildcard
    public static <T extends Number> double sum2(List<T> numberList) {
        double sum = 0.0;
        //Now we know that elements of the list will be a Number
        for (Number n : numberList) sum += n.doubleValue();
        return sum;
    }


    public static void print(List<? super Integer> list) {
        //for(Integer integer : list) // Error
        //for(Object obj : list) //OK
        for(int i = 0; i < list.size(); i++)
            System.out.println(list.get(i));
    }

    public static <S extends Number,T> void genericMethod(Collection<S> col, Collection<T> col2) {
        System.out.println("Printing first collection");
        System.out.println(col);
        System.out.println("Printing second collection");
        System.out.println(col2);
    }

    public static void main(String args[]) {
        List<Integer> integerList = Arrays.asList(1, 2, 3);
        System.out.println("sum = " + sum(integerList));

        List<Double> doubleList = Arrays.asList(1.2, 2.3, 3.5);
        System.out.println("sum = " + sum(doubleList));


        System.out.println("Printing integerList");
        print(integerList);

/*
        //print(doubleList); //Not allowed!
        List<Number> numberList = new ArrayList<>();
        numberList.add(3.14);
        numberList.add(15);
        System.out.println("Printing numberlist");
        print(numberList); //Is ok
*/

/*
        //List<Integer> integerList = Arrays.asList(1, 2, 3);
        //List<Double> doubleList = Arrays.asList(1.2, 2.3, 3.5);
        genericMethod(integerList, doubleList);

        List<String> stringList = new ArrayList<>();
        stringList.add("hi");
        //genericMethod(stringList, doubleList); //not accepted since String is not a subclass of Number!
*/
        //sum2(doubleList);
    }


}
