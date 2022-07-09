package components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import actors.Actor;
import actors.Grid;
import actors.Tile;
import math.Vector2;

public class PathFindingComponent extends Component {

	private Grid mGrid;
	private Tile mFrom, mTo;
	private ArrayList<Tile> mPath;
	private int mCurrentTile;

	private double[] g;
	private double[] h;
	private double[] f;
	private boolean[] inClosedSet;
	private boolean[] inOpenSet;
	private Tile[] parent;

	public PathFindingComponent(Actor owner, Grid grid) {
		super(owner);
		mPath = new ArrayList<>();
		this.mGrid = grid;
		mCurrentTile = Integer.MAX_VALUE;
		int nTiles = mGrid.numberOfTiles();
		g = new double[nTiles];
		h = new double[nTiles];
		f = new double[nTiles];
		inClosedSet = new boolean[nTiles];
		inOpenSet = new boolean[nTiles];
		parent = new Tile[nTiles];
	}

	public void setFromTile(Tile from) {
		mPath.clear();
		mCurrentTile = Integer.MAX_VALUE;
		mFrom = from;
	}

	public void setToTile(Tile to) {
		mPath.clear();
		mCurrentTile = Integer.MAX_VALUE;
		mTo = to;
	}

	public boolean findPath() {
		mPath.clear();
		Arrays.fill(g, 0.0);
		Arrays.fill(inClosedSet, false);
		Arrays.fill(inOpenSet, false);
		Arrays.fill(parent, null);

		ArrayList<Tile> openSet = new ArrayList<>();
		ArrayList<Tile> neighbours = new ArrayList<>();
		Tile current = mFrom;
		inClosedSet[current.getTileNum()] = true;

		do {
			mGrid.getAdjacentTiles(current, neighbours);
			for (Tile neighbor : neighbours) {
				if (neighbor.isBlocked())
					continue;
				int tnum = neighbor.getTileNum();
				if (!inClosedSet[tnum]) {
					if (!inOpenSet[tnum]) {
						parent[tnum] = current;
						h[tnum] = Vector2.sub(mTo.getPosition(), neighbor.getPosition()).length();
						g[tnum] = g[current.getTileNum()] + mGrid.TILE_SIZE;
						f[tnum] = g[tnum] + h[tnum];
						openSet.add(neighbor);
						inOpenSet[tnum] = true;
					} else {
						double newG = g[current.getTileNum()] + mGrid.TILE_SIZE;
						if (newG < g[tnum]) {
							parent[tnum] = current;
							g[tnum] = newG;
							f[tnum] = g[tnum] + h[tnum];
						}
					}
				}
			}
			if (openSet.isEmpty())
				break;
			Tile t = openSet.get(0);
			for (Tile _t : openSet) {
				if (h[_t.getTileNum()] < h[t.getTileNum()]) {
					t = _t;
				}
			}
			current = t;
			openSet.remove(t);
			inOpenSet[current.getTileNum()] = false;
			inClosedSet[current.getTileNum()] = true;
		} while (current != mTo);
		
		// reconstruct path
		if(current == mTo) {
			Tile t = mTo;
			do{
				mPath.add(t);
				t = parent[t.getTileNum()];
			}while(t!=mFrom);
			Collections.reverse(mPath);
			mCurrentTile = 0;
			return true;
		}
		mCurrentTile = Integer.MAX_VALUE;
		return false;
	}
	
	public Tile moveNext() {
		if(mCurrentTile < mPath.size()) {
			return mPath.get(mCurrentTile++);
		}
		return null;
	}
	
	public boolean hasReached() {
		return mCurrentTile == mPath.size();
	}
}
