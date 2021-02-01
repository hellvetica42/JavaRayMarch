/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.engine;

import java.awt.Color;

/**
 *
 * @author Petar
 */
public class Plane extends Geometry{
    private final Vector normal;
    private final Point point;
    private final Color color;
    
    public Plane(Vector normal, Point point){
        this(normal, point, Color.BLUE);
    }
    public Plane(Vector normal, Point point, Color color){
        this.normal = normal.getUnitVector();
        this.point = point;
        this.color = color;
    }
    
    public double getDistance(Plane plane){
        double dis = 0;
        if(Constants.PLANES_DISTANCE_VERBOSE){
            System.out.println("Comparing\np1 = " +this.toString() + "\np2 = " + plane.toString());
        }
        if(!this.isParalell(plane)){
            System.err.println("Planes are not paralell");
            System.err.printf("p1 = %s \np2 = %s \n", this.toString(), plane.toString());
            return 0.0;
        }
                
        double top = Math.abs(plane.getNormal().t.x*point.t.x + plane.getNormal().t.y*point.t.y + plane.getNormal().t.z*point.t.z + plane.getD());
        
        double bot = Math.sqrt(Math.pow(plane.getNormal().t.x, 2) + Math.pow(plane.getNormal().t.y, 2) + Math.pow(plane.getNormal().t.z, 2));
        
        dis = top/bot;
        
        return dis;
    }
    
    @Override
    public double getDistance(Point point){
        Vector vector = new Vector(this.point, point);
        
        return vector.dotProduct(normal);
        //return  point.t.y -this.point.t.y;
    }
    
    public double getD(){
        return - (point.t.x*normal.t.x + point.t.y*normal.t.y + point.t.z*normal.t.z);
    }
    
    public Vector getNormal(){
        return normal;
    }
    
    public Point getPoint(){
        return point;
    }
    
    public boolean isParalell(Plane plane){
        return this.normal.areCollinear(plane.getNormal());
    }
    
    @Override
    public String toString(){
        return  String.format("%.2fx + %.2fy + %.2fz + %.2f = 0", this.getNormal().t.x, this.getNormal().t.y, this.getNormal().t.z, this.getD());
    }
    
    @Override
    public Color getColor(){
        return this.color;
    }
    
    @Override
    public Transform transform(){
        return this.point.t;
    }
}
