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
public class Bioinformatics2 extends Bioinformatics {
    public List neighbours(String pattern, int d){
        
        List neighbourhood= new ArrayList();
        List suffixNeighbours= new ArrayList();
        if(d==0)
        {
         List noNeighbours=new ArrayList();
         noNeighbours.add(pattern);
         return noNeighbours;
        }
        if(pattern.length()==1){
         List allBases= new ArrayList();
         allBases.add("A");allBases.add("C");allBases.add("G");allBases.add("T");
         return allBases;
        }
        //Recursively calling neighbours for suffix.
        String suffix=pattern.substring(1);
        char firstSymbol=pattern.charAt(0);
        
        suffixNeighbours=neighbours(suffix,d);
        
        for( Object str:suffixNeighbours){
             if(hammingDistance(suffix,str.toString())<d){
               neighbourhood.add( "A"+ str.toString()  );
               neighbourhood.add( "C"+ str.toString()  );
               neighbourhood.add( "G"+ str.toString()  );
               neighbourhood.add( "T"+ str.toString()  );
             }
             else{
               neighbourhood.add(firstSymbol + str.toString()  );
             }
        }
        return neighbourhood;
    }
    
    public List motifEnumeration(List dna,int k, int d){
        List patterns= new ArrayList();
        String kmer,pattern;
        List kmerneighbours;
        int presentlocally,presentglobally;
        for(Object str:dna){
            for(int i=0;i<=str.toString().length()-k;i++){
               kmer=str.toString().substring(i,i+k);
               kmerneighbours=neighbours(kmer,d);
               for(Object kmerd:kmerneighbours){ 
                  presentglobally=0;
                   for(Object str2:dna){
                    presentlocally=0; 
                    //Traversing through each DNA string one at a time
                    for(int j=0; j<=str2.toString().length()-k;j++){
                        pattern=str2.toString().substring(j,j+k);
                        if(hammingDistance(pattern,kmerd.toString())<=d){
                          presentlocally=1;
                        }
                    }
                    
                    if(presentlocally==0){
                        presentglobally=0;
                        break;
                    }
                    else{
                        presentglobally=1;
                    }
                 
                  }
                   
                 if(presentglobally==1){
                     patterns.add(kmerd.toString());
                 }
               }
               
               
            }
        }
        
        
        patterns=removeDuplicates(patterns);
        
        return patterns;
    }
    
    public List removeDuplicates(List frequentPatterns){
     
    List uniqueElements=new ArrayList(); 
    Object tempPattern;
    int present;
        for (Object pattern : frequentPatterns) {
            present=0;
            tempPattern=pattern;
            for (Object pattern1 : uniqueElements) {
              if(pattern1.equals(tempPattern))
              {
               present=1;
               break;
              }
            }
            if(present==0)
                uniqueElements.add(tempPattern);
        }
        
        return(uniqueElements);
        
    }
    
    public int distanceBetweenPatternAndStrings(String pattern,List dna){
        int distance=0,k=pattern.length();
        int hammingDistance=k;
        String kmer;
        for(Object text:dna){
            hammingDistance=k;
            for(int i=0;i<=text.toString().length()-k;i++){
                kmer=text.toString().substring(i,i+k);
                if(hammingDistance>hammingDistance(kmer,pattern)){
                    hammingDistance=hammingDistance(kmer,pattern);
                }
            }
            distance+=hammingDistance;
        }
        return distance;
    }
    
    public String medianString(List dna,int k){
       String median = null,pattern;
       int distance=dna.size()*k;
       int end=power(4,k),i,j;
       
       for(i=0;i<=end-1;i++){
           pattern=numberToPattern(i,k);
           if(distance>distanceBetweenPatternAndStrings(pattern,dna)){
               distance=distanceBetweenPatternAndStrings(pattern,dna);
               median=pattern;
           }
       }
       
       return median;
    }
    
    //Redifining Max to account for floating point values
    public double maximum(double[] array){
     double max=array[0];
     for(int i=1;i<array.length;i++){
         if(array[i]>max){
         max=array[i];
        }
    
     }  
     return max;
    }
    
