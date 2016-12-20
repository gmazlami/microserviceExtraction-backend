package ch.uzh.ifi.seal.monolith2microservices.services.reporting;

import ch.uzh.ifi.seal.monolith2microservices.models.git.GitRepository;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.ClassNode;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.Component;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

public class TextFileReport {
	
	public static void generate(GitRepository repo, Set<Component> components) throws Exception {
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
		for(Component m: components){
			writer.newLine();
			writer.write(counter + "  |  ");
			for(ClassNode cls : m.getNodes()){
				writer.write(cls.getId() + " ,");
			}
			counter++;
		}
		
		writer.close();

	}

}
