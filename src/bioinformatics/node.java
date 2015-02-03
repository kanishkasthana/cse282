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
public class node {
    public static node[] allnodes;
    int value;
    int i;
    int j;
    List edges=new ArrayList();
    int score=-1;
    List parents=new ArrayList();
    List children=new ArrayList();
    
    public node(int value){
        this.value=value;
    }
    
    public node(int i,int j){
        this.i=i;
        this.j=j;
    }
    
    public int getNodeNumber(){
        return value;
    }
    
    
    
    public int getI(){
        return this.i;
    }
    public int getJ(){
        return this.j;
    }
    
    public List getEdges(){
        return edges;
    }
    
    public void setScore(int score){
        this.score=score;
    }
    public int getScore(){
        return this.score;
    }
    
    public void addChild(node child){
        children.add(child);
    }
    
    public void addParent(node parent){
        parents.add(parent);
    }
    
    public List getChildren(){
        return this.children;
    }
    
    public List getParents(){
        return this.parents;
    }
    
    public void addEdge(edge newedge){
        this.edges.add(newedge);
    }
    
    public edge getEdge(node parent){
      edge e=null;
        for(int i=0;i<this.getEdges().size();i++){
            if((  (edge)this.getEdges().get(i)  ).getParent().equals(parent))
                e= (edge) this.getEdges().get(i);
        }
      return e;
    }
    
    public void computeScores(){
      if(!this.getParents().isEmpty()){
          int[] scores=new int[this.getParents().size()];
          for(int i=0;i<this.getParents().size();i++){
              node parent=(node)this.getParents().get(i);
              scores[i]=parent.getScore()+getEdge(parent).getWeight();
          }
          scores=Bioinformatics.sort(scores);
          int maximum=scores[this.getParents().size()-1];
          this.setScore(maximum);
      }
          
    }
}
