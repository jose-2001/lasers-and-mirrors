package model;

public class Cell {
	
	//attributes
	
	char content;
	
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
	
}
