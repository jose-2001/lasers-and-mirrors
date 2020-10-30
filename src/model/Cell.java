package model;

public class Cell {
	
	//attributes
	
	char content;
	int row;
	int column;
	
	//relations
	
	private Cell right;
	private Cell left;
	private Cell up;
	private Cell down;
	
	//methods
	public Cell() {
		right=null;
		left=null;
		up=null;
		down=null;
		content=' ';
	}
	public Cell(int r, int c) {
		right=null;
		left=null;
		up=null;
		down=null;
		content=' ';
		row=r;
		column=c;
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
	@Override
	public String toString() {
		//
		String result="["+row+","+column+": "+content+"]";
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
