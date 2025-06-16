package com.eaxybank.accounts.service;
import com.eaxybank.accounts.dto.CustomerDto;
public interface IAccountService {

    void createCustomer(CustomerDto customerDto);

    CustomerDto fetchCustomerDetails(String mobileNumber);

    boolean updateCustomer(CustomerDto customerDto);

    boolean deleteCustomer(String mobileNumber);
}
