/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.engine;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Petar
 */
public class Camera {
    protected Transform t = new Transform();
    protected Vector direction = new Vector(1,0,0);
    protected double yAngle = 0;
    protected double zAngle = 0;
    double FOV = 60;
    double planeDistance = 5;
    private double projSize = 2*planeDistance*Math.tan(Math.toRadians(FOV/2));
    private int[][] screen;
    final private int[] screenSize = new int[2];
    double stepSize = 0.5;
    public static double multi = 0;
    
    public double val = 255;

    public Camera() {
        this(0, 0, 0, 500, 500);
    }
    public Camera(double x, double y, double z, int sX, int sY){
        t.x = x;
        t.y = y;
        t.z = z;
        
        screen = new int[sX][sY];
        screenSize[0] = sX;
        screenSize[1] = sY;
    }
    
    BufferedImage getImage(ArrayList<Geometry> objects){
        BufferedImage image = new BufferedImage(screenSize[0], screenSize[1], BufferedImage.TYPE_INT_RGB);
        projSize = 2*planeDistance*Math.tan(Math.toRadians(FOV/2));
        
        Sphere light = new Sphere(new Point(20, -100, multi).t, 3, Color.red);

        
        for(int j = screenY()-1; j >= 0 ; j--){
            for(int i = 0; i < screenX(); i++){
                //point on projection ie. tip of vector; base being the camera position
               Point start = new Point(this.t.x + planeDistance, projSize/screenY()*j - projSize/2 + this.t.y, projSize/screenX()*i - projSize/2 + this.t.z);
               Vector rayVector = new Vector(start.t.x - this.t.x, start.t.y - this.t.y, start.t.z - this.t.z);
               rayVector.rotate('z', this.zAngle);
               rayVector.rotate('y', this.yAngle);
               Ray ray = new Ray(start, rayVector);
               
               //Plane projPlane = new Plane(this.direction, start);
                boolean reachedEdge = false;
               rayStep : while(true){
                   double stepSize = ray.getMaxStepSize(objects);
                   if(stepSize < 0.1){
                       stepSize =  0.1;
                   }
                   
                   if(stepSize < 0.2){
                        reachedEdge = true;
                   }
                   
                   Point castedPoint = ray.cast(stepSize); //ray marching
                   if(ray.getLength() >= Constants.MAX_DISTANCE){
                       if(reachedEdge){ //edges
                           image.setRGB(i, j, 0);
                       }
                       else{ //background
                            image.setRGB(i, j, 0);
                       }
                       break rayStep;
                   }
                   
                   for(Geometry g : objects){
                       if(g.getDistance(castedPoint) <= 0){
                           //Plane hitPlane = new Plane(this.direction, castedPoint);
                           //image.setRGB(i, screenY()-j-1, (int)Utils.map(g.getDistance(start), 0, val, 255, 0));
                           Color c = g.getColor();
                           Vector norm = g.getNormal(castedPoint).getUnitVector();
                           Vector toLight = new Vector(castedPoint, new Point(light.t)).getUnitVector();
                           double coef = Math.cos(norm.getAngle(toLight));
                           
                           coef = Utils.map(coef, -1, 1, 0, 1);
                          
                           c = new Color((int)((double)c.getRed() * coef), (int)(c.getGreen() * coef), (int)(c.getBlue() * coef));
                           image.setRGB(i, j, c.getRGB());
                           if(Constants.HIT_VERBOSE){
                               System.out.printf("HIT at %s (x=%d, y=%d)\n", castedPoint.t.toString(), i, j);
                           }
                           break rayStep;
                       }
                   }
               }
            }
        }
               
        return image;
    }
    
