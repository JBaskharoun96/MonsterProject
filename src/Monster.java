import java.util.Scanner;

/* Author: Jonathan Baskharoun
 * Date: 01/23/2022
 * Version: Java 1.8
 * The Monster class manages monster objects and methods
 */

public class Monster {

	// private instance variables to store Monster traits
	private String myName;
	private String mySize;
	private String myType;
	private int    myHealth;
	private int    myStrength;
	private int    mySpeed;
	private int    myIntelligence;

	// Constructor
	public Monster(String name, String size, String type, int health,
			int strength, int speed, int intelligence) {

		myName		   = name;
		mySize		   = size;
		myType		   = type;
		myHealth	   = health;
		myStrength	   = strength;
		mySpeed		   = speed;
		myIntelligence = intelligence;
	}

	// Copy constructor
	public Monster(Monster monster) {
		myName			= monster.getName();
		mySize			= monster.getSize();
		myType			= monster.getType();
		myHealth		= monster.getHealth();
		myStrength		= monster.getStrength();
		mySpeed 		= monster.getSpeed();
		myIntelligence  = monster.getIntelligence();
	}

	// Lets player read previous output before going back to menu
	public void checkContinue(Scanner scnr) {

		// Two nextLines because the first is clearing leftovers from a previous nextInt
		System.out.print("Press Enter To Continue: ");
		scnr.nextLine();
		scnr.nextLine();
	}

	// Used to create dramatic delay
	public void theBeast(Scanner scnr) {

		try {
			System.out.println(".");
			Thread.sleep(1500);
			System.out.println(".");
			Thread.sleep(1500);
			System.out.println(".");
			Thread.sleep(1500);
			System.out.println("Yet The Beast Is Still Upon You");
			Thread.sleep(2000);

		} catch (InterruptedException e) {
		}
		System.out.println();
		checkContinue(scnr);
	}

	// This is where a fight against a monster happens
	public int fightMonster(Monster enemyMonster, Scanner scnr) {

		int captureCheck = 0;
		int menuChoice = 0;
		int keepFighting = 1;
		int fightResult = 0;

		enemyMonster.printMonster(); // show what they're going up against

		System.out.println();
		System.out.println("1. Yes");
		System.out.println("0. No");
		System.out.println();

		// last chance to back out of fight
		System.out.print("Do You Have The Will To Take On " + enemyMonster.getName() + "? ");
		keepFighting = scnr.nextInt();

		// easter egg for player bringing dead creature to fight
		if (myHealth == 0) {
			printMonster();
			System.out.println();
			System.out.println("Without A Companion, Nothing Stands Between You And The Beast");
			System.out.println("You Have No Choice But To Run");
			theBeast(scnr);

			System.out.println();
			System.out.println("As The Beast Tears You Apart You Feel Your Soul Shatter");
			System.out.println("The Fragments Of Your Soul Rise And Drift Across Universes");
			System.out.println("They Settle Into Another Body That Looks And Feels Like You");
			System.out.println("You Seem To Be Back Where You Started");
			System.out.println();
			checkContinue(scnr);

			return fightResult;
		} // end if

		// Loops as long as user doesn't pick an option which exits the fight
		while (keepFighting == 1) {


			do { // checks for out of bounds selection

				System.out.println();
				System.out.println("Options:");
				System.out.println("0. Sacrifice An Attribute To Do The Time Warp");
				System.out.println("1. Run Away In A Fit Of Existential Terror (Speed)");
				System.out.println("2. Hide From The Creature In The Fetal Position (Intelligence)");
				System.out.println("3. Muster Up The Heart To Attack The Beast (Any Attribute)");
				System.out.println("4. Attempt To Tame The Beast For Your Collection");
				System.out.println();

				System.out.print("What Will You Do? ");
				menuChoice = scnr.nextInt();
				System.out.println();

			} while (menuChoice < 0 || menuChoice > 4);

			// switch redirects to appropriate method
			switch (menuChoice) {

			case 0: // time warp sacrifice
				keepFighting = 0;
				sacrificeWarp(scnr);
				break;

			case 1: // run away
				keepFighting = 0;
				fleeFight(enemyMonster, scnr);
				break;

			case 2: // hide
				keepFighting = 0;
				hideFight(enemyMonster, scnr);
				break;

			case 3: // attack
				attackFight(enemyMonster, scnr);
				break;

			case 4: // attempt capture
				captureCheck = captureBeast(enemyMonster, scnr);

				if (captureCheck == 1) {
					keepFighting = 0; 
					fightResult = 3;
				}

			} // End switch

			// checks if a monster is killed in battle, can't be both due to how dmg works
			if (myHealth <= 0) {
				System.out.println();
				System.out.println("The Battle Results In Your Beast's Untimely Demise");

				keepFighting = 0;
				fightResult = 2;
			}
			else if (enemyMonster.getHealth() <= 0) {
				System.out.println();
				System.out.println("Against All Odds, The Enemy Beast Is Slain");

				keepFighting = 0;
				fightResult = 1;
			}

			System.out.println();
			checkContinue(scnr);

		} // End while

		return fightResult; // return flag indicates how main will continue
	}

