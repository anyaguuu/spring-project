package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import controller.AnyaController;

@SpringBootApplication(scanBasePackages = { "app.services", "app.beans" })
@ComponentScan(basePackageClasses = AnyaController.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}