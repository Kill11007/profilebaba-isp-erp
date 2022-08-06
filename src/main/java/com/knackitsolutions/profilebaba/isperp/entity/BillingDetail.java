package com.knackitsolutions.profilebaba.isperp.entity;

import com.knackitsolutions.profilebaba.isperp.dto.BillingDetailDTO;
import com.knackitsolutions.profilebaba.isperp.enums.BillDuration;
import com.knackitsolutions.profilebaba.isperp.enums.BillType;
import com.knackitsolutions.profilebaba.isperp.enums.GstType;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "billing_details")
@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillingDetail {

  @Id
  @Column(name = "customer_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long customerId;
  @OneToOne
  @JoinColumn(name = "customer_id", referencedColumnName = "id")
  @MapsId
  private Customer customer;

  @Column(nullable = false)
  private LocalDate startDate;

  @Column(nullable = false)
  private BigDecimal openingOutstandingBalance = new BigDecimal(0);

  @Column(nullable = false)
  private BigDecimal openingAdvanceBalance = new BigDecimal(0);

  private BigDecimal monthlyAdditionalCharge = new BigDecimal(0);
  private BigDecimal monthlyDiscount = new BigDecimal(0);

  @Column(columnDefinition = "ENUM('EOEM', 'DAYS', 'MONTHS')")
  private BillDuration billDuration;

  private Integer billDurationValue;

  @Column(columnDefinition = "ENUM('PRE_PAID', 'POST_PAID')")
  private BillType billType;

  @Column(columnDefinition = "ENUM('NA', 'IGST', 'CGST_SGST')")
  private GstType gstType;

  public void update(BillingDetailDTO dto) {
    this.setBillDuration(dto.getBillDuration());
    this.setBillDurationValue(dto.getBillDurationValue());
    this.setBillType(dto.getBillType());
    this.setGstType(dto.getGstType());
  }

  //without id and customer
  public BillingDetail(BillingDetailDTO dto) {
    if (dto == null) {
      return;
    }
    setStartDate(dto.getStartDate());
    setOpeningAdvanceBalance(dto.getOpeningAdvanceBalance());
    setOpeningOutstandingBalance(dto.getOpeningOutstandingBalance());
    setMonthlyAdditionalCharge(dto.getMonthlyAdditionalCharge());
    setMonthlyDiscount(dto.getMonthlyDiscount());
    setBillDuration(dto.getBillDuration());
    setBillDurationValue(dto.getBillDurationValue());
    setBillType(dto.getBillType());
    setGstType(dto.getGstType());
  }
}
