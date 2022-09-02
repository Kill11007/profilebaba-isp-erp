package com.knackitsolutions.profilebaba.isperp.entity.tenant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonValue;
import com.knackitsolutions.profilebaba.isperp.dto.SubscriptionDTO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import lombok.ToString.Exclude;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Table(name = "subscriptions")
@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @Exclude
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @ManyToOne(fetch = FetchType.LAZY)
  @Exclude
  @JoinColumn(name = "plan_id")
  private Plan plan;

  @Column(name = "fixed_bill_amount")
  private BigDecimal fixedBillAmountPerMonth;

  private int quantity;

  private Boolean futureDays;

  private SubscriptionStatus status;

  // No of months
  private int period;

  private LocalDate startDate;
  private LocalDate endDate;

  public enum SubscriptionStatus{

    ACTIVE("ACTIVE"), IN_ACTIVE("IN_ACTIVE");
    @JsonValue
    @Getter
    private final String status;

    SubscriptionStatus(String status) {
      this.status = status;
    }

    public static SubscriptionStatus of(String status) {
      String s = Optional.ofNullable(status).orElse("");
      return Arrays.stream(SubscriptionStatus.values())
          .filter(subscriptionStatus -> subscriptionStatus.getStatus().equalsIgnoreCase(s))
          .findFirst().orElse(IN_ACTIVE);
    }
  }

  //Plan is not updating from here
  public void update(SubscriptionDTO dto) {
    setFixedBillAmountPerMonth(dto.getFixedBillAmountPerMonth());
    setEndDate(dto.getEndDate());
    setStartDate(dto.getStartDate());
    setPeriod(dto.getPeriod());
    setFutureDays(dto.getFutureDays());
    setStatus(dto.getStatus());
    setQuantity(dto.getQuantity());
  }

  public Subscription(SubscriptionDTO dto, Customer customer) {
    setQuantity(dto.getQuantity());
    setFixedBillAmountPerMonth(dto.getFixedBillAmountPerMonth());
    setStatus(Optional.ofNullable(dto.getStatus()).orElse(SubscriptionStatus.ACTIVE));
    setPeriod(dto.getPeriod());
    setCustomer(customer);
    setFutureDays(dto.getFutureDays() == null || dto.getFutureDays());
    setStartDate(dto.getStartDate());
    setEndDate(dto.getEndDate());
  }


}
