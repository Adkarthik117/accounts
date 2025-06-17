package com.eaxybank.accounts.audit;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component("auditAwareImp")
public class AuditAwareImp implements AuditorAware<String>{
    @Override
    public Optional getCurrentAuditor() {
        return Optional.of("ACCOUNTS_MS");
    }
}
