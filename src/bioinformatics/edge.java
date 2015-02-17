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
    
    public edge(node parent,node child,int weight){
        this.parent=parent;
        this.child=child;
        this.weight=weight;
        this.parent.addChild(child);
        this.child.addParent(parent);
        this.parent.addEdge(this);
        this.child.addEdge(this);
        this.alledges.add(this);
    }
    /*
    public edge(int i1,int j1,int i2,int j2,int weight){
        for(int count=0;count<this.allnodes.length;count++){
            if(allnodes[count].getI()==i1 && allnodes[count].getJ()==j1)
        }
    }
    */
    public node getParent(){
        return parent;
    }
    
    public node getChild(){
        return child;
    }
    
    public int getWeight(){
        return weight;
    }
    
}