package ru.vienoulis.vihostelbot.di;

import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vienoulis.vihostelbot.process.MultyStepProcess;
import ru.vienoulis.vihostelbot.process.Process;
import ru.vienoulis.vihostelbot.service.ProcessFinderService;
import ru.vienoulis.vihostelbot.state.StateService;
import ru.vienoulis.vihostelbot.step.add.AddStepGenerator;
import ru.vienoulis.vihostelbot.step.list.AllStepGenerator;
import ru.vienoulis.vihostelbot.step.payday.PaydayStepGenerator;
import ru.vienoulis.vihostelbot.step.test.TestStepGenerator;

@Slf4j
@Configuration
public class ProcessConfiguration {

    @Bean
    public MultyStepProcess testMultyStepProcess(StateService stateService,
            TestStepGenerator testStepGenerator) {
        return new MultyStepProcess(stateService, testStepGenerator);
    }

    @Bean
    public MultyStepProcess allMultyStepProcess(StateService stateService,
            AllStepGenerator allStepGenerator) {
        return new MultyStepProcess(stateService, allStepGenerator);
    }

    @Bean
    public MultyStepProcess addMultyStepProcess(StateService stateService,
            AddStepGenerator testStepGenerator) {
        return new MultyStepProcess(stateService, testStepGenerator);
    }

    @Bean
    public MultyStepProcess payDayStepProcess(StateService stateService, PaydayStepGenerator payDayStepGenerator) {
        return new MultyStepProcess(stateService, payDayStepGenerator);
    }

    @Bean
    public ProcessFinderService processFinderService(
            MultyStepProcess testMultyStepProcess,
            MultyStepProcess addMultyStepProcess,
            MultyStepProcess allMultyStepProcess,
            MultyStepProcess payDayStepProcess) {

        Set<Process> processSet = Set.of(
                testMultyStepProcess,
                addMultyStepProcess,
                allMultyStepProcess,
                payDayStepProcess);
        return message -> processSet.stream()
                .filter(p -> p.canStartProcess(message))
                .peek(p -> log.debug("find process: {}", p.getAction()))
                .findFirst();
    }
}
