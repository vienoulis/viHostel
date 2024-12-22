package ru.vienoulis.vihostelbot.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vienoulis.vihostelbot.dto.Visitor;
import ru.vienoulis.vihostelbot.repo.Repository;
import ru.vienoulis.vihostelbot.repo.impl.InMemoryVisitorRepositoryImpl;

@Configuration
public class AppConfiguration {

    @Bean
    public Repository<Visitor> visitorRepository() {
        return new InMemoryVisitorRepositoryImpl();
    }
}
