package com.mycompany.engine;

import java.awt.Color;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Petar
 */
public class TestObject extends Geometry{
    public Transform t;
    double radius;
    Color color;
    public TestObject(Transform pos, double radius, Color color){
        this.t = pos;
        this.radius = radius;
        this.color = color;
    }
    public TestObject(Transform pos, double radius) {
        this(pos, radius, Color.BLUE);
    }
    
    @Override
    public double getDistance(Point point){
        Sphere sphere1 = new Sphere(new Transform(13,7,-20), 5, Color.RED);
        Sphere sphere2 = new Sphere(new Transform(13,8,30), 9, Color.ORANGE);
        double dis = Math.max(sphere1.getDistance(point), sphere2.getDistance(point));
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
