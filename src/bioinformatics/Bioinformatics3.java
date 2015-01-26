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
    
}
