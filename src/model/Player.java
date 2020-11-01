package model;

import java.io.Serializable;

public class Player implements Serializable,Comparable<Player>{
	
	//constants
	
	private static final long serialVersionUID = 1L;

	//attributes
	
	String username;
	long score;
	
	//relations
	
	private Player left;
	private Player right;
	
	//methods
	
	public Player(String un, long s) {
		username=un;
		score=s;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}

	public Player getLeft() {
		return left;
	}

	public void setLeft(Player left) {
		this.left = left;
	}

	public Player getRight() {
		return right;
	}

	public void setRight(Player right) {
		this.right = right;
	}

	@Override
	public int compareTo(Player p) {
		
		return 0;
	}
	
}
