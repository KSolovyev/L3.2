package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.bind.Marshaller;
import java.util.HashMap;

/**
 * Created by Solovyev on 09/11/2016.
 */
@Configuration
public class AppConfig {

    @Bean
    public Jaxb2Marshaller getMarshallerInternal() {
        final Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setPackagesToScan("com.example.data.jaxb");
        final HashMap<String, Object> settings = new HashMap<>();
        settings.put(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxb2Marshaller.setMarshallerProperties(settings);
        return jaxb2Marshaller;
    }
}