    public String profileMostProbableKmer(String text, int k, double[][] profileMatrix){
        String mostProbable=null,pattern;
        double[] patternProbability= new double[text.length()-k+1];
        
        for(int i=0;i<=text.length()-k;i++){
            pattern=text.substring(i,i+k);
            patternProbability[i]=patternProbability(pattern,profileMatrix);
        }
        
        double maxProbability=maximum(patternProbability);
        int maxPosition=0;
        for(int i=0;i<=text.length()-k;i++){
            if(patternProbability[i]==maxProbability){
                maxPosition=i;
                break;
            }
        }
        
        mostProbable=text.substring(maxPosition,maxPosition+k);        
        
        return mostProbable;
        
    }
    
    public double patternProbability(String pattern, double[][] profileMatrix){
        double patternProbability=1;
        char currentSymbol;
        int i;
        for(int j=0;j<pattern.length();j++){
            currentSymbol=pattern.charAt(j);
            i=symbolToNumber(currentSymbol);
            patternProbability*=profileMatrix[i][j];
        }
        return patternProbability;
    }
    
  
    public String[] greedyMotifSearch(List dna,int k, int t){
        String[] bestMotifs= new String[t];
        String[] motifs=new String[t];
        double[][] profileMatrix;
        String firstString;
        
        for(int i=0;i<t;i++){
            bestMotifs[i]=dna.get(i).toString().substring(0,0+k);
        }
        
        double minscore=score(bestMotifs);
        
        firstString=dna.get(0).toString();
        
        for(int i=0;i<=firstString.length()-k;i++){
            motifs[0]=firstString.substring(i,i+k);
            for(int j=1;j<t;j++){
                profileMatrix=formProfileMatrix(motifs,k);
                motifs[j]=profileMostProbableKmer(dna.get(j).toString(), k, profileMatrix);
            }
           
           if(score(motifs)<minscore){
               minscore=score(motifs);
           
            for(int count=0;count<t;count++){
                bestMotifs[count]=motifs[count];
            }
            
              
           }
           
       }   

        return bestMotifs;
    }
    
    public String[] greedyMotifSearchWithPseudocounts(List dna,int k, int t){
        String[] bestMotifs= new String[t];
        String[] motifs=new String[t];
        double[][] profileMatrix;
        String firstString;
        
        for(int i=0;i<t;i++){
            bestMotifs[i]=dna.get(i).toString().substring(0,0+k);
        }
        
        double minscore=score(bestMotifs);
        
        firstString=dna.get(0).toString();
        
        for(int i=0;i<=firstString.length()-k;i++){
            motifs[0]=firstString.substring(i,i+k);
            for(int j=1;j<t;j++){
                profileMatrix=formProfileMatrixWithPseudocounts(motifs,k);
                motifs[j]=profileMostProbableKmer(dna.get(j).toString(), k, profileMatrix);
            }
           
           if(score(motifs)<minscore){
               minscore=score(motifs);
           
            for(int count=0;count<t;count++){
                bestMotifs[count]=motifs[count];
            }
            
              
           }
           
       }   

        return bestMotifs;
    }
    
    public double[][] formProfileMatrix(String[] motifs,int k){
        double[][] profileMatrix= new double[4][k];
        double[][] countMatrix=new double[4][k];
        
        for(int j=0;j<k;j++){
            for(int i=0;i<4;i++){
                profileMatrix[i][j]=0.0;
                countMatrix[i][j]=0.0;
            }
        }
        
        int row;
        String motif;
        for(int j=0;j<k;j++){
            for(int i=0;i<motifs.length;i++){
                motif=motifs[i];
                row=symbolToNumber(motif.charAt(j));
                countMatrix[row][j]+=1;
            }
        }
        //Creating Profile Matrix from Count Matrix will divide counts by total
        //number of motifs to get probabilities
        double t=motifs.length;
        for(int j=0;j<k;j++){
           for(int i=0;i<4;i++){
               profileMatrix[i][j]=countMatrix[i][j]/t;
           }  
        }
        return profileMatrix;
    }
    
