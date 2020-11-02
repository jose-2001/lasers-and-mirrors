package model;

public class Cell {
	
	//attributes
	
	private char content;
	private int row;
	private int column;
	private boolean found;
	private boolean start;
	private boolean exit;
	private boolean wrong;
	
	//relations
	
	private Cell right;
	private Cell left;
	private Cell up;
	private Cell down;
	
	//methods
	
	public Cell(int r, int c) {
		right=null;
		left=null;
		up=null;
		down=null;
		content=' ';
		row=r;
		column=c;
		exit=false;
		start=false;
		found=false;
		wrong=false;
	}

	public char getContent() {
		return content;
	}

	public void setContent(char content) {
		this.content = content;
	}

	public Cell getRight() {
		return right;
	}

	public void setRight(Cell right) {
		this.right = right;
	}

	public Cell getLeft() {
		return left;
	}

	public void setLeft(Cell left) {
		this.left = left;
	}

	public Cell getUp() {
		return up;
	}

	public void setUp(Cell up) {
		this.up = up;
	}

	public Cell getDown() {
		return down;
	}

	public void setDown(Cell down) {
		this.down = down;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public boolean hasContent() {
		return content!=' ';
	}
	public boolean isFound() {
		return found;
	}
	public void setFound(boolean found) {
		this.found = found;
	}
	public boolean isStart() {
		return start;
	}
	public void setStart(boolean start) {
		this.start = start;
	}
	public boolean isExit() {
		return exit;
	}
	public void setExit(boolean exit) {
		this.exit = exit;
	}
	public boolean isWrong() {
		return wrong;
	}
	public void setWrong(boolean wrong) {
		this.wrong = wrong;
	}
	
	public String toString(boolean cheat) {
		//
		String result="";
		char col=(char)(column+64);
		if(cheat)
			 result = "[" + row + "," + col + ": ";
		else
			result ="[";
		if (start && exit) {
			result += "B]";
		} else {
			if (start) {
				result += "S]";
			} else {
				if (exit) {
					result += "E]";
				} else {
					if (wrong)
						result += "X]";
					else {
						result += ((cheat||found)?content:" ")+ "]";
					}
				}
			}
		}
		setStart(false);
		setExit(false);
		setWrong(false);
		return result;
	}
	public String printCon() {
		String result=toString()+"\n"
				+ "up:"+(up!=null?up.toString():"")+"\n"
				+ "right:"+(right!=null?right.toString():"")+"\n"
				+ "down:"+(down!=null?down.toString():"")+"\n"
				+ "left:"+(left!=null?left.toString():"")+"\n";
				
		return result;
	}
}
