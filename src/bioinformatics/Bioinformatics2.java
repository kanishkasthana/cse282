/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bioinformatics;

import java.io.*;
import java.util.*;

/**
 *
 * @author kanis_000
 */
public class Bioinformatics2 extends Bioinformatics {
    public List neighbours(String pattern, int d){
        
        List neighbourhood= new ArrayList();
        List suffixNeighbours= new ArrayList();
        if(d==0)
        {
         List noNeighbours=new ArrayList();
         noNeighbours.add(pattern);
         return noNeighbours;
        }
        if(pattern.length()==1){
         List allBases= new ArrayList();
         allBases.add("A");allBases.add("C");allBases.add("G");allBases.add("T");
         return allBases;
        }
        //Recursively calling neighbours for suffix.
        String suffix=pattern.substring(1);
        char firstSymbol=pattern.charAt(0);
        
        suffixNeighbours=neighbours(suffix,d);
        
        for( Object str:suffixNeighbours){
             if(hammingDistance(suffix,str.toString())<d){
               neighbourhood.add( "A"+ str.toString()  );
               neighbourhood.add( "C"+ str.toString()  );
               neighbourhood.add( "G"+ str.toString()  );
               neighbourhood.add( "T"+ str.toString()  );
             }
             else{
               neighbourhood.add(firstSymbol + str.toString()  );
             }
        }
        return neighbourhood;
    }
    
    public static void main2(){
         
    
    }
    
}
