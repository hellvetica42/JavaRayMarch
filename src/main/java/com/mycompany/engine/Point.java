/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.engine;

import javax.transaction.xa.Xid;

/**
 *
 * @author Petar
 */
public class Point {
    protected Transform t = new Transform();

    public Point() {
    }
    public Point(Transform t){
        this.t = t;
    }
    public Point(double x, double y, double z){
        t.x = x;
        t.y = y;
        t.z = z;
    }
    public Point(double[] c){
        if(c.length != 3){
            System.err.println("Point dimension mismatch");
            return;
        }
        
        t.x = c[0];
        t.y = c[1];
        t.z = c[2];
    }
    
    public double distance(Point p){
        return Math.sqrt(Math.pow(p.t.x - t.x, 2) + Math.pow(p.t.y - t.y, 2) + Math.pow(p.t.z - t.z, 2));
    }
}