	public void fightWon(Scanner scnr) {

		int statSelect = 0;

		do { // Controls for out of bound selection
			System.out.println();
			System.out.println("1. Elephant's Hide (+8 Health)");
			System.out.println("2. Bull's Bravado (+4 Strength)");
			System.out.println("3. Speed Of The Fox (+4 Speed)");
			System.out.println("4. Mind Of The Owl (+4 Intelligence)");
			System.out.println();

			System.out.print("Choose A Spell To Cast On Your Companion: ");
			statSelect = scnr.nextInt();

		} while (statSelect < 1 || statSelect > 4);

		System.out.println();

		// selects method for chosen stat boost
		switch (statSelect) {

		case 1:
			elephantHide();
			break;
		case 2:
			bullStrength();
			break;
		case 3:
			foxSpeed();
			break;
		case 4:
			owlMind();
			break;
		}
		System.out.println();
		System.out.println(myName + "'s New Attributes:");
		printMonster();

		System.out.println();
		checkContinue(scnr);
	}

	// player loses fight, monster becomes (Deceased) and loses all stats
	public void fightLost(Scanner scnr) {
		setName(myName.concat(" (Deceased)") );
		setHealth(0);
		setStrength(0);
		setSpeed(0);
		setIntelligence(0);
	}

	// Used when player attacks beast
	public void attackFight(Monster enemyMonster, Scanner scnr) {

		int statPick = 0;
		int differential = 0;
		int playerStat = 0;
		int monsterStat = 0;

		// choice of stat to use for attack
		System.out.println("1. Straight Beatdown (Strength)");
		System.out.println("2. Dashing Slicer (Speed)");
		System.out.println("3. Tactical Strike (Intelligence)");
		System.out.println();

		System.out.print("What Type Of Attack Will You Use? ");
		statPick = scnr.nextInt();
		System.out.println();

		// switch gets the appropriate stats for comparison
		switch (statPick) {

		case 1:
			playerStat = myStrength;
			monsterStat = enemyMonster.getStrength();
			System.out.println("A Battle Of Brawn: ");
			break;

		case 2:
			playerStat = mySpeed;
			monsterStat = enemyMonster.getSpeed();
			System.out.println("A Battle Of Agility: ");
			break;

		case 3:
			playerStat = myIntelligence;
			monsterStat = enemyMonster.getIntelligence();
			System.out.println("A Battle Of Minds: ");
			break;
		} // End switch

		System.out.println(myName + " Has: " + playerStat + " Points");
		System.out.println(enemyMonster.getName() + " Has: " + monsterStat + " Points");
		System.out.println();

		// differential used to determine who was stronger and damage done
		differential = playerStat - monsterStat;

		if (differential > 0) {

			System.out.println("You've Wounded The Beast");
			System.out.println("It Takes " + differential + " Damage");
			enemyMonster.loseHealth(differential);
		}
		else if (differential < 0) {

			System.out.println("The Beast Tears Into You");
			System.out.println("You Take " + Math.abs(differential) + " Damage");
			loseHealth(differential);
		}
		else {
			System.out.println("You're Evenly Matched");
		} // End if

		System.out.println();
		System.out.println(myName + "'s Health: " + myHealth);
		System.out.println(enemyMonster.getName() + "'s Health: " + enemyMonster.getHealth() );
	}

	// Used when player runs from a fight
	public void fleeFight(Monster enemyMonster, Scanner scnr) {

		int playerStat = 0;
		int monsterStat = 0;

		System.out.println("Probably For The Best");
		System.out.println();

		// comparing speeds to check for success
		playerStat = mySpeed;
		monsterStat = enemyMonster.getSpeed();

		System.out.println("Your Speed: " + playerStat);
		System.out.println(enemyMonster.getName() + "'s Speed: " + monsterStat );
		System.out.println();

		if (playerStat > monsterStat) {
			System.out.println("You've Managed To Escape Unharmed, This Time");
		}
		else { // run away failed, loses a stat point
			System.out.println("You Run Until Your Heart Beats Like A War Drum");
			theBeast(scnr);

			sacrificeWarp(scnr);
		}
	}

	// Used when player hides from a fight
	public void hideFight(Monster enemyMonster, Scanner scnr) {

		int playerStat = 0;
		int monsterStat = 0;

		System.out.println("We're All Hiding From Something");
		System.out.println();

		// intelligence comparison to check for hiding success
		playerStat = myIntelligence;
		monsterStat = enemyMonster.getIntelligence();

		System.out.println("Your Intelligence: " + playerStat);
		System.out.println(enemyMonster.getName() + "'s Intelligence: " + monsterStat);
		System.out.println();

		if (playerStat > monsterStat) {
			System.out.println("You've Managed To Go Unnoticed. For Now.");
		}
		else { // couldn't hide, lose a stat
			System.out.println("You Hide For A While, Hoping The Monster Will Move On");
			theBeast(scnr);
			sacrificeWarp(scnr);
		}
	}

