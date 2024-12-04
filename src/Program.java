import java.util.ArrayList;
import java.util.Random;

public class Program
{

	final static int NUM_PROCS = 6; // How many concurrent processes
	final static int TOTAL_RESOURCES = 30; // Total resources in the system
	final static int MAX_PROC_RESOURCES = 13; // Highest amount of resources any process could need
	final static int ITERATIONS = 30; // How long to run the program
	static Random rand = new Random();
	
	public static void main(String[] args)
	{
		System.out.println("**** TOTAL RESOURCES ****\n" + TOTAL_RESOURCES);
		// The list of processes:
		ArrayList<Proc> processes = new ArrayList<Proc>();
		int totalHeldResources = 0;
		for (int i = 0; i < NUM_PROCS; i++)
			processes.add(new Proc(MAX_PROC_RESOURCES - rand.nextInt(3))); // Initialize to a new Proc, with some small range for its max
		
		// Run the simulation:
		for (int i = 0; i < ITERATIONS; i++)
		{
			// loop through the processes and for each one get its request
			for (int j = 0; j < processes.size(); j++)
			{
				// Get the request
				int currRequest = processes.get(j).resourceRequest(TOTAL_RESOURCES - totalHeldResources);

				// just ignore processes that don't ask for resources
				if (currRequest == 0)
					continue;
				
				/* ***** KEEP THIS COMMENT IN YOUR FINAL SUBMISSION *****
				/* Here you have to enter code to determine whether or not the request can be granted,
				/* and then grant the request if possible. Remember to give output to the console 
				/* this indicates what the request is, and whether or not its granted.
				/* ***** KEEP THIS COMMENT IN YOUR FINAL SUBMISSION *****/

				if (currRequest < 0){
					System.out.println("**** RELEASED RESOURCES ****\n" + currRequest * -1);
					totalHeldResources += currRequest;
					continue;
				}

                ArrayList<Proc> tempProcesses = new ArrayList<>(processes);
				int tempTotalHeldResources = totalHeldResources + currRequest;

				while(!tempProcesses.isEmpty()){
					boolean found = false;
					for (int k=0; k< tempProcesses.size(); k++){
						if (tempProcesses.get(k).getMaxResources() - (tempProcesses.get(k).getHeldResources() + (k == j ? currRequest : 0)) <= TOTAL_RESOURCES - tempTotalHeldResources ) {
							tempTotalHeldResources -= tempProcesses.get(k).getHeldResources();
							tempProcesses.remove(k);
							found = true;
						}
					}
					if (!found){
						break;
					}
				}

				if(tempProcesses.isEmpty()){
					//grant request
					System.out.println("**** GRANTED ****");
					System.out.println("Process " + j + " will get " + currRequest + " resources");
					totalHeldResources += currRequest;
					processes.get(j).addResources(currRequest);
				} else {
					// this will lead to deadlock so continue
					System.out.println("**** REJECTED ****");
					System.out.println("Process " + j + " will NOT get " + currRequest + " resources");

				}

				// At the end of each iteration, give a summary of the current status:
				System.out.println("\n***** STATUS *****");
				System.out.println("Total Available: " + (TOTAL_RESOURCES - totalHeldResources));
				for (int k = 0; k < processes.size(); k++)
					System.out.println("Process " + k + " holds: " + processes.get(k).getHeldResources() + ", max: " +
							processes.get(k).getMaxResources() + ", claim: " + 
							(processes.get(k).getMaxResources() - processes.get(k).getHeldResources()));
				System.out.println("***** STATUS *****\n");
				
			}
		}

	}

}
