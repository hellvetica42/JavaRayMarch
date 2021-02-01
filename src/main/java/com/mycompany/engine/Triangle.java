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
public class Triangle {
    private Vector[] triangle = new Vector[3];
    public Triangle(){
        
    }
    public Triangle(Vector p1, Vector p2, Vector p3) {
        triangle[0] = p1;
        triangle[1] = p2;
        triangle[2] = p3;
    }
    public Triangle(float[][] points){
        for(int i = 0; i < triangle.length; i++){
      //      triangle[i].set(points[i][0], points[i][1], points[i][2]);
        }
    }
    
    public Vector[] getVectors(){
        return triangle;
    }
    
}
