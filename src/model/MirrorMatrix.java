package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

import exceptions.GameQuitException;
import exceptions.InvalidShootingCellException;

public class MirrorMatrix {

	// constants

	public static final String PLAYERS_FILE_NAME = "data/players.pla";
	public static final int RIGHT = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int UP = 3;

	// attributes

	private int mirrorsLeft;
	private long currentScore;
	private boolean finished;
	private boolean cheatMode = false;

	// relations

	private Player root;
	private Cell first;

	public MirrorMatrix() throws ClassNotFoundException, IOException {
		File f = new File(PLAYERS_FILE_NAME);
		if (f.exists()) {
			loadPlayers();
		}
	}

	private void loadPlayers() throws IOException, ClassNotFoundException {
		File f = new File(PLAYERS_FILE_NAME);
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
		root = (Player) ois.readObject();
		ois.close();
	}

	private void savePlayers() throws FileNotFoundException, IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PLAYERS_FILE_NAME));
		oos.writeObject(root);
		oos.close();
	}

	public int getMirrorsLeft() {
		return mirrorsLeft;
	}

	public void setMirrorsLeft(int mirrors) {
		this.mirrorsLeft = mirrors;
	}

	public long getCurrentScore() {
		return currentScore;
	}

	public void setCurrentScore(long currentScore) {
		this.currentScore = currentScore;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public void startGame(int n, int m, int k, String un) {
		createMatrix(n, m);
		createMirrors(n, m, k);
		setMirrorsLeft(k);
		setFinished(false);
		setCurrentScore(0);
	}

	public void createMatrix(int n, int m) {
		if (first == null)
			first = new Cell(1, 1);
		addDown(n, 2, first);
		fill(m, first);
		connect(first);
	}

	public void addDown(int totn, int n, Cell current) {
		if (n <= totn) {
			Cell newCell = new Cell(n, 1);
			current.setDown(newCell);
			newCell.setUp(current);
			n++;
			addDown(totn, n, newCell);
		}
	}

	public void addRight(int totm, int m, Cell current) {
		if (m <= totm) {
			Cell newCell = new Cell(current.getRow(), m);
			current.setRight(newCell);
			newCell.setLeft(current);
			m++;
			addRight(totm, m, newCell);
		}
	}

	public void fill(int totm, Cell current) {
		if (current != null) {
			addRight(totm, current.getColumn() + 1, current);
			fill(totm, current.getDown());
		}
	}

	public void connect(Cell current) {
		if (current.getDown() != null) {
			Cell below = current.getDown();
			if (current.getRight() != null && below.getRight() != null) {
				current.getRight().setDown(below.getRight());
				below.getRight().setUp(current.getRight());
				connect(current.getRight());
			}
			connect(current.getDown());
		}
	}

	public void createMirrors(int n, int m, int k) {
		if (k > 0) {
			Random rand = new Random();
			int row = rand.nextInt(n) + 1;
			int column = rand.nextInt(m) + 1;
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
			Cell target = goToCellFrom(row, column, first);
			if (!target.hasContent()) {
				target.setContent(pos);
				k--;
			}
			createMirrors(n, m, k);
		}
	}

	public Cell goToCellFrom(int row, int column, Cell current) {
		if (current.getRow() == row && current.getColumn() == column)
			return current;
		else {
			if (current.getRow() < row) {
				current = current.getDown();
			}
			if (current.getRow() > row) {
				current = current.getUp();
			}
			if (current.getColumn() < column) {
				current = current.getRight();
			}
			if (current.getColumn() > column) {
				current = current.getLeft();
			}
			return goToCellFrom(row, column, current);
		}
	}

	public String printMatrix() {
		String result = "";
		if (first != null) {
			result = printMatrix(first, result);
		} else
			result = "Matrix is empty";
		return result;
	}

	public String printMatrix(Cell current, String prev) {
		String result = printRow(current, prev);
		if (current.getDown() != null)
			result = printMatrix(current.getDown(), result);
		return result;
	}

	public String printRow(Cell current, String prev) {
		String result = prev + " " + current.toString(cheatMode);
		if (current.getRight() != null) {
			result = printRow(current.getRight(), result);
		} else {
			result += "\n";
		}
		return result;
	}

	public boolean action(int n, int m, String un, String line) throws InvalidShootingCellException, GameQuitException {
		if (line.equals("MENU") || mirrorsLeft == 0) {
			throw new GameQuitException();
		} else {
			if (line.charAt(0) == 'L') {
				locate(n, m, un, line);
			} else {
				shoot(n, m, un, line);
			}
			currentScore++;
			if (mirrorsLeft == 0) {
				setFinished(true);
				return false;
			} else
				return true;
		}
	}

	public void locate(int n, int m, String un, String line) {
		int row;
		int column;
		int rowDigits = getRowDigits(line, 1);
		row = Integer.parseInt(line.substring(1, rowDigits));
		column = line.charAt(rowDigits) - 64;
		Cell located = goToCellFrom(row, column, first);
		char guess = ' ';
		switch (line.charAt(line.length() - 1)) {
		case 'L':
			guess = 92;
			break;
		case 'R':
			guess = '/';
			break;
		}
		if (located.getContent() == guess) {
			located.setFound(true);
			mirrorsLeft--;
		} else
			located.setWrong(true);
	}

	public void shoot(int n, int m, String un, String line) throws InvalidShootingCellException {
		int row;
		int column;
		int rowDigits = getRowDigits(line, 0);
		row = Integer.parseInt(line.substring(0, rowDigits));
		column = line.charAt(rowDigits) - 64;
		int direction = -1;
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
			if (row == 1)
				direction = DOWN;
			if (row == n)
				direction = UP;
			if (column == 1)
				direction = RIGHT;
			if (column == m)
				direction = LEFT;
		}
		if (direction == -1) {
			throw new InvalidShootingCellException();
		}
		Cell start = goToCellFrom(row, column, first);
		Cell exit = getEnd(start, direction, un);
		start.setStart(true);
		exit.setExit(true);
	}

	public Cell getEnd(Cell current, int direction, String un) {
		if (current.getContent() == '/') {
			switch (direction) {
			case RIGHT:
				direction = UP;
				if (current.getUp() != null)
					return getEnd(current.getUp(), direction, un);
				else
					return current;
			case DOWN:
				direction = LEFT;
				if (current.getLeft() != null)
					return getEnd(current.getLeft(), direction, un);
				else
					return current;
			case LEFT:
				direction = DOWN;
				if (current.getDown() != null)
					return getEnd(current.getDown(), direction, un);
				else
					return current;
			case UP:
				direction = RIGHT;
				if (current.getRight() != null)
					return getEnd(current.getRight(), direction, un);
				else
					return current;
			}
		} else {
			if (current.getContent() == 92) {
				switch (direction) {
				case RIGHT:
					direction = DOWN;
					if (current.getDown() != null)
						return getEnd(current.getDown(), direction, un);
					else
						return current;
				case DOWN:
					direction = RIGHT;
					if (current.getRight() != null)
						return getEnd(current.getRight(), direction, un);
					else
						return current;
				case LEFT:
					direction = UP;
					if (current.getUp() != null)
						return getEnd(current.getUp(), direction, un);
					else
						return current;
				case UP:
					direction = LEFT;
					if (current.getLeft() != null)
						return getEnd(current.getLeft(), direction, un);
					else
						return current;
				}
			} else {
				switch (direction) {
				case RIGHT:
					if (current.getRight() == null)
						return current;
					else
						return getEnd(current.getRight(), direction, un);
				case DOWN:
					if (current.getDown() == null)
						return current;
					else
						return getEnd(current.getDown(), direction, un);
				case LEFT:
					if (current.getLeft() == null)
						return current;
					else
						return getEnd(current.getLeft(), direction, un);
				case UP:
					if (current.getUp() == null)
						return current;
					else
						return getEnd(current.getUp(), direction, un);
				}
			}
		}
		return current;
	}

	public int getRowDigits(String line, int i) {
		if (!Character.isDigit(line.charAt(i)))
			return i;
		else {
			i++;
			return getRowDigits(line, i);
		}
	}

	public void calculateScore(String un) throws FileNotFoundException, IOException {
		currentScore += 100 * mirrorsLeft;
		Player newPlayer = new Player(un, currentScore);
		if (root == null)
			root = newPlayer;
		else {
			addPlayer(newPlayer, root);
		}
	}

	public void addPlayer(Player toAdd, Player current) throws FileNotFoundException, IOException {
		if ((current.compareTo(toAdd) >= 0) && (current.getLeft() == null)) {
			current.setLeft(toAdd);
			toAdd.setP(current);
		} else if ((current.compareTo(toAdd) < 0) && (current.getRight() == null)) {
			current.setRight(toAdd);
			toAdd.setP(current);
		} else if ((current.compareTo(toAdd) < 0) && (current.getRight() != null)) {
			addPlayer(toAdd, current.getRight());
		} else if ((current.compareTo(toAdd) >= 0) && (current.getLeft() != null)) {
			addPlayer(toAdd, current.getLeft());
		}

		savePlayers();
	}

	public String showScores() {
		Position pos= new Position(1);
		String result = showScores(root, "",pos);
		return result;
	}

	public String showScores(Player current, String prev, Position pos) {
		String result = prev;
		if (current.getLeft() != null)
			result = showScores(current.getLeft(), result, pos);
		result += pos + ". " + current.toString() + "\n";
		pos.setPos(pos.getPos()+1);
		if (current.getRight() != null)
			result = showScores(current.getRight(), result, pos);
		return result;
	}

	public boolean isCheatMode() {
		return cheatMode;
	}

	public void setCheatMode(boolean cheatMode) {
		this.cheatMode = cheatMode;
	}

	public void toggleCheatMode() {
		if (cheatMode)
			cheatMode = false;
		else
			cheatMode = true;
	}
}