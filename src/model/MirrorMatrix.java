package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class MirrorMatrix {
	//constants
	
	public static final String PLAYERS_FILE_NAME = "data/players.pla";

	//relations
	
	private Player root;
	private Cell first;
	
	public MirrorMatrix() {
		File f = new File(PLAYERS_FILE_NAME);
		if (f.exists()) {
			try {
				loadPlayers();
			} catch (IOException ioe) {
				System.err.println("Restaurant data could not be loaded properly");
				ioe.printStackTrace();
			} catch (ClassNotFoundException cnfe) {
				System.err.println("The class was not found");
			}
		}
	}
	private void loadPlayers() throws IOException, ClassNotFoundException {
		File f = new File(PLAYERS_FILE_NAME);
	      ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
	      root = (Player)ois.readObject();
	      ois.close();
	}
	public void startGame(int n, int m, int k, String un) {
		createMatrix(n,m,k);
	}
	public void createMatrix(int n, int m, int k) {
		if (first==null)
			first=new Cell();
		addDown(m-1,first);
		addRight(n-1,first);
	}
	
	private void addDown(int m, Cell current) {
		if(m==0) {
			
		}
		else {
			Cell newCell= new Cell();
			current.setDown(newCell);
			newCell.setUp(current);
			m--;
			addDown(m,newCell);
		}
	}
	public void addRight(int n, Cell current) {
		if(n==0) {
			
		}
		else {
			Cell newCell= new Cell();
			current.setRight(newCell);
			newCell.setLeft(current);
			n--;
			addRight(n,newCell);
		}
	}
}
