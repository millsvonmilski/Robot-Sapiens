/*
 * based on code by Matthew Robbins
 * 
 * https://github.com/matthewrdev/Neural-Network
 */

package robosapiensNeuralNetwork;

import java.util.ArrayList;

public class GeneticAlgorithm {
	int currentGenome;
	int totalPopulation;
	int genomeID;
	int generation;
	int totalGenomeWeights;
	ArrayList<Genome> population = new ArrayList<Genome>();
	ArrayList<Integer> crossoverSplits = new ArrayList<Integer>();
	
	public GeneticAlgorithm()
	{
		currentGenome = -1;
		totalPopulation = 0;
		genomeID = 0;
		generation = 1;
	}

	public synchronized Genome GetNextGenome()
	{
		currentGenome++;
		if (currentGenome >= population.size())
			return null;
		population.get(currentGenome).index = currentGenome;
		return population.get(currentGenome);
	}

	public Genome GetBestGenome()
	{
		int bestGenome = -1;
		double fitness = 0;
		for (int i = 0; i < population.size(); i++)
		{
			if (population.get(i).fitness > fitness)
			{
				fitness = population.get(i).fitness;
				bestGenome = i;
			}
		}

		return population.get(bestGenome);
	}

	public Genome GetWorstGenome()
	{
		int worstGenome = -1;
		double fitness = 1000000;
		for (int i = 0; i < population.size(); i++)
		{
			if (population.get(i).fitness < fitness)
			{
				fitness = population.get(i).fitness;
				worstGenome = i;
			}
		}

		return population.get(worstGenome);
	}
	
	public Genome GetGenome(int index)
	{
		if (index >= totalPopulation)
			return null;

		return population.get(index);
	}

	int GetCurrentGenomeIndex()
	{
		return currentGenome;
	}

	int GetCurrentGenomeID()
	{
		return population.get(currentGenome).ID;
	}

	int GetCurrentGeneration()
	{
		return generation;
	}

	int GetTotalPopulation()
	{
		return totalPopulation;
	}

	void GenerateCrossoverSplits(int neuronsPerHidden, int inputs, int outputs)
	{
		// Unimplemented
	}
	
	void GetBestCases(int totalGenomes, ArrayList<Genome> out)
	{
		int genomeCount = 0;
		int runCount = 0;

		while (genomeCount < totalGenomes)
		{
			if (runCount > 10)
				return;

			runCount++;

			// Find the best cases for cross breeding based on fitness score.
			double bestFitness = 0;
			int bestIndex = -1;
			for (int i = 0; i < this.totalPopulation; i++)
			{
				if (population.get(i).fitness > bestFitness)
				{
					boolean isUsed = false;

					for (int j = 0; j < out.size(); j++)
					{
						if (out.get(j).ID == population.get(i).ID)
						{
							isUsed = true;
						}
					}

					if (isUsed == false)
					{
						bestIndex = i;
						bestFitness = population.get(bestIndex).fitness;
					}
				}
			}

			if (bestIndex != -1)
			{
				genomeCount++;
				out.add(population.get(bestIndex));
			}

		}
	}

	
	void CrossBreed(Genome g1, Genome g2, Genome baby1, Genome baby2)
	{
		// Select a random cross over point.
		int totalWeights = g1.weights.size();
		int crossover = ((int)(Math.random() * totalWeights * 10 ))% totalWeights;

		//baby1 = new Genome();
		baby1.ID = genomeID;
		//baby1.weights.resize(totalWeights);
		baby1.weights.clear();
		genomeID++;

		//baby2 = new Genome();
		baby2.ID = genomeID;
		//baby2->weights.resize(totalWeights);
		baby2.weights.clear();
		genomeID++;

		// Go from start to crossover point, copying the weights from g1.
		for (int i = 0; i < crossover; i++)
		{
			baby1.weights.add(g1.weights.get(i));
			baby2.weights.add(g2.weights.get(i));
		}
		// Go from start to crossover point, copying the weights from g2 to child.
		for (int i = crossover; i < totalWeights; i++)
		{
			baby1.weights.add(g2.weights.get(i));
			baby2.weights.add(g1.weights.get(i));
		}
	}

