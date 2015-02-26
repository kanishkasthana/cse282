/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bioinformatics;

import static bioinformatics.Bioinformatics3.getPos;
import java.io.*;
import java.util.*;
import java.lang.Math;

/**
 *
 * @author Kanishk Asthana kasthana@eng.ucsd.edu
 */
public class Bioinformatics {
    
    public static int upper=1;
    public static int lower=-1;
    public static int middle=0;
    
    private String text=null;
    private List frequentPatterns=null;
    int[] frequencyArray=null;
    private String reverseText=null;
    public static int red=1;
    public static int blue=2;
    
    public Bioinformatics(String text){
        this.text=text;
        
    }
    public Bioinformatics(){
        
    }
    
    public void setText(String text){
        this.text=text;
    }
    
    public String getText(){
        return this.text;
        
    }
    
    public String getReverseText(){
        this.reverseText();
        return this.reverseText;
    }
    public List getFrequentWords(){
     return this.frequentPatterns;
    }
    
    public int[] getFrequencyArray(){
     return frequencyArray;
    }
    
    public int patternCount(String pattern){
        int count=0,i;
        int k=pattern.length(),l=text.length();
        for(i=0;i<=(l-k);i++)
        {  
         if(text.substring(i,i+k).equals(pattern))
             count++;
        }
        return count;
    }
    
    public void frequentWords(int k){
    
        List frequentPatterns= new ArrayList();
        int[] count= new int[text.length()-k+1];
        int l=text.length(),i;
        String pattern;
        
        for(i=0;i<=(l-k);i++){
         pattern=text.substring(i,i+k);
         count[i]=patternCount(pattern);
        }
        //Storing Maximum Value from Count Array
        int maxCount=maximum(count);
        
        for(i=0;i<=(l-k);i++){
         if(count[i]==maxCount){
          frequentPatterns.add(text.substring(i,i+k));
         }
        }
        this.frequentPatterns=frequentPatterns;
        this.removeDuplicates();
    }
    
    public int maximum(int[] array){
     int max=array[0];
     for(int i=1;i<array.length;i++){
         if(array[i]>max){
         max=array[i];
        }
    
     }  
     return max;
    }
    public int minimum(int[] array){
     int min=array[0];
     for(int i=1;i<array.length;i++){
         if(array[i]<min){
         min=array[i];
        }
     }  
     return min;
    }
    
    public void removeDuplicates(){
     
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
        
        this.frequentPatterns=uniqueElements;
    }
    