    BufferedImage getImageRayCast(ArrayList<Geometry> objects){
        BufferedImage image = new BufferedImage(screenSize[0], screenSize[1], BufferedImage.TYPE_INT_RGB);
        projSize = 2*planeDistance*Math.tan(Math.toRadians(FOV/2));
        
        Sphere light = new Sphere(new Point(-20, -100, multi).t, 3, Color.red);
        
        for(int j = screenY()-1; j >= 0 ; j--){
            for(int i = 0; i < screenX(); i++){
                //point on projection ie. tip of vector; base being the camera position
               Point start = new Point(this.t.x + planeDistance, projSize/screenY()*j - projSize/2 + this.t.y, projSize/screenX()*i - projSize/2 + this.t.z);
               Vector rayVector = new Vector(start.t.x - this.t.x, start.t.y - this.t.y, start.t.z - this.t.z);
               rayVector.rotate('y', this.yAngle);
               rayVector.rotate('y', this.yAngle);
               
               Ray ray = new Ray(start, rayVector);
               
               RayHit hit = ray.rayCast(objects);
               
               if(hit.getHit()){
                   Vector lightVector = new Vector(hit.getHitPoint().t, light.t);
                   Ray lightRay = new Ray(hit.getHitPoint(), lightVector);
                   lightRay.cast(1);
                   RayHit lightHit = lightRay.rayCast(objects);
                   
                   if(lightHit.getHit()){
                        image.setRGB(i, j, Color.BLACK.getRGB());
                   }
                   else{
                           Color c = hit.getHitObject().getColor();
                           Vector norm = hit.getHitObject().getNormal(hit.getHitPoint()).getUnitVector();
                           double coef = Math.cos(norm.getAngle(lightVector));
                           
                           coef = Utils.map(coef, -1, 1, 0, 1);
                          
                           c = new Color((int)((double)c.getRed() * coef), (int)(c.getGreen() * coef), (int)(c.getBlue() * coef));
                           image.setRGB(i, j, c.getRGB());
                   }
                   
               }
               else if(hit.rechedEdge()){
                   image.setRGB(i, j, Color.WHITE.getRGB());
               }
               else{
                   image.setRGB(i, j, Color.BLACK.getRGB());
               }
            }
        }
               
        return image;
    }
    
    BufferedImage getImageRayCastMultiThread(ArrayList<Geometry> objects){
        BufferedImage image = new BufferedImage(screenSize[0], screenSize[1], BufferedImage.TYPE_INT_RGB);
        projSize = 2*planeDistance*Math.tan(Math.toRadians(FOV/2));
        
        ArrayList<Thread> threadList = new ArrayList<>();
        ArrayList<RayThread> rayThreadList = new ArrayList<>();
        
        for(int j = screenY()-1; j >= 0 ; j--){
            for(int i = 0; i < screenX(); i++){
                
                
                //point on projection ie. tip of vector; base being the camera position
               Point start = new Point(this.t.x + planeDistance, projSize/screenY()*j - projSize/2 + this.t.y, projSize/screenX()*i - projSize/2 + this.t.z);
               Vector rayVector = new Vector(start.t.x - this.t.x, start.t.y - this.t.y, start.t.z - this.t.z);
               
               RayThread th = new RayThread(start, rayVector, objects, i, j);
               rayThreadList.add(th);
               threadList.add(new Thread(th));
            }
        }
        
            for(Thread th : threadList){
                th.start();
            }

            for(Thread th : threadList){
                try {
                    th.join();
                } catch (InterruptedException ex) {
                    System.err.println(ex);
                }
            }

            for(RayThread th : rayThreadList){
                RayHit hit = th.getValue();
                if(hit.getHit()){
                       image.setRGB(th.i, th.j, hit.getHitObject().getColor().getRGB());
                   }
                   else if(hit.rechedEdge()){
                       image.setRGB(th.i, th.j, Color.WHITE.getRGB());
                   }
                   else{
                       image.setRGB(th.i, th.j, Color.BLACK.getRGB());
                   }
            }
               
        return image;
    }
    
    
    
