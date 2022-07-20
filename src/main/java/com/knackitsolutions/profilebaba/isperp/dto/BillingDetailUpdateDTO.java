package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.enums.BillDuration;
import com.knackitsolutions.profilebaba.isperp.enums.BillType;
import com.knackitsolutions.profilebaba.isperp.enums.GstType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillingDetailUpdateDTO {
  private BillDuration billDuration;
  private Integer billDurationValue;
  private BillType billType;
  private GstType gstType;
}
