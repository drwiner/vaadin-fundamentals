package org.vaadin.example.styling;

public class MonthlyExpense {
	private String month;
	private long expenses;

	public MonthlyExpense(String month, long expenses) {
		setMonth(month);
		setExpenses(expenses);
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public long getExpenses() {
		return expenses;
	}

	public void setExpenses(long expenses) {
		this.expenses = expenses;
	}
}
