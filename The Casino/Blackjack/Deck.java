import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Deck {

    private ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<Card>();

        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] suits = {"C", "D", "H", "S"};

        HashMap<String, Integer> values = new HashMap<>();  
        for(int i = 2; i<=10; i++) {
            values.put(String.valueOf(i), i);
        }
        values.put("J", 10);
        values.put("Q", 10);
        values.put("K", 10);
        values.put("A", 11);

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
