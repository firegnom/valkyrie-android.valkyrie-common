/*******************************************************************************
 * Copyright (c) 2010 Maciej Kaniewski (mk@firegnom.com).
 * 
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 3 of the License, or
 *    (at your option) any later version.
 * 
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 * 
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software Foundation,
 *    Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 * 
 *    Contributors:
 *     Maciej Kaniewski (mk@firegnom.com) - initial API and implementation
 ******************************************************************************/
package com.firegnom.valkyrie.map.pathfinding;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import com.firegnom.valkyrie.map.Position;
import com.firegnom.valkyrie.map.pathfinding.heuristics.ClosestHeuristic;

// TODO: Auto-generated Javadoc
/**
 * A path finder implementation that uses the AStar heuristic based algorithm to
 * determine a path.
 * 
 * @author Kevin Glass
 */
public class AStarPathFinder implements PathFinder, Serializable {

	/**
	 * A single node in the search graph.
	 */
	private class Node implements Comparable, Serializable {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/** The x coordinate of the node. */
		private final int x;

		/** The y coordinate of the node. */
		private final int y;

		/** The path cost for this node. */
		private float cost;

		/** The parent of this node, how we reached it in the search. */
		private Node parent;

		/** The heuristic cost of this node. */
		private float heuristic;

		/** The search depth of this node. */
		private int depth;

		/**
		 * Create a new node.
		 * 
		 * @param x
		 *            The x coordinate of the node
		 * @param y
		 *            The y coordinate of the node
		 */
		public Node(final int x, final int y) {
			this.x = x;
			this.y = y;
		}

		/**
		 * Compare to.
		 * 
		 * @param other
		 *            the other
		 * @return the int
		 * @see Comparable#compareTo(Object)
		 */
		public int compareTo(final Object other) {
			final Node o = (Node) other;

			final float f = heuristic + cost;
			final float of = o.heuristic + o.cost;

			if (f < of) {
				return -1;
			} else if (f > of) {
				return 1;
			} else {
				return 0;
			}
		}

		/**
		 * Set the parent of this node.
		 * 
		 * @param parent
		 *            The parent node which lead us to this node
		 * @return The depth we have no reached in searching
		 */
		public int setParent(final Node parent) {
			depth = parent.depth + 1;
			this.parent = parent;

			return depth;
		}
	}

	/**
	 * A simple sorted list.
	 * 
	 * @author kevin
	 */
	private class SortedList implements Serializable {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/** The list of elements. */
		private final LinkedList<Node> list = new LinkedList();

		/**
		 * Add an element to the list - causes sorting.
		 * 
		 * @param o
		 *            The element to add
		 */
		public void add(final Node o) {
			for (int i = 0; i < size(); i++) {
				if (list.get(i).compareTo(o) > 0) {
					list.add(i, o);
					return;
				}
			}
			list.add(o);
			return;
		}

		/**
		 * Empty the list.
		 */
		public void clear() {
			list.clear();
		}

		/**
		 * Check if an element is in the list.
		 * 
		 * @param o
		 *            The element to search for
		 * @return True if the element is in the list
		 */
		public boolean contains(final Object o) {
			return list.contains(o);
		}

		/**
		 * Retrieve the first element from the list.
		 * 
		 * @return The first element from the list
		 */
		public Object first() {
			return list.get(0);
		}

		/**
		 * Remove an element from the list.
		 * 
		 * @param o
		 *            The element to remove
		 */
		public void remove(final Object o) {
			list.remove(o);
		}

		/**
		 * Get the number of elements in the list.
		 * 
		 * @return The number of element in the list
		 */
		public int size() {
			return list.size();
		}
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The set of nodes that have been searched through. */
	private final LinkedList<Node> closed = new LinkedList<Node>();

	/** The set of nodes that we do not yet consider fully searched. */
	private final SortedList open = new SortedList();

	/** The map being searched. */
	private final Pathfindable map;

	/** The maximum depth of search we're willing to accept before giving up. */
	private int maxSearchDistance;

	/** The complete set of nodes across the map. */
	private Node[][] nodes;

	/** True if we allow diaganol movement. */
	private boolean allowDiagMovement;

	/** The heuristic we're applying to determine which nodes to search first. */
	private AStarHeuristic heuristic;

	/** The max complicity. */
	private int maxComplicity;

	/**
	 * Instantiates a new a star path finder.
	 * 
	 * @param map
	 *            the map
	 */
	public AStarPathFinder(final Pathfindable map) {
		this.map = map;
	}

