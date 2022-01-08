package org.vaadin.example.routing;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import java.util.Optional;

@Route("login")
public class LoginView extends Composite<Div> implements HasComponents {
	public LoginView() {
		add(new Button("Login", e -> {
			VaadinSession.getCurrent().setAttribute("userLoggedIn", true);
			Object intendedPath = VaadinSession.getCurrent().getAttribute("intendedPath");
			UI.getCurrent().navigate(Optional.ofNullable(intendedPath).map(Object::toString).orElse(""));
		}));
	}
}
