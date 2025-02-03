public class Dealer {

    private Hand hand;


    public Dealer(){
        this.hand = new Hand();
    }

    public Hand getHand(){
        return hand;
    }

    public void addCard(Card card){
        hand.addCard(card);
    }

    public int getHandTotal(){
        return hand.calcTotal();
    }

    public boolean isBusted(){
        return hand.isBusted();
    }

    public boolean isBlackjack(){
        return hand.isBlackjack();
    }

    public void playTurn(Deck deck){
        while(hand.calcTotal() < 17){
            hand.addCard(deck.dealCard());
        }
    }

    public void revealFirstCard(){
        System.out.println("The first card is: " + hand.getFirstCard());
    }

    public void revealHand(){
        System.out.println("The dealer's hand is "+ hand);
        System.out.println("The dealer's total: " + hand.calcTotal());
    }

    public void clearHand() {
        hand.clear();
    }
    
}
