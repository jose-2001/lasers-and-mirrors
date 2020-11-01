package model;

public class Position {
	private Integer pos;

	public Position(Integer p) {
		pos=p;
	}

	public Integer getPos() {
		return pos;
	}

	public void setPos(Integer pos) {
		this.pos = pos;
	}
	@Override
	public String toString() {
		String result=""+pos;
		return result;
	}
}
