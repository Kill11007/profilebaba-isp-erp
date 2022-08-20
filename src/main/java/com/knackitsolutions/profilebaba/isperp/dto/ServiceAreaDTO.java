package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.ServiceArea;
import com.knackitsolutions.profilebaba.isperp.exception.ServiceAreaNotFoundException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceAreaDTO {
  private Long id;
  @NotBlank @NotNull
  private String name;

  public ServiceAreaDTO(ServiceArea entity){
    if (entity == null) {
      return;
    }
    this.id = entity.getId();
    this.name = entity.getName();
  }
}
