package com.example;

import com.example.data.jaxb.SerializationObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@SuppressWarnings("OverlyBroadCatchBlock")
@SpringBootApplication
public class JaxBExample {
    private static final Logger logger = LoggerFactory.getLogger(JaxBExample.class);
    private static final String FILE_NAME = "object.xml";


    public static void main(String[] args) {
        final ConfigurableApplicationContext ctx = SpringApplication.run(new Object[]{JaxBExample.class, AppConfig.class}, args);
        final JaxBExample bean = ctx.getBean(JaxBExample.class);

        bean.serialize(new SerializationObject("Vasya", 5));
        final SerializationObject deserialized = bean.deserialize();
        assert deserialized != null;
    }

    private final Marshaller marshaller;
    private final Unmarshaller unmarshaller;

    public JaxBExample(Marshaller marshaller, Unmarshaller unmarshaller) {
        this.marshaller = marshaller;
        this.unmarshaller = unmarshaller;
    }

    public void serialize(SerializationObject serializationObject) {
        try (final FileOutputStream os = new FileOutputStream(FILE_NAME)) {
            this.marshaller.marshal(serializationObject, new StreamResult(os));
        } catch (IOException ex) {
            logger.error("Error serializing object", ex);
        }
    }

    public SerializationObject deserialize() {
        SerializationObject unmarshalled = null;
        try (FileInputStream is = new FileInputStream(FILE_NAME)) {
            unmarshalled = (SerializationObject) unmarshaller.unmarshal(new StreamSource(is));
        } catch (IOException ex) {
            logger.error("Error deserializing object", ex);
        }
        logger.info("Object deserialized: " + unmarshalled);
        return unmarshalled;
    }


}
