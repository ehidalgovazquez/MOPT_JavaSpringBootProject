package com.example.softlearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.softlearning.core.entity.book.appservices.BookServicesImpl;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.ServiceException;

@SpringBootApplication
public class App {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(App.class, args);

		System.out.println("\n*****   A P P L I C A T I O N    S T A R T E D   *****\n");

		System.out.println("\n *****   A P P S E R V I C E S   ***** \n");

		System.out.println("\n *****   AppServices Books_by_id   ***** \n");
		var bookServices = context.getBean(BookServicesImpl.class);
		try {
				System.out.println("\n *****   JSON DOCUMENT   ***** \n");
				System.out.println(bookServices.getByIdToJson(141));
				System.out.println("\n *****   XML DOCUMENT   ***** \n");
				System.out.println(bookServices.getByIdToXml(141));
				bookServices.deleteById(101);
		} catch (ServiceException e) {
				System.out.println("\n - - - - "+e.getMessage()+" - - - - \n");
		}
	}

}
