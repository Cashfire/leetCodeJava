package stackAndQueue;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * 232. Implement Queue using Stacks (Easy)
 * @author maoye
 *
 */
public class QueueUsingStackClass<T> {
	private Stack<T> in;
	private Stack<T> out;
	
	public QueueUsingStackClass(){
		this.in  = new Stack<>();
		this.out = new Stack<>();
	}
	
	/** Push element x to the back of queue. O(1) */
	public void push(T x){
		in.push(x);
	}

    /** Removes the element from in front of queue and returns that element. 
     * @throws EmptyQueueException */
    public T pop() throws EmptyQueueException {
        inToOut();
        if(out.isEmpty()) throw new EmptyQueueException();
        return out.pop();
    }
    
    /** Get the front element. */
    public T peek() {
        inToOut();
        if(out.isEmpty()) return null;
        return out.peek();
    }
    
    /** Returns whether the queue is empty. */
    public boolean empty() {
        return in.size() + out.size() == 0;
    }
    
    private void inToOut(){
    	if(out.isEmpty()){
    		while(!in.isEmpty()){
        		out.push(in.pop());
        	}
    	}   	
    }
    
	public static void main(String[] args) throws EmptyQueueException {
		// TODO Auto-generated method stub
		QueueUsingStackClass obj = new QueueUsingStackClass();
		 obj.push(1);
		 obj.push(2);
		 obj.push(3);
		 obj.push(4);
		System.out.println( obj.pop());
		 obj.push(5);
		 System.out.println(obj.pop());
		 System.out.println(obj.pop());
		 System.out.println(obj.pop());
		 System.out.println(obj.pop());

	}

}
