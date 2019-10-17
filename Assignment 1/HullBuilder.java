/* HullBuilder.java
   CSC 225 - Summer 2019

   Starter code for Convex Hull Builder. Do not change the signatures
   of any of the methods below (you may add other methods as needed).

   B. Bird - 03/18/2019
   Katherine Jacobs
   V00783178
   Last Modified: 12/06/2019
*/

import java.util.LinkedList;

public class HullBuilder{
    /*
      point_list: Linked list to hold set of all intake points
      hull_list: Linked list to hold entire hull
      upper_hull: Array that stores upper hull of Point2d objects from point_list
      lower_hull: Array that stores lower hull of Point2d objects from point_list
     */
    LinkedList point_list = new LinkedList();
    LinkedList hull_list = new LinkedList();

    // KAT!! REMINDER TO FIX THIS LATER, UNSURE OF SIZING FOR ARRAYS!!
    LinkedList upper_hull = new LinkedList();
    LinkedList lower_hull = new LinkedList();

    /*
        Default constructor for HullBuilder
     */
    public HullBuilder(){

    }

    /* addPoint(P)
       Add the point P to the internal point set for this object.
       Note that there is no facility to delete points (other than
       destroying the HullBuilder and creating a new one).
    */
    public void addPoint(Point2d P){
        Point2d temp = new Point2d();

        if(point_list.size() == 0){
            point_list.add(P);
        } else if (P.x >= ((Point2d)point_list.getLast()).x) {
            point_list.addLast(P);
        } else {
            for (int i = 0; i < point_list.size(); i++){
                temp = (Point2d) point_list.get(i);
                if (P.x < temp.x){
                    point_list.add(i, P);
                    break;
                }

            } // end of for loop
        } // end of else
    } //End of addPoint Method

    /* getHull()
       Return a java.util.LinkedList object containing the points
       in the convex hull, in order (such that iterating over the list
       will produce the same ordering of vertices as walking around the
       polygon).
       ** Note credit is given to the pseudocode from Dr. Bird's Slides,
       Application: Convex Hull  slide 101. I used this pseudocode to form
       this algorithm.
    */
    public LinkedList<Point2d> getHull(){
        hull_list.clear();
        upper_hull.clear();
        lower_hull.clear();

        Point2d p1 = new Point2d();
        Point2d p2 = new Point2d();
        Point2d p3 = new Point2d();


        int result = 0;
        int point_list_size = point_list.size();


        upper_hull.add(point_list.get(0));
        upper_hull.add(point_list.get(1));

        lower_hull.add(point_list.get(0));
        lower_hull.add(point_list.get(1));

        for (int i = 1; i < point_list_size; i++){
            while (upper_hull.size() >= 2){
            // Upper hull
                p1 = (Point2d) upper_hull.get(upper_hull.size()-2);
                p2 = (Point2d) upper_hull.get(upper_hull.size()-1);
                p3 = (Point2d) point_list.get(i);

                result = Point2d.chirality(p1, p2, p3);

                if (result > 0){
                    break;
                } else{
                    upper_hull.remove(upper_hull.size()-1);
                }
            } // end of while loop
            upper_hull.add(p3);
        } // end of for loop

        for(int i = 1; i < point_list_size; i++){
            while (lower_hull.size() >= 2){
                //Lower hull
                p1 = (Point2d) lower_hull.get(lower_hull.size()-2);
                p2 = (Point2d) lower_hull.get(lower_hull.size()-1);
                p3 = (Point2d) point_list.get(i);

                result = Point2d.chirality(p1, p2, p3);

                if (result < 0){
                    break;
                } else {
                    lower_hull.remove(lower_hull.size()-1);
                }
            } // end while
            lower_hull.add(p3);
        } // end for

        for (int i = 0; i < upper_hull.size(); i++){
            hull_list.add(upper_hull.get(i));
        }

        for (int i = 1; i <= lower_hull.size()-2; i++){
            hull_list.add(lower_hull.get(lower_hull.size()-(i+1)));
        }

        return hull_list;
    } // end of getHull method

    /* isInsideHull(P)
       Given an point P, return true if P lies inside the convex hull
       of the current point set (note that P may not be part of the
       current point set). Return false otherwise.
     */
    public boolean isInsideHull(Point2d P){

        hull_list.clear();
        hull_list = getHull();
        int hull_size = hull_list.size();
        int result = 0;

        Point2d p1 = new Point2d();
        Point2d p2 = new Point2d();

        for (int i = 0; i < (hull_size-1); i++){
            p1 = (Point2d) hull_list.get(i);
            p2 = (Point2d) hull_list.get(i+1);

            result = Point2d.chirality(p1, p2, P);

            if (result < 0 ){
                return false;
            }

        }

        p1 = (Point2d) hull_list.getLast();
        p2 = (Point2d) hull_list.getFirst();

        result = Point2d.chirality(p1, p2, P);
        if (result < 0 ){
            return false;
        }
        return true;
    } // end of isInsideHull method
} // end of HullBuilder Class
