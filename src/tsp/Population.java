package tsp;

import java.util.ArrayList;
import java.util.Random;

public class Population {
	int size;
	Chromosome [] chromosomes;
	
	Population()
	{
		size = 500;
		chromosomes = new Chromosome[size];
		for (int i = 0; i < size; i++)
		{
			chromosomes[i] = new Chromosome();
		}
	}
	
	public void sortChromosomes(Chromosome[] chrs)
	{
		int i, j;
		double key;
		for (i = 1; i < size; i++)
		{
			key = chrs[i].fitness;
			j = i-1;

			/* Move elements of arr[0..i-1], that are
		          greater than key, to one position ahead
		          of their current position */
			while (j >= 0 && chrs[j].fitness < key)
			{
				chrs[j+1].fitness = chrs[j].fitness;
				j = j-1;
			}
			chrs[j+1].fitness = key;
		}
	}
	
	public void createInitialPopulation()
	{
		for (int i = 0; i < size; i++)
		{
			chromosomes[i].generateRandomGenes();
		}
	}
	
	public void computeFitnesses()
	{
		for (int i = 0; i < size; i++)
		{
			chromosomes[i].calculateFitness();
		}
	}
	
	public double[] sumFitness(Chromosome chr[])
	{
		double sum = 0;
		for (int i = 0; i < size; i++)
		{
			sum = sum + chr[i].fitness;
		}
		
		double [] probabilities = new double[size+1];
		probabilities[0] = 0;
		
		for (int i = 1; i < size+1; i++)
		{
			probabilities[i] = (chr[i-1].fitness / (double)sum);
		}
		for (int i = 1; i < size+1; i ++)
		{
			probabilities[i] = probabilities[i] + probabilities[i-1];
		}
		return probabilities;
	}
	
	public void createNewPopulation()
	{		
		Random rand = new Random();
		Chromosome [] offspring = new Chromosome[size];
		//sortChromosomes(chromosomes);
		double [] probabilities = sumFitness(chromosomes);
		int parent1 = -1;
		int parent2= -1;
		for (int i = 0; i < size; i+= 2) // or will it be size/2 - 1? check by considering even odd possibilities of size
		{
			//rand = new Random();
			//n = rand.nextInt(size-1);
			double n = rand.nextDouble();
			double n2 = rand.nextDouble();
			for (int j = 0; j < size; j++)
			{
				if (n >= probabilities[j] && n < probabilities[j+1])
				{
					parent1 = j;
					if (parent1 == parent2)
					{
						j = 0;
						n = rand.nextDouble();
					}
				}
				if (n2 >= probabilities[j] && n2 < probabilities[j+1])
				{
					//select j as parent 1
					parent2 = j;
					if (parent1 == parent2)
					{
						j = 0;
						n2 = rand.nextDouble();
					}
				}
			}
			int limit = rand.nextInt(Map.getK()-1) + 1;
			offspring[i] = crossOver2(chromosomes[parent1],chromosomes[parent2], limit);
			offspring[i+1] = crossOver2(chromosomes[parent2],chromosomes[parent1], limit);
			//chromosomes[size-i-1] = child;
			//chromosomes[size-i-2] = child;
		}
		
//		for (int i = 0; i < size; i++)
//		{
//			offspring[i].calculateFitness();
//		}
//		sortChromosomes(offspring);
//		for (int i = size/2; i < size; i++)
//		{
//			chromosomes[i] = offspring[i-(size/2)];
//		}
		
		chromosomes = offspring;
		offspring = null;
		//pick top k fittest;
		//make 10 children from them ie 5 crossovers
		//replace all 10 old with new children.
	}
	
	public Chromosome crossOver2(Chromosome chr1, Chromosome chr2, int limit)
	{
		//1 point crossover
		Chromosome temp = new Chromosome();
		ArrayList<Integer> visited = new ArrayList<Integer>();
		
		for (int i = 0; i < limit; i++)
		{
			temp.genes[i] = chr1.genes[i];
			visited.add(temp.genes[i]);
		}
		
		int j = limit;
		for (int i = 0; i < Map.getK(); i++)
		{
			if (!visited.contains(chr2.genes[i]))
			{
				temp.genes[j] = chr2.genes[i];
				j++;
			}
		}
		
		//MUTATION
		Random rand = new Random();
		int g1 = rand.nextInt(Map.getK());
		int g2 = rand.nextInt(Map.getK());
		if (rand.nextDouble() < 0.005)
		{
			int save = temp.genes[g1];
			temp.genes[g1] = temp.genes[g2];
			temp.genes[g2] = save;
			//System.out.println("MUTATED");
		}
		
		//FOR SUCCESSIVE GENE MUTATION
//		int g1 = rand.nextInt(Map.getK()-1);
//		//int g2 = rand.nextInt(Map.getK());
//		if (rand.nextDouble() < 0.01)
//		{
//			int save = temp.genes[g1];
//			temp.genes[g1] = temp.genes[g1+1];
//			temp.genes[g1+1] = save;
//			//System.out.println("MUTATED");
//		}
		
		return temp;
	}
	
	public void crossOver(Chromosome chr1, Chromosome chr2, int l)
	{
		//UNIFORM
		Chromosome temp1 = new Chromosome();
		Chromosome temp2 = new Chromosome();
		ArrayList<Integer> visited1 = new ArrayList<Integer>();
		ArrayList<Integer> visited2 = new ArrayList<Integer>();
		
		Random rand = new Random();
		int n = rand.nextInt(Map.getK()+1);
		
		for(int i = 0; i < n/2; i++)
		{
			temp1.genes[i] = chr1.genes[i];
			temp2.genes[i] = chr2.genes[i];
			visited1.add(temp1.genes[i]);
			visited2.add(temp2.genes[i]);
		}
		int j = n/2;
		int k = j;
		for(int i = 0; i < Map.getK(); i++)
		{
			if(!visited1.contains(chr2.genes[i]))
			{
				temp1.genes[j] = chr2.genes[i];
				j++;
			}
			if(!visited2.contains(chr1.genes[i]))
			{
				temp2.genes[k] = chr1.genes[i];
				k++;
			}
			
		}
		for (int i = 0; i < Map.getK(); i++)
		{
			chromosomes[l].genes[i] = temp1.genes[i];
			chromosomes[l+1].genes[i] = temp2.genes[i];
		}
		//chr1 = temp1;
		//chr2 = temp2;
		visited2 = null;
		visited1 = null;
		temp1 = null;
		temp2 = null;
	}
	
	public Chromosome[] getChromosomes()
	{
		return chromosomes;
	}
	
	public Chromosome getFittest()
	{
		Chromosome max = chromosomes[0];
		for (int i = 1; i < size; i++)
		{
			if (chromosomes[i].getFitness() > max.getFitness())
			{
				max = chromosomes[i];
			}
		}
		return max;
	}
}