    public double[][] formProfileMatrixWithPseudocounts(String[] motifs,int k){
        double[][] profileMatrix= new double[4][k];
        double[][] countMatrix=new double[4][k];
        
        for(int j=0;j<k;j++){
            for(int i=0;i<4;i++){
                profileMatrix[i][j]=0.0;
                countMatrix[i][j]=0.0;
            }
        }
        
        int row;
        String motif;
        for(int j=0;j<k;j++){
            for(int i=0;i<motifs.length;i++){
                motif=motifs[i];
                row=symbolToNumber(motif.charAt(j));
                countMatrix[row][j]+=1;
            }
        }
        //Adding Pseudocounts
        for(int j=0;j<k;j++){
            for(int i=0;i<4;i++){
             countMatrix[i][j]+=1; 
            }    
        }
        //Creating Profile Matrix from Count Matrix will divide counts by total
        //number of motifs to get probabilities
        double t=motifs.length;
        
        for(int j=0;j<k;j++){
           for(int i=0;i<4;i++){
               //Modifying division to account for pseudocounts
               profileMatrix[i][j]=countMatrix[i][j]/(t+4);
           }  
        }
        return profileMatrix;
    }
    
    //Devising way to calculate scores from profile matrix
    public double score(String[] motifs){
        double score=0.0;
        int k=motifs[0].length();
        double[][] profileMatrix=formProfileMatrix(motifs,k);
        double[] scores=new double[k];
        double[] column=new double[4];
        double maxColumn;
        for(int j=0;j<k;j++){
            for(int i=0;i<4;i++){
                column[i]=profileMatrix[i][j];
            }
            maxColumn=maximum(column);
            scores[j]=(1.0-maxColumn)*motifs.length;
            score+=scores[j];
        }
        return score;
    }
    
    public double score(List motifs){
        double score=0.0;
        int k=motifs.get(0).toString().length();
        double[][] profileMatrix=formProfileMatrix(convertListToStringArray(motifs),k);
        double[] scores=new double[k];
        double[] column=new double[4];
        double maxColumn;
        for(int j=0;j<k;j++){
            for(int i=0;i<4;i++){
                column[i]=profileMatrix[i][j];
            }
            maxColumn=maximum(column);
            scores[j]=(1.0-maxColumn)*motifs.size();
            score+=scores[j];
        }
        return score;
    }
    
    public String[] formMotifs(double[][] profileMatrix,int k,List dna){
        String[] motifs=new String[dna.size()];
        String pattern;
        for(int i=0;i<dna.size();i++){
            pattern=profileMostProbableKmer(dna.get(i).toString(), k, profileMatrix);
            motifs[i]=pattern;
        }
        return motifs;
    }
    
    public String[] randomizedMotifSearch(List dna,int k, int t){
        double[][] profileMatrix;
        String[] bestMotifs=new String[t];
        Random random= new Random();
        String[] motifs= new String[t];
        String text;
        int rndnum;
        //Getting motifs with random starting positions
        for(int i=0;i<t;i++){
          text=dna.get(i).toString();
          rndnum=random.nextInt(text.length()-k+1);
          motifs[i]=text.substring(rndnum,rndnum+k);
        }
        
        for(int i=0;i<t;i++){
            bestMotifs[i]=motifs[i];
        }
        
        while(true){    
            profileMatrix=formProfileMatrixWithPseudocounts(motifs, k);
            motifs=formMotifs(profileMatrix,k,dna);
            if(score(motifs)<score(bestMotifs)){
                    for(int count=0;count<t;count++){
                        bestMotifs[count]=motifs[count];
                    }
            }
            else
                return bestMotifs;        
        }
      
    }

    public String[] runRandomizedMotifSearch(List dna, int k,int t,int iterations){
        String bestMotifs[]=randomizedMotifSearch(dna,k,t);
        String motifs[];
        for(int i=0;i<iterations;i++){
            motifs=randomizedMotifSearch(dna,k,t);
            if(score(motifs)<score(bestMotifs)){
                for(int count=0;count<t;count++){
                        bestMotifs[count]=motifs[count];
                }
            }
        }
        return bestMotifs;
    }
    
    
    public String[] convertListToStringArray(List l){
        String[] array=new String[l.size()];
        for(int i=0;i<l.size();i++){
            array[i]=l.get(i).toString();
        }
        return array;
    }
    
