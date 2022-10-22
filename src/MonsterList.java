import java.util.ArrayList;
import java.util.Scanner;

/* Author: Jonathan Baskharoun
 * Date: 01/23/2022
 * Version: Java 1.8
 * The MonsterList class is a handler for monster list objects 
 * and methods which need a list to do their jobs
 */

public class MonsterList {

	private ArrayList<Monster> myList;

	// Constructor
	public MonsterList(ArrayList<Monster> list) {

		myList = list;
	}

	// Lets player read previous output before going back to menu
	public void checkContinue(Scanner scnr) {

		// Two nextLines because the first is clearing leftovers from a previous nextInt
		System.out.print("Press Enter To Continue: ");
		scnr.nextLine();
		scnr.nextLine();
	}

	// Change the numerical attributes of a monster from the list
	public void adjustMonster(Scanner scnr) {

		Monster creatureSelect = null;
		Monster tempCreature = null;
		int finalizeChange = 0;
		int newStat = 0;
		int statCheck = 0;

		while (finalizeChange != 1) {

			System.out.println("Choose A Creature To Be.. \"Adjusted\"");
			creatureSelect = this.listAndSelectMonst(scnr);

			if (creatureSelect == null) { 
				return;
			}

			// Work is first done on this copied temp, then added to list later if confirmed
			tempCreature = new Monster(creatureSelect);

			System.out.println();
			System.out.println("Creature's Original Stats: ");
			tempCreature.printMonster();

			// Loops once for each attribute, switch is based on loop index
			for (int i = 0; i < 4; ++i) {

				System.out.println();
				System.out.println("1. Yes");
				System.out.println("0. No");
				System.out.println();

				switch (i) {
				case 0:
					System.out.print("Change Health? ");
					statCheck = scnr.nextInt();

					if (statCheck == 1) {
						System.out.print("To What Value? ");
						newStat = scnr.nextInt();

						System.out.println();
						System.out.println("The Flow Of The Creature's Life Force Is Manipulated");
						tempCreature.setHealth(newStat);
					}
					break;
				case 1:
					System.out.print("Change Strength? ");
					statCheck = scnr.nextInt();

					if (statCheck == 1) {
						System.out.print("To What Value? ");
						newStat = scnr.nextInt();

						System.out.println();
						System.out.println("The Creature's Muscles Ripple and Reform");
						tempCreature.setStrength(newStat);
					}
					break;
				case 2:
					System.out.print("Change Speed? ");
					statCheck = scnr.nextInt();

					if (statCheck == 1) {
						System.out.print("To What Value? ");
						newStat = scnr.nextInt();

						System.out.println();
						System.out.println("The Creature's Subjective Flow Of Time Is Distorted");
						tempCreature.setSpeed(newStat);
					}
					break;
				case 3:
					System.out.print("Change Intelligence? ");
					statCheck = scnr.nextInt();

					if (statCheck == 1) {
						System.out.print("To What Value? ");
						newStat = scnr.nextInt();

						System.out.println();
						System.out.println("The Creature Sees Beyond Infinite Voids As Its Mind Reshapes");
						tempCreature.setIntelligence(newStat);
					}
					break;
				} // End Switch
			} // End For Loop

			// Here the changes must be confirmed or discarded
			System.out.println();
			System.out.println("The Creature's New Form:");
			tempCreature.printMonster();

			System.out.println();
			System.out.println("1. Yes");
			System.out.println("0. No");
			System.out.println();

			System.out.print("Accept These Changes? ");
			finalizeChange = scnr.nextInt();
			System.out.println();

		} // End While

		myList.set(myList.indexOf(creatureSelect), tempCreature); 
	}

	// creates new monster as a tempcreature, must be confirmed to add to list
	public void createAndAddMonster(Scanner scnr) {

		int makeFinal = 0;
		Monster tempCreature = null;

		scnr.nextLine(); // Clears newline since user will have encountered nextInt() before getting here

		System.out.println("Enter the following information to create a new Monster entry:");
		System.out.println();

		System.out.print("(String) Name: ");
		String name = scnr.nextLine();

		System.out.print("(String) Size: ");
		String size = scnr.nextLine();

		System.out.print("(String) Type: ");
		String type = scnr.nextLine();

		System.out.print("(Integer) Health: ");
		int health = scnr.nextInt();

		System.out.print("(Integer) Strength: ");
		int strength = scnr.nextInt();

		System.out.print("(Integer) Speed: ");
		int speed = scnr.nextInt();

		System.out.print("(Integer) Intelligence: ");
		int intelligence = scnr.nextInt();

		// Creates the creature under consideration
		tempCreature = new Monster(name, size, type, health, strength, speed, intelligence);

		System.out.println("Your Freshly Minted Beast: ");
		tempCreature.printMonster();

		System.out.println();
		System.out.println("1. Yes");
		System.out.println("0. No");
		System.out.println();

		System.out.print("Can We Keep It? Please? ");
		makeFinal = scnr.nextInt();

		System.out.println();

		// Goes back to main menu if creature creation is abandoned
		if (makeFinal == 0) {
			System.out.println("The Smell Of Blood And Sulfur Fills Your Nostrils As The Creature Melts Away");
			System.out.println();
			checkContinue(scnr);
			return;
		}

		myList.add(tempCreature);
	}

