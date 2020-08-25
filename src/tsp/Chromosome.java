package tsp;

import java.util.ArrayList;
import java.util.Random;

public class Chromosome {
	int [] genes;
	double fitness;

	Chromosome()
	{
		genes = new int[Map.getK()];
		fitness = -1;
	}
	
	public void simulatedAnnealing(int millis, int limit)
	{
		Random rand = new Random();
		int g1 = rand.nextInt(Map.getK()-1);
		int g2 = rand.nextInt(Map.getK());

		double oldFitness = this.fitness;
		int save = this.genes[g1];
		this.genes[g1] = this.genes[g2];
		this.genes[g2] = save;
		//System.out.println("MUTATED");

		this.calculateFitness();
		double deltaE = this.getFitness() - oldFitness;

		//double prob = Math.exp(deltaE/(1.0/(millis*(312.0/Map.getK()))));
		double prob = Math.exp(deltaE/(1.0/(millis*10000)));
		if (deltaE > 0)
		{
			// DO NOTHING
		}
		else
		{
			// DO NOTHING WITH PROBABILTY e^DELTAE/T
			//System.out.println(1.0/(millis*10000));
			if(rand.nextDouble() < prob)
			{

			}
			else
			{
				this.fitness = oldFitness;
				save = this.genes[g1];
				this.genes[g1] = this.genes[g2];
				this.genes[g2] = save;
			}
		}
		
		//System.out.println("MUTATED");

	}
	
	public void generateRandomGenes()
	{
		ArrayList<Integer> visited = new ArrayList<Integer>();
		int i = 0;

		while(i<Map.getK())
		{
			Random rand = new Random();
			int n = rand.nextInt(Map.getK());
			if (!visited.contains(n))
			{
				genes[i] = n;
				visited.add(n);
				i++;
			}
		}
		visited = null;
	}
	
	public void calculateFitness()
	{
		int distance = 0;
		for (int i = 0; i < Map.getK()-1; i++)
		{
			distance = distance + Map.getDistances()[genes[i]][genes[i+1]];
		}
		fitness = 1.0/distance;
	}
	
	public int [] getGenes()
	{
		return genes;
	}
	
	public double getFitness()
	{
		return fitness;
	}
}
