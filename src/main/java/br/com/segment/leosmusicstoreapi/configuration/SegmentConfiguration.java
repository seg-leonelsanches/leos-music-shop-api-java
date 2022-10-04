package br.com.segment.leosmusicstoreapi.configuration;

import com.segment.analytics.Analytics;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SegmentConfiguration {

    @Value("${segment.write-key}")
    private String writeKey;

    @Bean
    public Analytics analytics() {
        return Analytics.builder(writeKey).build();
    }
}
