package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import exceptions.GameQuitException;
import exceptions.InvalidShootingCellException;
import model.MirrorMatrix;

public class Menu {

	// constants
	public static final int EXIT_OPTION = 3;

	// attributes

	private Scanner sc;
	private MirrorMatrix mm ;

	// methods

	public Menu() {
		sc = new Scanner(System.in);
		mm = new MirrorMatrix ();
	}
	public String getMenuText() {
		String menu = "";
		menu += "==============\n" + "     MENU\n" + "==============\n";
		menu += "Type the option you want\n";
		menu +=   "1. Play\n"
				+ "2. Show Scores\n"
				+ "3. Exit\n";
	return menu;
	}
	public void startMenu() {
		String msg = getMenuText();
		int dec;
			System.out.print(msg);
			dec = Integer.parseInt(sc.nextLine());
			decisionSwitch(dec);
			if(dec!=EXIT_OPTION)
				startMenu();
	}
	public void decisionSwitch(int dec) {
		switch (dec) {
		case 1:
			play();
			break;
		case 2:
			showScores();
			break;
		case 3:
			System.out.println("Goodbye!");
			break;
		}
	}
	public void play() {
		System.out.println("Input game parameters");
		String line=sc.nextLine();
		String[] parts=line.split(" ");
		String un= parts[0];
		int n= Integer.parseInt(parts[1]);
		int m= Integer.parseInt(parts[2]);
		int k= Integer.parseInt(parts[3]);
		mm.startGame(n,m,k,un);
		playing(n,m,un);
		finishGame(un);
	}

	public void playing(int n, int m, String un) {
		System.out.println(un + ": " + mm.getMirrorsLeft() + " mirrors reamining");
		System.out.println(mm.printMatrix());
		System.out.println("Input move");
		String line = sc.nextLine();
		line = line.toUpperCase();
		boolean cont = false;
		try {
			cont = mm.action(n, m, un, line);
		} catch (InvalidShootingCellException e) {
			System.err.println("You must shoot from a bordering cell and specify direction if corner cell");
			playing(n, m, un);
			// e.printStackTrace();
		}catch(GameQuitException gqe) {
			System.err.println("You will have a 100 point penalization for every mirror not found");
			cont=false;
		}
		if(mm.isFinished()) {
			System.out.println("Finished game!");
			cont=false;
		}
		if (cont) {
			playing(n, m, un);
		}
	}

	public void finishGame(String un) {
		try {
			mm.calculateScore(un);
		} catch (FileNotFoundException e) {
			System.err.println("The file where the Restaurant data is to be saved could not be found");
		} catch (IOException e) {
			System.err.println("Restaurant data could not be saved properly");
		}
	}
	public void showScores() {
		System.out.println(mm.showScores());
	}
}
