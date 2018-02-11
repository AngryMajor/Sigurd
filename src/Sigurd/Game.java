package Sigurd;

import java.awt.*;

import javax.swing.*;
import java.util.*;

import Sigurd.BoardObjects.*;

/**
 * Servers an an entry point and ties the different classes together.
 * @author Peter Major
 * Team: Sigurd
 */
public class Game {
	static Controler controler;
	static BoardObject currentObject;
	static CommandPanel command;
	public static DisplayPanel display;
	
	
	static Map<String,PlayerObject> playerMap = new HashMap<String,PlayerObject>();
	static Map<String,WeaponObject> weaponMap = new HashMap<String,WeaponObject>();
	
	/**
	 *  Private Constructor
	 */
	private Game() {} 
	
	/**
	 * @Summary the main that runs the game
	 * @param args
	 */
	public static void main(String[] args) {
		controler = new Controler();
		command = new CommandPanel();
		display = new DisplayPanel();
	
		
		PlacePlayers();
		PlaceWeapons();
        CreateWindow();
		SetCurrentObject("white");
	}
	
	/**
	 * @Summary creates the window that holds all the panels
	 */
    @SuppressWarnings("static-access")
	static void CreateWindow() {
        JFrame window = new JFrame();
		window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
		window.setLayout(new BorderLayout());
		
		window.add(Board.GetBoard().GetBoardPanel(), BorderLayout.CENTER);
		window.add(command, BorderLayout.SOUTH);
		window.add(display, BorderLayout.EAST);
		
		window.pack();
		window.setVisible(true);
	}
	
	/**
	 * @Summary creates and places all the players onto the board
	 */
	static void PlacePlayers() {
		playerMap.put("white",new PlayerObject(9, 0, Color.decode("#ffffff"), "White"));
		playerMap.put("green",new PlayerObject(14, 0, Color.decode("#00ff00"), "Green"));
		playerMap.put("peacock",new PlayerObject(23, 6, Color.decode("#326872"), "Peacock"));
		playerMap.put("plum",new PlayerObject(23, 19, Color.decode("#8E4585"), "Plum"));
		playerMap.put("scarlet",new PlayerObject(7, 24, Color.decode("#ff2400"), "Scarlet"));
		playerMap.put("mustard",new PlayerObject(0, 17, Color.decode("#ffdb58"), "Mustard"));

        Board board = Board.GetBoard();
		for(PlayerObject p : playerMap.values())
		    board.AddMovable(p);
	}
	
	/**
	 * @Summary creates and places all weapons onto the board
	 */
	static void PlaceWeapons() {
		weaponMap.put("rope",new WeaponObject(3,4,new Character('R'),"Rope"));
		weaponMap.put("dagger",new WeaponObject(4,12,new Character('D'),"Dagger"));
		weaponMap.put("wrench",new WeaponObject(3,21,new Character('W'),"Wrench"));
		weaponMap.put("pistol",new WeaponObject(10,3,new Character('P'),"Pistol"));
		weaponMap.put("candlestick",new WeaponObject(10,21,new Character('C'),"CandleStick"));
		weaponMap.put("leadpipe",new WeaponObject(20,22,new Character('L'),"LeadPipe"));
		
		Board board = Board.GetBoard();
        for(WeaponObject p : weaponMap.values())
            board.AddMovable(p);
	}
	
	/**
	 * @ Summary Sets the current object to a given one
	 * @param index
	 * @param isPlayer
	 */
	public static void SetCurrentObject(String name) {
		if(playerMap.containsKey(name))
			currentObject = playerMap.get(name);
		else if(weaponMap.containsKey(name))
			currentObject = weaponMap.get(name);
		else
			throw new RuntimeException("tried to set the current controled object to something that dose not exist");
	}
	
	/**
	 * returns true if they is an item in the system with the name given
	 * @param name
	 * @param isPlayer
	 * @return
	 */
	public static boolean ObjectExistes(String name) {
		if(playerMap.containsKey(name)) 
			return true;
		else
			return weaponMap.containsKey(name);
		
	}
	
	/**
	 * @Summary returns the working instance of the controller
	 * @return
	 */
	public static Controler GetControlerInstance() {
		return controler;
	}
	
	/**
	 * @Summary returns the object that is currently being controlled by commands
	 * @return
	 */
	public static BoardObject GetCurrentObject() {
		return currentObject;
	}
}
