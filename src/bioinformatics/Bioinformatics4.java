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
    public List<String> stringComposition(int k,String text){
        List <String>orderedStrings=new <String>ArrayList();
        
        for(int i=0;i<=text.length()-k;i++){
            String pattern=text.substring(i, i+k);
            orderedStrings.add(pattern);
        }
        orderedStrings=mergeSort(orderedStrings);
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
    
    //Implementing merge for mergesort
    public static List merge(List list1,List list2){
        List sortedList=new ArrayList();
        int firstStringPos=0,secondStringPos=0;
        String current1,current2;
        
        while(firstStringPos<list1.size() && secondStringPos<list2.size()){
            current1=(String)list1.get(firstStringPos);
            current2=(String)list2.get(secondStringPos);
            if(current1.compareTo(current2)<0){
                firstStringPos++;
                sortedList.add(current1);
            }
            else{
                secondStringPos++;
                sortedList.add(current2);
            }
        }
        
        
        for(int i=firstStringPos;i<list1.size();i++){
            sortedList.add(list1.get(i));
        }
        
        
        for(int i=secondStringPos;i<list2.size();i++){
            sortedList.add(list2.get(i));
        }
        
        return sortedList;
    }
    
    public static List mergeSort(List list){
        if(list.size()==1){
            return list;
        }
        int firstHalfIndex=list.size()/2;
        List firstHalf=list.subList(0, firstHalfIndex);
        List secondHalf=list.subList(firstHalfIndex,list.size());
        List sortedFirstHalf=mergeSort(firstHalf);
        List sortedSecondHalf=mergeSort(secondHalf);
        List sortedList=merge(sortedFirstHalf,sortedSecondHalf);
        
        return sortedList;
    }
    
    public List<node> getOverlapGraph(List<String> orderedStrings){
        List<node> nodes=new <node>ArrayList();
        
        for(int i=0;i<orderedStrings.size();i++){
            node newNode=new node(orderedStrings.get(i));
            nodes.add(newNode);
        }
        
        for(int i=0;i<nodes.size();i++){
            node tempNode=nodes.get(i);
            for(int j=0;j<nodes.size();j++){
                node tempNode2=nodes.get(j);
                String subString1,subString2;
                subString1=tempNode.getNodeString().substring(1);
                subString2=tempNode2.getNodeString().substring(0,tempNode2.getNodeString().length()-1);
                if(subString1.equals(subString2)){
                    edge newEdge=new edge(tempNode,tempNode2,0);
                }
                
            }
        }
        return nodes;
    }
    
    public List<edge> getDeBruijnGraph(List<String> orderedStrings,int k){
        List<edge> edges=new <edge>ArrayList();
        for(int i=0;i<orderedStrings.size();i++){
            String prefixString=orderedStrings.get(i).substring(0,k-1);
            String sufixString=orderedStrings.get(i).substring(1);
            node parent=null;
            node child=null;
            for(int j=0;j<node.allnodes.size();j++){
                if(node.allnodes.get(j).getNodeString().equals(prefixString))
                    parent=node.allnodes.get(j);
                if(node.allnodes.get(j).getNodeString().equals(sufixString))
                    child=node.allnodes.get(j);
            }
            if(parent==null){
                parent=new node(prefixString);
            }
            if(child==null){
                child=new node(sufixString);
            }
            
            edges.add(new edge(parent,child,orderedStrings.get(i)));
            
        }
        return edges;
    }
}
