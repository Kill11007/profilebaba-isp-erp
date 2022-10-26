package com.knackitsolutions.profilebaba.isperp.service;

import com.knackitsolutions.profilebaba.isperp.dto.CustomerFollowUpDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.CustomerFollowUp;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerNotFoundException;
import java.util.List;
import java.util.function.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerFollowUpService {

  List<CustomerFollowUp> all();
  Page<CustomerFollowUp> all(Pageable pageable);

  List<CustomerFollowUpDTO> allDTOs();
  Page<CustomerFollowUpDTO> allDTOs(Pageable pageable);

  CustomerFollowUp one(Long customerId);

  CustomerFollowUpDTO oneDTO(Long customerId);

  CustomerFollowUp newFollowUp(CustomerFollowUpDTO dto) throws CustomerNotFoundException;
  CustomerFollowUp updateFollowUp(CustomerFollowUpDTO dto);

  CustomerFollowUp oneById(Long id) throws CustomerFollowUpNotFoundException;

  CustomerFollowUpDTO oneDTOById(Long id) throws CustomerFollowUpNotFoundException;

  void deleteFollowUp(Long id);

  class CustomerFollowUpNotFoundException extends RuntimeException{
    private static final String cause = "Resource not found in db";
    private static final String defaultMessage = "Customer not present in database. Please provide valid customer.";

    private static final Supplier<Throwable> defaultCause = () -> new Throwable(cause);

    public CustomerFollowUpNotFoundException() {
      super(defaultMessage, defaultCause.get());
    }

    public CustomerFollowUpNotFoundException(String message) {
      super(message, defaultCause.get());
    }
  }
}
