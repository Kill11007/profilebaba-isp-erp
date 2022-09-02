package com.knackitsolutions.profilebaba.isperp.entity.tenant;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;

@Entity
@Table(name = "bills")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bill {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "bill_no")
  private String billNumber;
  private LocalDate invoiceDate;
  private LocalDate fromDate;
  private LocalDate toDate;
  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;
  private BigDecimal total;

  @OneToMany(mappedBy = "bill")
  @Exclude
  private List<BillItem> billItems;
  private LocalDate updatedDate;
}
