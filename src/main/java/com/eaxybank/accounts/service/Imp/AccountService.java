package com.eaxybank.accounts.service.Imp;
import com.eaxybank.accounts.constants.AccountsConstants;
import com.eaxybank.accounts.dto.AccountsDto;
import com.eaxybank.accounts.dto.CustomerDto;
import com.eaxybank.accounts.entity.Accounts;
import com.eaxybank.accounts.entity.Customer;
import com.eaxybank.accounts.exceptions.CustomerAlreadyExistsException;
import com.eaxybank.accounts.exceptions.ResourceNotFoundException;
import com.eaxybank.accounts.mapper.AccountsMapper;
import com.eaxybank.accounts.mapper.CustomerMapper;
import com.eaxybank.accounts.repository.AccountsRepository;
import com.eaxybank.accounts.repository.CustomerRepository;
import com.eaxybank.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
@Service
@AllArgsConstructor
public class AccountService implements IAccountService {


    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    @Override
    public void createCustomer(CustomerDto customerDto) {
        Customer customer= CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer>optionalCustomer=customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already exists"+customerDto.getMobileNumber());
        }
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Anonymous");
        Customer savedCustomer=customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    private  Accounts createNewAccount(Customer customer){
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccountNumber = 10000000000L + new Random().nextLong(900000000);
        newAccount.setAccountNumber(randomAccountNumber);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy("Anonymous");
        return newAccount;
    }
    @Override
    public CustomerDto fetchCustomerDetails(String mobileNumber) {
        Customer customer=customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()->new ResourceNotFoundException("Customer", "MobileNumber", mobileNumber)
        );
        Accounts accounts=accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                ()->new ResourceNotFoundException("Account","CustomerId",customer.getCustomerId().toString())
        );
        CustomerDto customerDto=CustomerMapper.mapToCustomerDto(customer,new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts,new AccountsDto()));
        return customerDto;
    }
    @Override
    public boolean updateCustomer(CustomerDto customerDto) {
        boolean isUpdated=false;
        AccountsDto accountsDto=customerDto.getAccountsDto();
        if(accountsDto!=null){
            Accounts accounts=accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    ()->new ResourceNotFoundException("Account","AccountNumber",accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountsDto,accounts);
            accounts=accountsRepository.save(accounts);
            Long customerId=accounts.getCustomerId();
            Customer customer=customerRepository.findById(customerId).orElseThrow(
                    ()->new ResourceNotFoundException("Customer","CustomerId",customerId.toString())
            );

            CustomerMapper.mapToCustomer(customerDto,customer);
            customer=customerRepository.save(customer);
            isUpdated=true;
        }

        return isUpdated;
    }
    @Override
    public boolean deleteCustomer(String mobileNumber) {
        Customer customer=customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()->new ResourceNotFoundException("Customer","MobileNumber",mobileNumber.toString())
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.delete(customer);
        return true;
    }
}
