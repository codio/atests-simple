package com.codio.common.users;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.codio.helpers.Driver;

public class Student extends User {

    protected String firstName;
    protected String lastName;

    public String getFirstName() {
        if (firstName == null) {
            Driver.exitTestWithError("First name was not set for user");
        }
        return firstName;
    }

    public String getLastName() {
        if (lastName == null) {
            Driver.exitTestWithError("Last name was not set for user");
        }
        return lastName;
    }

    public static Student getStudent(String student) {
        final Student st = new Student();
        Properties properties = new Properties();
        try {
            try (FileInputStream inputStream = new FileInputStream("src/test/resources/config.properties")) {
                properties.load(inputStream);
                st.setLogin(properties.getProperty(student + ".login"));
                st.setEmail(properties.getProperty(student + ".email"));
                st.setFirstName(properties.getProperty(student + ".firstName"));
                st.setLastName(properties.getProperty(student + ".lastName"));
                st.setFullName(st.getFirstName() + " " + st.getLastName());
                st.setPassword(properties.getProperty(student + ".password"));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return st;
    }

    public Student setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Student setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
}
