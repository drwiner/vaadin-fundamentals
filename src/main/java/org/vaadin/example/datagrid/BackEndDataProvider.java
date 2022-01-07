package org.vaadin.example.datagrid;


import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.router.Route;
import org.vaadin.example.MainView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Route(value = BackEndDataProvider.ROUTE, layout = MainView.class)
public class BackEndDataProvider extends VerticalLayout {
	private static final long serialVersionUID = 1L;

	public static final String ROUTE = "persongrid";
	public static final String TITLE = "Person  Grid";

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

//		// create lazy Data Provider using the PersonService
		final CallbackDataProvider<Person, AgeGroup> dataProvider = DataProvider.fromFilteringCallbacks(
				q -> service.getPersons(q.getOffset(), q.getLimit(), q.getFilter().orElse(null)),
				q -> service.countPersons(q.getOffset(), q.getLimit(), q.getFilter().orElse(null)));


//		dataProvider.setSor
		final ConfigurableFilterDataProvider<Person, Void, AgeGroup> filterProvider = dataProvider
				.withConfigurableFilter();


		grid.setItems(service.getAllPersons());


		// add value change listener to filter and update the DataProvider
		// accordingly
		filter.addValueChangeListener(e -> {
			final AgeGroup value = e.getValue();

			grid.setItems(service.getPersons(value).collect(Collectors.toList()));
//			grid.setItems(service.getAllPersons().stream().filter(p -> p.getAge() >= value.getMinAge() && p.getAge() <= value.getMaxAge()).collect(Collectors.toList()));
//			filterProvider.setFilter(value);
		});

		grid.addColumn(Person::getName).setHeader("Name").setKey("name");
		grid.addColumn(Person::getEmail).setHeader("Email").setKey("email");
		grid.addColumn(Person::getAge).setHeader("Age").setKey("age").setComparator(Comparator.comparingInt(Person::getAge)).getComparator(SortDirection.ASCENDING);
		grid.addColumn(new LocalDateRenderer<>(Person::getBirthday)).setHeader("Birthday").setKey("birthday");
	}

}
