package Cards;

import java.util.*;

import Sigurd.Game;
import Sigurd.Room;
import Sigurd.BoardObjects.PlayerObject;
import Sigurd.BoardObjects.WeaponObject;

public class Deck {

	//only pass references to these cards, to have a card is to just have a reference to one of these elements
	private Map<String,PlayerCard> playerCards = new HashMap<String,PlayerCard>();
	private Map<String,RoomCard> roomCards = new HashMap<String,RoomCard>();
	private Map<String,WeaponCard> weaponCards = new HashMap<String,WeaponCard>();
	private Stack<Card> deck = new Stack<Card>();
	private Card[] envelope;
	
	private Random rand = new Random();
	
	public Deck(){
		FillCards();
		FillEnvelpoe();
		FillDeck();
		ShuffleDeck();
	}
	
	private void FillEnvelpoe() {
        envelope = new Card[3];
        envelope[0] = GetEnvelopeCard(playerCards);
        envelope[1] = GetEnvelopeCard(weaponCards);
        envelope[2] = GetEnvelopeCard(roomCards);
        
        for(Card c : envelope)
            c.isInEnvelope = true;
    }
	
	public Card[] GetEnvelope()
	{
	    return envelope;
	}
	
	private Card GetEnvelopeCard(Map<String, ? extends Card> map)
	{
	    int playerCardIndex = rand.nextInt(map.size());
	    Card randCard = null;
        for(Card c : map.values())
        {
            if(playerCardIndex-- == 0)
                randCard = c;
        }
        return randCard;
	}

    private void FillCards() {//TODO : fill in all the cards
		//i think this should be done by pulling from a centeral location where we define
		//all the players, rooms and weapons... so below is just test values

    	Collection<PlayerObject> characters = Game.GetAllCharcters();
    	Collection<WeaponObject> weapons = Game.GetAllWeapons();
    	Room[] rooms = Game.GetBoard().GetRooms();
    	
    	for(PlayerObject p : characters) {
    		playerCards.put(p.GetObjectName(), new PlayerCard(p.GetObjectName(),p));
    	}
    	
    	for(WeaponObject w : weapons) {
    		weaponCards.put(w.GetObjectName(), new WeaponCard(w.GetObjectName(),w));
    	}
    	
    	for(Room r : rooms) {
    		roomCards.put(r.GetName(), new RoomCard(r.GetName(),r));
    	}
	}
	
	private void FillDeck() {
		String ErrorPlaces = "";
		
		if(playerCards.isEmpty())
			ErrorPlaces += " Players";
			
		if(roomCards.isEmpty())
			ErrorPlaces += " Rooms";
		
		if(weaponCards.isEmpty())
			ErrorPlaces += " Weapons";
		
		if(ErrorPlaces.equals("") == false)
			throw new RuntimeException("No cards found for" + ErrorPlaces);
			
		for(Card c : playerCards.values()) {
		    if(c.isInEnvelope != true)
		        deck.push(c);
		}
		for(Card c : weaponCards.values()) {
            if(c.isInEnvelope != true)
                deck.push(c);
        }
		for(Card c : roomCards.values()) {
            if(c.isInEnvelope != true)
                deck.push(c);
        }
	}
	
	
	private void ShuffleDeck() {
		Card[] array = new Card[deck.size()];
		int i = 0;
		
		while(deck.isEmpty() == false) {
			array[i++] = deck.pop();
		}
		
			for(int j = 0; j < array.length; j++) {
				int num = rand.nextInt(array.length - j) + j;
				Card temp = array[num];
				array[num] = array[j];
				array[j] = temp;
			}
		
			for(int j = 0; j < array.length; j++) {
				deck.push(array[j]);
			}
	}
	
	
	public Card DrawCard() {
		if(IsEmpty() == false)
			return deck.pop();
		throw new IllegalStateException("No more cards");
	}
	
	
	public int Size() {
		return deck.size();
	}
	
	
	public boolean IsEmpty() {
		return deck.size() == 0;
	}
	
	
	public Card getCard(String name, Class<?> typeOfCard ) {
		Card temp;
		
		if(typeOfCard == PlayerCard.class) {//can't do swtich statment on Class<?>
			if((temp = playerCards.get(name)) != null)
				return temp;
		}
		else if(typeOfCard == RoomCard.class) {
			if((temp = roomCards.get(name)) != null)
				return temp;			
		}
		else if(typeOfCard == WeaponCard.class) {
			if((temp = weaponCards.get(name)) != null)
				return temp;			
		}
		else throw new RuntimeException("Tryed to get a card with an incorect type in deck");
		
		throw new RuntimeException("Item not found when retriving from deck");
	}
	
	@SuppressWarnings("unchecked")
	public Iterator<? extends Card> GetAllCards(Class<? extends Card> typeOfCard) {
		if(typeOfCard == PlayerCard.class) {//can't do swtich statment on Class<?>
			return  playerCards.values().iterator();
		}
		else if(typeOfCard == RoomCard.class) {
			return roomCards.values().iterator();		
		}
		else if(typeOfCard == WeaponCard.class) {
			return weaponCards.values().iterator();
		}
		else throw new RuntimeException("Tryed to get cards with an incorect type in deck"); 
	}
	
	
	public static void main(String[] args) {
		Deck deck = new Deck();

        System.out.println(deck.Size());
        System.out.println(deck.IsEmpty());
        System.out.println(deck.Size());
        System.out.println(deck.IsEmpty());
        System.out.println(deck.getCard("p2",PlayerCard.class).getName());
        
		while(!deck.IsEmpty())
	        System.out.println(deck.DrawCard().getName());
		
	}
}
