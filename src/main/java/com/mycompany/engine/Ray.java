/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.engine;

import java.util.ArrayList;

/**
 *
 * @author Petar
 */
public class Ray {
    private final Point startPoint;
    private Point currentPoint;
    private final Vector v;
    public Ray(Point p, Vector v){
        this.startPoint = p;
        this.currentPoint = p;
        this.v = v.getUnitVector();
    }
    
    public Point getStartPoint(){
        return startPoint;
    }
    
    public double getLength(){
        double len = startPoint.distance(currentPoint);
        //System.out.println(len);
        return len;
    }
    
    public Point cast(double step){
        Point res = new Point();
         
        res.t.x = currentPoint.t.x + v.t.x * step;
        res.t.y = currentPoint.t.y + v.t.y * step;
        res.t.z = currentPoint.t.z + v.t.z * step;
        
        currentPoint = res;
        
        if(Constants.RAY_VERBOSE){
            System.out.printf("Casting ray step from %.2f,%.2f,%.2f in vector direction %.2f,%.2f,%.2f \n", currentPoint.t.x, currentPoint.t.y, currentPoint.t.z, v.t.x, v.t.y, v.t.z);
            
            System.out.printf("Distance from ray origin is %.2f \n", startPoint.distance(currentPoint));
        }
        
        return res;
    }


    
    public RayHit rayCast(ArrayList<Geometry> objects){
            RayHit rayHit = new RayHit();
            double nextStep = Constants.MAX_DISTANCE;


        
        rayStep : while(true){
            if(this.getLength() >= Constants.MAX_DISTANCE){
                break rayStep;
            }
            for(Geometry g : objects){
                double objectDistance = g.getDistance(this.currentPoint); //distance from object
               
                if(objectDistance <= 0){
                    rayHit.setHitObject(g);
                    rayHit.setHitPoint(this.currentPoint);

                    break rayStep;
                }
                else if(objectDistance < nextStep){
                    nextStep = objectDistance;
                    if(nextStep < Constants.EDGE_TRESHOLD){
                        rayHit.setReachedEdge(true);
                    }
                    if(nextStep < Constants.MIN_STEP){
                        nextStep = Constants.MIN_STEP;
                    }
                }
            }
            
            this.cast(nextStep);
        }
        return rayHit;
    }
    
    
    public double getMaxStepSize(ArrayList<Geometry> objects){
        double minDistance = Constants.MAX_DISTANCE;
        for(Geometry g : objects){
            if(g.getDistance(currentPoint) < minDistance){
                minDistance = g.getDistance(currentPoint);
            }
        }
        return Math.abs(minDistance);
    }
    
    public Geometry getClosestObject(ArrayList<Geometry> objects){
        double minDistance = Constants.MAX_DISTANCE;
        int minIndex = 0;
        for(int i = 0; i < objects.size(); i++){
            if(objects.get(i).getDistance(currentPoint) < minDistance){
                minDistance = objects.get(i).getDistance(startPoint);
                minIndex = i;
            }
        }
        return objects.get(minIndex);
    }
}

class RayHit{
    private Point hitPoint;
    private Geometry hitObject;
    private boolean hit = false;
    private boolean reachedEdge = false;

    public Point getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(Point hitPoint) {
        this.hitPoint = hitPoint;
        hit = true;
    }

    public Geometry getHitObject() {
        return hitObject;
    }

    public void setHitObject(Geometry hitObject) {
        this.hitObject = hitObject;
    } 
    
    public boolean getHit(){
        return hit;
    }
    
    public void setReachedEdge(boolean re){
        reachedEdge = re;
    }
    
    public boolean rechedEdge(){
        return reachedEdge;
    }
}
