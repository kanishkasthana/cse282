/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bioinformatics;


import java.io.*;
import java.util.*;
import java.lang.Math;

/**
 *
 * @author kanis_000
 */
public class Bioinformatics5 extends Bioinformatics4{
    
    public int[] greedySorting(int[] permutations, PrintWriter out){
        int approxReversalDistance=0;
        
        for(int i=0;i<permutations.length;i++){
            int count=i+1;
            if(Math.abs(permutations[i])!=count){
              permutations=reverse(permutations,count);
              approxReversalDistance++;
              printPermutation(permutations,out);
            }
            if(permutations[i]==-1*count){
                permutations[i]*=-1;
                approxReversalDistance++;
                printPermutation(permutations,out);
            }
        }
        System.out.println("Revesal Distance:");
        System.out.println(approxReversalDistance);
        return permutations;
    }
    
    public int[] reverse(int[] permutations, int count){
        int pos=0;
        
        for(int i=count-1;i<permutations.length;i++){
            if(Math.abs(permutations[i])==count){
                pos=i;
                break;
            }
        }
        
        int j=count-1;
        
        int [] permutations_clone= new int[permutations.length];
        //Copying permutations into another array
        for(int i=0;i<permutations.length;i++){
            permutations_clone[i]=permutations[i];
        }
        
        for(int i=pos;i>=count-1;i--){
            permutations[j++]=permutations_clone[i]*-1;
        }
        
        return permutations;
    }
    
    public void printPermutation(int[] permutations,PrintWriter out){
        out.print("(");
        for(int i=0;i<permutations.length;i++){
            if(permutations[i]>0)
                out.print("+");
            
            out.print(permutations[i]);
            
            if(i!=permutations.length-1)
                out.print(" ");
        }
        out.println(")");
    }
    

}
