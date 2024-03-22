package model.oefening1;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;


import model.oefening1.ownings.*;
import model.oefening1.persons.PersonTest;

@Suite
@SelectClasses( { OwnableTest.class, DogTest.class,
	PaintingTest.class, CarTest.class, PersonTest.class})
public class AllTests {}
