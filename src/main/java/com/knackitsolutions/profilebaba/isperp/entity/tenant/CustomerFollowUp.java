package com.knackitsolutions.profilebaba.isperp.entity.tenant;

import com.knackitsolutions.profilebaba.isperp.dto.CustomerFollowUpDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.hibernate.Hibernate;

@Entity
@Table(name = "customer_follow_up")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CustomerFollowUp {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "follow_up_date")
  private LocalDate followUpDate;

  @OneToOne
  @JoinColumn(name = "customer_id")
  @Exclude
  private Customer customer;

  @Column(name = "updated_date")
  private LocalDateTime updatedDate;

  private String reason;

  public CustomerFollowUp(CustomerFollowUpDTO dto) {
    this.setFollowUpDate(dto.getFollowUpDate());
    this.setReason(dto.getReason());
    this.setUpdatedDate(LocalDateTime.now());
  }

  public void update(CustomerFollowUpDTO dto) {
    this.setFollowUpDate(dto.getFollowUpDate());
    this.setReason(dto.getReason());
    this.setUpdatedDate(LocalDateTime.now());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    CustomerFollowUp that = (CustomerFollowUp) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
