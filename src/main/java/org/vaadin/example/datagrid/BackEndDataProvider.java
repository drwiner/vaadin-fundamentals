package org.vaadin.example.datagrid;


import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.*;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.router.Route;
import org.vaadin.example.MainView;

import javax.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Route(value = BackEndDataProvider.ROUTE, layout = MainView.class)
@PermitAll
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
				q -> {
					List<PersonSort> sortOrders = q.getSortOrders().stream()
							.map(sortOrder -> new PersonSort(sortOrder.getSorted(), sortOrder.getDirection().equals(SortDirection.ASCENDING))).collect(Collectors.toList());
					return service.getPersons(q.getOffset(), q.getLimit(), q.getFilter().orElse(null), sortOrders);
				},
				q -> service.countPersons(q.getOffset(), q.getLimit(), q.getFilter().orElse(null)));

		final ConfigurableFilterDataProvider<Person, Void, AgeGroup> filterProvider = dataProvider
				.withConfigurableFilter();

		// In memory option
//		grid.setItems(service.getAllPersons());


		// add value change listener to filter and update the DataProvider
		// accordingly
		filter.addValueChangeListener(e -> {
			final AgeGroup value = e.getValue();

			filterProvider.setFilter(value);
		});


		grid.addColumn(Person::getName).setHeader("Name").setKey("name");
		grid.addColumn(Person::getEmail).setHeader("Email").setKey("email");
		grid.addColumn(Person::getAge).setHeader("Age").setKey("age").setSortable(true);
		grid.addColumn(new LocalDateRenderer<>(Person::getBirthday)).setHeader("Birthday").setKey("birthday");

		grid.addSortListener(event -> {
			List<QuerySortOrder> querySortOrders = new ArrayList<>(grid.getDataCommunicator().getBackEndSorting());
			dataProvider.setSortOrders(querySortOrders);
		});

		grid.setItems(filterProvider);
	}

}
