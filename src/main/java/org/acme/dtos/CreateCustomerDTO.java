package org.acme.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CreateCustomerDTO {
  @NotNull
  @NotEmpty
  public String name;
  @NotNull
  @NotEmpty
  public String password;
}
