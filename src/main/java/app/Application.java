package app;

import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import app.beans.User;
import app.beans.Repo;
import controller.AnyaController;

@SpringBootApplication(scanBasePackages = { "app.services", "app.beans" })
@ComponentScan(basePackageClasses = AnyaController.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner init(Repo repo) {
		return args -> {
			Stream.of("Rachel", "Monica", "Phoebe", "Joey", "Ross", "Chandler").forEach(name -> {
				User user = new User(name, 1990, 20L);
				repo.save(user);
			});
			repo.findAll().forEach(System.out::println);
		};
	}

}
