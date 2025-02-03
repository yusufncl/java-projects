// A hand (list of cards)
// A bankroll (amount of money the player has)
// Methods for betting, receiving cards, calculating hand value, and deciding actions

public class Player {
    private String name;
    private int balance;
    private int bet;
    private Hand hand;
    
    public Player(String name, int balance){
        this.name = name;
        this.balance = balance;
        this.bet = 0;
        this.hand = new Hand();
    }

    public String getName(){
        return name;
    }

    public int getBalance(){
        return balance;
    }

    public int getBet(){
        return bet; 
    }
    
    public Hand getHand(){
        return hand;
    }

    public void setBet(int betAmount){
        this.bet = betAmount;
    }

    public void setName(String name){
        this.name = name;
    }

    public boolean placeBet(int bet) {
        if (bet <= 0) {
            System.out.println("Bet must be greater than 0.");
            return false;
        } 
        if (bet > balance) {
            System.out.println("Insufficient balance. Your balance is $" + balance + ".");
            return false;
        }

        this.bet = bet;
        return true;
    }
    

    public void addCard(Card card){
        hand.addCard(card);
    }

    public int getHandSize() {
        return hand.getCards().size(); 
    }
    

    public boolean canPlaceBet(int bet) {
        return bet > 0 && bet <= balance;
    }

    public void push(){
        bet = 0;
        System.out.println("Balance: " + balance);
    }


    public void refund(){
        balance+=bet;
        System.out.println("Balance: " + balance);

    }

    public boolean hasBlackjack() {
        return hand.getCards().size() == 2 && hand.calcTotal() == 21;
    }
    
    public void blackjack(){
        balance += bet * 1.5;
        bet = 0;
        System.out.println("Balance: " + balance);

    }

    public void win() {
        balance += bet;  
        bet = 0;  
        System.out.println("Balance: " + balance);
    }
    

    public void loss() {
        if (bet > balance) {
            bet = balance;
        }
        balance -= bet; 
        bet = 0; 
        if (balance < 0) {
            balance = 0; 
        }
        System.out.println("Balance: " + balance);
    }
    
    
    

    public void clearHand() {
        hand.clear();
    }

    
}