	// Lets user select a monster from given list
	public Monster listAndSelectMonst(Scanner scnr) {

		Monster chosenMon = null;
		int monSelect = 0;

		do { // Loop controls for out of bounds selection

			System.out.println();
			System.out.println("0. Return To Menu");

			// Uses a +1 offset when displaying which is corrected below
			for (int i = 0; i < myList.size(); ++i) {
				System.out.print( (i + 1) + ": ");
				System.out.println(myList.get(i).getName() );
			}
			System.out.println();

			System.out.print("Which Will It Be? ");
			monSelect = scnr.nextInt();

		} while (monSelect > myList.size() || monSelect < 0);

		if (monSelect == 0) { return chosenMon; }

		chosenMon = myList.get(monSelect - 1); // corrects the +1 offset;

		return chosenMon; 
	}
	
	// Used to list monsters when selection isn't needed (display only)
	public void listMonsters() {
		
		System.out.println();
		
		for (int i = 0; i < myList.size(); ++i) {
			System.out.print( (i + 1) + ": ");
			System.out.println(myList.get(i).getName() );
		}
		System.out.println();
	}

	// Lets player choose a familiar from the first four options in pre-built list
	public void playerSetup(ArrayList<Monster> bestiary, Scanner scnr) {

		int playerPick = 0;
		int finalChoice = 0;
		Monster currCreature = null;
		
		// If list size is 0, this is initial setup, so continue normally
		if (myList.size() != 0) {
			
			// this loop checks if all player's creatures are dead or not
			for (int i = 0; i < myList.size(); ++i) {
				
				if (myList.get(i).getHealth() > 0 )
					return; // alive creature found, go back to menu
			}
		}

		while (finalChoice != 1) { // to check if decision is final

			System.out.println();
			System.out.println("These Are Your Options For A Soulbound Familiar:");


			do { // Loops until user selects in range [1,4]
				
				// Player should only choose one of the first 4 basic options
				for (int i = 0; i < 4; ++i) {
					System.out.print( (i + 1) + ": ");
					System.out.println(bestiary.get(i).getName() );
				}
				
				System.out.println();
				System.out.print("Choose: ");

				playerPick = scnr.nextInt();

			} while( (playerPick < 1) || (playerPick > 4) );

			playerPick--; // Corrects +1 offset from listing

			currCreature = bestiary.get(playerPick);
			currCreature.printMonster();

			// Confirm choice or go back to top
			System.out.println();
			System.out.println("1. Yes");
			System.out.println("0. No");
			System.out.println();
			System.out.print("Will You Bind This Creature To Your Soul? ");

			finalChoice = scnr.nextInt();
		}

		myList.add(new Monster(currCreature) );

		System.out.println();
		System.out.println("You Have Formed A Covenant With The " + currCreature.getName() );
		System.out.println("You Timelines Are Now Woven Together, Spiraling Outward To Infinity");
		System.out.println();

		checkContinue(scnr);
	}

	// Prints full monster attributes based on selection
	public void printMonster(Scanner scnr) {

		Monster creatureSelect = null;

		creatureSelect = this.listAndSelectMonst(scnr);

		if (creatureSelect == null) {
			return;
		}

		creatureSelect.printMonster();
		System.out.println();

		checkContinue(scnr);
	}

	// Removes a monster from the given list
	public void removeMonster(Scanner scnr) {

		Monster creatureSelect = null;

		// Fun text for if all creatures are removed already
		if (myList.size() == 0) {
			System.out.println("None Remain");
			System.out.println();
			checkContinue(scnr);
			return;
		}

		creatureSelect = this.listAndSelectMonst(scnr);


		if (creatureSelect == null) { // Means user pressed 0. Return to menu
			return;
		}

		// Text is displayed here so get() can be used before it's removed
		System.out.println();
		System.out.println(creatureSelect.getName() + "'s Information Is Eradicated");
		System.out.println();

		myList.remove(creatureSelect);

		checkContinue(scnr);
	}

	// Get and Set
	public ArrayList<Monster> getList() {
		return myList;
	}
	public void setList(ArrayList<Monster> list) {
		myList = list;
	}

}
