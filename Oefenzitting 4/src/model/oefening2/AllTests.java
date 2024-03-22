package model.oefening2;

import card.JackCardTest;
import card.JokerCardTest;
import card.NumberedCardTest;
import deck.CardDeckTest;
import deck.HelpDeckTest;
import deck.SourceDeckTest;
import deck.TargetDeckTest;
import org.junit.platform.suite.api.*;

@Suite
@SelectClasses( { CardDeckTest.class,
        HelpDeckTest.class, JackCardTest.class, JokerCardTest.class,
        NumberedCardTest.class, SourceDeckTest.class, TargetDeckTest.class })
public class AllTests {}
