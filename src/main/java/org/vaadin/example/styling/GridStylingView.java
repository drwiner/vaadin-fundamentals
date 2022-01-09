package org.vaadin.example.styling;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.vaadin.example.MainView;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Route(value = GridStylingView.ROUTE, layout = MainView.class)
public class GridStylingView extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    public static final String ROUTE = "styling";
    public static final String TITLE = "Grid Styling";

    private Grid<MonthlyExpense> expensesGrid;
    private TextField limit;

    public GridStylingView() {
        setSizeFull();

        limit = createLimitTextField();
        add(limit);

        expensesGrid = new Grid<>();
        add(expensesGrid);
        expand(expensesGrid);

        initalizeAndPopulateGrid(expensesGrid);
    }

    private TextField createLimitTextField() {
        TextField limit = new TextField("Limit for monthly expenses");
        limit.addValueChangeListener(event -> expensesGrid.getDataProvider().refreshAll());
        return limit;
    }

    private void initalizeAndPopulateGrid(Grid<MonthlyExpense> grid) {
        grid.addColumn(MonthlyExpense::getMonth).setHeader("Month");

        grid.addColumn(MonthlyExpense::getExpenses).setClassNameGenerator(expense ->
                expense.getExpenses() > getMonthlyExpenseLimit()? "warn" : null);
        /**
         * To style the whole row
        expensesGrid.setClassNameGenerator(expense ->
             expense.getExpenses() > getMonthlyExpenseLimit()? "warn" : null);
         */

        List<MonthlyExpense> data = new ArrayList<>();
        for (int month = 0; month < 12; month++) {
            data.add(new MonthlyExpense(Month.values()[month].toString(), getExpenses(month)));
        }
        grid.setItems(data);
        grid.setAllRowsVisible(true);

    }

    private long getExpenses(int month) {
        return 300 + month * 50;
    }

    private int getMonthlyExpenseLimit() {
        if (limit.getValue() == null || limit.getValue().isEmpty()) {
            return 100000;
        }
        return Integer.parseInt(limit.getValue());
    }
}