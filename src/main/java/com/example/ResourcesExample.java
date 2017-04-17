package com.example;

import com.example.data.json.AlmostSameObject;
import com.example.data.Resource;
import com.example.data.ResourceFactory;
import com.example.data.json.SerializationObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by Solovyev on 17/04/2017.
 */
@SpringBootApplication
public class ResourcesExample {
    public static void main(String[] args) {
        final ConfigurableApplicationContext ctx = SpringApplication.run(new Object[]{JaxBExample.class, AppConfig.class}, args);

        ResourceFactory resourceFactory = ctx.getBean(ResourceFactory.class);
        Resource resourceRaw = resourceFactory.get("vasya.json");
        System.out.println("Resource read from the file with no type: " + resourceRaw);


        SerializationObject resource = resourceFactory.get("vasya.json", SerializationObject.class);
//        AlmostSameObject resource = resourceFactory.get("vasya.json", AlmostSameObject.class); // exception here
        System.out.println("Resource read from the file with type safe: " + resource);
    }
}
