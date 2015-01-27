/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bioinformatics;

/**
 *
 * @author kanis_000
 */
public class Bioinformatics3 extends Bioinformatics2{
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
}
