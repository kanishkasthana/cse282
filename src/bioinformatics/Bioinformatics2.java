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
    
    public List motifEnumeration(List dna,int k, int d){
        List patterns= new ArrayList();
        String kmer,pattern;
        List kmerneighbours;
        int presentlocally,presentglobally;
        for(Object str:dna){
            for(int i=0;i<=str.toString().length()-k;i++){
               kmer=str.toString().substring(i,i+k);
               kmerneighbours=neighbours(kmer,d);
               for(Object kmerd:kmerneighbours){ 
                  presentglobally=0;
                   for(Object str2:dna){
                    presentlocally=0; 
                    //Traversing through each DNA string one at a time
                    for(int j=0; j<=str2.toString().length()-k;j++){
                        pattern=str2.toString().substring(j,j+k);
                        if(hammingDistance(pattern,kmerd.toString())<=d){
                          presentlocally=1;
                        }
                    }
                    
                    if(presentlocally==0){
                        presentglobally=0;
                        break;
                    }
                    else{
                        presentglobally=1;
                    }
                 
                  }
                   
                 if(presentglobally==1){
                     patterns.add(kmerd.toString());
                 }
               }
               
               
            }
        }
        
        
        patterns=removeDuplicates(patterns);
        
        return patterns;
    }
    
    public List removeDuplicates(List frequentPatterns){
     
    List uniqueElements=new ArrayList(); 
    Object tempPattern;
    int present;
        for (Object pattern : frequentPatterns) {
            present=0;
            tempPattern=pattern;
            for (Object pattern1 : uniqueElements) {
              if(pattern1.equals(tempPattern))
              {
               present=1;
               break;
              }
            }
            if(present==0)
                uniqueElements.add(tempPattern);
        }
        
        return(uniqueElements);
        
    }
    
    public int distanceBetweenPatternAndStrings(String pattern,List dna){
        int distance=0,k=pattern.length();
        int hammingDistance=k;
        String kmer;
        for(Object text:dna){
            hammingDistance=k;
            for(int i=0;i<=text.toString().length()-k;i++){
                kmer=text.toString().substring(i,i+k);
                if(hammingDistance>hammingDistance(kmer,pattern)){
                    hammingDistance=hammingDistance(kmer,pattern);
                }
            }
            distance+=hammingDistance;
        }
        return distance;
    }
    
    public int maximumDistanceBetweenPatternAndStrings(String pattern,List dna){
        int distance=0,k=pattern.length();
        int hammingDistance=k;
        int maxdistance=0;
        String kmer;
        for(Object text:dna){
            hammingDistance=k;
            for(int i=0;i<=text.toString().length()-k;i++){
                kmer=text.toString().substring(i,i+k);
                if(hammingDistance>hammingDistance(kmer,pattern)){
                    hammingDistance=hammingDistance(kmer,pattern);
                }
            }
            if(maxdistance<hammingDistance){
                maxdistance=hammingDistance;
            }
        }
        return maxdistance;
    }
    
    public String medianString(List dna,int k){
       String median = null,pattern;
       int distance=dna.size()*k;
       int end=power(4,k),i,j;
       
       for(i=0;i<=end-1;i++){
           pattern=numberToPattern(i,k);
           if(distance>distanceBetweenPatternAndStrings(pattern,dna)){
               distance=distanceBetweenPatternAndStrings(pattern,dna);
               median=pattern;
           }
       }
       
       return median;
    }
    
    public String minimaxString(List dna,int k){
       String minimax = numberToPattern(0,k),pattern;
       int distance=1000000;
       int end=power(4,k),i,j;
       
       for(i=0;i<=end-1;i++){
           pattern=numberToPattern(i,k);
           System.out.println(distanceBetweenPatternAndStrings(pattern,dna));
           if(distance>=(distanceBetweenPatternAndStrings(pattern,dna)+maximumDistanceBetweenPatternAndStrings(pattern,dna))){
               distance=distanceBetweenPatternAndStrings(pattern,dna)+maximumDistanceBetweenPatternAndStrings(pattern,dna);
               System.out.println("This runs");
               minimax=pattern;
           }
       }
       
       return minimax;
    }
    
    public static void main2(){
         
    
    }
    
}
