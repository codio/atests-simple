package com.codio.common.users;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Teacher extends User {

    public static Teacher getTeacher(String teacher) {
        final Teacher t = new Teacher();
        Properties properties = new Properties();
        try {
            try (FileInputStream inputStream = new FileInputStream("src/test/resources/config.properties")) {
                properties.load(inputStream);
                t.setLogin(properties.getProperty(teacher + ".login"));
                t.setEmail(properties.getProperty(teacher + ".email"));
                t.setFullName(
                    properties.getProperty(teacher + ".firstName") + " " + properties.getProperty(teacher + ".lastName")
                );
                t.setPassword(properties.getProperty(teacher + ".password"));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return t;
    }
}
