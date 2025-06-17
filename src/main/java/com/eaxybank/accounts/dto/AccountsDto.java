package com.eaxybank.accounts.dto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
@Data
public class AccountsDto {

    @NotEmpty(message="Account should not be an empty")
    private String accountType;
    @Pattern(regexp="(^$|[0-9]{10})",message="accountNumber should not be null")
    private Long accountNumber;
    @NotEmpty(message="branch address should not be null")
    private String branchAddress;
}
