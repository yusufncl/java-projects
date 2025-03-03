// Storing the cards in the hand
// Calculating the total hand value (handling Aces as 1 or 11)
// Checking for Blackjack and busts
// Managing the hand for new rounds

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> cards;
    protected Object card;

    public Hand() {
        cards = new ArrayList<Card>();
    }

    public ArrayList<Card> getCards() {
        return cards;
    }    

    public int getHandSize() {
        return cards.size();
    }

    public void addCard(Card card){
        cards.add(card);
    }

    public Card getFirstCard(){
        if(!cards.isEmpty()){
            return cards.get(0);
        } else {
            return null;
        }
    }

    public int calcTotal(){
        int total = 0;
        int numberOfAces = 0;

        for(int i = 0; i < cards.size(); i++){
            Card currentCard = cards.get(i);
            total += currentCard.getValue();
            if (currentCard.getRank().equals("A")){
                numberOfAces++;
            }
        }

        while (total > 21 && numberOfAces > 0) {
            total-=10;
            numberOfAces--;
        }

        return total;
    }

    public boolean isBlackjack(){
        if(calcTotal()==21 && cards.size() == 2){
            return true;
        } else {
            return false;
        }
    }   

    public boolean isBusted(){
        if(calcTotal()>21){
            return true;
        } else {
            return false;
        }
    }
    
    public void clear(){
        cards.clear();
    }


    public String toString() {
        return cards.toString();  
    }
    
}
