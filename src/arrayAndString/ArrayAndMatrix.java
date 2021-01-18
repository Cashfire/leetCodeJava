package arrayAndString;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

class Pair{
    public int first;
    public int second;
    
    public Pair(int x, int y){
      this.first = x;
      this.second = y; 
    }
} 

public class ArrayAndMatrix {
	//283 Move Zeros to the end
	public static void moveZeroes(int[] nums) {
		int write_idx = 0;
		for(int num : nums){
			if(num != 0){
				nums[write_idx++] = num;
			}
		}
		while(write_idx < nums.length){
			nums[write_idx++] = 0;
		}
	}
	// Move All Zeros to the Beginning of the Array
	public static void moveZeroesToBegining(int[] nums) {
		int n = nums.length;
		if(n <= 1) return;
		int write_idx = n -1;
		int i=n-1;
		// traverse from back to front to find all non-zeroes.
		for(; i>= 0; i--){
			if(nums[i] != 0) nums[write_idx--] = nums[i];		
		}
		// fill the beginning with zeroes
		i = 0;
		while(i <= write_idx) nums[i++] = 0;
	}

	//566 Reshape matrix (if convertible, row-traversing)
	public static int[][] matrixReshape(int[][] nums, int r, int c) {
		int m = nums.length, n = nums[0].length;
		if(r*c != m*n) return nums;
		int[][] result = new int[r][c];
		int idx = 0;
		for(int i = 0; i < r; i++){
			for(int j = 0; j < c; j++){
				result[i][j] = nums[idx / n][idx % n];
				idx++;
			}
		}
		return result;
	}

	// 485. Max Consecutive Ones (Easy)
	public static int findMaxConsecutiveOnes(int[] nums) {
		int max = 0, cur = 0;
		for(int num : nums){
			cur = (num == 0)? 0 : cur+1;
			max = Math.max(max, cur);
		}
		return max;
	}

	// 240. Search a 2D Matrix II (Medium) -matrix is m*n
	public static boolean searchMatrix(int[][] matrix, int target) {
		if(matrix == null || matrix.length == 0 || matrix[0].length == 0) return false;
		for(int r = 0; r < matrix.length && matrix[r][0] <= target; r++){
			for(int c = matrix[r].length - 1; c >= 0 && matrix[r][c] >= target; c--){
				if(matrix[r][c] == target) return true;
			}
		}
		return false;
	}

	// 378. Kth Smallest Element in a Sorted Matrix (Medium)
	//minHeap in java: PriorityQueue;
	public int kthSmallest(int[][] matrix, int k) {
		int m = matrix.length, n = matrix[0].length;
		PriorityQueue<Tuple> pq = new PriorityQueue<Tuple>((n1,n2) -> matrix[n1.x][n1.y]- matrix[n2.x][n2.y]);
		for(int i = 0; i < n && i < k; i++) pq.offer(new Tuple(0, i)); //, matrix[0][j]));
		Tuple t;
		for(int i = 0; i < k - 1; i++) { 
			t = pq.poll();
			if(t.x == m - 1) continue;
			pq.offer(new Tuple(t.x + 1, t.y));    //, matrix[t.x + 1][t.y]));
		}
		t= pq.poll(); //.val
		return matrix[t.x][t.y];
	}
	class Tuple {//implements Comparable<Tuple> {
		int x, y;//, val;
		public Tuple(int x, int y){ //, int val) {
			this.x = x; this.y = y;// this.val = val;
		}
		//	    @Override
		//	    public int compareTo(Tuple that) {
		//	        return this.val - that.val;
		//	    }
	}

	//378. Kth Smallest -- binary search;
	public int kthSmallest_binary(int[][] matrix, int k) {
		int m = matrix.length, n = matrix[0].length;
		int x= (int)Math.ceil(Math.sqrt(k)) -1;

		//		System.out.println(x);
		int lo = matrix[0][0], hi = matrix[x][x];

		while(lo < hi){
			int count = 0;
			int mid = (lo + hi)/2;
			//			System.out.print("lo="+lo+",hi="+hi+",mid="+mid);
			// n1=(<=m)LargestSmall, n2=(>m)SmallestLarge.
			int n1= lo, n2 = hi, r = 0, c = n-1;
			while(r < m && c >= 0){
				int cur = matrix[r][c];
				if(cur > mid){
					n2 = Math.min(n2, cur);
					c--;
				}else{
					count+= c+1;
					n1 = Math.max(n1, cur);
					r++;
				}
			}
			//			System.out.println(",cnt="+count+",n1="+n1+",n2="+n2);
			if(count == k){
				return n1;
			}else if(count > k){
				hi = n1;
			}else{
				lo = n2;
			}
		}
		return lo;
	}

