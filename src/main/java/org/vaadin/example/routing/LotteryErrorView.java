package org.vaadin.example.routing;


import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.*;
import org.vaadin.example.MainView;

import javax.annotation.security.PermitAll;

@ParentLayout(MainView.class)
@PermitAll
public class LotteryErrorView extends Div  implements HasErrorParameter<InvalidLotteryException> {

    public static final String ERROR_TEXT = "Ooops, seems it's an invalid number";
    public LotteryErrorView() {
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
