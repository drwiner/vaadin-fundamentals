package org.vaadin.example.datagrid;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.router.Route;
import org.vaadin.example.MainView;

import javax.annotation.security.PermitAll;
import java.time.LocalDate;

@SuppressWarnings("serial")
@Route(value = FilteringDataProvider.ROUTE, layout = MainView.class)
@PermitAll
public class FilteringDataProvider extends Composite<VerticalLayout>{

	public static final String ROUTE = "productgrid";
	public static final String TITLE = "Product Grid";

	private final ListDataProvider<Product> dataProvider;

	public FilteringDataProvider() {
		final VerticalLayout layout = getContent();
		layout.setWidth("100%");

		dataProvider = DataProviderHelper.createProductDataProvider();

		// create layout for DateFields
		final DatePicker fromField = new DatePicker("Start");
		final DatePicker toField = new DatePicker("End");
		final Button filter = new Button("Filter");
		final HorizontalLayout filters = new HorizontalLayout(fromField, toField, filter);
		filters.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.END);

		filter.addClickListener(e -> {

			// setFilter will clear any previous filter
			dataProvider.setFilter(Product::getAvailable, available -> filterProduct(available, fromField.getValue(), toField.getValue()));
		});

		layout.add(filters);

		// create and populate Grid
		final Grid<Product> grid = new Grid<>();
		grid.setWidth("90%");
		grid.setItems(dataProvider);

		grid.addColumn(new LocalDateRenderer<>(Product::getAvailable)).setHeader("Available");
		grid.addColumn(Product::getName).setHeader("Name").setSortable(true);
		grid.addColumn(Product::getPrice).setHeader("Price");

		layout.add(grid);

	}

	private boolean filterProduct(LocalDate available, LocalDate start, LocalDate end) {

		//final LocalDate available = product.getAvailable();

		// Null checks
		if (available == null) {

			if (start != null || end != null) {
				// data is null, but user wants to filter; assume false
				return false;
			}
			return true;
		}

		if (start == null && end == null) {

			return true;

		} else if (start == null) {

			return available.isBefore(end) || available.equals(end);

		} else if (end == null) {

			return available.isAfter(start) || available.equals(start);

		} else {

			final boolean atEnds = available.equals(start) || available.equals(end);
			final boolean inbetween = available.isAfter(start) && available.isBefore(end);

			return atEnds || inbetween;
		}
	}

}
