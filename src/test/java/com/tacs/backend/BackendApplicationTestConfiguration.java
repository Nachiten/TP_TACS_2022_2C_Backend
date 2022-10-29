package com.tacs.backend;

import com.tacs.backend.repository.MatchRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("BackendTests")
@Configuration
public class BackendApplicationTestConfiguration {
    @Bean
    @Primary
    public MatchRepository mockMatchRepository() {
        return Mockito.mock(MatchRepository.class);
    }
}