	/**
	 * Create a path finder with the default heuristic - closest to target.
	 * 
	 * @param map
	 *            The map to be searched
	 * @param maxSearchDistance
	 *            The maximum depth we'll search before giving up
	 * @param maxComplicity
	 *            the max complicity
	 * @param allowDiagMovement
	 *            True if the search should try diaganol movement
	 */
	public AStarPathFinder(final Pathfindable map, final int maxSearchDistance,
			final int maxComplicity, final boolean allowDiagMovement) {
		this(map, maxSearchDistance, maxComplicity, allowDiagMovement,
				new ClosestHeuristic());
	}

	/**
	 * Create a path finder.
	 * 
	 * @param map
	 *            The map to be searched
	 * @param maxSearchDistance
	 *            The maximum depth we'll search before giving up
	 * @param maxComplicity
	 *            the max complicity
	 * @param allowDiagMovement
	 *            True if the search should try diaganol movement
	 * @param heuristic
	 *            The heuristic used to determine the search order of the map
	 */
	public AStarPathFinder(final Pathfindable map, final int maxSearchDistance,
			final int maxComplicity, final boolean allowDiagMovement,
			final AStarHeuristic heuristic) {
		this.heuristic = heuristic;
		this.map = map;
		this.maxSearchDistance = maxSearchDistance;
		this.allowDiagMovement = allowDiagMovement;
		this.maxComplicity = maxComplicity;

		nodes = new Node[map.getWidthInTiles()][map.getHeightInTiles()];
		for (int x = 0; x < map.getWidthInTiles(); x++) {
			for (int y = 0; y < map.getHeightInTiles(); y++) {
				nodes[x][y] = new Node(x, y);
			}
		}
	}

	/**
	 * Add a node to the closed list.
	 * 
	 * @param node
	 *            The node to add to the closed list
	 */
	protected void addToClosed(final Node node) {
		closed.add(node);
	}

	/**
	 * Add a node to the open list.
	 * 
	 * @param node
	 *            The node to be added to the open list
	 */
	protected void addToOpen(final Node node) {
		open.add(node);
	}

	/**
	 * Find path.
	 * 
	 * @param mover
	 *            the mover
	 * @param sx
	 *            the sx
	 * @param sy
	 *            the sy
	 * @param tx
	 *            the tx
	 * @param ty
	 *            the ty
	 * @return the path
	 * @see PathFinder#findPath(Mover, int, int, int, int)
	 */
	public Path findPath(final Mover mover, final int sx, final int sy,
			final int tx, final int ty) {
		nodes = new Node[map.getWidthInTiles()][map.getHeightInTiles()];
		for (int x = 0; x < map.getWidthInTiles(); x++) {
			for (int y = 0; y < map.getHeightInTiles(); y++) {
				nodes[x][y] = new Node(x, y);
			}
		}
		int complicity = 0;
		// long start = System.currentTimeMillis();
		// long stop = System.currentTimeMillis();
		// time += stop - start;
		// easy first check, if the destination is blocked, we can't get there
		final int widthInTiles = map.getWidthInTiles();
		final int heightInTiles = map.getHeightInTiles();
		if (tx >= widthInTiles || ty >= heightInTiles || tx < 0 || ty < 0
				|| map.blocked(mover, tx, ty)) {
			return null;
		}

		// initial state for A*. The closed group is empty. Only the starting
		// tile is in the open list and it's cost is zero, i.e. we're already
		// there
		nodes[sx][sy].cost = 0;
		nodes[sx][sy].depth = 0;
		closed.clear();
		open.clear();
		open.add(nodes[sx][sy]);

		nodes[tx][ty].parent = null;

		// while we haven't found the goal and haven't exceeded our max search
		// depth
		int maxDepth = 0;
		while ((maxDepth < maxSearchDistance) && (open.size() != 0)
				&& complicity < maxComplicity) {
			// pull out the first node in our open list, this is determined to
			// be the most likely to be the next step based on our heuristic
			final Node current = getFirstInOpen();
			if (current == nodes[tx][ty]) {
				break;
			}

			removeFromOpen(current);
			addToClosed(current);

			// search through all the neighbours of the current node evaluating
			// them as next steps
			int xp, yp, x, y;
			Node neighbour;
			float nextStepCost;

			for (x = -1; x < 2; x++) {
				for (y = -1; y < 2; y++) {

					// not a neighbour, its the current tile
					if ((x == 0) && (y == 0)) {
						continue;
					}

					// if we're not allowing diaganol movement then only
					// one of x or y can be set
					if (!allowDiagMovement) {
						if ((x != 0) && (y != 0)) {
							continue;
						}
					}

					// determine the location of the neighbour and evaluate it
					xp = x + current.x;
					yp = y + current.y;

					if (isValidLocation(mover, sx, sy, xp, yp, widthInTiles,
							heightInTiles)) {
						// the cost to get to this node is cost the current plus
						// the movement
						// cost to reach this node. Note that the heursitic
						// value is only used
						// in the sorted open list
						nextStepCost = current.cost;// + getMovementCost(mover,
													// current.x, current.y, xp,
													// yp);
						neighbour = nodes[xp][yp];
						// map.pathFinderVisited(xp, yp);

						// if the new cost we've determined for this node is
						// lower than
						// it has been previously makes sure the node hasn't
						// been discarded. We've
						// determined that there might have been a better path
						// to get to
						// this node so it needs to be re-evaluated

						final boolean inClosedList = inClosedList(neighbour);
						final boolean inOpenList = inOpenList(neighbour);
						if (nextStepCost < neighbour.cost) {
							if (inOpenList) {
								removeFromOpen(neighbour);

							}
							if (inClosedList) {
								removeFromClosed(neighbour);
							}
						}

						// if the node hasn't already been processed and
						// discarded then
						// reset it's cost to our current cost and add it as a
						// next possible
						// step (i.e. to the open list)
						if (!inOpenList && !inClosedList) {
							complicity++;
							neighbour.cost = nextStepCost;
							neighbour.heuristic = getHeuristicCost(mover, xp,
									yp, tx, ty);
							maxDepth = Math.max(maxDepth,
									neighbour.setParent(current));
							addToOpen(neighbour);
						}

					}
				}
			}
		}

		// since we've got an empty open list or we've run out of search
		// there was no path. Just return null
		if (nodes[tx][ty].parent == null) {
			return null;
		}

		// At this point we've definitely found a path so we can uses the parent
		// references of the nodes to find out way from the target location back
		// to the start recording the nodes on the way.
		final Path path = new Path();
		Node target = nodes[tx][ty];
		while (target != nodes[sx][sy]) {
			path.prependStep((short) (target.x), (short) (target.y));
			target = target.parent;
		}
		path.prependStep((short) sx, (short) sy);

		// thats it, we have our path
		return path;
	}

