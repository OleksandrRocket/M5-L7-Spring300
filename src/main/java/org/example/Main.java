package org.example;

import org.example.userrepo.UserRepository;
import org.example.userrepoconfig.CrudRepositoryConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
//        ApplicationContext applicationContext =  new AnnotationConfigApplicationContext(DaoConfig.class);
//        applicationContext.getBean(UserDao.class);

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CrudRepositoryConfig.class);
        UserRepository repository = applicationContext.getBean(UserRepository.class);
    }
}