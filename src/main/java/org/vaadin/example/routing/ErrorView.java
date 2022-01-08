package org.vaadin.example.routing;


import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.*;

@ParentLayout(MainLayout.class)
//@Route(value="error", layout = MainLayout.class)
public class ErrorView extends Div  implements HasErrorParameter<InvalidLotteryException> {

    public static final String ERROR_TEXT = "Ooops, seems it's an invalid number";
    public ErrorView() {
        setText(ERROR_TEXT);
    }

    @Override
    public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<InvalidLotteryException> parameter) {
//        setText(ERROR_TEXT);
        return 500;
    }


//
//    @Override
//    public int setErrorParameter(BeforeEnterEvent beforeEnterEvent, ErrorParameter<RuntimeException> errorParameter) {
//        setText("Ooops, seems it's an invalid number");
//        return 500;
//    }
}