	/**
	 * Find range.
	 * 
	 * @param mover
	 *            the mover
	 * @param sx
	 *            the sx
	 * @param sy
	 *            the sy
	 * @param maxSearchDistance
	 *            the max search distance
	 * @return the set
	 */
	public Set<Position> findRange(final Mover mover, final int sx,
			final int sy, final int maxSearchDistance) {
		this.maxSearchDistance = maxSearchDistance;
		nodes = new Node[map.getWidthInTiles()][map.getHeightInTiles()];
		for (int x = 0; x < map.getWidthInTiles(); x++) {
			for (int y = 0; y < map.getHeightInTiles(); y++) {
				nodes[x][y] = new Node(x, y);
			}
		}
		final HashSet<Position> path = new HashSet<Position>();
		final int widthInTiles = map.getWidthInTiles();
		final int heightInTiles = map.getHeightInTiles();
		closed.clear();
		open.clear();
		open.add(nodes[sx][sy]);

		// while we haven't found the goal and haven't exceeded our max search
		// depth
		int maxDepth = 0;

		while ((maxDepth < maxSearchDistance + 1) && (open.size() != 0)) {
			// pull out the first node in our open list, this is determined to
			// be the most likely to be the next step based on our heuristic
			final Node current = getFirstInOpen();

			removeFromOpen(current);
			addToClosed(current);

			// search through all the neighbours of the current node evaluating
			// them as next steps
			int xp, yp, x, y;
			Node neighbour;

			for (x = -1; x < 2; x++) {
				for (y = -1; y < 2; y++) {

					// not a neighbour, its the current tile
					if ((x == 0) && (y == 0)) {
						continue;
					}

					// if we're not allowing diaganol movement then only
					// one of x or y can be set
					if ((x != 0) && (y != 0)) {
						continue;
					}

					// determine the location of the neighbour and evaluate it
					xp = x + current.x;
					yp = y + current.y;

					if (isValidLocation(mover, sx, sy, xp, yp, widthInTiles,
							heightInTiles)) {
						// the cost to get to this node is cost the current plus
						// the movement
						// cost to reach this node. Note that the heursitic
						// value is only used
						// in the sorted open list

						neighbour = nodes[xp][yp];

						// map.pathFinderVisited(xp, yp);

						// if the new cost we've determined for this node is
						// lower than
						// it has been previously makes sure the node hasn't
						// been discarded. We've
						// determined that there might have been a better path
						// to get to
						// this node so it needs to be re-evaluated

						final boolean inClosedList = inClosedList(neighbour);
						final boolean inOpenList = inOpenList(neighbour);

						// if (nextStepCost < neighbour.cost) {
						// if (inOpenList) {
						// removeFromOpen(neighbour);
						//
						// }
						// if (inClosedList) {
						// removeFromClosed(neighbour);
						// }
						// }

						// if the node hasn't already been processed and
						// discarded then
						// reset it's cost to our current cost and add it as a
						// next possible
						// step (i.e. to the open list)

						if (!inOpenList && !inClosedList) {
							maxDepth = Math.max(maxDepth,
									neighbour.setParent(current));
							if (maxDepth <= maxSearchDistance) {
								path.add(new Position(xp, yp));
							}
							addToOpen(neighbour);

						}
						// maxDepth++;

					}
				}
			}
		}

		// since we've got an empty open list or we've run out of search
		// there was no path. Just return null
		// if (nodes[tx][ty].parent == null) {
		// return null;
		// }

		// At this point we've definitely found a path so we can uses the parent
		// references of the nodes to find out way from the target location back
		// to the start recording the nodes on the way.
		// Node target = nodes[tx][ty];
		// while (target != nodes[sx][sy]) {
		// path.add((short)(target.x), (short)(target.y));
		// target = target.parent;
		// }

		return path;

		// return null;
	}

