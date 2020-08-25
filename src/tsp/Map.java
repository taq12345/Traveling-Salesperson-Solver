package tsp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Map {
	private City [] cities;
	private static int k;
	private static int [][] distances;
	
	Map()
	{
		k = 312;
		cities = new City[k];
		for (int i = 0; i < k; i++)
		{
			cities[i] = new City();
		}
		distances = new int[k][k];
	}
	
	public void loadCities(String fileName) throws IOException
	{
		File file = new File(fileName);

		BufferedReader br = new BufferedReader(new FileReader(file));

		String st;
		int i = 0;
		while ((st = br.readLine()) != null)
		{
			String arr[] = st.split("       ");
			double a = Double.parseDouble(arr[0]);
			cities[i].setX(a);
			a = Double.parseDouble(arr[1]);
			cities[i].setY(a);
			i++;
		}
	}
	
	public void loadDistances(String fileName) throws IOException
	{
		Scanner scanner = new Scanner(new File(fileName));
		int a;
		int i = 0, j = 0;
		while(scanner.hasNextInt())
		{
			a = scanner.nextInt();
			distances[i][j] = a;
			j++;
			if (j%k == 0)
			{
				i++;
				j=0;
			}
		}
		System.out.println("DISTANCES LOADED");
	}
	
	public static int getK()
	{
		return k;
	}
	
	public static int [][] getDistances()
	{
		return distances;
	}
	public City [] getCities()
	{
		return cities;
	}
}
