/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Petar
 */
import com.mycompany.engine.Plane;
import com.mycompany.engine.Point;
import com.mycompany.engine.Vector;

public class PlaneDistanceTest {
    public static void main(String[] args){
        Plane p1 = new Plane(new Vector(3, 1, -4), new Point(0, 0, -1.0/2.0));
        Plane p2 = new Plane(new Vector(3, 1, -4), new Point(0.0, 0.0, -6));
        
        
        System.out.println("p1 : " + p1.toString());
        System.out.println("p2 : " + p2.toString());
        System.out.println("Planes are paralell : " + p1.isParalell(p2));
        
        System.out.println("Distance between p1 and p2 = " + p1.getDistance(p2));
    }
}
