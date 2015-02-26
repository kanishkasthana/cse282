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
    
    public void printPermutation(int[] permutations){
        System.out.print("(");
        for(int i=0;i<permutations.length;i++){
            if(permutations[i]>0)
                System.out.print("+");
            
            System.out.print(permutations[i]);
            
            if(i!=permutations.length-1)
                System.out.print(" ");
        }
        System.out.println(")");
    }

    
    public int findBreakPoints(int[] permutations){
        int breakPoints=0;
        for(int i=0;i<permutations.length;i++){
            if(i==0){
                int difference=permutations[i]-0;
                if(difference!=1)
                    breakPoints++;
            }
            else if(i==permutations.length-1){
                int difference=permutations.length+1-permutations[i];
                if(difference!=1)
                    breakPoints++;
            }
            else{
                int difference=permutations[i+1]-permutations[i];
                if(difference!=1)
                    breakPoints++;
            }
        }
        return breakPoints;
    }
    
    public List<node> chromosomeToCycle(int[] chromosomeValues,node[] nodes){
     List <node> nodeOrder=new <node> ArrayList();
     for(int j=0;j<chromosomeValues.length;j++){
         int i=chromosomeValues[j];
         if(i>0){
             nodeOrder.add(nodes[2*i-2]);
             nodeOrder.add(nodes[2*i-1]);
         }
         else{
             nodeOrder.add(nodes[-2*i-1]);
             nodeOrder.add(nodes[-2*i-2]);
         }
     }
        return nodeOrder;
    }
    
    public List<edge> coloredEdges(List <String> chromosomeStrings,node[] nodes,int color){
        List <edge>alledges=new <edge> ArrayList();
        for(int i=0;i<chromosomeStrings.size();i++){
            String currentChromosome=chromosomeStrings.get(i);
            StringTokenizer elements=new StringTokenizer(currentChromosome);
            int count=0;
            int[] chromosomeValues=new int[elements.countTokens()];
            
            while(elements.hasMoreTokens()){
                chromosomeValues[count++]=Integer.parseInt(elements.nextToken());
            }
            List <node> nodeOrder=chromosomeToCycle(chromosomeValues,nodes);
            node firstElement=nodeOrder.get(0);
            nodeOrder.add(firstElement);
            
            for(int j=0;j<chromosomeValues.length;j++){
                edge newEdge=new edge(nodeOrder.get(2*(j+1)-1),nodeOrder.get(2*(j+1)),color);
                alledges.add(newEdge);
            }
            
        }
        
        return alledges;
    }
    

}