	// 645. Set Mismatch (Easy) - avoid sorting which requires O(NlogN)
	/**
	 * given an array without order, output [duplicated, lost].
	 * e.g [4,1,2,2] -> [2, 3]; and [2,2] -> [2, 1]
	 * @param nums
	 * @return
	 */
	public static int[] findErrorNums(int[] nums) {
		int result[] = new int[2]; 
		int goodSum = nums.length * (nums.length + 1) / 2;
		int badSum = 0;
		for(int num : nums){
			int absNum = Math.abs(num);
			badSum += absNum;
			int idx = absNum - 1;
			nums[idx] *= -1;  //nums[num -1] is the position of num in a sorted array, mark it as negative.
			if(nums[idx] > 0) result[0] = absNum;  //positive means it's already marked by other and duplicated.
		}
		result[1] = result[0] + (goodSum - badSum);
		System.out.println("gS="+ goodSum+", bS="+badSum);
		return result;
	}

	// 287. Find the Duplicate Number (Medium)
	/**
	 * Given an array of n + 1 nums where each integer is between 1 and n (inclusive),
	 * find the duplicate one. e.g. [2,3,2,2] -> 2
	 * @param nums
	 * @return
	 */
	public static int findDuplicate_binary(int[] nums) {
		int min = 1, max = nums.length -1;
		while(min <= max){
			int mid = (min + max) / 2;
			int cnt = 0;
			for(int n: nums){
				if(n <= mid) cnt++;
			}
			System.out.println("min="+min+", max="+max+", mid="+mid+", cnt="+cnt);
			if(cnt > mid) max = mid-1;
			else min = mid+1;
		}
		return min;
	}

	public static int findDuplicate(int[] nums) {
		int slow = nums[0], fast = nums[nums[0]];
		System.out.println(slow+", "+fast);
		while (slow != fast) {
			slow = nums[slow];
			fast = nums[nums[fast]];
			System.out.println(slow+", "+fast);
		}
		fast = 0;
		System.out.println(fast = 0);
		while (slow != fast) {
			slow = nums[slow];
			fast = nums[fast];
			System.out.println(slow+", "+fast);
		}
		return slow;
	}

	//667. Beautiful Arrangement II (Medium)
	/**
	 * input n,k output an array with n numbers with k different distances. 
	 * e.g. (3,2)->[1,3,2], n=3 integers, k=2 different distance |1-3|=2, |3-2|=1.
	 * @param n
	 * @param k
	 * @return
	 */
	public static int[] constructArray(int n, int k) {
		int[] result = new int[n];
		result[0] = 1;
		for (int i = 1, interval = k; i <= k; i++, interval--) {
			result[i] = i % 2 == 1 ? result[i - 1] + interval : result[i - 1] - interval;
		}
		for (int i = k + 1; i < n; i++) {
			result[i] = i + 1;
		}
		return result;
	}

	// 697. Degree of an Array (Easy) 
	// Degree of an Array == length of shortest sub-array with most frequent element.
	//e.g. Input: [1,2,2,3,1,4,2] Output: 6 <-- [2,2,3,1,4,2] is shortest sub-array with 3 "2"s.
	public static int findShortestSubArray(int[] nums) {
		Map<Integer, Integer> count = new HashMap<>(), first = new HashMap<>();
		int max_degree = 1, min_len = 1;
		for(int i = 0; i < nums.length; i++){
			int k = nums[i];
			int cnt = count.getOrDefault(k, 0) + 1;
			first.putIfAbsent(k, i);
			count.put(k, cnt);

			if(cnt > max_degree){
				max_degree = cnt;
				min_len = i - first.get(k) + 1;
			}else if(cnt == max_degree && cnt != 1){
				min_len = Math.min(min_len, i - first.get(k) + 1);
			}

		}
		return min_len;
	}

