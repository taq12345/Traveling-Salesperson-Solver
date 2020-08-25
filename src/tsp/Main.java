package tsp;

import java.io.IOException;

import processing.core.PApplet;

public class Main extends PApplet{

	Map map = new Map();
	Population population = new Population();
	Chromosome fit;
	Chromosome chromosome;
	double fittest= -100000;
	double zoom = 1;

	int i = 0;
	public static void main(String[] args) {
		PApplet.main("tsp.Main");

	}

	public void settings(){
		size(displayWidth, displayHeight);
	}

	public void setup(){

		frameRate(Integer.MAX_VALUE);
		population.createInitialPopulation();
		//
		try {
			map.loadCities("usca312_xy312.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			map.loadDistances("usca312_dist312.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//SIMULATED ANNEALING
		chromosome = new Chromosome();
		chromosome.generateRandomGenes();
		chromosome.calculateFitness();
		
		//GENETIC ALGORITHM
		//population.computeFitnesses();
		

	}

	public void drawCities(Map map)
	{
		for (int i = 0; i < map.getK() ; i++)
		{
			int x = 1920 - (-1 * (int)map.getCities()[i].getX()/5);
			int y = 1080 - ((int)map.getCities()[i].getY()/5);
//			int x = (int)map.getCities()[i].getX()/4;
//			int y = (int)map.getCities()[i].getY()/4;
			ellipse(x,y,4,4 );
			
		}
	}

	public void drawPath(Chromosome chr, Map map)
	{
		int index1;
		int index2;
		for (int i = 0; i < map.getK() - 1 ; i++)
		{
			index1 = chr.getGenes()[i];
			index2 = chr.getGenes()[i+1];

//			int x1 = (int) map.getCities()[index1].getX()/4;
//			int y1 = (int) map.getCities()[index1].getY()/4;
//
//			int x2 = (int)map.getCities()[index2].getX()/4;
//			int y2 = (int)map.getCities()[index2].getY()/4;
//			
			int x1 = 1920 - (-1 * (int)map.getCities()[index1].getX()/5);
			int y1 = 1080 - ((int)map.getCities()[index1].getY()/5);

			int x2 = 1920 - (-1 * (int)map.getCities()[index2].getX()/5);
			int y2 = 1080 - ((int)map.getCities()[index2].getY()/5);
			line(x1,y1,x2,y2);
		}
	}

	public void draw(){
		
//		//SIMULATED ANNEALING
		for (int i = 0; i < 10000; i++)
		{
			chromosome.simulatedAnnealing(millis(), 300000);
		}
		translate(400, 100);
		background(255);
		drawCities(map);
		drawPath(chromosome, map);
		fill(128,0,0);
		textSize(32);
		text((int)(1/chromosome.getFitness()), 500, 0); 
		text(hour() + ":" + minute(), 500, 40); 


		// GENETIC ALGORITHM
//		translate(400, 100);
//		fit = population.getFittest();
//		if (fit.fitness > fittest)
//		{
//			background(255);
//			fittest = fit.fitness;
//			drawCities(map);
//			drawPath(fit, map);
//			fill(128,0,0);
//			textSize(32);
//			text((int)(1/fit.getFitness()), 500, 0); 
//			text(hour() + ":" + minute(), 500, 40); 
//
//		}
//		population.createNewPopulation();
//		population.computeFitnesses();
//		
	}
}
