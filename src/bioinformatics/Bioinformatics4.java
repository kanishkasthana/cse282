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
public class Bioinformatics4 extends Bioinformatics3{
    public String[] stringComposition(int k,String text){
        String[] orderedStrings=new String[text.length()-k+1];
        long[] kmers=new long[text.length()-k+1];
        
        for(int i=0;i<=text.length()-k;i++){
            String pattern=text.substring(i, i+k);
            kmers[i]=patternToNumberLong(pattern);
            System.out.println(kmers[i]);
        }
        //May need to implement faster sort algorithm in the future;
        kmers=sort(kmers);
        for(int i=0;i<kmers.length;i++){
            orderedStrings[i]=numberToPatternLong(kmers[i],k);
        }
        return orderedStrings;
    }
    
    public long patternToNumberLong(String pattern){
     char symbol;   
     if(pattern.isEmpty())
         return (long)0;
     else{
      symbol=pattern.charAt(pattern.length()-1);
      pattern=pattern.substring(0,pattern.length()-1);
      return 4*patternToNumberLong(pattern)+ symbolToNumberLong(symbol);
     }
    }
    
    public long symbolToNumberLong(char symbol){
     long number;
     //Lexically arranged in increasing order
     switch(symbol){
         case 'A':
             number=0;
             break;
         case 'C':
             number=1;
             break;
         case 'G':
             number=2;
             break;
         case 'T':
             number=3;
             break;
         default:
             number=0;
             break;
     }
     return number;   
    }
    
    public static long[] sort(long[] array)
    {
    boolean swapped = true;
    long j = 0,temp;
    while (swapped) {
        swapped = false;
        j++;
        for (int i = 0; i < array.length - j; i++) {
            if (array[i] > array[i + 1]) {
                temp = array[i];
                array[i] = array[i + 1];
                array[i + 1] = temp;
                swapped = true;
            }
        }
     }
     return array;
    }
    
    public String numberToPatternLong(long index,long k){
     if(k==1)
         return numberToSymbol(index)+"";//Converting character to string
     long prefixIndex=quotient(index,4);
     long r=remainder(index,4);
     String prefixPattern=numberToPatternLong(prefixIndex,k-1);
     char symbol=numberToSymbol(r);
     //Returning Concatenation;
     return prefixPattern+symbol;
    }
    
    public char numberToSymbol(long number){
        if(number==0)
             return 'A';
        else if(number==1)
             return 'C';
        else if(number==2)
             return 'G';
        else if(number==3)
             return 'T';
        else return 'A';
    
    }
    public long quotient(long dividend, long divisor){
     return dividend/divisor;
    }
    
    public long remainder(long dividend, long divisor){
     return dividend%divisor;
    }
}
