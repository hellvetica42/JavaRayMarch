/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.engine;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Timer;
import javax.swing.*;
        
/**
 *
 * @author Petar
 */
public class PixelCanvas extends Canvas {
    static boolean[] movementFlags = {false, false ,false, false, false, false}; //forward, back, left, right, up, down
    //private static final Random random = new Random();
    static Camera cam = new Camera(-20,0,0,Constants.WIDTH,Constants.HEIGHT);
    
    //Sphere sphere1 = new Sphere(new Transform(13,7,-20), 5, Color.RED);
    //Sphere sphere2 = new Sphere(new Transform(13,8,30), 9, Color.ORANGE);
    static Sphere sphere3 = new Sphere(new Transform(20,0,-20), 9, Color.BLUE);
    static Plane floor = new Plane(new Vector(0,-1,0), new Point(0,20,0), Color.PINK);
    Cube cube1 = new Cube(new Transform(13,8,0), 9, Color.YELLOW);
    
    TestObject testObj = new TestObject(new Transform(20, 20, 20), 7, Color.BLACK);
    

    ArrayList<Geometry> obj = new ArrayList<>();
    public static long frameNum = 0;
 
    public PixelCanvas() {
        Timer timer = new Timer();
        TimerTask myTask = new TimerTask() {
        @Override
            public void run() {
                repaint();
            }
        };
        
        timer.scheduleAtFixedRate(myTask, 3000, 25);
        
       // obj.add(sphere1);
       // obj.add(sphere2);
        obj.add(sphere3);
        obj.add(cube1);
        obj.add(testObj);
        //obj.add(floor);
    }
        
    
    @Override
    public void paint(Graphics g) {
        update(g);
    }
    
    @Override
    public void update(Graphics g){
        frameNum++;
        
        BufferedImage im = cam.getImage(obj);
        im = Utils.getScaledImage(im, 1000, 1000);
        int wScale = 3;
        int hScale = 3;

        g.drawImage(im, 0, 0, this);
        
        //mouse controls
        double mousex = MouseInfo.getPointerInfo().getLocation().x;
        double camAngle = Utils.map(mousex, 0, 2559, -180, 180);
        //cam.setYAngle(camAngle);
        
        if(movementFlags[0]){
            cam.t.translate(cam.direction);
        }
        if(movementFlags[1]){
            cam.t.translate(cam.direction.getOppositeVector());
        }
        if(movementFlags[2]){
            cam.t.translate(cam.direction.getRotatedVector('y', 90));
        }
        if(movementFlags[3]){
            cam.t.translate(cam.direction.getRotatedVector('y', 90).getOppositeVector());
        }
        if(movementFlags[4]){
            cam.t.translate(new Vector(0,1,0));
        }
        if(movementFlags[5]){
            cam.t.translate(new Vector(0,-1,0));
        }
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.add(new PixelCanvas());

        frame.setVisible(true);
        
        JFrame controlPanel = new ControlPanel();
        controlPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JTextField keyListener = new JTextField();
        keyListener.addKeyListener(new MovementListener());
        
        controlPanel.add(keyListener);
        
        controlPanel.setVisible(true);
    }   
}

class MovementListener extends KeyAdapter{
    @Override
    public void keyPressed(KeyEvent evt) {
    if (evt.getKeyChar() == 'w') {
        PixelCanvas.movementFlags[0] = true;
    }
    if (evt.getKeyChar() == 's') {
        PixelCanvas.movementFlags[1] = true;
    }
    if (evt.getKeyChar() == 'a') {
        PixelCanvas.movementFlags[2] = true;
    }
    if (evt.getKeyChar() == 'd') {
        PixelCanvas.movementFlags[3] = true;
    }
    if (evt.getKeyChar() == 'e') {
        PixelCanvas.movementFlags[4] = true;
    }
    if (evt.getKeyChar() == 'q') {
        PixelCanvas.movementFlags[5] = true;
    }
  }
    
    @Override
    public void keyReleased(KeyEvent evt) {
    if (evt.getKeyChar() == 'w') {
        PixelCanvas.movementFlags[0] = false;
    }
    if (evt.getKeyChar() == 's') {
        PixelCanvas.movementFlags[1] = false;
    }
    if (evt.getKeyChar() == 'a') {
        PixelCanvas.movementFlags[2] = false;
    }
    if (evt.getKeyChar() == 'd') {
        PixelCanvas.movementFlags[3] = false;
    }
    if (evt.getKeyChar() == 'e') {
        PixelCanvas.movementFlags[4] = false;
    }
    if (evt.getKeyChar() == 'q') {
        PixelCanvas.movementFlags[5] = false;
    }
  }
}