    public int symbolToNumber(char symbol){
     int number;
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
    
    public char numberToSymbol(int number){
     char symbol;
     switch(number){
         case 0:
             symbol='A';
             break;
         case 1:
             symbol='C';
             break;
         case 2:
             symbol='G';
             break;
         case 3:
             symbol='T';
             break;
         default:
             symbol='A';
             break;
     }
     return symbol;
    }
    
    public int patternToNumber(String pattern){
     char symbol;   
     if(pattern.isEmpty())
         return 0;
     else{
      symbol=pattern.charAt(pattern.length()-1);
      pattern=pattern.substring(0,pattern.length()-1);
      return 4*patternToNumber(pattern)+ symbolToNumber(symbol);
     }
    }
    
    public String numberToPattern(int index,int k){
     if(k==1)
         return numberToSymbol(index)+"";//Converting character to string
     int prefixIndex=quotient(index,4);
     int r=remainder(index,4);
     String prefixPattern=numberToPattern(prefixIndex,k-1);
     char symbol=numberToSymbol(r);
     //Returning Concatenation;
     return prefixPattern+symbol;
    }
    
    public int quotient(int dividend, int divisor){
     return dividend/divisor;
    }
    
    public int remainder(int dividend, int divisor){
     return dividend%divisor;
    }
    
    public int power(int base,int exponent){
     int answer=1;
     for(int i=0;i<exponent;i++){
      answer=answer*base;
     }
     return answer;
    }
    
    public int[] computingFrequencies(int k){
     int end=power(4,k),i,j;
     int[] frequencyArray= new int[end];
     String pattern;
     
     for(i=0;i<=end-1;i++){
      frequencyArray[i]=0;
     }
     
     for(i=0;i<=text.length()-k;i++){
      pattern=text.substring(i,i+k);
      j=patternToNumber(pattern);
      frequencyArray[j]++;
     }
       this.frequencyArray=frequencyArray;
       return frequencyArray;
    }
    
    public int[] computingFrequencies(String text,int k){
     int end=power(4,k),i,j;
     int[] frequencyArray= new int[end];
     String pattern;
     
     for(i=0;i<=end-1;i++){
      frequencyArray[i]=0;
     }
     
     for(i=0;i<=text.length()-k;i++){
      pattern=text.substring(i,i+k);
      j=patternToNumber(pattern);
      frequencyArray[j]++;
     }
       return frequencyArray;
    }
    
    public List frequentWordsWithMismatches(int k, int d){
     int end=power(4,k),i,j;
     int[] frequencyArray= new int[end];
     String patternd,pattern;
     
     for(i=0;i<=end-1;i++){
         frequencyArray[i]=0;
     }
     
     for(i=0;i<=text.length()-k;i++){
         patternd=text.substring(i,i+k);
         for(j=0;j<=end-1;j++){
          pattern=numberToPattern(j,k);
          if(hammingDistance(pattern,patternd)<=d)
              frequencyArray[j]++;
         }
     }
     List frequentPatterns=new ArrayList();
     int maxCount=maximum(frequencyArray);
     
     for(i=0;i<=end-1;i++){
         if(frequencyArray[i]==maxCount){
          pattern=numberToPattern(i,k);
          frequentPatterns.add(pattern);
         }
     }
      this.frequencyArray=frequencyArray;
      
      return frequentPatterns;
    }
    
    public List frequentWordsWithMismatchesAndReverseComplement(int k, int d){
     int end=power(4,k),i,j;
     int[] frequencyArray= new int[end];
     
     String patternd,pattern;
     
     for(i=0;i<=end-1;i++){
         frequencyArray[i]=0;
     }
     
     String reverseText=getReverseText();
     
     for(i=0;i<=text.length()-k;i++){
         patternd=text.substring(i,i+k);
         for(j=0;j<=end-1;j++){
          pattern=numberToPattern(j,k);
          if(hammingDistance(pattern,patternd)<=d)
              frequencyArray[j]++;
         }
     }

     for(i=0;i<=reverseText.length()-k;i++){
         patternd=reverseText.substring(i,i+k);
         for(j=0;j<=end-1;j++){
          pattern=numberToPattern(j,k);
          if(hammingDistance(pattern,patternd)<=d)
              frequencyArray[j]++;
         }
     }

     List frequentPatterns=new ArrayList();
     int maxCount=maximum(frequencyArray);
     
     for(i=0;i<=end-1;i++){
         if(frequencyArray[i]==maxCount){
          pattern=numberToPattern(i,k);
          frequentPatterns.add(pattern);
         }
     }
      this.frequencyArray=frequencyArray;
      
      return frequentPatterns;
    }
    
    public void fasterFrequentWords(int k){
     List frequentPatterns=new ArrayList();
     computingFrequencies(k);
     int maxCount=maximum(this.frequencyArray);
     int end=power(4,k),i;
     String pattern;
     
     for(i=0;i<=end-1;i++){
         if(frequencyArray[i]==maxCount){
          pattern=numberToPattern(i,k);
          frequentPatterns.add(pattern);
         }
     }
      this.frequentPatterns=frequentPatterns;
    }
    
    public void findingFrequentWordsBySorting(int k){
     List frequentPatterns=new ArrayList();
     int[] count=new int[text.length()-k+1];
     int[] index=new int[text.length()-k+1];
     int[] sortedIndex=new int[text.length()-k+1];
     int i,maxCount;
     String pattern;
     for(i=0;i<=text.length()-k;i++){
      pattern=text.substring(i,i+k);
      index[i]=patternToNumber(pattern);
      count[i]=1;
     }
     sortedIndex=sort(index);
     
     for(i=1;i<=text.length()-k;i++){
         if(sortedIndex[i]==sortedIndex[i-1])
             count[i]=count[i-1]+1;
     }
     maxCount=maximum(count);
     
     for(i=0;i<=text.length()-k;i++){
         if(count[i]==maxCount){
             pattern=numberToPattern(sortedIndex[i],k);
             frequentPatterns.add(pattern);
         }            
     }
     this.frequentPatterns=frequentPatterns;
    }
    
    //Implementing bubble sort
    public static int[] sort(int[] array)
    {
    boolean swapped = true;
    int j = 0,temp;
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
    
    public void reverseText(){
     reverseText=reverseComplement(text);
    }
    public String reverseComplement(String forwardStrand){
        StringBuilder reverseStrand=new StringBuilder();
        char base;
        for(int i=forwardStrand.length()-1;i>=0;i--){
           base=forwardStrand.charAt(i);
           reverseStrand.append(complementaryBase(base));
        }
        return reverseStrand.toString();
    }
    
    public static char complementaryBase(char base){
     switch(base){
         case 'A': return 'T';
         case 'T': return 'A';
         case 'G': return 'C';
         case 'C': return 'G';
         default: return 'A';
     }
    }
    
    public String patternMatching(String pattern){
     StringBuilder positions=new StringBuilder();
     int k=pattern.length(),l=text.length();
        for(int i=0;i<=(l-k);i++)
        {  
         if(text.substring(i,i+k).equals(pattern))
         {
             positions.append(i);
             positions.append('\t');
         }
        }
          
     return positions.toString();
    }

    public List clumpFinding(String genome,int k,int t,int l){
        List frequentPatterns=new ArrayList();
        int end=power(4,k),i,j;
        int[] clump=new int[end];
        int[] frequencyArray;
        String text,pattern;
        for(i=0;i<=end-1;i++){
            clump[i]=0;
        }
        
        for(i=0;i<=genome.length()-l;i++){
          text=genome.substring(i,i+l);
          frequencyArray=computingFrequencies(text,k);
          for(j=0;j<=end-1;j++){
              if(frequencyArray[j]>=t)
                  clump[j]=1;
          }
        }
        
        for(i=0;i<=end-1;i++){
            if(clump[i]==1){
              pattern=numberToPattern(i,k);
              frequentPatterns.add(pattern);
            }
        }    
        return frequentPatterns;
    }
    
    public List betterClumpFinding(String genome,int k,int t,int l){
        List frequentPatterns=new ArrayList();
        int end=power(4,k),i,j;
        int[] clump=new int[end];
        int[] frequencyArray;
        String text,pattern,firstPattern,lastPattern;
        for(i=0;i<=end-1;i++){
            clump[i]=0;
        }
        text=genome.substring(0,0+l);
        
        frequencyArray=computingFrequencies(text,k);
          for(i=0;i<=end-1;i++){
              if(frequencyArray[i]>=t)
                  clump[i]=1;
          }
        
        for(i=1;i<=genome.length()-l;i++){
          firstPattern=genome.substring(i-1,i-1+k);
          j=patternToNumber(firstPattern);
          frequencyArray[j]--;
          lastPattern=genome.substring(i+l-k,i+l);
          j=patternToNumber(lastPattern);
          frequencyArray[j]++;
          if(frequencyArray[j]>=t)
              clump[j]=1;
          
        }
        
        for(i=0;i<=end-1;i++){
            if(clump[i]==1){
              pattern=numberToPattern(i,k);
              frequentPatterns.add(pattern);
            }
        }    
        return frequentPatterns;
    }
    
    public List minimumSkew(String genome){
     List mins=new ArrayList();
     int[] skew=new int[genome.length()+1];
     skew[0]=0;
     char current;
     for(int i=1;i<=genome.length();i++){
         current=genome.charAt(i-1);
         if(current=='G'){
          skew[i]=skew[i-1]+1;
         }
         else if(current=='C'){
          skew[i]=skew[i-1]-1;   
         }
         else
             skew[i]=skew[i-1];
     }
     int minSkew=minimum(skew);
     
     for(int i=0;i<=genome.length();i++){
         if(skew[i]==minSkew){
             mins.add(i);
         }
     }
     return mins;
    }
    
    public int hammingDistance(String text1,String text2){
        int distance=0;
        for(int i=0;i<text1.length();i++){
            if(text1.charAt(i)!=text2.charAt(i))
                distance++;
        }
        return distance;
    }
    
    public List approximatePatternMatch(String pattern,int d){
        List positions= new ArrayList();
        String newPattern;
        for(int i=0;i<=text.length()-pattern.length();i++){
            newPattern=text.substring(i,i+pattern.length());
            if(hammingDistance(pattern,newPattern)<=d)
                positions.add(i);
        }
        return positions;
    }
    public int approximatePatternCount(String pattern,int d){
        int count=0;
        String patternd;
        for(int i=0;i<=text.length()-pattern.length();i++){
            patternd=text.substring(i,i+pattern.length());
            if(hammingDistance(pattern,patternd)<=d)
                count++;
        }
        return count;
    }
    
    public static List <node> reorganizeCycle(List <node> cycle, node nodeWithUnexploredEdges){
        
        int pos=cycle.indexOf(nodeWithUnexploredEdges);
        List<node> tempCycle=new <node>ArrayList();
        tempCycle=cycle.subList(pos, cycle.size());
        tempCycle.addAll(cycle.subList(1, pos+1));
        return tempCycle;
        
    }
    
    public static boolean isCycleStillUnexplored(List <node>cycle){
        for(int i=0;i<cycle.size();i++){
            if(cycle.get(i).hasUnexploredEdges())
                return true;
        }
        
       return false;
    }
    
    //Returns the first Unexplored Node it finds from a cycle, null if there are no explored nodes
    public static node getUnexploredNodeFromCycle(List <node> cycle){
        for(int i=0;i<cycle.size();i++){
            if(cycle.get(i).hasUnexploredEdges())
                return cycle.get(i);
        }
        return null;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            //Getting Working Directory
            System.out.println(System.getProperty("user.dir"));
            //Storing inputs in list inputs
            List<String> matrixInputs=new <String>ArrayList();
            List<String> inputs= new <String>ArrayList();
            //Reading downloaded file
            File newFile=new File("testdata.txt");
            FileReader fileReader=new FileReader(newFile);
            BufferedReader reader=new BufferedReader(fileReader);
            String line = null;
            while ((line = reader.readLine()) != null) {
             inputs.add(line);
            }
                        
            PrintWriter out= new PrintWriter(new FileWriter("out.txt"));
            Bioinformatics5 newText=new Bioinformatics5();
            String data=inputs.get(0).substring(1,inputs.get(0).length()-1);
            StringTokenizer chromosomes=new StringTokenizer(data,")(");
            List <String>chromosomeStrings=new <String> ArrayList();
            int count=0;
            while(chromosomes.hasMoreTokens()){
                String currentChromosome=chromosomes.nextToken();
                chromosomeStrings.add(currentChromosome);
                StringTokenizer elements=new StringTokenizer(currentChromosome);
                count+=elements.countTokens();
            }
            
            //System.out.println(count);
            
            node[] nodes=new node[count*2];
            for(int i=0;i<nodes.length;i++){
                nodes[i]=new node(i+1);
            }
            
            List <edge> alledgesRed=newText.coloredEdges(chromosomeStrings, nodes, red);
            
            for(int i=0;i<alledgesRed.size();i++){
                edge currentEdge=alledgesRed.get(i);
                System.out.print("(");
                System.out.print(currentEdge.getParent().getNodeNumber());
                System.out.print(", ");
                System.out.print(currentEdge.getChild().getNodeNumber());
                System.out.print(")");
                if(i!=alledgesRed.size()-1)
                    System.out.print(", ");
            }
            
            System.out.println("");
            data=inputs.get(1).substring(1,inputs.get(1).length()-1);
            chromosomes=new StringTokenizer(data,")(");
            chromosomeStrings=new <String> ArrayList();
            while(chromosomes.hasMoreTokens()){
                String currentChromosome=chromosomes.nextToken();
                chromosomeStrings.add(currentChromosome);
            }
            
            List <edge> alledgesBlue=newText.coloredEdges(chromosomeStrings, nodes, blue);
            
            for(int i=0;i<alledgesBlue.size();i++){
                edge currentEdge=alledgesBlue.get(i);
                System.out.print("(");
                System.out.print(currentEdge.getParent().getNodeNumber());
                System.out.print(", ");
                System.out.print(currentEdge.getChild().getNodeNumber());
                System.out.print(")");
                if(i!=alledgesBlue.size()-1)
                    System.out.print(", ");
            }
            System.out.println("");
            
            List <node> notCountedInCycles= new <node>ArrayList();
            
            for(int i=0;i<nodes.length;i++){
             notCountedInCycles.add(nodes[i]);
            }
            
            List <List> listofCycles=new <List> ArrayList();
            
            int numCycles=0;
            for(int i=0;i<nodes.length;i++){
                if(notCountedInCycles.contains(nodes[i])){
                System.out.println("Start:");
                node startNode=nodes[i];
                System.out.println(startNode.getNodeNumber());
                notCountedInCycles.remove(startNode);
                node currentNode;
                edge firstEdge=startNode.getEdges().get(0);
                firstEdge.traversed();
                if(firstEdge.getChild().equals(startNode)){
                    currentNode=firstEdge.getParent();
                }
                else{
                    currentNode=firstEdge.getChild();
                }
                System.out.println(currentNode.getNodeNumber());
                notCountedInCycles.remove(currentNode);
                System.out.println("Reached Here:");
                while(!currentNode.equals(startNode)){    
                    
                    for(int j=0;j<currentNode.getEdges().size();j++){
                        edge currentEdge=currentNode.getEdges().get(j);
                        
                        if(!currentEdge.isTraversed()){
                            if(currentEdge.getChild().equals(currentNode)){
                                currentNode=currentEdge.getParent();
                            }
                            else{
                                currentNode=currentEdge.getChild();
                            }
                            currentEdge.traversed();
                            notCountedInCycles.remove(currentNode);
                            System.out.println(currentNode.getNodeNumber());
                        }
                    }
                    
                    
                   
                }
                
                numCycles++;
              }
           }
            System.out.println("Number of Cycles:");
            System.out.println(numCycles);
            out.close();
                
        }
        
        
        catch(Exception e)
        {
         e.printStackTrace();
        }
        
    }
    
}
