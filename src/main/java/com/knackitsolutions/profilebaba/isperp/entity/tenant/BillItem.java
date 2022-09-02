package com.knackitsolutions.profilebaba.isperp.entity.tenant;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "bill_items")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "bill_id")
  private Bill bill;

  private String name;
  private Long itemId;
  private Integer quantity;
  private String hsnCode;
  private BigDecimal discount;
  private BigDecimal additionalCharges;
  private Integer gstPercent;
  private BigDecimal gstAmount;
  private BigDecimal amount;
  private LocalDateTime createdDate;

}