	//766. Toeplitz Matrix (Easy)
	// Toeplitz matrix is diagonally symmetric matrix. e.g. "9", "55", "111", "222", "33", "4".
	// 1234
	// 5123
	// 9512
	public static boolean isToeplitzMatrix(int[][] matrix) {
		for(int r = 0; r < matrix.length -1; r++){
			for(int c = 0; c < matrix[0].length - 1; c ++){
				if(matrix[r+1][c+1] != matrix[r][c]) return false;
			}
		}
		return true;
	}

	// 565. Array Nesting (Medium)   O(n)
	public static int arrayNesting(int[] nums) {
		int max_size = 0;
		for(int i = 0; i < nums.length; i++){
			int cnt = 0;
			for(int j = i; nums[j] != -1;){	
				cnt++;
				int t = nums[j];
				nums[j] = -1; //mark as visited, so it visits an element once --> O(n)
				j = t;

			}
			max_size = Math.max(cnt, max_size);
		}
		return max_size;
	}

	// 769. Max Chunks To Make Sorted (Medium)
	// e.g. [1,2,0,3,4] --> 2 ([0,1,2],[3,4])
	// e.g. [4,x,x,x,x] --> must be 1 (why?)
	public static int maxChunksToSorted(int[] arr) {
		if(arr == null) return 0;
		int result = 0, right = arr[0];
		for(int i = 0; i< arr.length; i++){
			right = Math.max(right, arr[i]);
			if(right == i) result++; // the max until index i must be i
			System.out.println("i="+i+", r="+right+" ,ret="+result);
		}
		return result;
	}

	//239 Sliding Window Maximum (hard)
	// e.g. [4, 1, 3, 2, 6], window=3 --> [4,3,6]
	public static int[] maxSlidingWindow(int[] nums, int k) {
		ArrayDeque<Integer> indices = new ArrayDeque<>(); 
		int n = nums.length;
		if(n < k) return null;
		int[] ans = new int[n - k + 1];

		for(int i = 0; i < n; i++) {
			while(indices.size() > 0 && nums[i] >= nums[indices.getLast()]){
				indices.removeLast();      
			}
			indices.addLast(i);
			if(i- k + 1 >= 0) ans[i-k+1] = nums[indices.getFirst()];
			if(i-k+1 >= indices.getFirst()) indices.removeFirst();
			//    		System.out.println(indices);
			//    		System.out.println(Arrays.toString(ans));
		}
		return ans;
	}

	// 33. Search in Rotated Sorted Array
	// e.g. [4,5,6,7,0,1,2], 7 --> 3
	// e.g. [4,5,6,7,0,1,2], 3 --> -1
	public static int binarySearchRotated(int[] arr,int key) {
		int l = 0, h = arr.length -1;
		while(l <= h){
			int m = (l+h) / 2;
			//        	System.out.println("arr["+l+"]="+arr[l] + ",arr["+m+"]="+arr[m]+" ,arr["+h+"]="+arr[h]);
			if(arr[m] == key) return m;
			if(arr[m] >= arr[l]){
				if(arr[l] <= key && key < arr[m]) h = m - 1;
				else l = m + 1;
			}else{
				if(arr[m] < key && key <= arr[h]) l = m + 1;
				else h = m - 1;
			}     	
		}
		return -1;
	}

	// 1198 Find the Smallest Common Number
	//	public static Integer findLeastCommonNumber(int[] arr1, int[] arr2, int[] arr3) {
	public static Integer findLeastCommonNumber(int[] arr1, int[] arr2, int[] arr3) {
		int i = 0, j = 0, k = 0;

		while(i < arr1.length && j < arr2.length && k < arr3.length) {
			if(arr1[i] == arr2[j] && arr2[j] == arr3[k]) return arr1[i];

			if(arr1[i] <= arr2[j] && arr1[i] <= arr3[k]) i++;
			else if(arr2[j] <= arr1[i] && arr2[j] <= arr3[k]) j++;
			else if(arr3[k] <= arr1[i] && arr3[k] <= arr2[j]) k++;
			
		}
		return -1;
	}

