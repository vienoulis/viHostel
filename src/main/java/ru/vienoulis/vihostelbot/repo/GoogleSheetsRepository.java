package ru.vienoulis.vihostelbot.repo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vienoulis.vihostelbot.dto.SheetsVisitor;
import ru.vienoulis.vihostelbot.service.ConfigProvider;
import ru.vienoulis.vihostelbot.service.googlesheets.GoogleSheetsConnector;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GoogleSheetsRepository implements Repository<SheetsVisitor> {

    private final ConfigProvider configProvider;
    private final GoogleSheetsConnector sheetsConnector;
    private final DateTimeFormatter googleSheetsFormatter;
    private SheetsVisitor transitionalEntry = SheetsVisitor.builder().build();

    @Override
    public SheetsVisitor getTransitionalEntry() {
        return transitionalEntry;
    }

    @Override
    public void setTransitionalEntry(SheetsVisitor entry) {
        this.transitionalEntry = entry;
    }

    @Override
    public void saveEntry(SheetsVisitor visitor) {
        throw new UnsupportedOperationException("saveEntry not supported yet;");
    }

    @Override
    public Optional<SheetsVisitor> getEntryBy(Predicate<SheetsVisitor> predicate) {
        return Optional.empty();
    }

    @Override
    @SneakyThrows
    public Set<SheetsVisitor> getEntrysBy(Predicate<SheetsVisitor> predicate) {
        return sheetsConnector.getService().spreadsheets().values()
                .get(configProvider.getSpreadsheetId(), configProvider.getSheetsRange())
                .execute().getValues().stream()
                .filter(l -> l.size() >= 3)
                .filter(l -> StringUtils.isNotBlank((CharSequence) l.getFirst()))
                .map(l -> SheetsVisitor.builder()
                        .room((String) l.getFirst())
                        .fio((String) l.get(1))
                        .startDate(LocalDate.parse((String) l.get(2), googleSheetsFormatter))
                        .endDate(LocalDate.parse((String) l.get(3), googleSheetsFormatter))
                        .contact(l.size() == 5 ? (String) (l.get(4)) : "")
                        .build())
                .filter(predicate)
                .collect(Collectors.toSet());
    }
}
