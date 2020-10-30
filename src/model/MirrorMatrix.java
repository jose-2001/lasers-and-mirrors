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
		createMatrix(n,m);
		createMirrors(n,m,k);
	}
	public void createMatrix(int n, int m) {
		System.out.println(n+","+m);
		if (first==null)
			first=new Cell(1,1);
		addDown(n,2,first);
		fill(m,first);
		connect(first);
	}
	
	public void addDown(int totn, int n, Cell current) {
		if(n<=totn){
			Cell newCell= new Cell(n,1);
			current.setDown(newCell);
			newCell.setUp(current);
			n++;
			addDown(totn,n,newCell);
		}
	}
	public void addRight(int totm,int m, Cell current) {
		if(m<=totm){
			Cell newCell= new Cell(current.getRow(),m);
			current.setRight(newCell);
			newCell.setLeft(current);
			m++;
			addRight(totm,m,newCell);
		}
	}
	public void fill(int totm,Cell current) {
		if(current!=null){
			addRight(totm,current.getColumn()+1,current);
			fill(totm,current.getDown());
		}
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
		if (k>0) {
			Random rand = new Random();
			int row = rand.nextInt(n)+1;
			int column = rand.nextInt(m)+1;
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
			Cell target=goToCellFrom(row, column,first);
			if (!target.hasContent()) {
				target.setContent(pos);
				k--;
			}
			createMirrors(n, m, k);
		}
	}

	public Cell goToCellFrom(int row, int column, Cell current) {
		System.out.println("Going to: "+row+","+column+" from: "+current);
		if(current.getRow()==row&&current.getColumn()==column)
			return current;
		else {
			if(current.getRow()<row) {
				current=current.getDown();
			}
			if(current.getRow()>row) {
				current=current.getUp();
			}
			if(current.getColumn()<column) {
				current=current.getRight();
			}
			if(current.getColumn()>column) {
				current=current.getLeft();
			}
			return goToCellFrom(row,column,current);
		}
	}
	public String printMatrix() {
		String result="";
		if(first!=null) {
			result=printMatrix(first,result);
		}
		else
			result="Matrix is empty";
		return result;
	}
	public String printMatrix(Cell current, String prev) {
		String result = printRow(current, prev);
		if (current.getDown() != null)
			result = printMatrix(current.getDown(), result);
		return result;
	}
	
	public String printRow(Cell current, String prev) {
		String result = prev+" "+current.toString();
		if (current.getRight() != null) {
			result = printRow(current.getRight(), result);
		} else {
			result += "\n";
		}
		return result;
	}
	public boolean action(int n, int m, int k, String un, String line) {
		int row;
		int column;
		if (line.equals("MENU")) {
			return false;
		} else {
			if(line.charAt(0)=='L') {
				//implement
			}
			else {
				if(m>9) {
					row=getRow(line,0);
					//implement
				}
			}
			return true;
		}
	}
	public int getRow(String line,int i) {
		if(!Character.isDigit(line.charAt(i)))
			return i-1;
		else {
			i++;
			return getRow(line,i);
		}
	}
}
