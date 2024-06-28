package com.knackitsolutions.profilebaba.isperp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IspQuery {
  private String phone;
  private Long vendorId;
  private Long userId;
  private String tenantId;
  private String businessName;
  private Pageable pageable;

  private static final int DEFAULT_PAGE_SIZE = 10;
  private static final int DEFAULT_PAGE_NUMBER = 0;
  private static final Sort.Direction DEFAULT_SORT_ORDER_DIRECTION = Direction.ASC;
  private static final String DEFAULT_SORT_ATTRIBUTE = "id";

  public IspSpecification toSpecification() {
    return new IspSpecification(this);
  }

  public Pageable getPageable() {
    if (this.pageable != null) {
      return pageable;
    }
    return PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE,
        Sort.by(DEFAULT_SORT_ORDER_DIRECTION, DEFAULT_SORT_ATTRIBUTE));
  }
}
