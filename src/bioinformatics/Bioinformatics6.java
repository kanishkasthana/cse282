/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bioinformatics;

import java.io.*;
import java.util.*;
import java.lang.Math;

/**
 *
 * @author kanis_000
 */
public class Bioinformatics6 extends Bioinformatics5{
    public boolean prefixTrieMatching(String text,node root){
        int count=0;
        char symbol=text.charAt(count++);
        node v=root;
        while(true){
            
            edge edgeWithSymbol=v.charPresentInEdges(symbol);
            if(edgeWithSymbol!=null){
                
                v=edgeWithSymbol.getChild();
                
                if(v.getOutgoingEdges().isEmpty()){
                    return true;
                }
                if(count+1>text.length()-1){
                    return false;
                }
                symbol=text.charAt(count++);
            }
            else{
                return false;
            }
            
        }
    
    }
    
    public void trieMatching(String text,node root,PrintWriter out){
        for(int i=0;i<text.length();i++){
            String pattern=text.substring(i);
            boolean answer=prefixTrieMatching(pattern,root);
            if(answer==true){
                out.print(i);
                out.print(" ");
            }
        }
        out.println("");
    }
    
    
}
