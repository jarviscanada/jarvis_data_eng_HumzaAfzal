package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCExecutor {

  public static void main(String[] args) {
    DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "hplussport",
        "postgres", "password");
    try {
      Connection connection = dcm.getConnection();
      CustomerDAO customerDAO = new CustomerDAO(connection);
      customerDAO.findAllSorted(20).forEach(System.out::println);
      System.out.println("PAGED");
      for (int i = 1; i < 3; i++) {
        System.out.println("Page number: " + i);
        customerDAO.findAllPaged(10, i).forEach(System.out::println);
      }
//      CustomerDAO customerDAO = new CustomerDAO(connection);
//      Customer customer = new Customer();
//      customer.setFirstName("John");
//      customer.setLastName("Adams");
//      customer.setEmail("jadams@wh.gov");
//      customer.setAddress("1234 Main St");
//      customer.setCity("Arlington");
//      customer.setState("VA");
//      customer.setPhone("(555) 555-9845");
//      customer.setZipCode("01234");
//
//      Customer dbCustomer = customerDAO.create(customer);
//      System.out.println(dbCustomer);
//      dbCustomer = customerDAO.findById(dbCustomer.getId());
//      System.out.println(dbCustomer);
//      dbCustomer.setEmail("john.adams@wh.gov");
//      dbCustomer = customerDAO.update(dbCustomer);
//      System.out.println(dbCustomer);
//      customerDAO.delete(dbCustomer.getId());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
