package snakepackage;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import snakepackage.GameEvent.gameEventType;

public class SnakePiece implements Constants,Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vector<Point> body;	
	private List<GameListener> gameListenerlist = new ArrayList<GameListener>();;

	SnakePiece() {

		body = new Vector<Point>();
		Point init = new Point(5, 5);// tail
		Board.setPoints(init, FILLED_BLOCK);
		Point init2 = new Point(6, 5);
		Board.setPoints(init2, FILLED_BLOCK);
		body.add(init);
		body.add(init2);
		body.add(new Point(7, 5));
		Board.setPosition(7, 5, FILLED_BLOCK);
		

	}

	public Vector<Point> getBody() {
		return body;
	}

	
	public void move(int dir) {

		int headIndex = this.body.size() - 1;
		int x = this.body.get(headIndex).x;
		int y = this.body.get(headIndex).y;
		
		if (dir == RIGHT) {
			checkAndfire(x + 1, y,dir);
		} else if (dir == LEFT) {
			checkAndfire(x - 1, y,dir);
		} else if (dir == UP) {
			checkAndfire(x, y + 1,dir);
		} else if (dir == DOWN)
			checkAndfire(x, y - 1,dir);

	}
	
	
	private void checkAndfire(int x, int y,int dir) {
			
		/**check if position is used by snake or is wall*/
		if (Board.isPositionFilled(x, y)) {
			fireGameEventHitWall();//snake body is like a wall too
			return;
		}
		
		/**check if food is present
		 * if not at border remove tail & create 2 heads
		 * at border , leave tail and create one head*/
		if (Board.foodIsthere(x, y) == 1) {
			System.out.println("food eaten----------");
			fireGameEventFoodEaten();
			removeTail();
			makeHead(x, y);
			if(dir==RIGHT)	makeHead(x+1, y);
			else if(dir==LEFT)	makeHead(x-1, y);
			else if(dir==UP)	makeHead(x, y+1);
			else if(dir==DOWN)	makeHead(x, y-1);
			
			return;
		} else if (Board.foodIsthere(x, y) == 0) { // at border
			System.out.println("food eaten at border----------");
			fireGameEventFoodEaten();
			makeHead(x, y); // TAIL NOT REMOVED
			return;
		}
		/**default no food*/
		removeTail();
		makeHead(x, y);

	}

	private void makeHead(int x, int y) {

		this.body.add(new Point(x, y));
		Board.setPosition(x, y, FILLED_BLOCK);

	}

	public void removeTail() {

		Board.removePoints(body.get(0).x, body.get(0).y);
		this.body.remove(0);

	}

	

	public void addGameListener(GameListener l) {
		gameListenerlist.add(l);
	}

//	private void fireGameEventHitself() {
//		GameEvent event = new GameEvent(this, gameEventType.HITSELF);
//
//		Iterator<GameListener> itr = gameListenerlist.iterator();
//		while (itr.hasNext()) {
//			((GameListener) itr.next()).snakeChange(event);
//		}
//	}

	private void fireGameEventHitWall() {
		GameEvent event = new GameEvent(this, gameEventType.HITWALL);
		Iterator<GameListener> itr = gameListenerlist.iterator();
		while (itr.hasNext()) {
			((GameListener) itr.next()).snakeChange(event);
		}
	}
	private void fireGameEventFoodEaten() {
		GameEvent event = new GameEvent(this, gameEventType.EATFOOD);
		Iterator<GameListener> itr = gameListenerlist.iterator();
		while (itr.hasNext()) {
			((GameListener) itr.next()).snakeChange(event);
		}
	}
}
