package com.example.softlearning;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.softlearning.infrastructure.persistence.jpa.auth.RoleEntity;
import com.example.softlearning.infrastructure.persistence.jpa.auth.UserEntity;
import com.example.softlearning.infrastructure.persistence.jpa.auth.UserRepository;
import com.example.softlearning.infrastructure.security.config.RoleEnum;

@Component
public class LoadDefaultData implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Este componente se ejecutará al iniciar la aplicación y se encargará de cargar los datos por defecto en la base de datos si no existen.

        if (userRepository.count() == 0) {
            /* Create ROLES */
            RoleEntity roleAdmin = new RoleEntity(RoleEnum.ADMIN);

            RoleEntity roleManager = new RoleEntity(RoleEnum.MANAGER);

            RoleEntity roleClient = new RoleEntity(RoleEnum.CLIENT);

            /* CREATE USERS */
            UserEntity admin = new UserEntity("admin", new BCryptPasswordEncoder().encode("1234"), true,
                                    true, true, true, Set.of(roleAdmin)
            );

            UserEntity manager = new UserEntity("manager", new BCryptPasswordEncoder().encode("1234"), true,
                                    true, true, true, Set.of(roleManager)
            );

            UserEntity client1 = new UserEntity("client1", new BCryptPasswordEncoder().encode("1234"), true,
                                    true, true, true, Set.of(roleClient)
            );

            UserEntity client2 = new UserEntity("client2", new BCryptPasswordEncoder().encode("1234"), true,
                            true, true, true, Set.of(roleClient)
            );

            userRepository.saveAll(List.of(admin, manager, client1, client2));
        }
    }
}
