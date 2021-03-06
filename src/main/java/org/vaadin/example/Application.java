package org.vaadin.example;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.vaadin.example.security.Auth0Servlet;

@SpringBootApplication
@PWA(name = "Project Base for Vaadin", shortName = "Project Base")
@Theme(themeClass= Lumo.class, variant=Lumo.DARK)
@CssImport(value = "./styles/vaadin-grid-warning.css", themeFor = "vaadin-grid")
public class Application extends SpringBootServletInitializer implements AppShellConfigurator {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private ApplicationContext ctx;

    @Bean
    public ServletRegistrationBean anotherServlet() {
        return new ServletRegistrationBean(new Auth0Servlet(ctx), "/*");
    }
}
