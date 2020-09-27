package com.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

class ExampleMain {
    private static EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("example-unit");

    public static void main(String[] args) {
        try {
            persistEmployees();
            findEmployeesWithTasks();
        } finally {
            entityManagerFactory.close();
        }
    }

    public static void persistEmployees() {
        Task task1 = new Task("Coding", "Denise");
        Task task2 = new Task("Refactoring", "Rose");
        Task task3 = new Task("Designing", "Denise");
        Task task4 = new Task("Documentation", "Mike");

        Employee employee1 = Employee.create("Diana", task1, task3);
        Employee employee2 = Employee.create("Mike", task2, task4, task1);
        Employee employee3 = Employee.create("Tim", task3, task4);
        Employee employee4 = Employee.create("Jack");

        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(employee1);
        em.persist(employee2);
        em.persist(employee3);
        em.persist(employee4);
        em.getTransaction().commit();
        em.close();

        System.out.println("-- Employee persisted --");
        System.out.println(employee1);
        System.out.println(employee2);
        System.out.println(employee3);
        System.out.println(employee4);
        System.out.println("\n\n\n\n");
    }

    private static void findEmployeesWithTasks() {
        System.out.println("-- find employees with tasks --");
        EntityManager em = entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
        Root<Employee> employee = query.from(Employee.class);


//        EntityType<Employee> Employee_ = em.getMetamodel().entity(Employee.class);

        employee.join(Employee_.TASKS);

        query.select(employee).distinct(true);
        query.where(cb.or(
                cb.equal(employee.get("name"), "Mike"),
                cb.equal(employee.get("name"), "Tim")
        ));
        TypedQuery<Employee> typedQuery = em.createQuery(query);
        typedQuery.getResultList().forEach(System.out::println);
    }
}
