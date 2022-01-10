package org.vaadin.example.routing;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;
import org.vaadin.example.security.SecurityService;

import javax.annotation.security.PermitAll;
import java.util.Optional;

@Route("login")
@PageTitle("Login")
@PermitAll
public class LoginView extends VerticalLayout implements BeforeEnterObserver, BeforeLeaveObserver, ComponentEventListener<AbstractLogin.LoginEvent> {

	private final LoginForm login = new LoginForm();

	public LoginView() {
		addClassName("login-view");
		setSizeFull();
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);

		login.setAction("login");
		add(new H1("Vaadin App"), login);

		login.addLoginListener(this);

//		login.addLoginListener(e -> {

//			VaadinSession.getCurrent().setAttribute("userLoggedIn", true);
//			Object intendedPath = VaadinSession.getCurrent().getAttribute("intendedPath");
//			UI.getCurrent().navigate(Optional.ofNullable(intendedPath).map(Object::toString).orElse(""));
//		});
	}

	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		if(beforeEnterEvent.getLocation()
				.getQueryParameters()
				.getParameters()
				.containsKey("error")) {
			login.setError(true);
		}
	}

	@Override
	public void beforeLeave(BeforeLeaveEvent beforeLeaveEvent) {
//		System.out.println("LEaving");
	}

	@Override
	public void onComponentEvent(AbstractLogin.LoginEvent loginEvent) {
		boolean authenticated = SecurityService.authenticate(
				loginEvent.getUsername(), loginEvent.getPassword());
		if (authenticated) {
			VaadinSession session = UI.getCurrent().getSession();
			session.setAttribute("userLoggedIn", true);
			Object intendedPath = VaadinSession.getCurrent().getAttribute("intendedPath");
			UI.getCurrent().navigate(Optional.ofNullable(intendedPath).map(Object::toString).orElse(""));

		} else {
			login.setError(true);
		}
	}
}
