package model;

import java.io.Serializable;

public class Player implements Serializable,Comparable<Player>{
	
	//constants
	
	private static final long serialVersionUID = 1L;

	//attributes
	
	private String username;
	private long score;
	
	//relations
	
	private Player left;
	private Player right;
	private Player p;
	
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

	public Player getP() {
		return p;
	}

	public void setP(Player p) {
		this.p = p;
	}

	@Override
	public int compareTo(Player p) {
		if(p.getScore()>score)
		return -1;
		else if(p.getScore()<score)
			return 1;
		else
			return 0;
	}
	@Override
	public String toString() {
		String result="Username: "+getUsername()+". Score: "+getScore();
		return result;
	}
}
