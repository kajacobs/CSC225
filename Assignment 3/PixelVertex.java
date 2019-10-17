/* PixelVertex.java
   CSC 225 - Summer 2019


   B. Bird - 04/28/2019
   Katherine Jacobs
   V00783178
   Last Modified: July 30th 2019
*/

import java.util.*;
import java.awt.Color;

public class PixelVertex{

	int x;
	int y;
	int degree;
	Color colour;
	boolean visited;
	ArrayList<PixelVertex> neighbs = new ArrayList<PixelVertex>();

	public PixelVertex(int x, int y, Color colour){
		this.x = x;
		this.y = y;
		this.degree = 0;
		this.visited = false;
		this.colour = colour;
	}

	public PixelVertex(int x, int y){
		this.x = x;
		this.y = y;
		this.visited = false;
		this.degree = 0;
	}
	/* Add a constructor here (if necessary) */


	/* getX()
	   Return the x-coordinate of the pixel corresponding to this vertex.
	*/
	public int getX(){
		return x;
	}

	/* getY()
	   Return the y-coordinate of the pixel corresponding to this vertex.
	*/
	public int getY(){
		return y;
	}

	/* getNeighbours()
	   Return an array containing references to all neighbours of this vertex.
	   The size of the array must be equal to the degree of this vertex (and
	   the array may therefore contain no duplicates).
	*/
	public PixelVertex[] getNeighbours(){
		PixelVertex[] myneighbours = new PixelVertex[neighbs.size()];
		for (int i=0; i<neighbs.size(); i++){
			myneighbours[i] = neighbs.get(i);
		}
		return myneighbours;
	}

	/* addNeighbour(newNeighbour)
	   Add the provided vertex as a neighbour of this vertex.
	*/
	public void addNeighbour(PixelVertex newNeighbour){
		neighbs.add(newNeighbour);
		degree++;
	}

	/* removeNeighbour(neighbour)
	   If the provided vertex object is a neighbour of this vertex,
	   remove it from the list of neighbours.
	*/
	public void removeNeighbour(PixelVertex neighbour){
		for (int i=0; i<neighbs.size(); i++){
			if (neighbour.x == neighbs.get(i).x){
				if(neighbour.y == neighbs.get(i).y){
				neighbs.remove(i);
				degree--;
				}
			}
		}
	}

	/* getDegree()
	   Return the degree of this vertex. Since the graph is simple,
	   the degree is equal to the number of distinct neighbours of this vertex.
	*/
	public int getDegree(){
		return degree;
	}

	/* isNeighbour(otherVertex)
	   Return true if the provided PixelVertex object is a neighbour of this
	   vertex and false otherwise.
	*/
	public boolean isNeighbour(PixelVertex otherVertex){
		for (int i=0; i<neighbs.size(); i++){
			if (otherVertex.x == neighbs.get(i).x){
				if(otherVertex.y == neighbs.get(i).y){
				return true;
				}
			}
		}
		return false;
	}

}
