 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.engine;

import java.awt.Color;
import java.lang.System.*;
/**
 *
 * @author Petar
 */
public class Sphere extends Geometry{
    public Transform t;
    double radius;
    Color color;
    public Sphere(Transform pos, double radius, Color color){
        this.t = pos;
        this.radius = radius;
        this.color = color;
    }
    public Sphere(Transform pos, double radius) {
        this(pos, radius, Color.BLUE);
    }
    
    @Override
    public double getDistance(Point point){
        double dis = this.t.distance(point.t) - radius;
        //double dis = this.t.distance(point.t) - radius*Math.abs(Math.sin((double)(PixelCanvas.frameNum)/10.0)) + 0.5;
        if(Constants.SPHERE_DISTANCE_VERBOSE){
            System.out.printf("Distance from %s to sphere %s is %.2f \n", point.t.toString(), this.t.toString(), dis);
        }
        return dis;
    }
    
    @Override
    public Color getColor(){
        return color;
    }
    
    @Override
    public Transform transform(){
        return this.t;
    }
    
}
