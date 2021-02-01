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
public class Vector {
    public Transform t = new Transform();
    public Vector(){
        this(0, 0, 0);
    }
    public Vector(Transform t1, Transform t2){
        this(new Point(t1), new Point(t2));
    }
    public Vector(double x, double y){
        this(x, y, 0.0);
    }
    public Vector(Point p1, Point p2){
        this(p2.t.x - p1.t.x, p2.t.y - p1.t.y, p2.t.z - p1.t.z);
    }
    public Vector(double x, double y, double z) {
        t.x = x;
        t.y = y;
        t.z = z;         
    }
    
    @Override
    public String toString(){
        return String.format("(%.2f, %.2f, %.2f)", t.x, t.y, t.z);
    }
    
    public void rotate(char axis, double angle){
        t = Utils.multiplyMatrixVector3D(Utils.getRotationMatrix(axis, angle), this).t;
    }
    
    public Vector getRotatedVector(char axis, double angle){
        return Utils.multiplyMatrixVector3D(Utils.getRotationMatrix(axis, angle), this);
    }
    
    public double dotProduct(Vector v){
        return (t.x*v.t.x + t.y*v.t.y + t.z*v.t.z)/v.getLength();
    }
    
    public double getLength(){
        return Math.sqrt(t.x*t.x + t.y*t.y + t.z*t.z);
    }
    
    public boolean areCollinear(Vector v){
        return (this.getAngle(v) < Constants.TRESHOLD);
    }
    
    public double getAngle(Vector v){
        double coef = (this.dotProduct(v)) / (this.getLength() * v.getLength());
        if(coef > 1.0){
            coef = 1.0;
        }
        
        if(coef < -1.0){
            coef = -1.0;
        }
        return Math.acos(coef);
    }
    
    public Vector getUnitVector(){
        return new Vector(this.t.x/this.getLength(), this.t.y/this.getLength(), this.t.z/this.getLength());
    }
    
    public Vector getOppositeVector(){
        return new Vector(-this.t.x, -this.t.y, -this.t.z);
    }
    
}
