package ui;

import java.util.Scanner;

import model.MirrorMatrix;

public class Menu {

	// constants
	public static final int EXIT_OPTION = 17;

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
		do {
			System.out.print(msg);
			dec = Integer.parseInt(sc.nextLine());
			decisionSwitch(dec);
		} while (dec != EXIT_OPTION);
	}
	public void decisionSwitch(int dec) {
		switch (dec) {
		case 1:
			play();
			break;
		case 2:
			//registerProduct();
			break;
		case 3:
			//registerClient();
			break;
		}
	}
	public void play() {
		String line=sc.nextLine();
		String[] parts=line.split(" ");
		String un= parts[0];
		int n= Integer.parseInt(parts[1]);
		int m= Integer.parseInt(parts[2]);
		int k= Integer.parseInt(parts[3]);
		mm.startGame(n,m,k,un);
	}
	
}
