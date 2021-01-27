package stackAndQueue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 225. Implement Stack using Queues (Easy)
 * Follow-up: use 1 Queue to implement Stack.
 * @author maoye
 *
 */
public class StackUsingQueue {
    private Queue<Integer> queue;

    public StackUsingQueue() {
        queue = new LinkedList<>();
    }

    public void push(int x) {
        queue.add(x);
        int cnt = queue.size();
        while (cnt-- > 1) {
            queue.offer(queue.poll());
        }
    }

    public int pop() {
        return queue.remove();
    }

    public int top() {
        return queue.peek();
    }

    public boolean empty() {
        return queue.isEmpty();
    }
    
	public static void main(String[] args) {
		StackUsingQueue sq = new StackUsingQueue();
		
//	    System.out.println("Pop(): " + sq.pop());
	    sq.push(3);
	    sq.push(5);
	    sq.push(9);
	    System.out.println("Pop(): " + sq.pop());
	    sq.push(10);
	    sq.push(16);
	    System.out.println("Pop(): " + sq.pop());
		
	}

}
