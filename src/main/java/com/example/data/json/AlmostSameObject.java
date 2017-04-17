package com.example.data.json;

import com.example.data.Resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Solovyev on 09/11/2016.
 */
@SuppressWarnings("unused")
public class AlmostSameObject extends Resource {
    private String name;
    private int age;

    public AlmostSameObject() {
    }

    public AlmostSameObject(String type, String name, int age) {
        super(type);
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "SerializationObject{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}