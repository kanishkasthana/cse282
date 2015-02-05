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
    public static List<node> allnodes=new <node>ArrayList();
    int matrixlevel;
    int value;
    int i;
    int j;
    List edges=new ArrayList();
    int score=-1;
    List parents=new ArrayList();
    List children=new ArrayList();
    node backtrackNode=null;
    
    public node(int value){
        this.value=value;
    }
    
    public node(int i,int j){
        this.i=i;
        this.j=j;
    }
    
    public node(int i,int j,int matrixlevel){
        this.i=i;
        this.j=j;
        this.matrixlevel=matrixlevel;
        allnodes.add(this);
        this.setScore(0);
    }
    
    public int getNodeNumber(){
        return value;
    }
    
    public int getMatrixLevel(){
        return this.matrixlevel;
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
    
    public node getBacktrackNode(){
        return backtrackNode;
    }
    
    public edge getEdge(node child){
      edge e=null;
        for(int i=0;i<this.getEdges().size();i++){
            if((  (edge)this.getEdges().get(i)  ).getChild().equals(child))
                e= (edge) this.getEdges().get(i);
        }
      return e;
    }
    
    public edge getEdgeParent(node parent){
      edge e=null;
        for(int i=0;i<this.getEdges().size();i++){
            if((  (edge)this.getEdges().get(i)  ).getParent().equals(parent))
                e= (edge) this.getEdges().get(i);
        }
      return e;    
    }
    //Wow, who would have thought the performance improvements you could get from reorganizing your code, its incredible!
    public void computeScores(node sinknode, node sourcenode){
      if(!this.getParents().isEmpty()){
          int[] scores=new int[this.getParents().size()];
          node maxParent=(node)this.getParents().get(0);         
          int maxScore=maxParent.getScore();
          if(!this.equals(sinknode))
              maxScore+=this.getEdgeParent(maxParent).getWeight();
          else{
              if(!maxParent.equals(sourcenode))
                  maxScore+=maxParent.getEdge(this).getWeight();
          }

          for(int i=0;i<this.getParents().size();i++){
              node parent=(node)this.getParents().get(i);
              int score=parent.getScore();
              if(!this.equals(sinknode))  
                score+=this.getEdgeParent(parent).getWeight();
              else{
                  if(!parent.equals(sourcenode))
                      score+=parent.getEdge(this).getWeight();
              }
                if(score>maxScore){
                      maxScore=score;
                      maxParent=parent;
                }
          }
          this.setScore(maxScore);
          this.backtrackNode=maxParent;
      }     
    }
}