	/**
	 * Get the first element from the open list. This is the next one to be
	 * searched.
	 * 
	 * @return The first element in the open list
	 */
	protected Node getFirstInOpen() {
		return (Node) open.first();
	}

	/**
	 * Get the heuristic cost for the given location. This determines in which
	 * order the locations are processed.
	 * 
	 * @param mover
	 *            The entity that is being moved
	 * @param x
	 *            The x coordinate of the tile whose cost is being determined
	 * @param y
	 *            The y coordiante of the tile whose cost is being determined
	 * @param tx
	 *            The x coordinate of the target location
	 * @param ty
	 *            The y coordinate of the target location
	 * @return The heuristic cost assigned to the tile
	 */
	public float getHeuristicCost(final Mover mover, final int x, final int y,
			final int tx, final int ty) {
		return heuristic.getCost(map, mover, x, y, tx, ty);
	}

	/**
	 * Get the cost to move through a given location.
	 * 
	 * @param mover
	 *            The entity that is being moved
	 * @param sx
	 *            The x coordinate of the tile whose cost is being determined
	 * @param sy
	 *            The y coordiante of the tile whose cost is being determined
	 * @param tx
	 *            The x coordinate of the target location
	 * @param ty
	 *            The y coordinate of the target location
	 * @return The cost of movement through the given tile
	 */
	public float getMovementCost(final Mover mover, final int sx, final int sy,
			final int tx, final int ty) {
		return map.getCost(mover, sx, sy, tx, ty);
	}

	/**
	 * Check if the node supplied is in the closed list.
	 * 
	 * @param node
	 *            The node to search for
	 * @return True if the node specified is in the closed list
	 */
	protected boolean inClosedList(final Node node) {
		return closed.contains(node);
	}

	/**
	 * Check if a node is in the open list.
	 * 
	 * @param node
	 *            The node to check for
	 * @return True if the node given is in the open list
	 */
	protected boolean inOpenList(final Node node) {
		return open.contains(node);
	}

	/**
	 * Check if a given location is valid for the supplied mover.
	 * 
	 * @param mover
	 *            The mover that would hold a given location
	 * @param sx
	 *            The starting x coordinate
	 * @param sy
	 *            The starting y coordinate
	 * @param x
	 *            The x coordinate of the location to check
	 * @param y
	 *            The y coordinate of the location to check
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @return True if the location is valid for the given mover
	 */
	protected boolean isValidLocation(final Mover mover, final int sx,
			final int sy, final int x, final int y, final int width,
			final int height) {
		boolean invalid = (x < 0) || (y < 0) || (x >= width) || (y >= height);

		if ((!invalid) && ((sx != x) || (sy != y))) {
			invalid = map.blocked(mover, x, y);
		}
		return !invalid;
	}

	/**
	 * Remove a node from the closed list.
	 * 
	 * @param node
	 *            The node to remove from the closed list
	 */
	protected void removeFromClosed(final Node node) {
		closed.remove(node);
	}

	/**
	 * Remove a node from the open list.
	 * 
	 * @param node
	 *            The node to remove from the open list
	 */
	protected void removeFromOpen(final Node node) {
		open.remove(node);
	}
}