    /*
    BufferedImage getImageGPU(ArrayList<Geometry> objects){
        BufferedImage image = new BufferedImage(screenSize[0], screenSize[1], BufferedImage.TYPE_INT_RGB);
        projSize = 2*planeDistance*Math.tan(Math.toRadians(FOV/2));
        
        ArrayList<Vector> vectorList = new ArrayList<>();
        ArrayList<Point> startPointList = new ArrayList<>();
        
        for(int j = 0; j < screenY(); j++){
            for(int i = 0; i < screenX(); i++){
                Point start = new Point(this.t.x + planeDistance, projSize/screenY()*j - projSize/2 + this.t.y, projSize/screenX()*i - projSize/2 + this.t.z);
                startPointList.add(start);
                vectorList.add(new Vector(start.t.x - this.t.x, start.t.y - this.t.y, start.t.z - this.t.z));
            }   
        }
        
       ArrayList<Vector> rVectorList = Utils.multiplyMatrixVector3DGPU(Utils.getRotationMatrix('y', yAngle), vectorList);
        
        for(int j = 0; j < screenY(); j++){
            for(int i = 0; i < screenX(); i++){
                //point on projection ie. tip of vector; base being the camera position
               
               //rayVector.rotate('y', this.angle);
               Ray ray = new Ray(startPointList.get(j*screenX() + i), rVectorList.get(j*screenX() + i));
               
               //Plane projPlane = new Plane(this.direction, start);
                boolean reachedEdge = false;
               rayStep : while(true){
                   double stepSize = ray.getMaxStepSize(objects);
                   if(stepSize < 0.1){
                       stepSize =  0.1;
                       reachedEdge = true;
                   }
                   Point castedPoint = ray.cast(stepSize); //ray marching
                   if(ray.getLength() >= Constants.MAX_DISTANCE){
                       if(reachedEdge){ //edges
                           image.setRGB(i, screenY()-j-1, Color.WHITE.getRGB());
                       }
                       else{
                            image.setRGB(i, j, 0);
                       }
                       break rayStep;
                   }
                   
                   for(Geometry g : objects){
                       if(g.getDistance(castedPoint) <= 0){
                           //Plane hitPlane = new Plane(this.direction, castedPoint);
                           //image.setRGB(i, j, (int)Utils.map(projPlane.getDistance(hitPlane), 0, val, 255, 0));
                           image.setRGB(i, screenY()-j-1, g.getColor().getRGB());
                           if(Constants.HIT_VERBOSE){
                               System.out.printf("HIT at %s (x=%d, y=%d)\n", castedPoint.t.toString(), i, j);
                           }
                           break rayStep;
                       }
                   }
               }
            }
        }  
        return image;
    }
    */
    
    public int screenX(){
        return screenSize[0];
    }
    
    public int screenY(){
        return screenSize[1];
    }
    
    public double getYAngle(){
        return yAngle;
    }
    
    public void setYAngle(double angle){
        this.yAngle = angle;
        double radAngle = Math.toRadians(angle);
        
        direction = new Vector(Math.cos(radAngle), direction.t.y, -Math.sin(radAngle));
    }
    
    public void setZAngle(double angle){
        this.zAngle = angle;
        double radAngle = Math.toRadians(angle);
        
        direction = new Vector(direction.t.x, Math.sin(radAngle), direction.t.z);
    }
    
}

class RayThread implements Runnable {
     private volatile RayHit hit;
     private final Point start; 
     private final Vector rayVector;
     private final ArrayList<Geometry> objects;
     int i, j;
     public RayThread(Point start, Vector rayVector, ArrayList<Geometry> objects, int i, int j){
         this.start = start;
         this.rayVector = rayVector;
         this.objects = objects;
         this.i = i;
         this.j = j;
     }
     @Override
     public void run() {
        Ray ray = new Ray(start, rayVector);
               
        this.hit = ray.rayCast(objects);
     }

     public RayHit getValue() {
         return hit;
     }
 }


