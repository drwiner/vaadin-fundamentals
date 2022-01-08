package org.vaadin.example.datagrid;

import java.util.Comparator;

public class PersonSort implements Comparator<Person> {

    @Override
    public int compare(Person o1, Person o2) {
        if (propertyName.equals("age")) {
            if (! descending)
                return Integer.compare(o1.getAge(), o2.getAge());
            else {
                return Integer.compare(o2.getAge(), o1.getAge());
            }
        }

        return 0;
    }

    private String propertyName;

    private boolean descending;

    public PersonSort(String sorted, boolean ascending) {
        this.propertyName = sorted;
        this.descending = ! ascending;
    }


    public String getPropertyName() {

        return propertyName;

    }

    public void setPropertyName(String propertyName) {

        this.propertyName = propertyName;

    }

    public boolean isDescending() {

        return descending;

    }

    public void setDescending(boolean descending) {

        this.descending = descending;

    }



}