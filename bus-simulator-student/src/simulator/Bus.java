/*
 * Copyright 2017 Marc Liberatore.
 */

package simulator;

public class Bus {
	public final int number;
	private final RoadMap roadMap;
	private int x;
	private int y;
	private String direction;
	private boolean moving;

	public Bus(int number, RoadMap roadMap, int x, int y) {
		this.number = number;
		this.roadMap = roadMap;
		this.x = x;
		this.y = y;
		// iniitialize moving to false when the bus is first placed
		direction = "null";
		moving = false;

	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int moveNorth() {
		return y - 1;
	}

	public int moveSouth() {
		return y + 1;
	}

	public int moveEast() {
		return x + 1;
	}

	public int moveWest() {
		return x - 1;
	}

	/**
	 * Move the bus. Buses only move along the cardinal directions
	 * (north/south/east/west), not diagonally.
	 * 
	 * If the bus is stopped (that is, if it was just placed, or if it didn't
	 * move last time move() was called), then it should attempt to move north.
	 * If it cannot (no road, or off the map), then it should attempt south,
	 * then east, then west. If no move is available, it should stay in its
	 * current position.
	 * 
	 * If the bus is moving (that is, if it successfully moved the last time
	 * move() was called), then it should attempt to continue moving in the same
	 * direction.
	 * 
	 * If it cannot (no road, or off the map), then it should attempt to turn
	 * right. For example, if the bus was moving north, but there is no more
	 * road to the north, it should move east if possible.
	 * 
	 * If it cannot turn right, it should turn left. If it cannot turn left, it
	 * should reverse direction (that is, move backward, if possible).
	 * If it cannot do any of these things, it should stay in its current position.
	 */

	public void move() {
		System.out.println("moving");

		if ("null".equals(this.direction) && !this.moving) {
			System.out.println("from stop");
			if (roadMap.isRoad(x, y - 1)) {
				this.y = moveNorth();
				this.direction = "north";
				this.moving = true;
			} else if (roadMap.isRoad(x, y + 1)) {
				this.y = moveSouth();
				this.direction = "south";
				this.moving = true;

			} else if (roadMap.isRoad(x + 1, y)) {
				this.x = moveEast();
				this.direction = "east";
				this.moving = true;

			} else if (roadMap.isRoad(x - 1, y)) {
				this.x = moveWest();
				this.direction = "west";
				this.moving = true;

			} else {
				this.direction = "null";
				this.moving = false;
			}
			System.out.println(getX());
			System.out.println(getY());
			return;

		} else if (this.moving) {
			System.out.println("in motion");
			if ("north".equals(this.direction)) {
				// should try to go north then try to take right turn east then try west then
				// try south
				if (roadMap.isRoad(x, y - 1)) {
					this.y = moveNorth();
					this.direction = "north";
					return;
				} else if (roadMap.isRoad(x + 1, y)) {
					this.x = moveEast();
					this.direction = "east";
					return;

				} else if (roadMap.isRoad(x - 1, y)) {
					this.x = moveWest();
					this.direction = "west";
					return;

				} else if (roadMap.isRoad(x, y + 1)) {
					this.y = moveSouth();
					this.direction = "south";
					return;
				}
				this.moving = false;
				return;
			}

			if ("east".equals(this.direction)) {
				// should try east then south then north then west
				if (roadMap.isRoad(x + 1, y)) {
					this.x = moveEast();
					this.direction = "east";
					return;
				} else if (roadMap.isRoad(x, y + 1)) {
					this.y = moveSouth();
					this.direction = "south";
					return;

				} else if (roadMap.isRoad(x, y - 1)) {
					this.y = moveNorth();
					this.direction = "north";
					return;
				} else if (roadMap.isRoad(x - 1, y)) {
					this.x = moveWest();
					this.direction = "west";
					return;
				}
				this.moving = false;
				return;

			}
			if ("south".equals(this.direction)) {
				// should try south then west then east then norht
				if (roadMap.isRoad(x, y + 1)) {
					this.y = moveSouth();
					this.direction = "south";
					this.moving = true;
					return;
				} else if (roadMap.isRoad(x - 1, y)) {
					this.x = moveWest();
					this.direction = "west";
					return;
				} else if (roadMap.isRoad(x + 1, y)) {
					this.x = moveEast();
					this.direction = "east";
					return;

				} else if (roadMap.isRoad(x, y - 1)) {
					this.y = moveNorth();
					this.direction = "north";
					return;
				}
				this.moving = false;
				return;
			}

			if ("west".equals(this.direction)) {
				// should try west then north then south then east
				if (roadMap.isRoad(x - 1, y)) {
					this.x = moveWest();
					this.direction = "west";
					this.moving = true;
					return;
				} else if (roadMap.isRoad(x, y - 1)) {
					this.y = moveNorth();
					this.direction = "north";
					return;
				} else if (roadMap.isRoad(x, y + 1)) {
					this.y = moveSouth();
					this.direction = "south";
					return;

				} else if (roadMap.isRoad(x + 1, y)) {
					this.x = moveEast();
					this.direction = "east";
					return;

				}
				this.moving = false;
				return;

			}

			System.out.println(getX());
			System.out.println(getY());
			return;
		}

	}
}
