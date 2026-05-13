package com.example.softlearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.softlearning.core.entity.book.dtos.BookDTO;
import com.example.softlearning.infrastructure.persistence.jpa.JpaBookRepository;

@SpringBootApplication
public class BookJPAFunctionals {

    // public static void main(String[] args) {
    //     ApplicationContext context = SpringApplication.run(BookJPAFunctionals.class, args);
    //     var repo = context.getBean(JpaBookRepository.class);

    //     System.out.println("\n *****   Books in the repository   ***** \n");
    //     repo.findAll().forEach(System.out::println);

    //     System.out.println("\n *****   Java Books by title  ***** \n");
    //     repo.findByName("java").forEach(System.out::println);

    //     System.out.println("\n *****   Add a new Java Book  ***** \n");
    //     repo.save(new BookDTO(137,"Java Spring JPA",29.99,true,"Princeton","2024-04-26","2024-05-01",0.45,21.0,29.0,2.5,true));

    //     System.out.println("\n *****   Java Books by partial title  ***** \n");
    //     repo.findByPartialTitle("java").forEach(System.out::println);

    //     System.out.println("\n *****   Update a Java Book  ***** \n");
    //     repo.save(new BookDTO(137,"Spring JPA",34.99,true,"Princeton","2024-04-26","2024-05-10",0.50,21.0,29.0,2.8,false));

    //     System.out.println("\n *****   Books by id   ***** \n");
    //     repo.findById(137).ifPresent(System.out::println);

    //     System.out.println("\n *****   Delete a Book  ***** \n");
    //     repo.deleteById(137);

    //     System.out.println("\n *****   Books by id   ***** \n");
    //     repo.findById(137).ifPresent(System.out::println);

    //     System.out.println("\n *****    Java Books available: " + repo.countByPartialTitle("java"));
    // }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(BookJPAFunctionals.class, args);
        var repo = context.getBean(JpaBookRepository.class);

        System.out.println("\n*****   Current Books in repository   *****\n");
        repo.findAll().forEach(System.out::println);

        System.out.println("\n*****   Adding new books   *****\n");
        repo.save(new BookDTO(137,"Java Spring JPA",29.99,true,"Princeton","2024-04-26","2024-05-01",0.45,21.0,29.0,2.5,true));
        repo.save(new BookDTO(138,"Advanced Java",39.99,true,"Oracle","2023-09-10","2023-09-20",0.55,22.0,30.0,3.0,false));
        repo.save(new BookDTO(139,"Python Basics",25.50,true,"Marcombo","2022-06-15","2022-06-25",0.40,20.0,28.0,2.0,true));

        System.out.println("\n*****   Adding duplicate Book   *****\n");
        // Se modifica el libro con id 139 ya existente, no se duplica.
        repo.save(new BookDTO(139,"MySQL vs SQLite",25.50,true,"Marcombo","2022-06-15","2022-06-25",0.40,20.0,28.0,2.0,true));

        System.out.println("\n*****   All Books after adding   *****\n");
        repo.findAll().forEach(System.out::println);

        System.out.println("\n*****   Find Book by exact name ('Advanced Java')   *****\n");
        repo.findByName("Advanced Java").forEach(System.out::println);

        System.out.println("\n*****   Find Book by invalid exact name ('Advanced Javaaaa')   *****\n");
        repo.findByName("Advanced Javaaaa").forEach(System.out::println);

        System.out.println("\n*****   Find Books by partial name ('Java')   *****\n");
        repo.findByPartialName("Java").forEach(System.out::println);
        System.out.println("Count of Books: " + repo.countByPartialName("Java"));

        System.out.println("\n*****   Find Books by invalid partial name ('Javaaaa')   *****\n");
        repo.findByPartialName("Javaaaa").forEach(System.out::println);
        System.out.println("Count of Books: " + repo.countByPartialName("Javaaaa"));

        System.out.println("\n*****   Find Book with id 137   *****\n");
        repo.findById(137).ifPresent(System.out::println);

        System.out.println("\n*****   Find Book with invalid id 140   *****\n");
        repo.findById(140).ifPresent(System.out::println);

        System.out.println("\n*****   Updating Book with id 137   *****\n");
        repo.save(new BookDTO(137,"Java Spring Boot",32.99,true,"Princeton","2024-04-26","2024-05-05",0.45,21.0,29.0,2.5,true));

        System.out.println("\n*****   Updating Book with invalid id 140   *****\n");
        // Se crea un nuevo libro ya que no existe el id 140. UPSERT.
        repo.save(new BookDTO(140,"JavaScript vs Python",38.99,false,"Marcombo","2024-04-29","2024-06-05",0.45,21.0,29.0,2.5,true));
        
        System.out.println("\n*****   Book with id 137 after update   *****\n");
        repo.findById(137).ifPresent(System.out::println);

        System.out.println("\n*****   Book with invalid id 140 after update   *****\n");
        repo.findById(140).ifPresent(System.out::println);

        System.out.println("\n*****   Repository state   *****\n");
        repo.findAll().forEach(System.out::println);
        System.out.println("Total books: " + repo.count());
        
        System.out.println("\n*****   Final Repository state after deletion   *****\n");
        repo.deleteAll();
        System.out.println("Total books: " + repo.count());
    }
}
