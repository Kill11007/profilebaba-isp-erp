package com.knackitsolutions.profilebaba.isperp.service;

import com.knackitsolutions.profilebaba.isperp.entity.tenant.Customer;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ExcelService {
  List<Customer> getCustomers(MultipartFile file);

  Resource download(List<Customer> customers);
}
