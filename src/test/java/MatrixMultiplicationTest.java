
import com.mycompany.engine.Utils;
import com.mycompany.engine.Vector;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Petar
 */
public class MatrixMultiplicationTest {
    public static void main(String[] args){
        Vector vec = new Vector(0,0,1);
        
        Vector rVector = Utils.multiplyMatrixVector3D(Utils.getRotationMatrix('x', 90), vec);
        
        System.out.println(rVector.toString());
    }
}
