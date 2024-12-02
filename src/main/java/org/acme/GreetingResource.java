package org.acme;

import java.util.List;

import org.acme.dtos.CreateCustomerDTO;
import org.acme.entities.Customer;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class GreetingResource {

  @GET
  @Path("/hello")
  @Produces(MediaType.TEXT_PLAIN)
  public String hello() {
    return "Hello from Quarkus REST";
  }

  @GET
  @Path("/customers")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Customer> getCustomers() {
    return Customer.findAll().list();
  }

  @POST
  @Path("/customers")
  @Transactional
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Customer createCustomer(@Valid CreateCustomerDTO dto) {
    var customer = new Customer(dto.name, dto.password);
    customer.persist();
    return customer;
  }

}