	// method for the capture attempt, returns an int flag to indicate success / failure
	public int captureBeast(Monster enemyMonster, Scanner scnr) {

		int captureCheck = 0;
		int justChecking = 0;
		int playerStat = 0;
		int monsterStat = 0;
		int statAdvantage = 0;

		System.out.println("1. Yes");
		System.out.println("0. No");
		System.out.println();

		System.out.println("This Task Will Make Use Of Strength, Speed, and Intelligence");
		System.out.println();

		// last chance to back out
		System.out.print("Will You Try To Capture " + enemyMonster.getName() + "? ");
		justChecking = scnr.nextInt();
		System.out.println();

		if (justChecking == 0) { // User can back out here
			return 0;
		}

		// capture is done by comparing total stats of player and enemy monsters
		playerStat = myHealth + myStrength + mySpeed + myIntelligence;

		monsterStat = enemyMonster.getHealth() + enemyMonster.getStrength() 
		+ enemyMonster.getSpeed() + enemyMonster.getIntelligence();

		statAdvantage = playerStat - monsterStat;

		System.out.println(myName + "'s Total Power: " + playerStat);
		System.out.println(enemyMonster.getName() + "'s Total Power: " + monsterStat);
		System.out.println();

		// Checking who won
		if (statAdvantage < 0) {
			System.out.println("Capturing The Creature Is Beyond Your Ability");
			sacrificeWarp(scnr);
		}
		else if (statAdvantage == 0) {
			System.out.println("Close, But No Cigar");
		}
		else {
			System.out.println("Through Great Effort, You Subdue The Beast");
			System.out.println("Part Of Your Soul Chains Itself To The Beast's Mind");
			captureCheck = 1; // flag sets to 1 here if captured
		}

		return captureCheck;
	}

	// Subtracts damage, then checks to see if HP less than 0
	public void loseHealth(int damageDone) {
		myHealth -= Math.abs(damageDone);
		myHealth = Math.max(0, myHealth);
	}

	// Prints attributes of selected monster
	public void printMonster() {

		System.out.println();
		System.out.println("Name: " 		+ myName );
		System.out.println("Size: " 		+ mySize );
		System.out.println("Type: " 		+ myType );
		System.out.println("Health: "		+ myHealth );
		System.out.println("Strength: " 	+ myStrength );
		System.out.println("Speed: " 		+ mySpeed );
		System.out.println("Intelligence: " + myIntelligence );
	}

	// Sacrifices a point from the monster for leaving / losing a fight
	public void sacrificeWarp(Scanner scnr) {

		int sacPoint = 0;

		System.out.println();
		System.out.println("Current Stats: ");
		printMonster();

		System.out.println();
		System.out.println("1. Health (2)");
		System.out.println("2. Strength (1)");
		System.out.println("3. Speed (1)");
		System.out.println("4. Intelligence (1)");
		System.out.println();

		do { // Controls for out of bounds selection

			System.out.print("What Attribute Will You Sacrifice? ");
			sacPoint = scnr.nextInt();

		} while (sacPoint < 1 || sacPoint > 4);

		switch(sacPoint) {

		case 1:
			myHealth -= 2;
			break;

		case 2:
			myStrength--;
			break;

		case 3:
			mySpeed--;
			break;

		case 4:
			myIntelligence--;
			break;
		} // End Switch
	}

	// Abilities which permanently boost Monster stats, reward for winning
	public void elephantHide() {
		System.out.println("The Flow Of The Creature's Life Force Is Manipulated");
		myHealth += 4;

	}
	public void bullStrength() {
		System.out.println("The Creature's Muscles Ripple and Reform");
		myStrength += 4;
	}

	public void foxSpeed() {
		System.out.println("The Creature's Subjective Flow Of Time Is Distorted");
		mySpeed += 4;
	}

	public void owlMind() {
		System.out.println("The Creature Sees Beyond Infinite Voids As Its Mind Reshapes");
		myIntelligence += 4;
	}

	// Getters and Setters grouped by variable
	public String getName() {
		return myName;
	}
	public void setName(String newName) {
		myName = newName;
	}

	public String getSize() {
		return mySize;
	}
	public void setSize(String newSize) {
		mySize = newSize;
	}

	public String getType() {
		return myType;
	}
	public void setType(String newType) {
		myType = newType;
	}

	public int getHealth() {
		return myHealth;
	}
	public void setHealth(int newHealth) {
		myHealth = newHealth;
	}

	public int getStrength() {
		return myStrength;
	}
	public void setStrength(int newStrength) {
		myStrength = newStrength;
	}

	public int getSpeed() {
		return mySpeed;
	}
	public void setSpeed(int newSpeed) {
		mySpeed = newSpeed;
	}

	public int getIntelligence() {
		return myIntelligence;
	}
	public void setIntelligence(int newIntelligence) {
		myIntelligence = newIntelligence;
	}

}