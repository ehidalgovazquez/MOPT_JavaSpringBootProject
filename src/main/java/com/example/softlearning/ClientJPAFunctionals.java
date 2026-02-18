package com.example.softlearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.softlearning.core.entity.client.dtos.ClientDTO;
import com.example.softlearning.infrastruture.persistence.jpa.JpaClientRepository;

@SpringBootApplication
public class ClientJPAFunctionals {

    // public static void main(String[] args) {
    //     ApplicationContext context = SpringApplication.run(ClientJPAFunctionals.class, args);
    //     var repo = context.getBean(JpaClientRepository.class);

    //     System.out.println("\n *****   Clients in the repository   ***** \n");
    //     repo.findAll().forEach(System.out::println);

    //     System.out.println("\n *****   Java Clients by email  ***** \n");
    //     repo.findByEmail("cliente").forEach(System.out::println);

    //     System.out.println("\n *****   Add a new Java Client  ***** \n");
    //     repo.save(new ClientDTO(137,"12345678","cliente@correo.com","+1-555-1234","Princeton","Java Spring JPA", "2024-04-26")); 

    //     System.out.println("\n *****   Java Clients by partial email  ***** \n");
    //     repo.findByPartialEmail("correo").forEach(System.out::println);

    //     System.out.println("\n *****   Update a Java Client  ***** \n");
    //     repo.save(new ClientDTO(137,"87654321","correo@cliente.com","+1-555-1234","Princeton","Java Spring JPA","2024-04-26" ));

    //     System.out.println("\n *****   Clients by id   ***** \n");
    //     repo.findById(137).ifPresent(System.out::println);

    //     System.out.println("\n *****   Delete a Client  ***** \n");
    //     repo.deleteById(137);

    //     System.out.println("\n *****   Clients by id   ***** \n");
    //     repo.findById(137).ifPresent(System.out::println);

    //     System.out.println("\n *****    Java Clients available: " + repo.countByPartialEmail("java"));
    // }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ClientJPAFunctionals.class, args);
        var repo = context.getBean(JpaClientRepository.class);

        System.out.println("\n*****   Current Clients in repository   *****\n");
        repo.findAll().forEach(System.out::println);

        System.out.println("\n*****   Adding new Clients   *****\n");
        repo.save(new ClientDTO(137,"12345678","cliente@correo.com","+1-555-1234","Princeton","Java Spring JPA", "2024-04-26"));
        repo.save(new ClientDTO(138,"87654321","advanced@correo.com","+1-555-5678","Princeton","Advanced Java", "2023-09-10"));
        repo.save(new ClientDTO(139,"98765432","python@correo.com","+1-555-9012","Madrid","Python Basics", "2022-06-15"));

        System.out.println("\n*****   Adding duplicate Client   *****\n");
        // Se modifica el cliente con id 139 ya existente, no se duplica.
        repo.save(new ClientDTO(139,"23456789","cliente@correo.com","+1-555-1234","Princeton","Java Spring JPA", "2024-04-26"));

        System.out.println("\n*****   All Clients after adding   *****\n");
        repo.findAll().forEach(System.out::println);

        System.out.println("\n*****   Find Client by exact email ('cliente@correo.com')   *****\n");
        repo.findByEmail("cliente@correo.com").forEach(System.out::println);

        System.out.println("\n*****   Find Client by invalid email ('cliente@correoooo.com')   *****\n");
        repo.findByEmail("cliente@correoooo.com").forEach(System.out::println);

        System.out.println("\n*****   Find Clients by partial email ('advanced')   *****\n");
        repo.findByPartialEmail("advanced").forEach(System.out::println);
        System.out.println("Count of Clients: " + repo.countByPartialEmail("advanced"));

        System.out.println("\n*****   Find Clients by invalid partial email ('advancedddd')   *****\n");
        repo.findByPartialEmail("advancedddd").forEach(System.out::println);
        System.out.println("Count of Clients: " + repo.countByPartialEmail("advancedddd"));
        
        System.out.println("\n*****   Find Client with id 137   *****\n");
        repo.findById(137).ifPresent(System.out::println);

        System.out.println("\n*****   Find Client with invalid id 140   *****\n");
        repo.findById(140).ifPresent(System.out::println);

        System.out.println("\n*****   Updating Client with id 137   *****\n");
        repo.save(new ClientDTO(137,"12345678","correo@cliente.com","+1-555-1234","Princeton","Java Spring JPA", "2024-04-26"));

        System.out.println("\n*****   Updating Client with invalid id 140   *****\n");
        // Se crea un nuevo cliente ya que no existe el id 140. UPSERT.
        repo.save(new ClientDTO(140,"98765432","correo@cliente.com","+1-555-1234","Princeton","Java Spring JPA", "2024-04-26"));

        System.out.println("\n*****   Find Client with id 137 after update   *****\n");
        repo.findById(137).ifPresent(System.out::println);

        System.out.println("\n*****   Find Client with invalid id 140 after update   *****\n");
        repo.findById(140).ifPresent(System.out::println);

        System.out.println("\n*****   Repository state   *****\n");
        repo.findAll().forEach(System.out::println);
        System.out.println("Total Clients: " + repo.count());

        System.out.println("\n*****   Final repository state after deletion   *****\n");
        repo.deleteAll();
        System.out.println("Total Clients: " + repo.count());
    }
}
