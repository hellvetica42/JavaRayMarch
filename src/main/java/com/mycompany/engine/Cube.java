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
public class Cube extends Geometry{
    private Color color;
    Transform t;
    private double sideLength;
    public Cube(Transform t, double sideLenght, Color color) {
        this.t = t;
        this.sideLength = sideLenght;
        this.color = color;
    }
    
    @Override
    public double getDistance(Point point){
        double xDif = Math.abs(point.t.x - this.t.x);
        double yDif = Math.abs(point.t.y - this.t.y);
        double zDif = Math.abs(point.t.z - this.t.z);

        return Math.max(Math.max(xDif, yDif), zDif) - sideLength/2;
    }
    
    @Override
    public Color getColor(){
        return this.color;
    }
    
        @Override
    public Transform transform(){
        return this.t;
    }
}
