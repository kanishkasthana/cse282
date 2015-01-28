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
public class edge {
    public static List alledges=new ArrayList();
    public static node[] allnodes;
    node parent;
    node child;
    int weight;
    
    public edge(String edgedata){
        
        StringTokenizer fullString=new StringTokenizer(edgedata,"->");
        int parentNodeValue=Integer.parseInt(fullString.nextToken());
        StringTokenizer childString=new StringTokenizer(fullString.nextToken(),":");
        int childNodeValue=Integer.parseInt(childString.nextToken());
        int weight=Integer.parseInt(childString.nextToken());
        for(int i=0;i<this.allnodes.length;i++){
            if(allnodes[i].getNodeNumber()==parentNodeValue){
                this.parent=allnodes[i];
            }
            if(allnodes[i].getNodeNumber()==childNodeValue){
                this.child=allnodes[i];
            }
        }
        
        parent.addChild(child);
        child.addParent(parent);
        parent.addEdge(this);
        child.addEdge(this);
        this.weight=weight;
        this.alledges.add(this);
    }
    
    public node getParent(){
        return parent;
    }
    
    public node getChild(){
        return child;
    }
    
    public int getWeight(){
        return weight;
    }
    
    public static int getEdgeWeight(node parent, node child){
     int weight=0;
        for(int i=0;i<alledges.size();i++){
            edge temp=(edge)alledges.get(i);
            if(temp.getParent().equals(parent) && temp.getChild().equals(child))
                 weight=temp.getWeight();
        }
      return weight;
    
    }
  
}
