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
    
    public int[][] getAlignmentScores(String firstProtein,String secondProtein,int[][] scoringMatrix,List alphabets,int gapPenalty,PrintWriter out ){
        int[][] backtrack = new int[firstProtein.length()+1][secondProtein.length()+1];
        int[][] s=new int[firstProtein.length()+1][secondProtein.length()+1];
        s[0][0]=0;
        for(int i=1;i<=firstProtein.length();i++){
            s[i][0]=s[i-1][0]-gapPenalty;
        }
        for(int j=1;j<=secondProtein.length();j++){
            s[0][j]=s[0][j-1]-gapPenalty;
        }
        
        for(int i=1;i<=firstProtein.length();i++){
            for(int j=1;j<=secondProtein.length();j++){
                char rowchar=firstProtein.charAt(i-1);
                char columnchar=secondProtein.charAt(j-1);
                int row=getPos(rowchar,alphabets);
                int column=getPos(columnchar,alphabets);
                int score=scoringMatrix[row][column];
                if(rowchar==columnchar){
                    score=0;
                }
                else
                    score=-1;
                
                s[i][j]=max(s[i-1][j]-gapPenalty,s[i][j-1]-gapPenalty,s[i-1][j-1]+score);
                
                if(s[i][j]==s[i-1][j]-gapPenalty){
                    backtrack[i][j]=down;
                }
                else if(s[i][j]==s[i][j-1]-gapPenalty){
                    backtrack[i][j]=right;
                }
                else if (s[i][j]==s[i-1][j-1]+score){
                    backtrack[i][j]=diag;
                }
                
            }
        }
        //outputing allignment score;
        out.println(s[firstProtein.length()][secondProtein.length()]*-1);
        /*
        for(int i=0;i<=firstProtein.length();i++){
            for(int j=0;j<=secondProtein.length();j++){
                System.out.print(s[i][j]);
                System.out.print("\t");
            }
            System.out.println("");
        }
        */        
        StringBuilder firstProteinAlign=new StringBuilder();
        StringBuilder secondProteinAlign=new StringBuilder();
        outputBacktrack(backtrack, firstProtein, secondProtein, firstProtein.length(),secondProtein.length(),firstProteinAlign,secondProteinAlign);
        //out.println(firstProteinAlign.toString());
        //out.println(secondProteinAlign.toString());
        return s;
    }
    public void outputBacktrack(int[][] backtrack,String firstProtein,String secondProtein,int i,int j,StringBuilder firstProteinAlign,StringBuilder secondProteinAlign){
        if(i==0 || j==0)
            return;
        if(backtrack[i][j]==down){
            this.outputBacktrack(backtrack, firstProtein, secondProtein, i-1, j, firstProteinAlign, secondProteinAlign);
            secondProteinAlign.append("-");
            firstProteinAlign.append(firstProtein.charAt(i-1));
        }
        else if(backtrack[i][j]==right){
            this.outputBacktrack(backtrack, firstProtein, secondProtein, i, j-1, firstProteinAlign, secondProteinAlign);
            firstProteinAlign.append("-");
            secondProteinAlign.append(secondProtein.charAt(j-1));
        }
        else{
            this.outputBacktrack(backtrack, firstProtein, secondProtein, i-1, j-1, firstProteinAlign, secondProteinAlign);
            firstProteinAlign.append(firstProtein.charAt(i-1));
            secondProteinAlign.append(secondProtein.charAt(j-1));
        }
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
    public static List toList(node[][] nds,int n,int m){
        List<node> nodeslist=new <node>ArrayList();
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
             nodeslist.add(nds[i][j]);    
            }
        }
        return nodeslist;
    }
    
    public static int getPos(char letter,List alphabets){
        int pos=0;
        for(int i=0;i<alphabets.size();i++){
            if(alphabets.get(i).toString().charAt(0)==letter)
                pos=i;
        }
        return pos;
    }
}
