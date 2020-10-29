package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Random;

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
		createMirrors(n,m,k);
	}
	public void createMatrix(int n, int m, int k) {
		if (first==null)
			first=new Cell();
		addDown(m-1,first);
		addRight(n-1,first);
		fill(n-1,first.getDown());
		connect(first);
	}
	
	public void addDown(int m, Cell current) {
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
	public void fill(int n, Cell current) {
		addRight(n,current);
		if(current.getDown()!=null)
			fill(n,current.getDown());
	}
	public void connect(Cell current) {
		if(current.getDown()!=null) {
			Cell below=current.getDown();
			if(current.getRight()!=null&&below.getRight()!=null) {
				current.getRight().setDown(below.getRight());
				below.getRight().setUp(current.getRight());
				connect(current.getRight());
			}
			connect(current.getDown());
		}
	}

	public void createMirrors(int n, int m, int k) {
		if (k == 0) {

		} else {
			Random rand = new Random();
			int row = rand.nextInt(m);
			int column = rand.nextInt(n);
			int mir = rand.nextInt(2);
			char pos;
			switch (mir) {
			case 0:
				pos = '/';
				break;
			default:
				pos = 92;// pos='\';
				break;
			}
			getSpecificCell(row, column).setContent(pos);
			k--;
			createMirrors(n, m, k);
		}
	}

	public Cell getSpecificCell(int row, int column) {
		int n=0;
		int m=0;
		return getSpecificCell(row,column,n,m,first);
	}
	public Cell getSpecificCell(int row, int column, int n, int m, Cell current) {
		if(m==row&&n==column)
			return current;
		else {
			if(n<column) {
				current=current.getRight();
				n++;
			}
			if(m<column) {
				current=current.getDown();
				m++;
			}
			return getSpecificCell(row,column,n,m,current);
		}
	}
	public String printMatrix() {
		String result="";
		if(first!=null) {
			result=printMatrixRight(first,result);
			result=printMatrixDown(first.getDown(),result);
		}
		else
			result="Matrix is empty";
		return result;
	}
	public String printMatrixRight(Cell current, String prev) {
		String result = prev;
		if (current.getRight() != null) {
			result = printMatrixRight(current.getRight(), prev);
		} else {
			result += "\n";
		}
		return result;
	}
	public String printMatrixDown(Cell current, String prev) {
		String result="";
		result=printMatrixRight(current,prev);
		result=printMatrixRight(current.getDown(),result);
		return result;
	}

	public boolean action(int n, int m, int k, String un, String line) {
		if (line.equals("menu")) {
			return false;
		} else {

			return true;
		}
	}
}
