package linkedlist;
import java.io.*;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Stack;

public class LinkedList{   
	// 445. Add Two Numbers II (Medium) 
	public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		Stack<Integer> f1 = new Stack<>(), f2 = new Stack<>();
		ListNode curr = null;

		while(l1 != null){f1.push(l1.val); l1 = l1.next;}
		while(l2 != null){f2.push(l2.val); l2 = l2.next;}

		int carry = 0;
		while(!f1.empty() || !f2.empty() || carry != 0){ 	
			int sum  = (f1.empty()? 0 : f1.pop()) + (f2.empty()? 0 : f2.pop()) + carry;
			ListNode tmp = new ListNode(sum % 10);
			carry = sum / 10;
			tmp.next = curr;
			curr = tmp;
		}
		return curr;
	}

	// 234. Palindrome Linked List (Easy)
	// O(1) space required, so cannot use stack to store and compare the halves.
	public static boolean isPalindrome(ListNode head) {
		if(head == null || head.next == null) return true;
		ListNode slow = head;
		ListNode fast = head.next;
		while(fast != null && fast.next != null){
			fast = fast.next.next;
			slow = slow.next;
		}
		ListNode reversedHalf = (fast == null)? reverse(slow) : reverse(slow.next); //if(fast==null),then odd nodes

		while(head != slow.next){
			if(head.val != reversedHalf.val) return false;
			head = head.next;
			reversedHalf = reversedHalf.next;
		}
		return true;	
	}

	//206. Reverse Linked List (Easy)
	public static ListNode reverse(ListNode head) {
		ListNode newHead = null;
		while(head != null){
			ListNode nextNode = head.next;
			head.next = newHead;
			newHead = head;
			head = nextNode;
		}
		return newHead;	
	}

	public static ListNode reverseRecur(ListNode head){
		if(head == null || head.next == null) return head;
		ListNode newHead = reverseRecur(head.next);
		head.next.next = head;
		head.next = null;
		return newHead;
	}
	
	
	// 92. Reverse Linked List II, (medium), 1 ≤ m ≤ n ≤ length of list.
	// Input: 1->2->3->4->5->NULL, m = 2, n = 4
	// Output: 1->4->3->2->5->NULL
    public static ListNode reverseBetween(ListNode head, int m, int n) {
    	if(m == n) return head;
    	int cnt = 1;
    	ListNode curr= head;
    	ListNode newHead= null;
    	ListNode end= head;
    	ListNode start= null;
    	ListNode  nxt;
    	
    	while(curr != null && cnt <= n){
    		if(cnt < m){
    			
    			if(cnt == m-1){
    				start = curr;
    				end = curr.next;
    			}
    			curr = curr.next;
    		}else if(cnt >= m && cnt <= n){
    			nxt = curr.next;
    			curr.next = newHead;
    			newHead  = curr;
    			curr = nxt;
    			if(cnt == n){
    				end.next = curr;
    				if(start != null) start.next = newHead;
    				else return newHead;
    			}
    		}
    		cnt++;
    	}
        return head;
    }	
	
	// 92 reverse linked list II with recursive
    private boolean stop;
    private ListNode left;
    
    private void recurseAndReverse(ListNode right, int m, int n) {
//    	System.out.println(right.val+ " , m="+m+" ,n="+n);
        if (n == 1)  return;
        if (m > 1)  this.left = this.left.next; // move "l" forward, until m=1        
        right = right.next; // move "r" forward, until n=1 cause return.

        // Recurse with m and n reduced.
        this.recurseAndReverse(right, m - 1, n - 1);

        if (this.left == right || right.next == this.left) this.stop = true;   // stop swapping condition          
 
        // swap data of the 2 pointers
        if (!this.stop) {
            int t = this.left.val;
            this.left.val = right.val;
            right.val = t;
        
            this.left = this.left.next; // move "l" forward again, for backtracking.
        }
    }

    
    public ListNode reverseBetween2(ListNode head, int m, int n) {
    	this.left = head;
        this.stop = false;
        recurseAndReverse(head, m, n);
        return head;
    }

	public static ListNode[] splitListToParts(ListNode root, int k) {
		ListNode[] result = new ListNode[k];
		if(root == null || k == 0) return result;

		int len = 0;
		ListNode curr = root;
		while(curr != null){ 
			len++;
			curr = curr.next;
		}
		int n = len / k;
		int r = len % k;
		curr = root;
		for(int i = 0; curr != null && i < k; i++){
			result[i] = curr;
			int size = n + (r > 0? 1: 0);
			r--;
			for(int j = 1; j < size; j++){
				curr = curr.next;
			}
			ListNode nxt = curr.next;
			curr.next = null;
			curr = nxt;
		}
			return result;      
	}
	public static ListNode oddEvenList(ListNode head) {
		if(head == null || head.next == null) return head;
		ListNode odd = head;
		ListNode evenHead = head.next, even = evenHead;
		while(even != null && even.next != null){
			odd.next = odd.next.next;
			odd = odd.next;
			even.next = even.next.next;
			even = even.next;
		}
		odd.next = evenHead;
        return head;
    }
	
	// 82. Remove Duplicates from Sorted List II (Medium)
	// e.g. head=[1,2,3,3,4,5,5]  output: [1,2,4]
    public static ListNode deleteDuplicates(ListNode head) {
//        if(head == null || head.next == null) return head;
//        if(head.val == head.next.val) return null;
        ListNode dummy = new ListNode(-1, head);
        ListNode prev = dummy;
        while(head != null){
            if(head.next != null && head.val == head.next.val){
                while(head.next != null && head.val == head.next.val){
                	head = head.next;
                }
            	prev.next = head.next; //remove duplicates out
            }else{
            	prev = head; //prev.next;
            }
            head = head.next;
        }
        return dummy.next;
    }
	
	// intersection point of 2 linkedList
    public static ListNode getIntersect(ListNode h1, ListNode h2){
    	int len1 = getLength(h1);
    	int len2 = getLength(h2);
    	int len_diff;
    	ListNode shorter = null, longer = null;
    	if(len1 >= len2){
    		len_diff = len1 - len2;
    		shorter = h2;
    		longer = h1;
    	}else{
    		len_diff = len2 - len1;
    		shorter = h1;
    		longer = h2;
    	}
    	while(len_diff > 0){
    		longer = longer.next;
    		len_diff--;
    	}
    	while(shorter != null){
    		if(shorter == longer) return shorter;
    		shorter = shorter.next;
    		longer = longer.next;
    	}
    	return null;
    }
    
    
    
	private static int getLength(ListNode h1) {
		int length = 0;
		while(h1 != null){
			length++;
			h1 = h1.next;
		}
		return length;
	}

	public static ListNode arrayToLinkedList(int[] arr){
		if(arr.length == 0) return null;
		ListNode head = new ListNode(arr[0]);
		ListNode curr = head;
		for(int i = 1; i < arr.length; i++){
			curr.next = new ListNode(arr[i]);
			curr = curr.next;
		}		
		return head;
	}
	
	
	public static void printList(ListNode head){
		while(head != null){
			System.out.print(head+ " -> ");
			head = head.next;
				}
		System.out.println();
	}
	
	// 203 Remove Linked List Elements (Easy)
	// Input:  1->2->6->3->4->5->6, val = 6
	// Output: 1->2->3->4->5
	public static ListNode deleteNode(ListNode head, int key) {
		ListNode sentinel = new ListNode(-1, head);
		ListNode prev = sentinel, curr = head;
		while(curr != null){
			if(curr.val == key)  prev.next = curr.next;
			else  prev = prev.next;
			
			curr = curr.next;
		}
		return sentinel.next;
	}
	
	//147. Insertion Sort List. O(n^2)
	// (insertion sort = insert one element to right place each time) O(n^2)
	public static ListNode insertionSortList(ListNode head){
		if(head == null) return head;
		ListNode dummyHead = new ListNode(-1,head);
		ListNode lastSorted = head, curr = head.next;
		ListNode prev = null;
		while(curr != null){
			if(curr.val >= lastSorted.val) lastSorted = lastSorted.next;
			else{
				prev = dummyHead;
				while(prev.next.val < curr.val){
					prev = prev.next; 
				}//Now prev.next > curr.val
				lastSorted.next = curr.next;
				curr.next = prev.next;
				prev.next = curr;		
			}
			curr = lastSorted.next;
		}
		return dummyHead.next;
	}
	
	
	
	public static void main(String[] args){
		ListNode h0 = new ListNode(1); //singleton test
		ListNode h02 = arrayToLinkedList(new int[] {1, 2});
		ListNode h1 = new ListNode(1, 
				new ListNode (2, 
						new ListNode(3)));
		ListNode h2 = new ListNode(1, 
				new ListNode (8, 
						new ListNode(8,
								new ListNode(1))));
		int[] a1 = {1,2,3,4,5,6,7};  //,8,9,10,11,12,13,14};

		ListNode h3 = arrayToLinkedList(a1);
//		ListNode[] curr = splitListToParts(h3, 4);
	
//		printList(oddEvenList(h3));
//		System.out.println(isPalindrome(h1));
//		System.out.println(a1.length);

		// Test intersection point of 2 linkedList
		ListNode h11 = new ListNode(2, h0);
	    printList(getIntersect(h11, h0));
	    printList(getIntersect(h02, h1));
	    
		// Test 147 
//		ListNode h147 = arrayToLinkedList(new int[] {4,2,1,3});
//		printList(insertionSortList(h147));
		
		// Test 82. Remove Duplicates from Sorted List II (Medium)
		// e.g. head=[1,2,3,3,4,5,5]  output: [1,2,4]
//		ListNode h82_1 = arrayToLinkedList(new int[]{1,2,3,3,4,5,5});
//		ListNode h82_2 = arrayToLinkedList(new int[]{1,1,1,3,4,5,5});
//		System.out.print("[1,2,4 = ");
//	    printList(deleteDuplicates(h82_1));
//	    System.out.print("[3,4= ");
//	    printList(deleteDuplicates(h82_2));
	    
		// Test 92
//		printList(reverseBetween(h3, 1, 6));
//		printList(reverseBetween(h3, 1, 13));
		// Test 92 recursive solution
//		LeetCodeQuestions testInstance = new LeetCodeQuestions();
//		printList(testInstance.reverseBetween2(h3, 2, 6));

		
		
		
		//Test 206 reverse
//		printList(reverse(h1));
//		printList(reverseRecur(h3));
		
	}
}
