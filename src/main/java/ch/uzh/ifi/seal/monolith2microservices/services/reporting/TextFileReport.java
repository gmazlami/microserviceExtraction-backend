package ch.uzh.ifi.seal.monolith2microservices.services.reporting;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import ch.uzh.ifi.seal.monolith2microservices.models.git.GitRepository;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.Microservice;

public class TextFileReport {
	
	public static void generate(GitRepository repo, List<Microservice> microservices) throws Exception {
		String homePath = System.getProperty("user.home");
		
		Path outputPath = Paths.get(homePath + "/" + repo.getName() + "_" + repo.getId()+ ".txt");
		
		BufferedWriter writer = new BufferedWriter(Files.newBufferedWriter(outputPath));
		
		
		writer.write("Microservice Decomposition for " + repo.getName());
		writer.newLine();
		writer.write("----------------------------------------------------------------");
		writer.newLine();
		writer.newLine();
		writer.write("Microservice id | Belonging classes");
		writer.newLine();
		writer.write("----------------------------------------------------------------");

		int counter = 0;
		for(Microservice m: microservices){
			writer.newLine();
			writer.write(counter + "  |  ");
			for(String cls : m.getClasses()){
				writer.write(cls + " ,");
			}
			counter++;
		}
		
		writer.close();

	}

}
