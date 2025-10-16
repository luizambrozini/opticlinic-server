package tec.br.opticlinic.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {
	public static void main(String[] args) {

		SpringApplication.run(ApiApplication.class, args);
		var enc = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
		System.out.println(enc.encode("admin123"));
	}
}
