import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Deck {

    private ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<Card>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

        HashMap<String, Integer> values = new HashMap<>();  
        for(int i = 2; i<=10; i++) {
            values.put(String.valueOf(i), i);
        }
        values.put("Jack", 10);
        values.put("Queen", 10);
        values.put("King", 10);
        values.put("Ace", 11);

        for(int i = 0; i < suits.length; i++){
            for(int j =0; j < ranks.length; j++) {
                cards.add(new Card(ranks[j], suits[i], values.get(ranks[j])));
            }
        }
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }

    public Card dealCard(){
        if(!cards.isEmpty()){
            return cards.remove(0);
        } else {
            System.out.println("The deck is empty!");
            return null;
        }
    }
}
