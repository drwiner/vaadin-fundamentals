package org.vaadin.example;

public class GreetService {

    public String greet(String name) {
        if (name == null || name.isEmpty()) {
            return "Hello anonymous user 2";
        } else {
            return "Hello 2 " + name;
        }
    }
}