	public static Integer findLeastCommonNumber2(int[] arr1, int[] arr2, int[] arr3) {
		int i = 0, j = 0, k = 0;

		while(i < arr1.length && j < arr2.length && k < arr3.length) {
			if(arr1[i] == arr2[j] && arr2[j] == arr3[k]) return arr1[i];

			while(i < arr1.length && (arr1[i] < arr2[j] || arr1[i] < arr3[k])) i++;
			while(j < arr2.length && (arr2[j] < arr1[i] || arr2[j] < arr3[k])) j++;
			while(k < arr3.length && (arr3[k] < arr1[i] || arr3[k] < arr2[j])) k++;
			
		}
		return -1;
	}

	// 189. Rotate Array (medium)
	 public static void rotateArray(List<Integer> arr, int k) {
		 int n = arr.size();
		 if( n == 0 || k == 0) return;
		 // check if k is positive or negative int.
		 if(k > n) k = k % n;
		 else if(k < 0) k = n + k;
		 
		 reverseArrayInPlace(arr, 0, n-1);
		 reverseArrayInPlace(arr, 0, k-1);
		 reverseArrayInPlace(arr, k, n-1);   
	}
	 
	private static void reverseArrayInPlace(List<Integer> arr, int low, int high){
		while(low <= high){
			int temp = arr.get(high);
			arr.set(high, arr.get(low));
			arr.set(low, temp);
			low++;
			high--;	
		}
	}
	
	// {1, 10, 20, 0, 59, 86, 32, 11, 9, 40}, 2 -> { 20, 0, 59, 86, 32, 11, 9, 40, 1, 10}
	public static void rotate_CyclicReplace(int[] nums, int k) { 
        k = k % nums.length;
        int count = 0;
        for (int start = 0; count < nums.length; start++) {
            int current = start;
            int prev = nums[start];
            do {
                int next = (current + k) % nums.length;
                int temp = nums[next];
                nums[next] = prev;
                prev = temp;
                current = next;
                count++;
            } while (start != current);
        }
    }

	//153. Find Minimum in Rotated Sorted Array
	//e.g. [3,1,2] -> 1
	public static int findMin(int[] nums) {
		// divide and conquer: T(n) =O(1) + T(n/2) = O(logN). 
		// Best case like [4,5,1,2,3] -> min([4,5],[1,2,3]) = min(4,1) = 1
		return findMin(nums, 0, nums.length - 1);
	}
	
	private static int findMin(int[] nums, int l, int r){
		//if only 1 or 2 numbers
		if(l + 1 >= r) return Math.min(nums[l], nums[r]);
		
		//if monotonically sorted
		if(nums[l] < nums[r]) return nums[l];
		//else( not sorted): continue to "divide and conquer"
		int m = l + (r - l)/ 2;
		return Math.min(findMin(nums, l, m-1), findMin(nums, m , r));
	}
	
    public static int findMin2(int[] nums) {
        int n = nums.length;
        int l = 0, h = n -1;
        while(l <= h){
            int m = (l + h) /2;
            if(m+1 < n && nums[m] > nums[m+1]) return nums[m+1];
            if(m-1 > 0 && nums[m] < nums[m-1]) return nums[m];
            if(nums[m] > nums[h]) l = m + 1;
            else h = m - 1;       
        }      
        return nums[l];
    }	
	
    // 34. Find First and Last Position of Element in Sorted Array with duplicates (medium) 
    // O(logN) time
    // e.g. nums = [5,7,7,8,8,8,8,10], target = 8 -> [3,6]
    public static int[] searchRange(int[] nums, int target) {
        int[] result = new int[2];
        result[0] = findFirstIdx(nums, target);
        result[1] = findLastIdx(nums, target);
        return result;
    }
	
    private static int findFirstIdx(int[] nums, int target){
    	int result = -1;
    	int l = 0, r = nums.length;
    	while(l <= r){
    		int m = l + (r - l)/ 2;
    		if(nums[m] == target) result = m;
    		if(nums[m] >= target){
    			r = m - 1;
    		}else{
    			l = m + 1;
    		}
    	}
    	return result;
    }
	
