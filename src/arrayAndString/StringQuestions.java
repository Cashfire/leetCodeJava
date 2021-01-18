package arrayAndString;

public class StringQuestions {

	// 242. Valid Anagram (Easy)
	public static boolean isAnagram(String s, String t) {
        int[] cnts = new int[256];
        for(char c : s.toCharArray()){
        	cnts[c]++;
        }
        for(char c : t.toCharArray()){
        	cnts[c]--;
        }
        
        for(int cnt : cnts){
        	if(cnt != 0) return false;
        }
        return true;
    }
	
	//409. Longest Palindrome (Easy), case sensitive,so "Aa" is not a palindrome here.
	//e.g."abccccdd"->7, b/c longest palindrome is "dccaccd" whose length is 7.
	public static int longestPalindrome(String s) {
        int len = 0;
        int[] cnts = new int[256];
        for(char c : s.toCharArray()){
        	cnts[c]++;
        }
        for(int cnt:cnts){
        	len += (cnt/2) * 2;
        }
        if(len < s.length()) len++;
        return len;
    }
	
	// 205. Isomorphic Strings (Easy), assume both have the same length.
    public static boolean isIsomorphic(String s, String t) {
    	int[] preIndexS = new int[256], preIndexT = new int[256];
    	for(int i = 0; i< s.length(); i++){
    		char cS = s.charAt(i), cT = t.charAt(i);
    		if(preIndexS[cS] != preIndexT[cT]) return false;
    		preIndexS[cS] = i+1;
    		preIndexT[cT] = i+1;
    	}
        return true;
    }
	
	// 647. Palindromic Substrings (Medium)
    public static int countSubstrings(String s) {
        int count = s.length();
        for(int i = 0; i< s.length(); i++){
        	// for even length, like "...abba..."
        	int start = i, end = i+1;
        	while(start >= 0 && end < s.length() && s.charAt(start) == s.charAt(end)){
//        		System.out.println("("+start+","+end+"): "+s.substring(start,end+1));
        		start--;
        		end++;
        		count++;
        	}
        	//for odd length, like "...aba..."
        	start = i-1;
        	end = i+1;
        	while(start >= 0 && end < s.length() && s.charAt(start) == s.charAt(end)){
//        		System.out.println("("+start+","+end+"): "+s.substring(start,end+1));
        		start--;
        		end++;
        		count++;
        	}
        }
        return count;
    }
    
    // 9. Palindrome Number (Easy)
    public static boolean isPalindrome(int x) {
    	if(x == 0) return true;
    	if(x < 0 || x%10 == 0) return false;
        int reversedHalf = 0;
        while(reversedHalf < x){
        	reversedHalf = reversedHalf*10 + x%10;
        	x /= 10;
        }
        return x == reversedHalf || x == reversedHalf/10;
    }
    
    // 696. Count Binary Substrings (easy)
    //e.g. "10101"-> 4 substrings: "10", "01", "10", "01" that have equal number of consecutive 1's and 0's.
    public static int countBinarySubstrings(String s) {
        int preLen = 0, curLen = 1, cnt = 0;
        for(int i= 1; i<s.length(); i++){
        	if(s.charAt(i) == s.charAt(i-1)){
        		curLen++;
        	}else{
        		cnt += Math.min(curLen, preLen);
        		preLen = curLen;
        		curLen = 1;
        	}
        }
        return cnt + Math.min(curLen, preLen);
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "anagram", t = "nagaram";
		String s1= "paper",t1= "title";
		String palin = "abccccdd";
		String palindrome1 = "aaa";
		System.out.println(countBinarySubstrings("00011100"));
	}

}
