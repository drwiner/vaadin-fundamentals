package org.vaadin.example.routing;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;

import java.util.Random;

@Route(value="lottery", layout = MainLayout.class)
public class LotteryView extends Composite<VerticalLayout> implements HasComponents, HasUrlParameter<Integer>, HasDynamicTitle, BeforeLeaveObserver {

	private final Div lotteryResult = new Div();
	private final TextField numberInput = new TextField();;

	public LotteryView() {
		add(new H2("Lottery View"));

		HorizontalLayout inputBar = new HorizontalLayout();
		inputBar.add(numberInput);
		numberInput.setPlaceholder("Input your number");
		Button button = new Button("Try my luck!", e -> {
			final String value = numberInput.getValue();
			if (value != null && !value.isEmpty()) {
				try {
					final Integer number = Integer.parseInt(value);

					if (! validate(number)){
						lotteryResult.setText(ErrorView.ERROR_TEXT);
					}
					updateContent(number);
				} catch (final NumberFormatException ex) {
					lotteryResult.setText("Please input a valid number");
				}
			}
		});
		button.setEnabled(false);
		numberInput.addValueChangeListener(e->{
			UI.getCurrent().getPage().setTitle("Lottery View "+e.getValue());
			button.setEnabled(e.getValue() != null && !e.getValue().isEmpty());
		});
		inputBar.add(button);
		add(inputBar);
		add(lotteryResult);
	}

	private void updateContent(Integer number) {
		if (number == null) {
			lotteryResult.setText("");
		} else {
			final int luckyNumber = new Random().nextInt(10) + 1;
			StringBuilder builder = new StringBuilder();
			if(number.equals(luckyNumber)){
				builder.append("Congrats, you win! ");
			}else{
				builder.append("Sorry, better luck next time. ");
			}
			builder.append("Your number is: ").append(number).append(", the lucky number is:").append(luckyNumber);

			lotteryResult.setText(builder.toString());
		}
	}

	private boolean validate(Integer number){
		if(number!=null){
			if(number<1 || number >10){
				return false;
			}
		}
		return true;
	}

	@Override
	public void setParameter(BeforeEvent event, @OptionalParameter Integer parameter) {
		if(parameter!=null){
			if (!validate(parameter))
				throw new InvalidLotteryException();
			numberInput.setValue(String.valueOf(parameter));
		}
	}

	@Override
	public String getPageTitle() {
		return "Lottery View " + numberInput.getValue();
	}


	@Override
	public void beforeLeave(BeforeLeaveEvent event) {
		if(numberInput!=null && !numberInput.isEmpty()){
			BeforeLeaveEvent.ContinueNavigationAction action = event.postpone();
			Dialog confirmDialog = new Dialog();
			Paragraph text = new Paragraph("Are you sure you want to leave this page?");
			Button confirmButton = new Button("Confirm", e -> {
				action.proceed();
				confirmDialog.close();
			});
			Button cancelButton = new Button("Cancel", e->confirmDialog.close());
			confirmDialog.add(text, new HorizontalLayout(confirmButton, cancelButton));
			confirmDialog.open();
		}
	}
}
