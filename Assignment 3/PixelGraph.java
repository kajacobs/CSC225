/* PixelGraph.java
   CSC 225 - Summer 2019

   B. Bird - 04/28/2019
   Katherine Jacobs
   V00783178
   Last Modified: July 30 2019
*/

import java.awt.Color;

public class PixelGraph{

	int height;
	int width;
	PixelVertex[][] mynodes;

	/* PixelGraph constructor
	   Given a 2d array of colour values (where element [x][y] is the colour
	   of the pixel at position (x,y) in the image), initialize the data
	   structure to contain the pixel graph of the image.
	*/
	public PixelGraph(Color[][] imagePixels){
		this.height = imagePixels.length;
		this.width = imagePixels[0].length;


		//Initializing a PixelVertex Matrix
		this.mynodes = new PixelVertex[height][width];
		for(int i=0; i<imagePixels.length; i++){
			for(int j=0; j < imagePixels[i].length; j++){
				mynodes[i][j] = new PixelVertex(i, j, imagePixels[i][j]);
			}
		}

		//Creating all the connections between vertices
		for(int i=0; i< mynodes.length; i++){
			for(int j=0; j < mynodes[i].length; j++){
				//checking down
				if (i < mynodes.length-1 && mynodes[i][j].colour.equals(mynodes[i+1][j].colour)){
					mynodes[i][j].addNeighbour(mynodes[i+1][j]);
				} //checking right
				if (j < mynodes[i].length-1 && mynodes[i][j].colour.equals(mynodes[i][j+1].colour)){
					mynodes[i][j].addNeighbour(mynodes[i][j+1]);
				} //checking up
				if (i > 0 && mynodes[i][j].colour.equals(mynodes[i-1][j].colour)){
					mynodes[i][j].addNeighbour(mynodes[i-1][j]);
				} //checking left
				if (j > 0 && mynodes[i][j].colour.equals(mynodes[i][j-1].colour)){
					mynodes[i][j].addNeighbour(mynodes[i][j-1]);
				}
			}
		}

	}

	/* getPixelVertex(x,y)
	   Given an (x,y) coordinate pair, return the PixelVertex object
	   corresponding to the pixel at the provided coordinates.
	   This method is not required to perform any error checking (and you may
	   assume that the provided (x,y) pair is always a valid point in the
	   image).
	*/
	public PixelVertex getPixelVertex(int x, int y){
		return mynodes[x][y];
	}

	/* getWidth()
	   Return the width of the image corresponding to this PixelGraph
	   object.
	*/
	public int getWidth(){
		return this.width;
	}

	/* getHeight()
	   Return the height of the image corresponding to this PixelGraph
	   object.
	*/
	public int getHeight(){
		return this.height;
	}

}
