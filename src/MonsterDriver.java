import java.util.ArrayList;
import java.util.Scanner;

/* Author: Jonathan Baskharoun
 * Date: 01/23/2022
 * Version: Java 1.8
 * The Monster Driver class manages monster objects and program control flow
 * For a small game involving fighting and collecting monsters
 */

public class MonsterDriver {

	public static void main(String[] args) {

		int fightResult = 0;
		int menuChoice = 0;
		Monster playerMonster = null;
		Monster enemyMonster = null;
		Monster tempMonster = null;

		Scanner scnr = new Scanner(System.in);

		// bestiary is like the official book, collection is the player's personal set of creatures
		ArrayList<Monster> bestiary = new ArrayList<Monster>();
		ArrayList<Monster> collection = new ArrayList<Monster>();

		// Lists Pre-built Monsters
		// Animals/Companions      Name				 Size	    Type	  HP   Str  Spd  Int
		bestiary.add( new Monster("Komodo Dragon",  "Medium",  "Animal",  30,  18,  12,   6) );
		bestiary.add( new Monster("Great Raven"  ,  "Small" ,  "Animal",  16,   6,  18,  18) );
		bestiary.add( new Monster("Arctic Wolf"  ,  "Medium",  "Animal",  22,  10,  18,  12) );
		bestiary.add( new Monster("Hybrid Ibex"  ,  "Medium",  "Animal",  44,  12,  12,   8) );

		// Common Mobs			   Name				   Size	      Type	      HP   Str  Spd  Int
		bestiary.add( new Monster("Kobold"         ,  "Small" ,  "Humanoid" , 18,  10,  12,  10) );
		bestiary.add( new Monster("Goblin"         ,  "Small" ,  "Humanoid" , 24,  14,  14,  12) );
		bestiary.add( new Monster("Skeleton"       ,  "Medium",  "Undead"   , 32,  16,  12,   4) );
		bestiary.add( new Monster("Shambling Mound",  "Large" ,  "Plant"    , 64,  22,   8,   8) );
		bestiary.add( new Monster("Wood Golem"     ,  "Large" ,  "Construct", 80,  26,  10,  10) );

		// Boss Fights			   Name							Size	      Type	     HP    Str  Spd  Int
		bestiary.add( new Monster("Polyphemus the Cyclops"   ,  "Huge"    ,  "Humanoid", 124,  32,  12,  12) );
		bestiary.add( new Monster("Dullahan the Shadow Rider",  "Medium"  ,  "Undead"  , 241,  28,  22,  24) );
		bestiary.add( new Monster("Great Wyrm Umbral Dragon" ,  "Colossal",  "Dragon"  , 458,  64,  14,  40) );

		// List Handlers for Convenience
		MonsterList bList = new MonsterList(bestiary);
		MonsterList cList = new MonsterList(collection);

		// Player Collection Initial Setup
		cList.playerSetup(bestiary, scnr);

		System.out.println("Welcome To The Monster Mash, This Will Be A Graveyard Smash");

		// Main menu loop entry point
		do {
			System.out.println();
			System.out.println("Monster Mash Main Menu");
			System.out.println("--------------------------------------------------");
			System.out.println("0. Quit");
			System.out.println("1. View Collection");
			System.out.println("2. Alter A Creature In Your Collection");
			System.out.println("3. Release A Creature From Your Control");
			System.out.println("4. View Bestiary");
			System.out.println("5. Adjust A Monster's Attributes In The Bestiary");
			System.out.println("6. Add A New Monster To The Bestiary");
			System.out.println("7. Remove A Monster From The Bestiary");
			System.out.println("8. Look For Trouble");
			System.out.println();
			System.out.print("Choose: ");
			menuChoice = scnr.nextInt();

			System.out.println();
			switch (menuChoice) {

			case 0: // Quit
				break;

			case 1: // View collection
				System.out.println("Your Current Collection Of Creatures:");
				cList.printMonster(scnr);

				break;

			case 2: // Alter collection creature
				cList.adjustMonster(scnr);
				break;
				
			case 3: // Remove a monster from player's collection
				cList.removeMonster(scnr);
				cList.playerSetup(bestiary, scnr); // gives new monster if last one removed
				break;

			case 4: // View bestiary
				System.out.println("Creatures Currently Known Of:");
				bList.printMonster(scnr);

				// Monster.printMonster(bestiary, scnr);
				break;

			case 5: // Adjust bestiary monster
				bList.adjustMonster(scnr);
				break;

			case 6: // Add new monster to bestiary
				System.out.println("Entering Creature Creator..");
				bList.createAndAddMonster(scnr);
				break;

			case 7: // Remove monster from bestiary
				bList.removeMonster(scnr);
				break;

			case 8: // Look for trouble - fight a monster

				// gets a monster from user's collection, or return to menu
				System.out.println();
				System.out.println("Choose A Monster To Bring With You:");
				playerMonster = cList.listAndSelectMonst(scnr);
				if (playerMonster == null) { break; }
				
				System.out.println();

				// get monster from bestiary to use for fight, or return to menu
				System.out.println("Choose The Monster You Will Challenge:");
				tempMonster = bList.listAndSelectMonst(scnr);
				if (tempMonster == null) { break; }
				
				// Copies monster from bestiary for the fight, so bestiary is unaffected
				enemyMonster = new Monster(tempMonster);
				
				// sends user to fight menu, returns a flag to indicate result of fight
				fightResult = playerMonster.fightMonster(enemyMonster, scnr);

				switch (fightResult) { // Checks how fight ended

				case 1: // player wins fight
					playerMonster.fightWon(scnr);
					break;

				case 2: // player lost
					playerMonster.fightLost(scnr);
					cList.playerSetup(bestiary, scnr); 
					break;

				case 3: // successfully captured enemy
					// new instance of monster recorded before battle so it has full health
					collection.add(new Monster(tempMonster) );
					break;
				} // End case 7 inner switch

				break;
			} // End menu switch

		} while (menuChoice != 0);

		System.out.println();
		System.out.println("Thanks For Playing! Don't Forget To Destroy The");
		System.out.println("Like Button and Obliterate That Subscribe Button!");

		scnr.close();

	}

}
