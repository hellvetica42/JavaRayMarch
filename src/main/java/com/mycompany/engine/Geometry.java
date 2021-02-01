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
abstract class Geometry {
    abstract public double getDistance(Point point);
    
    abstract public Color getColor();
    
    abstract public Transform transform();
    
    public Vector getNormal(Point point){
        Point p = new Point();
        p.t = this.transform();
        return new Vector(p, point);
    }
            
}
