package org.vaadin.example.datagrid;


import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.router.Route;
import org.vaadin.example.MainView;

import java.util.ArrayList;
import java.util.List;

@Route(value = BackEndDataProvider.ROUTE, layout = MainView.class)
public class BackEndDataProvider extends VerticalLayout {
	private static final long serialVersionUID = 1L;

	public static final String ROUTE = "ex3";
	public static final String TITLE = "Exercise 3";

	final PersonService service = new PersonService();

	public BackEndDataProvider() {
        setWidth("100%");

		final List<AgeGroup> groups = new ArrayList<>();
		groups.add(new AgeGroup(0, 18));
		groups.add(new AgeGroup(19, 26));
		groups.add(new AgeGroup(27, 40));
		groups.add(new AgeGroup(41, 100));

		final ComboBox<AgeGroup> filter = new ComboBox<>("Filter", groups);
		add(filter);

		final Grid<Person> grid = new Grid<>();
		grid.setWidth("90%");
		add(grid);

		// create lazy Data Provider using the PersonService
		final CallbackDataProvider<Person, AgeGroup> dataProvider = DataProvider.fromFilteringCallbacks(
				q -> service.getPersons(q.getOffset(), q.getLimit(), q.getFilter().orElse(null)),
				q -> service.countPersons(q.getOffset(), q.getLimit(), q.getFilter().orElse(null)));

		final ConfigurableFilterDataProvider<Person, Void, AgeGroup> filterProvider = dataProvider
				.withConfigurableFilter();

		grid.setDataProvider(filterProvider);

		// add value change listener to filter and update the DataProvider
		// accordingly
		filter.addValueChangeListener(e -> {
			final AgeGroup value = e.getValue();
			filterProvider.setFilter(value);
		});

		grid.addColumn(Person::getName).setHeader("Name").setKey("name");
		grid.addColumn(Person::getEmail).setHeader("Email").setKey("email");
		grid.addColumn(Person::getAge).setHeader("Age").setKey("age");
		grid.addColumn(new LocalDateRenderer<>(Person::getBirthday)).setHeader("Birthday").setKey("birthday");
	}

}
