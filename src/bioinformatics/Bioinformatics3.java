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
public class Bioinformatics3 extends Bioinformatics2{
    
    public static int down=-1;
    public static int right=1;
    public static int diag=0;
    public static int source=3;
    public int dpChange(int money, int[] coins){
        int[] minNumCoins=new int[money+1];
        minNumCoins[0]=0;
        for(int m=1;m<=money;m++){
            minNumCoins[m]=money/coins[coins.length-1];//money divided by minimum value of coins should be max possible value
            for(int i=0;i<coins.length;i++){
                if(m>=coins[i]){
                    if(minNumCoins[m-coins[i]]+1<minNumCoins[m])
                        minNumCoins[m]=minNumCoins[m-coins[i]]+1;
                }
            }
        }
        return minNumCoins[money];
    }
    
    public int max(int num1,int num2){
        if(num1>num2)
            return num1;
        else 
            return num2;
    }
    
    public int max(int num1,int num2,int num3){
        int[] tempArray=new int[3];
        tempArray[0]=num1;
        tempArray[1]=num2;
        tempArray[2]=num3;
        tempArray=sort(tempArray);
        return tempArray[2];
    }
    
    public int manhattanTourist(int n, int m, int[][] down, int [][] right){
        int[][] s=new int[n+1][m+1];
        s[0][0]=0;
        //Filling first Column
        for(int i=1;i<=n;i++){
            s[i][0]=s[i-1][0]+down[i-1][0];
        }
        //Filling First Row
        for(int j=1;j<=m;j++){
            s[0][j]=s[0][j-1]+right[0][j-1];
        }
        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){
              s[i][j]=max(s[i-1][j]+down[i-1][j],s[i][j-1]+right[i][j-1]);  
            }
        }
        return s[n][m];
    }
    
    public int[][] lcsBacktrack(String v, String w){
        int[][] backtrack = new int[v.length()+1][w.length()+1];
        int[][] s=new int[v.length()+1][w.length()+1];
        for(int i=0;i<=v.length();i++){
            s[i][0]=0;
        }
        
        for(int j=0;j<=w.length();j++){
            s[0][j]=0;
        }
                
        for(int i=1;i<=v.length();i++){
            for(int j=1;j<=w.length();j++){
                boolean flag=false;
                if(v.charAt(i-1)==w.charAt(j-1))
                    flag=true;
                if(flag==true)
                    s[i][j]=max(s[i-1][j],s[i][j-1],s[i-1][j-1]+1);
                else
                    s[i][j]=max(s[i-1][j],s[i][j-1]);
                if(s[i][j]==s[i-1][j])
                    backtrack[i][j]=down;
                else if(s[i][j]==s[i][j-1])
                    backtrack[i][j]=right;
                else if(s[i][j]==s[i-1][j-1]+1 && v.charAt(i-1)==w.charAt(j-1))
                    backtrack[i][j]=diag;
            }
        }
        
        
        
        return backtrack;
    }
    
    public void outputLCS(int[][] backtrack,String v,int i,int j,StringBuilder out){
        if(i==0 || j==0)
            return;
        if(backtrack[i][j]==down){
            outputLCS(backtrack,v,i-1,j,out);
        }
        else if(backtrack[i][j]==right){
            outputLCS(backtrack,v,i,j-1,out);
        }
        else{
            outputLCS(backtrack,v,i-1,j-1,out);
            out.append(v.charAt(i-1));
        }
    }
    
    public List<node> getAdjacencyList(List <node>allnodes){
        edge.alledges.clear();
        List<node> adjacencyList=new <node>ArrayList();
        List<node> candidates=new <node>ArrayList();
        for(int i=0;i<allnodes.size();i++){
            if(allnodes.get(i).getParents().isEmpty()){
                candidates.add(allnodes.get(i));
            }
        }
        while(!candidates.isEmpty()){
           node arbitary=candidates.get(0);
           candidates.remove(arbitary);
           adjacencyList.add(arbitary);
           for(int i=0;i<arbitary.getChildren().size();i++){
               node child=(node)arbitary.getChildren().get(i);
               child.getParents().remove(arbitary);
               if(child.getParents().isEmpty())
                   candidates.add(child);
           }
           arbitary.getChildren().clear();
           arbitary.getEdges().clear();
        }
        
        return adjacencyList;
    }
    
    public List stripEverythingButTheCore(List <node>allnodes,int maxnode,int sinknode, int sourcenode){
        List<node> nodes=new <node>ArrayList();
        List<node> candidates=new<node>ArrayList();
        for(int i=0;i<allnodes.size();i++){
            if(allnodes.get(i).getParents().isEmpty() && allnodes.get(i).getNodeNumber()!=sourcenode){
                candidates.add(allnodes.get(i));
            }
            if(allnodes.get(i).getChildren().isEmpty() && allnodes.get(i).getNodeNumber()!=sinknode){
                nodes.add(allnodes.get(i));
            }
        }
        while(!candidates.isEmpty()){
            node arbitary=candidates.get(0);
            candidates.remove(arbitary);
            allnodes.remove(arbitary);
            for(int i=0;i<arbitary.getChildren().size();i++){
                node child=(node) arbitary.getChildren().get(i);
                child.getParents().remove(arbitary);
                edge childedge=child.getEdge(arbitary);
                child.getEdges().remove(childedge);
                arbitary.getEdges().remove(childedge);
                edge.alledges.remove(childedge);
                if(child.getParents().isEmpty() && child.getNodeNumber()!=sourcenode){
                    candidates.add(child);
                }
            }
            arbitary.getChildren().clear();
            arbitary.getEdges().clear();
        }
        
           while(!nodes.isEmpty()){
            node arbitary=nodes.get(0);
            nodes.remove(arbitary);
            allnodes.remove(arbitary);
            for(int i=0;i<arbitary.getParents().size();i++){
                node parent=(node) arbitary.getParents().get(i);
                parent.getChildren().remove(arbitary);
                edge parentedge=arbitary.getEdge(parent);
                parent.getEdges().remove(parentedge);
                arbitary.getEdges().remove(parentedge);
                edge.alledges.remove(parentedge);
                if(parent.getChildren().isEmpty() && parent.getNodeNumber()!=sinknode){
                    nodes.add(parent);
                }
            }
            arbitary.getParents().clear();
            arbitary.getEdges().clear();

        }

        
        return allnodes;
    }
    
    public static List toList(node[] nds){
        List<node> nodeslist=new <node>ArrayList();
        for(int i=0;i<nds.length;i++){
            nodeslist.add(nds[i]);
        }
        return nodeslist;
    }
}
