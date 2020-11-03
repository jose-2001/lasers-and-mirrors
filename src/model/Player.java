package model;

import java.io.Serializable;

public class Player implements Serializable,Comparable<Player>{
	
	//constants
	
	private static final long serialVersionUID = 1L;

	//attributes
	
	private String username;
	private long score;
	private int n;
	private int m;
	private int k;
	
	//relations
	
	private Player left;
	private Player right;
	private Player p;
	
	//methods
	
	public Player(String un, long s,int np,int mp,int kp) {
		username=un;
		score=s;
		n=np;
		m=mp;
		k=kp;
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

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
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
		String result="Username: "+getUsername()+". Score: "+getScore()+
				". Matrix dimensions: "+getN()+"x"+getM()+". Mirrors: "+getK();
		return result;
	}
}
