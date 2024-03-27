package model.oefening2;

import model.oefening2.card.JackCardTest;
import model.oefening2.card.JokerCardTest;
import model.oefening2.card.NumberedCardTest;
import model.oefening2.deck.CardDeckTest;
import model.oefening2.deck.HelpDeckTest;
import model.oefening2.deck.SourceDeckTest;
import model.oefening2.deck.TargetDeckTest;
import org.junit.platform.suite.api.*;

@Suite
@SelectClasses( { CardDeckTest.class,
        HelpDeckTest.class, JackCardTest.class, JokerCardTest.class,
        NumberedCardTest.class, SourceDeckTest.class, TargetDeckTest.class })
public class AllTests {}
