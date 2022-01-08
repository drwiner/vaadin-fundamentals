package org.vaadin.example.databind;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.vaadin.example.MainView;

@Route(value = Validation.ROUTE, layout = MainView.class)
//@RouteAlias(value="", layout = MainView.class)
public class Validation extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	public static final String ROUTE = "ex1";
	public static final String TITLE = "Validation";

	public Validation() {
		// We need to provide the bean class reference in the constructor in
		// order to be able to use binding by property names (done for
		// doubleField in this example). Binding using lambda expressions
		// wouldn't need the class literal.
		final Binder<FormValue> binder = new Binder<>(FormValue.class);

		binder.setBean(new FormValue());

		final EmailField emailField = new EmailField("Email validator");
		binder.forField(emailField)
				.withValidator(new EmailValidator("Are you sure the given value is an email address"))
				.bind(FormValue::getEmailValue, FormValue::setEmailValue);

		final TextField stringField = new TextField("String length validator");
		binder.forField(stringField).withValidator(new StringLengthValidator("Maximum of 10 characters allowed", 0, 10))
				.bind(FormValue::getStringValue, FormValue::setStringValue);

		final TextField vaadinField = new TextField("Vaadin validator");
		binder.forField(vaadinField).withValidator((value, context) -> {
			if (value == null || "".equals(value) || value.equals("Vaadin")) {
				return ValidationResult.ok();
			} else {
				return ValidationResult.error(value + " not accepted");
			}
		}).bind(FormValue::getVaadinValue, FormValue::setVaadinValue);

		add(emailField, stringField, vaadinField);

	}

}