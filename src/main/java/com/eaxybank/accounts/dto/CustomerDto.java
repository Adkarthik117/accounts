package com.eaxybank.accounts.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
public class CustomerDto {

    @NotEmpty(message="NAME SHOULD NOT BE A NULL")
    @Size(min=3,max=30,message="Name should be in the length of 3 to 30")
    private String name;
    @NotEmpty(message="email should not be an empty")
    @Email(message="email should be valid")
    private String email;
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Length of the mobile number is 10 ")
    private String mobileNumber;
    private AccountsDto accountsDto;
}
