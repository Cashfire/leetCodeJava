package search;

public class Search {
	public static int evaluate(GameNode node, int alpha, int beta){
		System.out.println("eva("+node.name+", a="+alpha+", b="+beta);
		if(node.state == "LEAF") {
			System.out.println("return: "+node.value);  
			return node.value;
		}
		if(node.state == "MAX"){
			for(GameNode child : node.children){
				int val = evaluate(child, alpha, beta);
				System.out.print("a := max("+alpha+","+val+")=");
				alpha = Math.max(alpha, val);
				System.out.println(alpha);
				if(alpha >= beta) {
					System.out.println("a("+alpha+") >= b("+beta+") , so return: "+alpha+" Pruning...");  
					return alpha;
				}
			}
			System.out.println("eva("+node.name+") return a: "+alpha);  
			return alpha;
		}else{  //node.state == "MIN"
			for(GameNode child : node.children){
				int val = evaluate(child, alpha, beta);
				System.out.print("a := min("+beta+","+val+")=");
				beta = Math.min(beta, val);
				System.out.println(beta);
				if(beta <= alpha) {
					System.out.println("b("+beta+") >= a("+alpha+"), so return: "+beta+" Pruning..."); 
					return beta;
				}
			}
			System.out.println("eva("+node.name+") return b: "+beta);  
			return  beta;			
		}	
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GameNode L = new GameNode(4, "L");
		GameNode M = new GameNode(8, "M");
		GameNode N = new GameNode(9, "N");
		GameNode O = new GameNode(3, "O");
		GameNode P = new GameNode(2, "P");
		GameNode Q = new GameNode(-2, "Q");
		GameNode R = new GameNode(9, "R");
		GameNode S = new GameNode(-1, "S");
		GameNode T = new GameNode(8, "T");
		GameNode U = new GameNode(4, "U");
		GameNode V = new GameNode(3, "V");
		GameNode W = new GameNode(6, "W");
		GameNode X = new GameNode(5, "X");
		GameNode Y = new GameNode(7, "Y");
		GameNode Z = new GameNode(1, "Z");
		
		GameNode E = new GameNode("MAX", "E", new GameNode[]{L,M});
		GameNode F = new GameNode("MAX", "F", new GameNode[]{N,O});
		GameNode G = new GameNode("MAX", "G", new GameNode[]{P,Q});
		GameNode H = new GameNode("MAX", "H", new GameNode[]{R,S});
		GameNode I = new GameNode("MAX", "I", new GameNode[]{T,U});
		GameNode J = new GameNode("MAX", "J", new GameNode[]{V,W,X});
		GameNode K = new GameNode("MAX", "K", new GameNode[]{Y,Z});
		GameNode B = new GameNode("MIN", "B", new GameNode[]{E,F});
		GameNode C = new GameNode("MIN", "C", new GameNode[]{G,H,I});
		GameNode D = new GameNode("MIN", "D", new GameNode[]{J,K});
		GameNode A = new GameNode("MAX", "A", new GameNode[]{B,C,D});
		
		evaluate(A, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

}

class GameNode{
	public String state;
	public String name;
	public int value;
	public GameNode[] children;
	public GameNode(String s, String name, GameNode[] child_list){
		this.state = s;
		this.name = name;
		this.children = child_list;
	}
	public GameNode(int v, String name){
		this.state = "LEAF";
		this.name = name;
		this.value = v;
		this.children = null;		
	}
}