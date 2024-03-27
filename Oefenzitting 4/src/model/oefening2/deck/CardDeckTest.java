package model.oefening2.deck;


import java.util.List;


import model.oefening2.card.Card;
import model.oefening2.card.JackCard;
import model.oefening2.card.JokerCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class CardDeckTest {

    @Test
    public void testMutatorPush_Legal() {
        SourceDeck myDeck = new SourceDeck(5);
        myDeck.push(new JokerCard());
        myDeck.push(new JokerCard());

        Card joker = new JokerCard();
        myDeck.push(joker);

        assertEquals(myDeck.getNbCards(), 3);
        assertEquals(myDeck.getTop(), joker);
    }

    @Test
    public void testMutatorPush_Illegal1(){
        assertThrows( IllegalStateException.class,() -> {
                SourceDeck myDeck = new SourceDeck(5);
        myDeck.push(null);
        });
    }

    @Test
    public void testMutatorPush_Illegal2() {
        assertThrows( IllegalStateException.class,() -> {
            SourceDeck myDeck = new SourceDeck(0);
            myDeck.push(new JokerCard());
        });
    }

    @Test
    public void testMutatorPop_Legal1() {
        CardDeck myDeck = new SourceDeck(5);
        myDeck.push(new JokerCard());
        Card joker = new JokerCard();
        myDeck.push(joker);
        myDeck.push(new JokerCard());
        myDeck.pop();

        assertEquals(myDeck.getNbCards(), 2);
        assertEquals(myDeck.getTop(), joker);
    }

    @Test
    public void testMutatorPop_Legal2() {
        CardDeck myDeck = new SourceDeck(5);
        myDeck.push(new JokerCard());
        myDeck.pop();

        assertEquals(myDeck.getNbCards(), 0);
    }

    @Test
    public void testMutatorPop_Illegal1() {
        assertThrows( IllegalStateException.class,() -> {
            CardDeck myDeck = new SourceDeck(5);
            myDeck.pop();
        });
    }

    @Test
    public void testInspectorHasMaximalSize_Legal1() {
        CardDeck deck = new SourceDeck(0);
        assertTrue(deck.hasMaximalSize());
    }

    @Test
    public void testInspectorHasMaximalSize_Legal2() {
        CardDeck deck = new SourceDeck(2);
        assertFalse(deck.hasMaximalSize());
    }

    @Test
    public void testInspectorGetCardAt_Illegal1() {
        assertThrows( IndexOutOfBoundsException.class,() -> {
            CardDeck deck = new SourceDeck(5);
            deck.getCardAt(0);
        });
    }

    @Test
    public void testInspectorGetCardAt_Illegal2() {
        assertThrows( IndexOutOfBoundsException.class,() -> {
            CardDeck deck = new SourceDeck(5);
            deck.getCardAt(1);
        });
    }

    @Test
    public void testInspectorGetTop_Legal() {
        CardDeck deck = new SourceDeck(5);
        deck.push(new JokerCard());
        Card joker = new JokerCard();
        deck.push(joker);

        assertEquals(deck.getTop(), joker);
    }

    @Test
    public void testInspectorGetTop_Illegal()  {
        assertThrows( IllegalStateException.class,() -> {
            CardDeck deck = new SourceDeck(5);
            deck.getTop();
        });
    }

    @Test
    public void testInspectorContains_Legal1() {
        CardDeck deck = new SourceDeck(2);
        deck.addCard(new JackCard());
        Card myCard = new JokerCard();
        deck.addCard(myCard);

        assertTrue(deck.contains(myCard));
    }

    @Test
    public void testInspectorContains_Legal2() {
        CardDeck deck = new SourceDeck(5);
        Card myCard = new JokerCard();
        deck.addCard(myCard);

        assertTrue(deck.contains(myCard));
    }

    @Test
    public void testInspectorContains_Illegal1() {
        CardDeck deck = new SourceDeck(5);
        deck.addCard(new JokerCard());
        deck.addCard(new JackCard());

        assertFalse(deck.contains(new JackCard()));
    }

    @Test
    public void testInspectorCanHaveAsTop_Legal() {
        CardDeck myDeck = new SourceDeck(2);
        myDeck.push(new JackCard());

        assertTrue(myDeck.canHaveAsTop(new JokerCard()));
    }

    @Test
    public void testInspectorCanHaveAsTop_Illegal1() {
        CardDeck myDeck = new SourceDeck(2);
        assertFalse(myDeck.canHaveAsTop(null));
    }

    @Test
    public void testInspectorCanHaveAsTop_Illegal2() {
        CardDeck myDeck = new SourceDeck(5);
        Card myCard = new JokerCard();
        myDeck.push(myCard);
        myDeck.push(new JackCard());

        assertFalse(myDeck.canHaveAsTop(myCard));
    }

    @Test
    public void testInspectorCanHaveAsTop_Illegal3() {
        CardDeck myDeck = new SourceDeck(1);
        myDeck.push(new JokerCard());

        assertFalse(myDeck.canHaveAsTop(new JokerCard()));
    }

    @Test
    public void testInspectorGetCards_Legal() {
        CardDeck deck = new SourceDeck(5);
        Card[] pack = new Card[3];

        for (int i = 0; i < pack.length; i++) {
            pack[i] = new JokerCard();
            deck.push(pack[i]);
        }

        List<Card> result = deck.getCards();
        assertEquals(result.size(), deck.getNbCards());

        for (int i = 0; i < result.size(); i++) {
            assertEquals(result.get(i), pack[i]);
        }
    }


    @Test
    public void testMutatorMoveTop_Legal() {
        CardDeck source = new SourceDeck(5);
        Card joker = new JokerCard();
        source.push(joker);

        TargetDeck target = new TargetDeck(2, new JokerCard());
        source.moveTop(target);

        assertEquals(source.getNbCards(), 0);
        assertEquals(target.getNbCards(), 2);
        assertEquals(target.getTop(), joker);
    }

    @Test
    public void testMutatorMoveTop_Illegal1() {
        assertThrows( IllegalStateException.class,() -> {
            SourceDeck source = new SourceDeck(5);
            source.push(new JokerCard());
            source.moveTop((TargetDeck) null);
        });
    }

    @Test
    public void testMutatorMoveTop_Illegal2() {
        assertThrows( IllegalStateException.class,() -> {
            SourceDeck source = new SourceDeck(5);
            source.push(new JackCard());
            TargetDeck target = new TargetDeck(5, new JackCard());
            source.moveTop(target);
        });
    }

    @Test
    public void testMutatorMoveTop_Illegal3() {
        assertThrows( IllegalStateException.class,() -> {
            CardDeck source = new SourceDeck(5);
            TargetDeck target = new TargetDeck(2, new JokerCard());
            source.moveTop(target);
        });
    }

    @Test
    public void testMutatorMoveTop_Illegal4() {
        assertThrows( IllegalStateException.class,() -> {
            CardDeck source = new SourceDeck(5);
            source.push(new JokerCard());
            TargetDeck target = new TargetDeck(1, new JokerCard());
            source.moveTop(target);
        });
    }

    @Test
    public void testInspectorHasMinimalSize_Legal1() {
        CardDeck deck = new SourceDeck(4);
        assertTrue(deck.hasMinimalSize());
    }

    @Test
    public void testInspectorHasMinimalSize_Legal2() {
        CardDeck deck = new SourceDeck(2);
        deck.addCard(new JackCard());

        assertFalse(deck.hasMinimalSize());
    }
}
