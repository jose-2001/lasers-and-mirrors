package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Random;

import exceptions.InvalidShootingCellException;

public class MirrorMatrix {
	
	//constants
	
	public static final String PLAYERS_FILE_NAME = "data/players.pla";
	public static final int RIGHT = 0;
	public static final int DOWN = 1;
	public static final int LEFT= 2;
	public static final int UP = 3;

	//attributes
	
	private int mirrorsLeft;
	
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
	public int getMirrorsLeft() {
		return mirrorsLeft;
	}
	public void setMirrorsLeft(int mirrors) {
		this.mirrorsLeft = mirrors;
	}
	public void startGame(int n, int m, int k, String un) {
		createMatrix(n,m);
		createMirrors(n,m,k);
		setMirrorsLeft(k);
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
	public boolean action(int n, int m, String un, String line) throws InvalidShootingCellException {	
		if (line.equals("MENU")||mirrorsLeft==0) {
			return false;
		} else {
			if(line.charAt(0)=='L') {
				//implement locate((int n, int m, String un, String line)
			}
			else {
				shoot(n,m,un,line);
			}
			return true;
		}
	}
	public void shoot(int n, int m, String un, String line) throws InvalidShootingCellException {
		int row;
		int column;
		int rowDigits = getRowDigits(line, 0);
		row = Integer.parseInt(line.substring(0, rowDigits));
		column = line.charAt(rowDigits) - 64;
		System.out.println("looking for: "+row+","+column);
		int direction=-1;
		if ((row == 1 && (column == 1 || column == m)) || (row == n && (column == 1 || column == m))) {
			if (row == 1 && column == 1) {
				switch (line.charAt(line.length() - 1)) {
				case 'H':
					direction = RIGHT;
					break;
				case 'V':
					direction = DOWN;
					break;
				}
			}
			if (row == 1 && column == m) {
				switch (line.charAt(line.length() - 1)) {
				case 'H':
					direction = LEFT;
					break;
				case 'V':
					direction = DOWN;
					break;
				}
			}
			if (row == n && column == 1) {
				switch (line.charAt(line.length() - 1)) {
				case 'H':
					direction = RIGHT;
					break;
				case 'V':
					direction = UP;
					break;
				}
			}
			if (row == n && column == m) {
				switch (line.charAt(line.length() - 1)) {
				case 'H':
					direction = LEFT;
					break;
				case 'V':
					direction = UP;
					break;
				}
			}
		} else {
			if(row == 1) 
				direction = DOWN;
			if(row==n)
				direction=UP;
			if(column== 1)
				direction=RIGHT;
			if(column== m)
				direction=LEFT;
		}
		if(direction==-1) {
			throw new InvalidShootingCellException();
		}
		Cell start=goToCellFrom(row,column,first);
		Cell exit=getEnd(start,direction,un);
		start.setStart(true);
		exit.setExit(true);
	}
	
	public Cell getEnd(Cell current, int direction, String un) {
		if (current.getContent() == '/') {
			switch(direction) {
			case RIGHT:
				direction=UP;
				if(current.getUp()!=null)
					return getEnd(current.getUp(),direction,un);
				else
					return current;
			case DOWN:
				direction=LEFT;
				if(current.getLeft()!=null)
					return getEnd(current.getLeft(),direction,un);
				else
					return current;
			case LEFT:
				direction=DOWN;
				if(current.getDown()!=null)
					return getEnd(current.getDown(),direction,un);
				else
					return current;
			case UP:
				direction=RIGHT;
				if(current.getRight()!=null)
					return getEnd(current.getRight(),direction,un);
				else 
					return current;
			}
		} else {
			if (current.getContent() == 92) {
				switch(direction) {
				case RIGHT:
					direction=DOWN;
					if(current.getDown()!=null)
						return getEnd(current.getDown(),direction,un);
					else
						return current;
				case DOWN:
					direction=RIGHT;
					if(current.getRight()!=null)
						return getEnd(current.getRight(),direction,un);
					else 
						return current;
				case LEFT:
					direction=UP;
					if(current.getUp()!=null)
						return getEnd(current.getUp(),direction,un);
					else
						return current;
				case UP:
					direction=LEFT;		
					if(current.getLeft()!=null)
						return getEnd(current.getLeft(),direction,un);
					else
						return current;
				}
			}
			else {
				switch(direction) {
				case RIGHT:
					if(current.getRight()==null)
						return current;
					else
						return getEnd(current.getRight(),direction,un);
				case DOWN:
					if(current.getDown()==null)
						return current;
					else
						return getEnd(current.getDown(),direction,un);
				case LEFT:
					if(current.getLeft()==null)
						return current;
					else
						return getEnd(current.getLeft(),direction,un);
				case UP:
					if(current.getUp()==null)
						return current;
					else
						return getEnd(current.getUp(),direction,un);
				}
			}
		}
		System.err.println("Error revise getEnd in mm");
		return current;
	}
	
	public int getRowDigits(String line,int i) {
		if(!Character.isDigit(line.charAt(i)))
			return i;
		else {
			i++;
			return getRowDigits(line,i);
		}
	}
}
