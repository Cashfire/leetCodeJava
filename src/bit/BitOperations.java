package bit;

import java.util.*;


public class BitOperations {

    public static int hammingDistance(int x, int y) {
    	int z = x ^ y;
    	
        int cnt = 0;
        while(z != 0) {
        	System.out.println("z="+z);
            if ((z & 1) == 1) cnt++;
            z = z >> 1;
        }
        return cnt;
    }
    
    public static int hammingDistance1(int x, int y) {
        int z = x ^ y;
        int cnt = 0;
        while (z != 0) {
        	System.out.println("z="+z);
            z &= (z - 1);
            cnt++;
        }
        return cnt;
    }
    
    public static int[] singleNumber(int[] nums) {
        int diff = 0;
        for (int num : nums) diff ^= num;
        System.out.println("diff="+diff+"("+Integer.toBinaryString(diff)+"),"
        		+"-diff="+ Integer.toBinaryString(-diff));
        diff &= -diff;//get the number to divide the array into 2 groups
        System.out.println("diff&=-diff = "+diff+"("+Integer.toBinaryString(diff)+")");
        int[] ret = new int[2];
        for (int num : nums) {
            if ((num & diff) == 0) ret[0] ^= num;
            else ret[1] ^= num;
        }
        return ret;
    }
    
    
    public static int reverseBits1(int n) {
        int ret = 0;
        for (int i = 0; i < 32; i++) {
            ret <<= 1;
            ret |= (n & 1);
            n >>>= 1;
        }
        return ret;
    }
    private static Map<Byte, Integer> cache = new HashMap<>();

    public static int reverseBits(int n) {
        int ret = 0;
        for (int i = 0; i < 4; i++) {
            ret <<= 8;
//            System.out.println(i+" ret="+Integer.toBinaryString(ret));
            ret |= reverseByte((byte) (n & 0b11111111));
            n >>= 8;
        }
        return ret;
    }

    private static int reverseByte(byte b) {
    	System.out.println("reverseByte("+String.format("%8s",
    			Integer.toBinaryString(b & 0xFF)).replace(' ', '0')+")");
        if (cache.containsKey(b)) return cache.get(b);
        int ret = 0;
        byte t = b;
        for (int i = 0; i < 8; i++) {
            ret <<= 1;
            ret |= t & 1;
            t >>= 1;
        }
        cache.put(b, ret);
        return ret;
    }
    
    
    //693. Binary Number with Alternating Bits (Easy)
    public static boolean hasAlternatingBits(int n) {
        int a = (n >> 1) ^ n;   //(0101 >> 1) = 0010, 0010^0101 -> 0111
        // (a+1)=1000
        return (a & (a+1)) == 0;
    }
    
    
    // 476. Number Complement (Easy)
    public static int findComplement(int num) {
        if (num == 0) return 1;
        int mask = 1 << 30;
		System.out.println(Integer.toBinaryString(mask));
        while ((num & mask) == 0) mask >>= 1;
        mask = (mask << 1) - 1;
        return num ^ mask;
    }
    
    // 371. Sum of Two Integers (Easy)
    public static int getSum(int a, int b) {
    	System.out.println("a="+a+"("+Integer.toBinaryString(a)+"),"+
    						"b="+b+"("+Integer.toBinaryString(b)+")");
        return b == 0 ? a : getSum((a ^ b), (a & b) << 1);
    }
    
    
    // 318. Maximum Product of Word Lengths (Medium)
    // {"abcw","baz","foo","bar","xtfn","abcdef"} -> 16 ("abcw" * "xtfn")
    public static int maxProduct(String[] words) {
    	int max_len = 0, n = words.length;
    	int[] bits = new int[n];
    	for(int i=0; i< n; i++){
    		for(char c: words[i].toCharArray()){
    			bits[i] |= (1 << (c - 'a'));
    		}
    	}
    	for(int i = 0; i < n-1; i++){
    		for(int j = i+1; j < n; j++){
    			if((bits[i] & bits[j]) == 0) 
    				max_len = Math.max(max_len, words[i].length() * words[j].length());
    		}
    	}
    	return max_len;
    }

    // 338. Counting Bits (Medium)
    public static int[] countBits(int num) {
        int[] result = new int[num + 1];
        for(int i = 1; i <= num; i++){
            result[i] = result[i & (i-1)] + 1; 
        }
        return result;
    }
    
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] nums = {1,2,1,3,2,5};
		String string = "01010011000100101111010011110000";
		String reversed = "00001111001011110100100011001010";
//		int number = Integer.parseInt(string,2);
		System.out.println(Integer.toBinaryString(Integer.MAX_VALUE));
//		System.out.println("start to reverseBits("+number+") : ");
//		System.out.println(reverseBits(number) == Integer.parseInt(reversed,2));
		String[] w1 = {"abcw","baz","foo","bar","xtfn","abcdef"};
		String[] w2 = {"a","aa","aaa","aaaa"};
// 		System.out.println(maxProduct(w1));
		
	}

}
