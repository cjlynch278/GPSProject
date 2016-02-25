
// GraphAlgorithms.java
// Chris Lynch
// COSC 201 - Fall 2015
// Last Revision: December 9th, 2015

//This class consists of two algorithms, A* and Dijkstra's. These two algorithims find the shortest path
// in a graph from one node to another using different methods.
import java.util.ArrayList;
import java.util.HashSet;
//This project finds the shortest path from one point on a graph to another. This is done by using either dijkstra's algorithm
// or the A* algorithm.
public class GraphAlgorithms {

	public GraphAlgorithms(){
		
	}
	
	// PreCondition: Graph is a two dimensional array representing a graph consisting of values between one vertex and another.
	//				 Graph must not be empty.
    // PostCondtion: The method will return the value of the shortest path using the specified algorithm, unless an invalid
	// 				 algorithm is chosen
	 
	public ArrayList<Integer> processGraph(int[][] graph, int s, int d){
		return aStar(graph,s,d);
		
	}
	//Precondition: The method requires a two dimensional matrix representing a graph. The graph is made up of
	//  			values that represent the distances between each node. The graph needs to have neither negative entries 
	//				nor non numerical entries. The s and d parameters represent the starting node and destination node. 
	//Postcondition: The shortest path from the starting point to the destination will be found along with the cost. This path
	//				will be found using an A* algorithm which uses a heuristic based off of the Manhattan distance between each node.
	public ArrayList<Integer> aStar(int [][] graph, int s, int d){
		//I used this video to help me figure out how a star works and more specifically, how the heuristic works
		//https://www.youtube.com/watch?v=uIVu7ViLaZo
		HashSet<Integer> closed = new HashSet<Integer>();
		HashSet<Integer> visited = new HashSet<Integer>();
		
		
		//Path is an array that keeps track of which row number changed the specific node last.
		int[] path = new int[graph.length];
		int min = Integer.MAX_VALUE;
		int rowNumber =s ;
		
		//F will be the sum of the heuristic and the distance to get to a certain node
		int[] f = new int[graph.length];
		// H will be the value of the heuristic at a certain node.
		int[] h = new int[graph.length];
		//Distances is the calculated cost to get to a node.
		int[] distances = new int[graph.length];
		//Finds the position of the node for calculating heuristic
		int destColumn = d/5;
		int destRow=0;
		//Avoids dividing by 0.
		if(graph.length/5!=0){
		 destRow = d%(graph.length/5);
		}	
		int currentRow= 0;
		int currentColumn=0;
		
		//If the starting node is the destination node 
		if(s == d){
			System.out.println(d);
			return pathCreator(s,d,path,distances);
		}
		//The paths are initiated to -1 which will mean that they are not used.
		for(int i =0; i <path.length; i++){
			path[i] = -1;
		}
		
		//Certain errors that can occur
		if(d>graph.length|| d<0){
			System.out.println("Error. Destination vertex doesn't exist");
			return pathCreator(s,d,path,distances);
		}
		if(s>graph.length|| s<0){
			System.out.println("Error. Source vertex doesn't exist");
			return pathCreator(s,d,path,distances);
		}
		path[s] =s;
		//Initiate first row of distances
		for(int i = 0; i<graph.length; i ++){
			
			if(i ==s){
				distances[i] =0;
			}
			else if(graph[s][i]== 0){
				distances[i] = Integer.MAX_VALUE;
			}
			else{	
				distances[i] = graph[s][i]; 
				visited.add(i);
				path[i] = s;
			}
		}
		//Initiates heuristics of neighboring nodes
		for(int i =0; i<graph.length; i ++){
				
			if(graph.length/5 !=0){
				//To get the column of the current node, mod it by the total rows
				currentColumn = i%((graph.length+1)/5);
			}
				//To get the row of the current node, divide it by 5.
				currentRow= i/5;
				//Get the distance from each row and column then add it to get the Manhattan distance
				h[i] = Math.abs(destRow-currentRow) + Math.abs(destColumn - currentColumn);
			
		}
		//Initiates the totals, f
		for(int i =0; i<distances.length;i++){
			f[i] = h[i] + distances[i];
		}
		//Finds the node's neighbor with the shortest estimated distance
		for(int i =0; i< h.length; i++){
			if(f[i]< min&& visited.contains(i)){
				min = f[i];		
				rowNumber= i;
			}
		}
		//Adds the first node to closed
		closed.add(rowNumber);
				
		while(!visited.isEmpty())
		{
			
			for(int i = 0; i < distances.length; i ++){
				//If a shorter distance is found, replaces it and update the path.
				 if(!closed.contains(i) && min+ graph[rowNumber]
						 [i]< distances[i]&&graph[rowNumber][i]!= 0){
					visited.add(i);
					distances[i] = distances[rowNumber]+ graph[rowNumber][i];
					path[i] = rowNumber;
				}
			}
			
			visited.remove(rowNumber);
			closed.add(rowNumber);
			
			min = Integer.MAX_VALUE;
				for(int i =0; i<graph.length; i ++){
					//If the destination is the node, print the results
					if(rowNumber ==d){
						return pathCreator(s,d,path,distances);
					}
					//Calculate the heuristic for all of the visited nodes
					if(graph[rowNumber][i] !=0&& visited.contains(i)){
					
						if(graph.length/5!=0)
						currentColumn = i%(graph.length/5);
						currentRow = i/5;
						h[i] = Math.abs(destRow-currentRow) + Math.abs(destColumn - currentColumn);
						
					}
					
				}
				//Update f
				for(int i =0; i<distances.length;i++){
					if(visited.contains(i))
					f[i] = h[i] + distances[i];
				}
			
				//Get the point from visited with the least estimated total value
				for(int i =0; i< h.length; i++){
					
					if(f[i]< min&& f[i] !=0&& visited.contains(i)){	
						min = f[i];		
						rowNumber= i;
					}
				}
				
			//If there are no more reachable paths use the shortest one
			if(min == Integer.MAX_VALUE){
				 return pathCreator(s,d,path,distances);
			}
		}
				
		return pathCreator(s,d,path,distances);
		
	}
	//PreConditions: Distances is an array that contains the shortest weighted value from the starting node
	//				 to the destination node. The 's' and 'd' parameters represent the start and destination node.
	//				 The path array is a set of values that indicates which node the the path came from last. 
	//PostConditions: The method will print the path from the start node to the destination node
	// 				  and return the value of the cost of the path to that point
	public ArrayList<Integer> pathCreator(int s, int d, int [] path, int[] distances){
		// I used this video to help me in figuring out how this algorithm works.
		//https://www.youtube.com/watch?v=0nVYi3o161A
		int next = d;
		ArrayList <Integer> buildPath = new ArrayList<Integer>();
		ArrayList <Integer> resultPath = new ArrayList<Integer>();
		
		//If visited has not been evaluated, then it cannot be reached at all
		if(path.length == 1){
			System.out.println();
		}
		while(next!= s){
			//Unless the path at path[d] has been altered, the value will be -1.
			if(next == -1){
				System.out.println("Error. No path present.");
				return resultPath;
			}
			//The values of each path index tells us where the path came from.
			buildPath.add(next);
			next =path[next]; 

		}
		//Adds the start node to the result path to complete it 
		buildPath.add(s);
		//Prints the resulting path
		for(int i = buildPath.size()-1; i >=0;i--){
			resultPath.add(buildPath.get(i));
			
		}
		System.out.println("");
		return resultPath;
	}

}
	