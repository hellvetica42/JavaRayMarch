/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.engine;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;


/**
 *
 * @author Petar
 */
public class Utils {
    
    
    
    public static double map(double val, double f1, double f2, double t1, double t2){
        return (val-f1)/(f2-f1) * (t2 - t1) + t1;
    }
    
    public static Vector multiplyMatrixVector3D(double[][] mat, Vector v){
        Vector rVector = new Vector();
        
        rVector.t.x = mat[0][0]*v.t.x + mat[0][1]*v.t.y + mat[0][2]*v.t.z;
        rVector.t.y = mat[1][0]*v.t.x + mat[1][1]*v.t.y + mat[1][2]*v.t.z;
        rVector.t.z = mat[2][0]*v.t.x + mat[2][1]*v.t.y + mat[2][2]*v.t.z;
        
        return rVector;
    }
    /*
    public static ArrayList<Vector> multiplyMatrixVector3DGPU(double [][] mat, ArrayList<Vector> vectors){
        ArrayList<Vector> rVectors = new ArrayList<>();
        final float[] primVectors = new float[vectors.size()*3]; //primitive vectors
        final float[] primResultVectors = new float[vectors.size()*3]; //primitive result vectors
        final float [] mat1D = new float[9];
        
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                mat1D[i*3 + j] = (float)mat[i][j];
            }
        }
        
        for(int i = 0; i < vectors.size(); i++){ //transfer to primitive type
            primVectors[3*i+0] = (float)vectors.get(i).t.x;
            primVectors[3*i+1] = (float)vectors.get(i).t.y;
            primVectors[3*i+2] = (float)vectors.get(i).t.z;
        }
        
        Kernel kernel = new Kernel() {
            @Override
            public void run() {
                int i = getGlobalId()*3;
                
                primResultVectors[i+0] = (mat1D[0]*primVectors[i+0] + mat1D[1]*primVectors[i+1] + mat1D[2]*primVectors[i+2]);
                primResultVectors[i+1] = (mat1D[3]*primVectors[i+0] + mat1D[4]*primVectors[i+1] + mat1D[5]*primVectors[i+2]);
                primResultVectors[i+2] = (mat1D[6]*primVectors[i+0] + mat1D[7]*primVectors[i+1] + mat1D[8]*primVectors[i+2]);
            }
        };
        int range = vectors.size();
        kernel.execute(range);
        
        for(int i = 0; i < vectors.size()*3; i+=3){ //transfer primitive to array
            rVectors.add(new Vector(primResultVectors[i+0], primResultVectors[i+1], primResultVectors[i+2]));
        }
        
        return rVectors;
    }
    */
    public static double[][] getRotationMatrix(char axis, double angle){
        double rad = Math.toRadians(angle);
        final double ccos = Math.cos(rad);
        final double csin = Math.sin(rad);
        double[][] rMat;
        switch(axis){
            case 'x':
                rMat = new double[][] {{1,   0,    0},
                                       {0, ccos, -csin},
                                       {0, csin, ccos}};
                break;
            
            case 'y':
                rMat = new double[][] {{ccos, 0, csin},
                                       {0,    1,  0},
                                       {-csin, 0, ccos}};
                break;
                
            case 'z':
                rMat = new double[][] {{ccos, -csin, 0},
                                       {csin, ccos,  0},
                                       {0,     0,    1}};
                break;
                
            default:
                rMat = new double[3][3];
                System.err.println("Axis not recognised");
                break;
        }
        
        return rMat;
    }
    
    public static BufferedImage getScaledImage(Image srcImg, int w, int h){

    //Create a new image with good size that contains or might contain arbitrary alpha values between and including 0.0 and 1.0.
    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);

    //Create a device-independant object to draw the resized image
    Graphics2D g2 = resizedImg.createGraphics();

    //This could be changed, Cf. http://stackoverflow.com/documentation/java/5482/creating-images-programmatically/19498/specifying-image-rendering-quality
    //g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

    //Finally draw the source image in the Graphics2D with the desired size.
    g2.drawImage(srcImg, 0, 0, w, h, null);

    //Disposes of this graphics context and releases any system resources that it is using
    g2.dispose();

    //Return the image used to create the Graphics2D 
    return resizedImg;
}
}

