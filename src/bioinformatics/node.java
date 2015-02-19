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
    int k;
    List <edge>edges=new <edge>ArrayList();
    List <edge>outgoingEdges=new <edge>ArrayList();
    List <edge>incomingEdges=new <edge>ArrayList();
    
    int score;
    List <node>parents=new <node>ArrayList();
    List <node>children=new <node>ArrayList();
    node backtrackNode=null;
    String nodeString=null;
    
    public node(int value){
        this.value=value;
        allnodes.add(this);
    }
    
    public node(int i,int j){
        this.i=i;
        this.j=j;
        allnodes.add(this);
    }
    
    /*
    public node(int i,int j,int matrixlevel){
        this.i=i;
        this.j=j;
        this.matrixlevel=matrixlevel;
        allnodes.add(this);
        this.setScore(0);
    }*/
    
    public node(int i,int j, int k){
        this.i=i;
        this.j=j;
        this.k=k;
        allnodes.add(this);
        this.setScore(0);
    }
    
    public node(String nodeString){
        this.nodeString=nodeString;
        allnodes.add(this);
    }
    
    public String getNodeString(){
        return this.nodeString;
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
    
    public int getK(){
        return this.k;
    }
    public List<edge> getEdges(){
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
    
    public List<node> getChildren(){
        return this.children;
    }
    
    public List<node> getParents(){
        return this.parents;
    }
    
    public void addEdge(edge newedge){
        this.edges.add(newedge);
    }
    
    public void addOutgoingEdge(edge e){
        this.outgoingEdges.add(e);
    }
    
    public void addIncomingEdge(edge e){
        this.incomingEdges.add(e);
    }
    
    public List<edge> getOutgoingEdges(){
        return this.outgoingEdges;
    }
    
    public List<edge> getIncomingEdges(){
        return this.incomingEdges;
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
    
    public static void sort(){
        allnodes=mergeSort(allnodes);
    }
    
    public static List mergeSort(List<node> list){
        if(list.size()==1){
            return list;
        }
        int firstHalfIndex=list.size()/2;
        List <node>firstHalf=list.subList(0, firstHalfIndex);
        List <node>secondHalf=list.subList(firstHalfIndex,list.size());
        List <node>sortedFirstHalf=mergeSort(firstHalf);
        List <node>sortedSecondHalf=mergeSort(secondHalf);
        List <node>sortedList=merge(sortedFirstHalf,sortedSecondHalf);
        
        return sortedList;
    }
    
    public static List merge(List <node>list1,List <node>list2){
        List <node>sortedList=new <node>ArrayList();
        int firstStringPos=0,secondStringPos=0;
        String current1,current2;
        
        while(firstStringPos<list1.size() && secondStringPos<list2.size()){
            current1=list1.get(firstStringPos).getNodeString();
            current2=list2.get(secondStringPos).getNodeString();
            if(current1.compareTo(current2)<0){
                sortedList.add(list1.get(firstStringPos));
                firstStringPos++;
            }
            else{
                sortedList.add(list2.get(secondStringPos));
                secondStringPos++;
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
    
    public boolean hasUnexploredEdges(){
        for(int i=0;i<getOutgoingEdges().size();i++)
            if(!getOutgoingEdges().get(i).isTraversed())
                return true;
        
        return false;
    }
}
