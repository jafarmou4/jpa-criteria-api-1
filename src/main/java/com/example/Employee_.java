package com.example;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Employee.class)
public abstract class Employee_ {
    public static volatile SingularAttribute<Employee, String> name;
    public static volatile SingularAttribute<Employee, Long> id;
    public static volatile SingularAttribute<Employee, Task> tasks;
    public static final String NAME = "name";
    public static final String ID = "id";
    public static final String TASKS = "tasks";

    public Employee_() {
    }
}
