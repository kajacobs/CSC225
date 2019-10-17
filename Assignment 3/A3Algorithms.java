/* A3Algorithms.java
   CSC 225 - Summer 2019


   B. Bird - 04/28/2019
   Katherine Jacobs
   V00783178
   Last Modified: July 30 2019
*/

import java.awt.Color;
import java.util.*;

public class A3Algorithms{

	/* FloodFillDFS(v, writer, fillColour)
	   Traverse the component the vertex v using DFS and set the colour
	   of the pixels corresponding to all vertices encountered during the
	   traversal to fillColour.

	   To change the colour of a pixel at position (x,y) in the image to a
	   colour c, use
			writer.setPixel(x,y,c);
	*/
	public static void FloodFillDFS(PixelVertex v, PixelWriter writer, Color fillColour){
		/* Your code here */
		Stack<PixelVertex> dfs = new Stack<PixelVertex>();
		PixelVertex first = v;

		v.visited = true;
		writer.setPixel(v.x,v.y,fillColour);
		dfs.push(v);

		while (!dfs.empty()){
			v = dfs.pop();
			if (v.visited == false){
				v.visited = true;
				writer.setPixel(v.x,v.y,fillColour);
			}
			PixelVertex[] vertices = v.getNeighbours();
			for (int i=0; i < vertices.length; i++){
				if (vertices[i].visited== false){
					dfs.push(vertices[i]);
				}
			}
		}

		boolFalse(first);

	}

	/*
	boolFalse resets each PixelVertex visited value to false
	*/
	public static void boolFalse(PixelVertex v){
		Queue<PixelVertex> bool = new LinkedList<PixelVertex>();

		bool.add(v);
		while (!bool.isEmpty()){
			v = bool.poll();
			v.visited = false;
			PixelVertex[] vertices = v.getNeighbours();
			for (int i=0; i < vertices.length; i++){
				if (vertices[i].visited == true){
					vertices[i].visited = false;
					bool.add(vertices[i]);
				}

			}
		}
	}


	/* FloodFillBFS(v, writer, fillColour)
	   Traverse the component the vertex v using BFS and set the colour
	   of the pixels corresponding to all vertices encountered during the
	   traversal to fillColour.

	   To change the colour of a pixel at position (x,y) in the image to a
	   colour c, use
			writer.setPixel(x,y,c);
	*/
	public static void FloodFillBFS(PixelVertex v, PixelWriter writer, Color fillColour){
		/* Your code here */

		Queue<PixelVertex> bfs = new LinkedList<PixelVertex>();
		PixelVertex first = v;

		bfs.add(v);
		v.visited = true;
		writer.setPixel(v.x,v.y,fillColour);
		while (!bfs.isEmpty()){
			v = bfs.poll();
			PixelVertex[] vertices = v.getNeighbours();
			for (int i=0; i < vertices.length; i++){
				if (vertices[i].visited == false){;
					vertices[i].visited = true;
					writer.setPixel(vertices[i].x,vertices[i].y,fillColour);
					bfs.add(vertices[i]);
				}
			}
		}

		boolFalse(first);
	}


	/* OutlineRegionDFS(v, writer, outlineColour)
	   Traverse the component the vertex v using DFS and set the colour
	   of the pixels corresponding to all vertices with degree less than 4
	   encountered during the traversal to outlineColour.

	   To change the colour of a pixel at position (x,y) in the image to a
	   colour c, use
			writer.setPixel(x,y,c);
	*/
	public static void OutlineRegionDFS(PixelVertex v, PixelWriter writer, Color outlineColour){
		/* Your code here */
		Stack<PixelVertex> dfs = new Stack<PixelVertex>();
		PixelVertex first = v;

		if (v.degree < 4){
			writer.setPixel(v.x,v.y,outlineColour);
		}
		v.visited = true;
		dfs.push(v);

		while (!dfs.empty()){
			v = dfs.pop();
			if (v.visited == false){
				v.visited = true;
			}
			if (v.degree < 4){
				writer.setPixel(v.x,v.y,outlineColour);
			}
			PixelVertex[] vertices = v.getNeighbours();
			for (int i=0; i < vertices.length; i++){
				if (vertices[i].visited== false){
					dfs.push(vertices[i]);
				}
			}
		}

		boolFalse(first);
	}


	/* OutlineRegionBFS(v, writer, outlineColour)
	   Traverse the component the vertex v using BFS and set the colour
	   of the pixels corresponding to all vertices with degree less than 4
	   encountered during the traversal to outlineColour.

	   To change the colour of a pixel at position (x,y) in the image to a
	   colour c, use
			writer.setPixel(x,y,c);
	*/
	public static void OutlineRegionBFS(PixelVertex v, PixelWriter writer, Color outlineColour){
		/* Your code here */
		Queue<PixelVertex> bfsoutline = new LinkedList<PixelVertex>();
		PixelVertex first = v;

		bfsoutline.add(v);
		v.visited = true;
		if (v.degree < 4){
			writer.setPixel(v.x,v.y,outlineColour);
		}
		while (!bfsoutline.isEmpty()){
			v = bfsoutline.poll();
			PixelVertex[] vertices = v.getNeighbours();
			for (int i=0; i < vertices.length; i++){
				if (vertices[i].visited == false){;
					vertices[i].visited = true;
					if (vertices[i].degree < 4){
						writer.setPixel(vertices[i].x,vertices[i].y,outlineColour);
					}
					bfsoutline.add(vertices[i]);
				}
			}
		}

		boolFalse(first);
	}

	/* CountComponents(G)
	   Count the number of connected components in the provided PixelGraph
	   object.
	*/
	public static int CountComponents(PixelGraph G){
		/* Your code here */
		int count = 0;
		Queue<PixelVertex> bfs = new LinkedList<PixelVertex>();

		for(int i=0; i< G.mynodes.length; i++){
			for(int j=0; j < G.mynodes[i].length; j++){

			PixelVertex v = G.mynodes[i][j];

			if (v.visited != true){
				v.visited = true;
				bfs.add(v);
				count++;
			}
				while (!bfs.isEmpty()){
					v = bfs.poll();
					PixelVertex[] vertices = v.getNeighbours();
					for (int k=0; k < vertices.length; k++){
						if (vertices[k].visited == false){
							vertices[k].visited = true;
							bfs.add(vertices[k]);
						}
					}

				} // end of while
			} // end of inner for loop
		} // end of outer for loop
		clearBool(G);
		return count;
	}

	/*
	clearBool returns all PixelVertex visited values back to false after
	calling Count Components
	*/
	public static void clearBool(PixelGraph G){

		Queue<PixelVertex> bfs = new LinkedList<PixelVertex>();
		for(int i=0; i< G.mynodes.length; i++){
			for(int j=0; j < G.mynodes[i].length; j++){

			PixelVertex v = G.mynodes[i][j];

			if (v.visited != false){
				v.visited = false;
				bfs.add(v);
			}
				while (!bfs.isEmpty()){
					v = bfs.poll();
					PixelVertex[] vertices = v.getNeighbours();
					for (int k=0; k < vertices.length; k++){
						if (vertices[k].visited == true){
							vertices[k].visited = false;
							bfs.add(vertices[k]);
						}
					}

				} //end of while
			} //end of inner for loop
		} // end of outer for loop

	}

}
