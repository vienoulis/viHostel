package ru.vienoulis.vihostelbot.di;

import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vienoulis.vihostelbot.process.MultyStepProcess;
import ru.vienoulis.vihostelbot.process.Process;
import ru.vienoulis.vihostelbot.service.ProcessFinderService;
import ru.vienoulis.vihostelbot.state.StateService;
import ru.vienoulis.vihostelbot.step.add.AddStepGenerator;
import ru.vienoulis.vihostelbot.step.test.TestStepGenerator;

@Configuration
public class ProcessConfiguration {

    @Bean
    public MultyStepProcess testMultyStepProcess(StateService stateService,
            TestStepGenerator testStepGenerator) {
        return new MultyStepProcess(stateService, testStepGenerator);
    }

    @Bean
    public MultyStepProcess addMultyStepProcess(StateService stateService,
            AddStepGenerator testStepGenerator) {
        return new MultyStepProcess(stateService, testStepGenerator);
    }

    @Bean
    public ProcessFinderService processFinderService(MultyStepProcess testMultyStepProcess,
            MultyStepProcess addMultyStepProcess) {
        Set<Process> processSet = Set.of(testMultyStepProcess, addMultyStepProcess);
        return message -> processSet.stream()
                .filter(p -> p.canStartProcess(message))
                .findFirst();
    }
}