    public List convertStringArrayToList(String[] array){
        List l= new ArrayList();
        for(int i=0;i<array.length;i++){
            l.add(array[i]);
        }
        return l;
    }
    
    public void printMatrix(double[][] matrix,int k){
        for(int i=0;i<4;i++){
            for(int j=0;j<k;j++){
                System.out.print(matrix[i][j]);
                System.out.print('\t');
            }
            System.out.println("");
        }
    }
    
   
    public String[] gibbsSampler(List dna,int k,int t, int n){

        String[] bestMotifs=new String[t];
        Random random= new Random();
        String[] motifs= new String[t];
        String text;
        int rndnum;
        //Getting motifs with random starting positions
        for(int i=0;i<t;i++){
          text=dna.get(i).toString();
          rndnum=random.nextInt(text.length()-k+1);
          motifs[i]=text.substring(rndnum,rndnum+k);
        }
        //Copying values of motifs into bestMotifs
        for(int i=0;i<t;i++){
            bestMotifs[i]=motifs[i];
        }
        
        for(int j=0;j<n;j++){
            int i=random.nextInt(t);
            List tempMotifs=new ArrayList();
            //Gettting all motifs except motif i
            for(int count=0;count<t;count++)
                if(count!=i)
                    tempMotifs.add(motifs[count]);
            double[][] profileMatrix=formProfileMatrixWithPseudocounts(convertListToStringArray(tempMotifs),k);
            motifs[i]=profileRandomlyGeneratedKmer(profileMatrix,k,i,dna);
            
            if(score(motifs)<score(bestMotifs)){
                for(int count=0;count<t;count++){
                    bestMotifs[count]=motifs[count];
                }
              
            }    
            
        }

        
        return bestMotifs;
    }
    
    public String profileRandomlyGeneratedKmer(double[][] profileMatrix,int k,int i,List dna){
        String randomKmer=null;
        String text=dna.get(i).toString();
        int n=text.length()-k+1;
        double[] patternProbabilities= new double[n];
        String pattern;
        for(int count=0;count<n;count++){
            pattern=text.substring(count,count+k);
            patternProbabilities[count]=patternProbability(pattern,profileMatrix);
        }
        double probabilitySum=0.0;
        for(int j=0;j<n;j++){
            probabilitySum+=patternProbabilities[j];
        }
        double[] normalizedPatternProbabilities= new double[n];
        for(int j=0;j<n;j++){
            normalizedPatternProbabilities[j]=patternProbabilities[j]/probabilitySum;
        }
        
        int pos=improvedRandom(normalizedPatternProbabilities,n);
        
        randomKmer=text.substring(pos,pos+k);
        
        return randomKmer;
    }
    //Method to get array positions using a weighted probability array.
    public int improvedRandom(double[] normalizedPatternProbabilities,int n){
        int i=0,pos=0;
        boolean next;
        
        double[] cumulativeProbabilities=new double[n];
        cumulativeProbabilities[0]=normalizedPatternProbabilities[0];
        
        for(int j=1;j<n;j++){
            cumulativeProbabilities[j]=normalizedPatternProbabilities[j]+cumulativeProbabilities[j-1];
        }
        double randomNumber=Math.random();
        
        do{
            next=false;
            if(randomNumber<=cumulativeProbabilities[i]){
                pos=i;
                break;
            }
            else{
                next=true;
                i++;
            }
            
        }while(next==true);
        
        return pos;        
    }
    
    public String[] runGibbsSampler(List dna, int k,int t,int n,int iterations){
        String bestMotifs[]=gibbsSampler(dna,k,t,n);
        String motifs[];
        for(int i=0;i<iterations;i++){
            motifs=gibbsSampler(dna,k,t,n);
            if(score(motifs)<score(bestMotifs)){
                for(int count=0;count<t;count++){
                        bestMotifs[count]=motifs[count];
                }
            }
        }
        return bestMotifs;
    }
   
    
}
