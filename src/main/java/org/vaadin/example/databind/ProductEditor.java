package org.vaadin.example.databind;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

import java.util.function.Consumer;

public class ProductEditor extends Composite<VerticalLayout> implements HasComponents, HasSize{
    public ProductEditor(Product product, Consumer<Product> productConsumer){
        final ProductFormLayout productEditLayout = new ProductFormLayout();

        // First we create a BeanBinder typed for the Product bean
        final Binder<Product> binder = new Binder<>(Product.class);

        // Bonus task: Binds the price field using the CurrencyConverter. This
        // is also why the price field doesn't have a @PropertyId annotation in
        // ProductFormLayout, we bind it manually.
        binder.forField(productEditLayout.getPriceField()).withConverter(new CurrencyConverter())
                .bind(Product::getPrice, Product::setPrice);

        // Manual binding needed due to name inconsistency between UI and Bean, and lacking of @PropertyId annotation
        binder.forField(productEditLayout.getAvailableField()).bind(Product::getAvailable, Product::setAvailable);

        // This binds the name and options fields using the @PropertyId
        // annotations in the ProductForm
        binder.bindInstanceFields(productEditLayout);

        // Reads the initial data from the Product bean
        binder.readBean(product);

        // Build a footer, add Save and Cancel buttons
        final HorizontalLayout footer = new HorizontalLayout();

        footer.add(new Button("Save", event -> {
            try {
                binder.writeBean(product);
                // Refreshes the read-only view on successful save
                productConsumer.accept(product);
            } catch (final ValidationException e) {
                e.printStackTrace();
            }
        }));

        footer.add(new Button("Cancel", event -> binder.readBean(product)));
        footer.getThemeList().set("spacing", true);

        // Build a containing layout for the editor and the footer
        add(productEditLayout, footer);
    }
}
