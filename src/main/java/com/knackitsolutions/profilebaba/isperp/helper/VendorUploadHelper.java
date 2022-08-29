package com.knackitsolutions.profilebaba.isperp.helper;

import com.knackitsolutions.profilebaba.isperp.entity.main.Vendor;
import java.io.File;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class VendorUploadHelper {

  @Value("${file.upload.location}")
  private String fileUploadLocation;

  public void createVendorDirectory(Vendor vendor) {
    String directoryName = fileUploadLocation.concat(String.valueOf(vendor.getId()));
    log.info("DirectoryName " + directoryName);
    File directory = new File(directoryName);
    if (!directory.exists()) {
      boolean mkdirs = directory.mkdirs();
      if (mkdirs) {
        log.info("Vendor directory is created.");
      }else{
        throw new RuntimeException("Vendor directory cannot be created.");
      }
    }
  }
}
