/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bioinformatics;

import static bioinformatics.Bioinformatics3.getPos;
import java.io.*;
import java.util.*;

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
            File newFile=new File("rosalind_5j.txt");
            FileReader fileReader=new FileReader(newFile);
            BufferedReader reader=new BufferedReader(fileReader);
            String line = null;
            while ((line = reader.readLine()) != null) {
             inputs.add(line);
            }
            File matrixFile=new File("BLOSUM62.txt");
            FileReader matrixFileReader=new FileReader(matrixFile);
            BufferedReader matrixReader= new BufferedReader(matrixFileReader);
            String matrixLine=null;
            while((matrixLine=matrixReader.readLine())!=null){
                matrixInputs.add(matrixLine);
            }
            
            StringTokenizer letters=new StringTokenizer(matrixInputs.get(0));
            List alphabets=new ArrayList();
            //Reading in Alphabets            
            while(letters.hasMoreTokens())
            {
                alphabets.add(letters.nextToken());
            }
            //Creating Scoring Matrix;
            int[][] scoringMatrix=new int[alphabets.size()][alphabets.size()];
            for(int i=1;i<matrixInputs.size();i++){
                StringTokenizer row=new StringTokenizer(matrixInputs.get(i));
                char letter=row.nextToken().toString().charAt(0);
                int j=0;
                while(row.hasMoreTokens()){
                    scoringMatrix[i-1][j++]=Integer.parseInt(row.nextToken().toString());
                }    
            }
            
            String firstProtein=inputs.get(0);
            String secondProtein=inputs.get(1);
            
            //firstProtein="PRTEINS";
            //secondProtein="PRTWPSEIN";
            
            int gapOpeningPenalty=11;
            int gapExtensionPenalty=1;
            //Creating PrintWriter for writing to output file
            PrintWriter out= new PrintWriter(new FileWriter("out.txt"));
            //Creating new Object to handle this string
            Bioinformatics3 newText=new Bioinformatics3();
            int n=firstProtein.length()+1;
            int m=secondProtein.length()+1;
            node[][] middleNodeMatrix=new node[n][m];
            node[][] upperNodeMatrix=new node[n][m];
            node[][] lowerNodeMatrix=new node[n][m];
            
            //Initializing all nodes
            for(int i=0;i<n;i++){
                for(int j=0;j<m;j++){
                    middleNodeMatrix[i][j]=new node(i,j,middle);
                    upperNodeMatrix[i][j]=new node(i,j,upper);
                    lowerNodeMatrix[i][j]=new node(i,j,lower);
                }
            }
            
            node sourcenode=middleNodeMatrix[0][0];
            node sinknode=middleNodeMatrix[n-1][m-1];
            
            List <edge>alledges=new <edge>ArrayList();
            //Adding Diagonal Edges to middleNodeMatrix
            for(int i=1;i<n;i++){
                for(int j=1;j<m;j++){
                    char rowchar=firstProtein.charAt(i-1);
                    char columnchar=secondProtein.charAt(j-1);
                    int row=getPos(rowchar,alphabets);
                    int column=getPos(columnchar,alphabets);
                    int score=scoringMatrix[row][column];
                    alledges.add(new edge(middleNodeMatrix[i-1][j-1],middleNodeMatrix[i][j],score));
                }
            }
            
            //I think there might be confusion between the v and the w and whether they correspond to the upper or lower matrices. make sure you take this into 
            //account when you are getting the final output dude. damn this problem is hard. Now I know what hard kind of means!
            
            
            //Adding vertical edges to lowerNodeMatrix
            for(int i=1;i<n;i++){
                for(int j=0;j<m;j++){
                    alledges.add(new edge(lowerNodeMatrix[i-1][j],lowerNodeMatrix[i][j],-1*gapExtensionPenalty));
                }
            }
            
            //Adding horizontal edges to upperNodeMatrix
            for(int i=0;i<n;i++){
                for(int j=1;j<m;j++){
                    alledges.add(new edge(upperNodeMatrix[i][j-1],upperNodeMatrix[i][j],-1*gapExtensionPenalty));
                }
            }
            
            //Adding edges with gapOpening penalty to lower from middle and edges with zero weights from lower to middle
            
            for(int i=0;i<n-1;i++){
                for(int j=0;j<m;j++){
                    alledges.add(new edge(middleNodeMatrix[i][j],lowerNodeMatrix[i+1][j],-1*gapOpeningPenalty));
                    alledges.add(new edge(lowerNodeMatrix[i+1][j],middleNodeMatrix[i+1][j],0));
                }
            }
            
            for(int i=0;i<n;i++){
                for(int j=0;j<m-1;j++){
                   alledges.add(new edge(middleNodeMatrix[i][j],upperNodeMatrix[i][j+1],-1*gapOpeningPenalty));
                   alledges.add(new edge(upperNodeMatrix[i][j+1],middleNodeMatrix[i][j+1],0));
                }
            }
            
            List<node> adjacencyList=newText.getAdjacencyList(node.allnodes);
            
            /*
            for(int i=0;i<adjacencyList.size();i++){
                System.out.print(adjacencyList.get(i).getI());
                System.out.print(",");
                System.out.print(adjacencyList.get(i).getJ());
                System.out.print(":");
                System.out.println(adjacencyList.get(i).getMatrixLevel());
            }
            */
            
            //Repopulating nodes:
            alledges=new ArrayList();

            //Adding Diagonal Edges to middleNodeMatrix
            for(int i=1;i<n;i++){
                for(int j=1;j<m;j++){
                    char rowchar=firstProtein.charAt(i-1);
                    char columnchar=secondProtein.charAt(j-1);
                    int row=getPos(rowchar,alphabets);
                    int column=getPos(columnchar,alphabets);
                    int score=scoringMatrix[row][column];
                    alledges.add(new edge(middleNodeMatrix[i-1][j-1],middleNodeMatrix[i][j],score));
                }
            }

            //Adding vertical edges to lowerNodeMatrix
            for(int i=1;i<n;i++){
                for(int j=0;j<m;j++){
                    alledges.add(new edge(lowerNodeMatrix[i-1][j],lowerNodeMatrix[i][j],-1*gapExtensionPenalty));
                }
            }
            
            //Adding horizontal edges to upperNodeMatrix
            for(int i=0;i<n;i++){
                for(int j=1;j<m;j++){
                    alledges.add(new edge(upperNodeMatrix[i][j-1],upperNodeMatrix[i][j],-1*gapExtensionPenalty));
                }
            }
            
            //Adding edges with gapOpening penalty to lower from middle and edges with zero weights from lower to middle
            
            for(int i=0;i<n-1;i++){
                for(int j=0;j<m;j++){
                    alledges.add(new edge(middleNodeMatrix[i][j],lowerNodeMatrix[i+1][j],-1*gapOpeningPenalty));
                    alledges.add(new edge(lowerNodeMatrix[i+1][j],middleNodeMatrix[i+1][j],0));
                }
            }
            
            for(int i=0;i<n;i++){
                for(int j=0;j<m-1;j++){
                   alledges.add(new edge(middleNodeMatrix[i][j],upperNodeMatrix[i][j+1],-1*gapOpeningPenalty));
                   alledges.add(new edge(upperNodeMatrix[i][j+1],middleNodeMatrix[i][j+1],0));
                }
            }
            
            for(int i=0;i<adjacencyList.size();i++){
                node temp=(node)adjacencyList.get(i);
                temp.computeScores(sinknode, sourcenode);
            }
            
            //outputting score
            //out.println(sinknode.getScore());
            
            List allnodes=newText.stripEverythingButTheCore(node.allnodes, sinknode, sourcenode);
            
            StringBuilder firstProteinAlign=new StringBuilder();
            StringBuilder secondProteinAlign=new StringBuilder();
            node currentNode=sinknode;
            while(currentNode!=null){
                
                if(currentNode.getBacktrackNode()!=null){
                    int backi,backj,i,j;            
                    i=currentNode.getI();
                    j=currentNode.getJ();
                    backi=currentNode.getBacktrackNode().getI();
                    backj=currentNode.getBacktrackNode().getJ();
                    if(backi==i-1 && backj==j){
                        secondProteinAlign.append("-");
                        firstProteinAlign.append(firstProtein.charAt(i-1));
                    }
                    if(backi==i && backj==j-1){
                        firstProteinAlign.append("-");
                        secondProteinAlign.append(secondProtein.charAt(j-1));
                    }
                    if(backi==i-1 && backj==j-1){
                        firstProteinAlign.append(firstProtein.charAt(i-1));
                        secondProteinAlign.append(secondProtein.charAt(j-1));
                    }
                    
                }
                currentNode=currentNode.getBacktrackNode();

            }
            //Thank you past mistakes you are awesome!
            out.println(sinknode.getScore());
            out.println(firstProteinAlign.reverse().toString());
            out.println(secondProteinAlign.reverse().toString());

            //Tests to see if graph was made correctly
            int count=0;
            List<node> nodes=Bioinformatics3.toList(upperNodeMatrix,n,m);
            for(int i=0;i<nodes.size();i++){
                count+=(nodes.get(i).getEdges().size());  
            }            
            
            System.out.println(count);
            System.out.println((n)*(m-1)*4);
            
            
            //System.out.println(sinknode.getChildren().size());
            /*
            List<node> adjacencyList=newText.getAdjacencyList(nodes);
            for(int i=0;i<adjacencyList.size();i++){
                System.out.println(adjacencyList.get(i).getNodeNumber());
            }

            //Repopulating nodes:
            alledges=new ArrayList();
            for(int i=2;i<inputs.size();i++){
                alledges.add(new edge(inputs.get(i).toString())); 
            }
            nodes=Bioinformatics3.toList(edge.allnodes);
            nodes=newText.stripEverythingButTheCore(nodes,maxnode,sinknode,sourcenode);
            
            for(int i=0;i<nodes.size();i++){
                if(nodes.get(i).getParents().isEmpty()){
                    nodes.get(i).setScore(0);
                }
            }
            

            node currentNode=null;
            
            for(int i=0;i<adjacencyList.size();i++){
                adjacencyList.get(i).computeScores();
                System.out.println(adjacencyList.get(i).getScore());
                if(adjacencyList.get(i).getNodeNumber()==sinknode){
                    currentNode=adjacencyList.get(i);
                }
            }
            out.println(currentNode.getScore());

            */
            
            
            
            /*
            
            
            node[][] nodeMatrix=new node[firstProtein.length()+1][secondProtein.length()+1];
            nodeMatrix[0][0]=new node(0,0);
            nodeMatrix[0][0].setScore(0);
            node sourcenode=nodeMatrix[0][0];
            nodeMatrix[n-1][m-1]=new node(n-1,m-1);
            node sinknode=nodeMatrix[n-1][m-1];
            List alledges=new ArrayList();
            

            for(int i=1;i<=firstProtein.length();i++){
                nodeMatrix[i][0]=new node(i,0);
                alledges.add(new edge(nodeMatrix[i-1][0],nodeMatrix[i][0],-1*gapPenalty));
            }
            
            for(int j=1;j<=secondProtein.length();j++){
                nodeMatrix[0][j]=new node(0,j);
                alledges.add(new edge(nodeMatrix[0][j-1],nodeMatrix[0][j],-1*gapPenalty));
                alledges.add(new edge(sourcenode,nodeMatrix[0][j],0));
            }
            
            for(int i=1;i<=firstProtein.length();i++){
            for(int j=1;j<=secondProtein.length();j++){
                char rowchar=firstProtein.charAt(i-1);
                char columnchar=secondProtein.charAt(j-1);
                int score;
                if(rowchar==columnchar)
                    score=1;
                else
                    score=-2;
                
                if(!(i==n-1 && j==m-1))//Take care of boolean algebra next time
                    nodeMatrix[i][j]=new node(i,j);
                alledges.add(new edge(nodeMatrix[i-1][j-1],nodeMatrix[i][j],score));
                alledges.add(new edge(nodeMatrix[i-1][j],nodeMatrix[i][j],-1*gapPenalty));
                alledges.add(new edge(nodeMatrix[i][j-1],nodeMatrix[i][j],-1*gapPenalty));
            }
         }
            for(int i=0;i<firstProtein.length();i++){
                alledges.add(new edge(nodeMatrix[i][m-1],sinknode,0));
            }
            for(int i=0;i<=firstProtein.length();i++){
               for(int j=0;j<=secondProtein.length();j++){
                   nodeMatrix[i][j].computeScores(sinknode,sourcenode);
               }
           }
            
            StringBuilder firstProteinAlign=new StringBuilder();
            StringBuilder secondProteinAlign=new StringBuilder();
            node currentNode=nodeMatrix[n-1][m-1];
            while(currentNode!=null){
                
                if(currentNode.getBacktrackNode()!=null){
                    int backi,backj,i,j;            
                    i=currentNode.getI();
                    j=currentNode.getJ();
                    backi=currentNode.getBacktrackNode().getI();
                    backj=currentNode.getBacktrackNode().getJ();
                    if(backi==i-1 && backj==j){
                        secondProteinAlign.append("-");
                        firstProteinAlign.append(firstProtein.charAt(i-1));
                    }
                    if(backi==i && backj==j-1){
                        firstProteinAlign.append("-");
                        secondProteinAlign.append(secondProtein.charAt(j-1));
                    }
                    if(backi==i-1 && backj==j-1){
                        firstProteinAlign.append(firstProtein.charAt(i-1));
                        secondProteinAlign.append(secondProtein.charAt(j-1));
                    }
                    
                }
                currentNode=currentNode.getBacktrackNode();

            }
            
            out.println(sinknode.getScore());
            out.println(secondProteinAlign.reverse().toString());
            out.println(firstProteinAlign.reverse().toString());
            */
            out.close();
        }
        
        
        catch(Exception e)
        {
         e.printStackTrace();
        }
        
    }
    
}
