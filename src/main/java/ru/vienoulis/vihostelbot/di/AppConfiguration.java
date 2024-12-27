package ru.vienoulis.vihostelbot.di;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
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

    @Bean
    public JsonFactory jsonFactory() {
        return JacksonFactory.getDefaultInstance();
    }

    @Bean
    public List<String> googleDataSheetScopes() {
        return Collections.singletonList(SheetsScopes.SPREADSHEETS);
    }

    @Bean
    public NetHttpTransport netHttpTransport() throws GeneralSecurityException, IOException {
        return GoogleNetHttpTransport.newTrustedTransport();
    }

    @Bean
    public DateTimeFormatter googleSheetsFormatter() {
        return DateTimeFormatter.ofPattern("dd.MM.yyyy");
    }
}
