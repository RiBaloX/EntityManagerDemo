/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitymanagerdemo;


import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Address;
import model.Customer;




/**
 *
 * @author sarun
 */
public class EntityManagerDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        
        //createData
        createData(1L, "John", "Henry", "jh@mail.com",1L, "123/4 Viphavadee Rd.", "Bangkok", "10900", "TH");
        createData(2L, "Marry", "Jane", "mj@mail.com",2L, "123/5 Viphavadee Rd.", "Bangkok", "10900", "TH");
        createData(3L, "Peter", "Parker", "pp@mail.com",3L, "543/21 Fake Rd.", "Nonthaburi", "20900", "TH");

        
        //printAllCustomer
        List<Customer> CustomerList = findAllCustomer();
        printAllCustomer(CustomerList);
  
        
        //printCustomerByCity

        printCustomerByCity("Bangkok");

    }
    public static void createData(Long  customer_id, String firstname, String lastname, String email, Long  address_id, String street, String city, String zipcode, String country){
        Customer customer = new Customer(customer_id, firstname, lastname, email); 
        Address address = new Address(address_id, street, city, zipcode, country); 
        address.setCustomerFk(customer);
        customer.setAddressId(address); 
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerDemoPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(address);
            em.persist(customer);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    public static void printAllCustomer(List<Customer> CustomerList) {
        for(Customer Customer : CustomerList) {
//           System.out.print(Customer.getId() + " ");
           System.out.println("First Name: "+ Customer.getFirstname());
           System.out.println("Last Name: "+Customer.getLastname());
           System.out.println("Email: "+Customer.getEmail());
            Address address;
           address = findAddressById(Customer.getId());
           System.out.println("Street: "+address.getStreet());
           System.out.println("City: "+address.getCity());
           System.out.println("Country: "+address.getCity());
           System.out.println("Zip Code: "+address.getZipcode());
           System.out.println("------------------------------------------");
           System.out.println("------------------------------------------");
       }
    }
    
    public static List<Customer> findAllCustomer() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerDemoPU");
        EntityManager em = emf.createEntityManager();
        List<Customer> CustomerList = (List<Customer>) em.createNamedQuery("Customer.findAll").getResultList();
        em.close();
        return CustomerList;
    }
    
    public static Address findAddressById(Long id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerDemoPU");
        EntityManager em = emf.createEntityManager();
        Address emp = em.find(Address.class, id);
        em.close();
        return emp;
    }
    
 
    public static void printCustomerByCity(String city) {
        List<Address> AddressList = findCustomerByCity("Bangkok");
                
        for(Address Address : AddressList) {
           Customer customer;
           customer = findCustomerById(Address.getId());
           
           System.out.println("First Name: "+ customer.getFirstname());
           System.out.println("Last Name: "+customer.getLastname());
           System.out.println("Email: "+customer.getEmail());
           System.out.println("Street: "+Address.getStreet());
           System.out.println("City: "+Address.getCity());
           System.out.println("Country: "+Address.getCity());
           System.out.println("Zip Code: "+Address.getZipcode());
           System.out.println("------------------------------------------");
           System.out.println("------------------------------------------");
       }
    }

    public static List<Address> findCustomerByCity(String city) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerDemoPU");
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("Address.findByCity");
        query.setParameter("city", city);
        List<Address> AddressList = (List<Address>) query.getResultList();
        em.close();
        return AddressList;
    }
    
    public static Customer findCustomerById(Long id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerDemoPU");
        EntityManager em = emf.createEntityManager();
        Customer customer = em.find(Customer.class, id);
        em.close();
        return customer;
    }
    
    public void persist(Object object) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerDemoPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}
