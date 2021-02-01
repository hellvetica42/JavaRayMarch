/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.engine;

/**
 *
 * @author Petar
 */
public class Transform {
    public double x,y,z;
    public Transform(){
        this(0, 0, 0);
    }
    public Transform(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void translate(Vector v){
        this.x += v.t.x;
        this.y += v.t.y;
        this.z += v.t.z;
    }
    
    public double distance(Transform t){
        return Math.sqrt(Math.pow(this.x - t.x, 2) + Math.pow(this.y - t.y, 2) + Math.pow(this.z - t.z, 2));
    }
    
    @Override
    public String toString(){
        return String.format("%.2f, %.2f, %.2f", this.x, this.y, this.z);
    }
    
}