	Genome CreateNewGenome(int totalWeights)
	{
		Genome genome = new Genome();
		genome.ID = genomeID;
		genome.fitness = 0.0f;
		genome.weights.clear();
		//genome.weights.resize(totalWeights);
		for (int j = 0; j < totalWeights; j++)
		{
			genome.weights.add(utils.RandomClamped());
			//genome->weights[j] = RandomClamped();
		}
		genomeID++;
		return genome;
	}

	void GenerateNewPopulation(int totalPop, int totalWeights)
	{
		generation = 1;
		ClearPopulation();
		currentGenome = -1;
		totalPopulation = totalPop;
		//population.resize(totalPop);
		population.clear();
		for (int i = 0; i < totalPop; i++)
		{
			Genome genome = new Genome();
			genome.ID = genomeID;
			genome.fitness = 0.0f;
			//genome.weights.resize(totalWeights);
			genome.weights = new ArrayList<Double>();
			for (int j = 0; j < totalWeights; j++)
			{
				genome.weights.add(utils.RandomClamped());
				//genome->weights[j] = RandomClamped();
			}
			genomeID++;
			population.add(genome);
		}
	}

	void BreedPopulation()
	{
		ArrayList<Genome> bestGenomes = new ArrayList<Genome>();

		// Find the 4 best genomes.
		this.GetBestCases(4, bestGenomes);

		// Breed them with each other twice to form 3*2 + 2*2 + 1*2 = 12 children
		ArrayList<Genome> children = new ArrayList<Genome>();

		// Carry on the best dude.
		Genome bestDude = new Genome();
		bestDude.fitness = 0.0f;
		bestDude.ID = bestGenomes.get(0).ID;
		bestDude.weights = bestGenomes.get(0).weights;
		Mutate(bestDude);
		children.add(bestDude);

		// Child genomes.
		Genome baby1 = new Genome();
		Genome baby2 = new Genome();

		// Breed with genome 0.
		CrossBreed(bestGenomes.get(0), bestGenomes.get(1), baby1, baby2);
		Mutate(baby1);
		Mutate(baby2);
		children.add(baby1);
		children.add(baby2);
		baby1 = new Genome();
		baby2 = new Genome();
		CrossBreed(bestGenomes.get(0),bestGenomes.get(2), baby1, baby2);
		Mutate(baby1);
		Mutate(baby2);
		children.add(baby1);
		children.add(baby2);
		baby1 = new Genome();
		baby2 = new Genome();
		CrossBreed(bestGenomes.get(0), bestGenomes.get(3), baby1, baby2);
		Mutate(baby1);
		Mutate(baby2);
		children.add(baby1);
		children.add(baby2);

		// Breed with genome 1.
		baby1 = new Genome();
		baby2 = new Genome();
		CrossBreed(bestGenomes.get(1), bestGenomes.get(2), baby1, baby2);
		Mutate(baby1);
		Mutate(baby2);
		children.add(baby1);
		children.add(baby2);
		baby1 = new Genome();
		baby2 = new Genome();
		CrossBreed(bestGenomes.get(1), bestGenomes.get(3), baby1, baby2);
		Mutate(baby1);
		Mutate(baby2);
		children.add(baby1);
		children.add(baby2);

		// For the remainding n population, add some random kiddies.
		int remainingChildren = (totalPopulation - children.size());
		for (int i = 0; i < remainingChildren; i++)
		{

			children.add(this.CreateNewGenome(bestGenomes.get(0).weights.size()));
		}

		ClearPopulation();
		population = children;

		currentGenome = -1;
		generation++;
	}

	void ClearPopulation()
	{
		/*
		for (unsigned int i = 0; i < population.size(); i++)
		{
			if (population[i] != NULL)
			{
				delete population[i];
				population[i] = NULL;
			}
		}
*/
		population.clear();
	}


	void Mutate(Genome genome)
	{
		for (int i = 0; i < genome.weights.size(); ++i)
		{
			// Generate a random chance of mutating the weight in the genome.
			if (utils.RandomClamped() < NeuralNetGlobals.MUTATION_RATE)
			{
				genome.weights.set(i,genome.weights.get(i)+(utils.RandomClamped() * NeuralNetGlobals.MAX_PERBETUATION));
			}
		}
	}
	
	void SetGenomeFitness(double fitness, int index)
	{
		if (index >= population.size())
			return;

		population.get(index).fitness = fitness;
	}
}