    private static int findLastIdx(int[] nums, int target){
    	int result = -1;
    	int l = 0, r = nums.length;
    	while(l <= r){
    		int m = l + (r - l)/ 2;
    		if(nums[m] == target) result = m;
    		if(nums[m] <= target){
    			l = m + 1;
    		}else{
    			r = m - 1;
    		}
    	}
    	return result;
    }
    
    
    // find Stock Buy Sell to Maximize Profit. 
    // Kadane's Algo, O(n) time & O(1) space.
    public static int[] findMaxStockBuySell(int[] arr){
    	if(arr.length <= 2) return arr;
    	int curr_buy = arr[0];
    	int global_sell = arr[1];
    	int gloabl_profit = global_sell - curr_buy;
    	int curr_profit = Integer.MIN_VALUE;
    	
    	for(int i = 1; i<arr.length; i++){
    		int curr_strock_price = arr[i];
    		curr_profit = curr_strock_price - curr_buy;
    		if(curr_profit > gloabl_profit){
    			gloabl_profit = curr_profit;
    			global_sell = curr_strock_price;
    		}
    		if(curr_strock_price < curr_buy)  curr_buy = curr_strock_price;
    	}
    	return (new int[] {global_sell- gloabl_profit, global_sell});
    }
    
    //56. Merge Intervals.
    // Given an array of intervals where intervals[i] = [starti, endi], merge all overlapping intervals, 
    // and return an array of the non-overlapping intervals that cover all the intervals in the input.
    public static int[][] mergeIntervals(int[][] intervals) {
        if(intervals.length <= 1) return intervals;
        //sort input
        Arrays.sort(intervals, (a, b)-> (a[0] == b[0]) ? (a[1]- b[1]): (a[0] - b[0]));
        
        List<int[]> res = new ArrayList<>();
        res.add(intervals[0]);
        for(int i = 1; i< intervals.length; i++){
        	int x1 = res.get(res.size() -1)[0];
        	int y1 = res.get(res.size() -1)[1];
        	int x2 = intervals[i][0];
        	int y2 = intervals[i][1];
        	if(x2 <= y1){
        		res.get(res.size() -1)[1] = Math.max(y1, y2);
        	}else {
				res.add(intervals[i]);
			}
        }
        return res.toArray(new int[0][]);
    }
    
    
	public static void main(String[] args) {
		//Test 56. Merge Intervals.
		int[][] intervals56 = {{12,15},{1,5},{1,3},{4,8}};
		int[][] merged = mergeIntervals(intervals56);
		for(int[] arr: merged){
			System.out.println(Arrays.toString(arr));
		}
		
		
		//Test findMaxBuySell
//		int[] stock_prices1 = {12, 5, 9, 19, 3};
//		int[] stock_prices2 = {21, 12, 11, 9, 6, 3};
//		System.out.println(Arrays.toString(findMaxStockBuySell(stock_prices1))+ "==[5, 19]");
//		System.out.println(Arrays.toString(findMaxStockBuySell(stock_prices2))+ "==[12, 11]");
		
		
		// Test 34. Find First and Last Position of Element in Sorted Array with duplicates
		int[] nums34 = {5,7,7,8,8,8,8,10};
		int target = 8;
//		System.out.println(Arrays.toString(searchRange(nums34, target)));
		
		
		//Test 153 findMin
//		int[] nums153 = {1,2,3,4};
//		int[] nums153_1 = {3, 1,2};
//		int[] nums153_2 = {1};
//		int[] nums153_3 = {4,5, 1,2,3};
//		int[][] test153 = {nums153, nums153_1, nums153_2, nums153_3};
//		for(int[] arr: test153){
//			System.out.println(findMin(arr));
//		}
			
		
		// TODO Auto-generated method stub
		int[] nums = {4,1,0,3,2};
		int[] nums287 = {1,2,3,3,4,5};

		int[][] arr = {{1,2}, {3,1}};
		
		// Test 283 Move Zeros to the end
		int[] nums283_0 = {};
		int[] nums283_1 = {0};
		int[] nums283_2 = {4,1,0,3,2};
		int[] nums283_3 = {0,1,0,3,0};
		int[] nums283_4 = {1,1};
		int[][] test283 = {nums283_0, nums283_1,nums283_2,nums283_3,nums283_4};
//		for(int[] nums283: test283){
//			moveZeroes(nums283);
//			System.out.println(Arrays.toString(nums283));
//		}
		// Test  Move Zeros to the beginning
//		for(int[] nums283: test283){
//			moveZeroesToBegining(nums283);
//			System.out.println(Arrays.toString(nums283));
//		}
		
		int[] nums1 = {1,1,0,1,1,1};
		int[] nums2 = {5,4,0,3,1,6,2};
		//		System.out.println(Arrays.deepToString(matrixReshape(arr, 1,4)));
		int[][] matrix = {{1,7,12},{3,11,15},{6,20,40}};
		int[][] matrix1 = {{ 1,  5,  9},{10, 11, 13},{12, 13, 15}};
		int[][] matrix2 = {
				{1,   4,  7, 11, 15},
				{2,   5,  8, 12, 19},
				{3,   6,  9, 16, 21},
				{10, 13, 14, 17, 24},
				{18, 20, 22, 23, 25}
		};
		// Test 287 findDuplicate_binary
		//		System.out.println(findDuplicate_binary(nums287));
		//		System.out.println(findMaxConsecutiveOnes(nums1));
		//		int[][] nums ={{}};
		//		System.out.print(searchMatrix(nums, 12));

		//		System.out.println(nums.length);

		//		Scanner scanner = new Scanner(System.in);
		//		name = scanner.nextLine();
		//		age = Integer.parseInt(scanner.next());
		//		name = scanner.next();
		//		ArrayAndMatrix arrayAndMatrix  = new ArrayAndMatrix();
		//		int num = arrayAndMatrix.kthSmallest(arr, 3);
		//test case for kth smallest element in sorted matrix.
		//		for(int i = 1; i <= 25; i++){
		//			System.out.print(arrayAndMatrix.kthSmallest(matrix2, i) + ", ");
		//		}
		//		System.out.println(Arrays.toString(findErrorNums(nums)));

		// Test 769 maxChunksToSorted
		//		System.out.println(maxChunksToSorted(nums2));

		//		int[] result = constructArray(8, 3);
		//		System.out.println(Arrays.toString(result));
		//
		//		for(int i = 1; i < result.length; i++){
		//			System.out.print(Math.abs(result[i]- result[i-1])+", ");
		//		}

		// Test 239 Sliding Window Maximum
		//		int[] nums239 = {1,3,-1,-3,5,3,6,7};
		//		int[] ans = maxSlidingWindow(nums239, 3);
		//		System.out.println("Test 239, output: "+ Arrays.toString(ans));

		// Test 33
//		int[] nums33 = {5, 6, 7, 0, 1, 2, 4};
//		for(int i : nums33){
//			System.out.println(binarySearchRotated(nums33, i));
//		}
//		System.out.println(binarySearchRotated(nums33, 3));
		
		// Test 1198 Find the Smallest Common Number
		int[] a1 = {6,7,10};
		int[] a2 = {1,5,7};
		int[] a3 = {1,6,7};
		int[] a4 = {2,5,6};
//		System.out.println(findLeastCommonNumber(a1, a2, a3));
//		System.out.println(findLeastCommonNumber(a1, a4, a3));
//		System.out.println(findLeastCommonNumber(a1, a2, a4));
		
//		System.out.println(findLeastCommonNumber2(a1, a2, a3));
//		System.out.println(findLeastCommonNumber2(a1, a4, a3));
//		System.out.println(findLeastCommonNumber2(a1, a2, a4));
		
		// Test 
//		List<Integer> nums34 = Arrays.asList(1,2,3,4,5,6,7);
//		rotateArray(nums34, -2);
//		System.out.println(nums34);
//		nums34 = Arrays.asList(1,2,3,4,5,6,7);
//		rotateArray(nums34, 2);
//		System.out.println(nums34);
//		nums34 = Arrays.asList(1,2,3,4,5,6,7);
//		rotateArray(nums34, 8);
//		System.out.println(nums34);
		
		// Test 189
//		int[] nums189 =  {1, 10, 20, 0, 59, 86, 32, 11, 9, 40};
//		for(int i = 0; i<= nums189.length; i++){
//			nums189 =  new int[] {1, 10, 20, 0, 59, 86, 32, 11, 9, 40};
//			rotate_CyclicReplace(nums189, i);
//			System.out.println(Arrays.toString(nums189));
//		}
//		rotate_CyclicReplace(nums189, 15);
//		System.out.println(Arrays.toString(nums189));
		 
		
	}

}
