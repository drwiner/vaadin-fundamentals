package org.vaadin.example.routing;


import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.ParentLayout;
import org.vaadin.example.MainView;

import java.nio.file.AccessDeniedException;

@ParentLayout(MainView.class)
//@Route(value="error", layout = MainLayout.class)
public class InvalidAccessErrorView extends Div  implements HasErrorParameter<AccessDeniedException> {

    public static final String ERROR_TEXT = "Must Login first.";
    public InvalidAccessErrorView() {
        setText(ERROR_TEXT);
    }

    @Override
    public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<AccessDeniedException> parameter) {
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
