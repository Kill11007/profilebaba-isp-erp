package com.knackitsolutions.profilebaba.isperp.service;

import com.knackitsolutions.profilebaba.isperp.entity.BillingDetail;
import com.knackitsolutions.profilebaba.isperp.entity.Customer;
import com.knackitsolutions.profilebaba.isperp.enums.BillDuration;
import com.knackitsolutions.profilebaba.isperp.enums.BillType;
import com.knackitsolutions.profilebaba.isperp.enums.GstType;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Log4j2
public class ExcelServiceImpl implements ExcelService{

  private static final String SHEET = "customers";
  @Override
  public List<Customer> getCustomers(MultipartFile file) {

    Function<Cell, String> cellStringFunction = cell ->
        cell.getCellType() == CellType.NUMERIC ? String.valueOf(
            cell.getNumericCellValue()) : cell.getStringCellValue();

    try {
      Workbook workbook = new XSSFWorkbook(file.getInputStream());
      Sheet sheet = workbook.getSheet(SHEET);
      Iterator<Row> rows = sheet.iterator();
      List<Customer> customers = new ArrayList<>();
      int rowNumber = 0;
      while (rows.hasNext()) {
        Row currentRow = rows.next();
        // skip two header rows
        if (rowNumber <= 1) {
          rowNumber++;
          continue;
        }
        Iterator<Cell> cellsInRow = currentRow.iterator();
        Customer customer = new Customer();
        BillingDetail billingDetail = new BillingDetail();
        int cellIdx = 0;
        while (cellsInRow.hasNext()) {
          Cell currentCell = cellsInRow.next();
          log.info("Current Cell index: " + cellIdx );
          switch (cellIdx) {
            case 0:
              customer.setCustomerCode(currentCell.getStringCellValue());
              break;
            case 1:
              customer.setName(currentCell.getStringCellValue());
              break;
            case 2:
              customer.setBillingName(currentCell.getStringCellValue());
              break;
            case 3:
              customer.setPrimaryMobileNo(cellStringFunction.apply(currentCell));
              break;
            case 4:
              customer.setSecondaryMobileNo(cellStringFunction.apply(currentCell));
              break;
            case 5:
              customer.setEmail(currentCell.getStringCellValue());
              break;
            case 6:
              customer.setSecurityDeposit(BigDecimal.valueOf(currentCell.getNumericCellValue()));
              break;
            case 7:
              customer.setAddress(currentCell.getStringCellValue());
              break;
            case 8:
              customer.setGstNo(currentCell.getStringCellValue());
              break;
            case 9:
              customer.setRemark(currentCell.getStringCellValue());
              break;
            case 10:
              customer.setActive("active".equalsIgnoreCase(cellStringFunction.apply(currentCell)));
              break;
            //Billing Details From Here
            case 12:
              log.info("value: " + currentCell.getDateCellValue());
              billingDetail.setStartDate(LocalDate.ofInstant(currentCell.getDateCellValue()
                  .toInstant(), ZoneId.systemDefault()));
              break;
            case 13:
              log.info("current cell value: " + currentCell.getStringCellValue());
              billingDetail.setBillDuration(BillDuration.of(currentCell.getStringCellValue()));
              break;
            case 14:
              billingDetail.setBillDurationValue((int)currentCell.getNumericCellValue());
              break;
            case 15:
              billingDetail.setBillType(BillType.of(currentCell.getStringCellValue()));
              break;
            case 16:
              billingDetail.setGstType(GstType.of(currentCell.getStringCellValue()));
              break;
            default:
              break;
          }
          cellIdx++;
        }
        customer.setBillingDetail(billingDetail);
        customers.add(customer);
      }
      workbook.close();
      return customers;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse excel file: " + e.getMessage());
    }
  }

  @Override
  public Resource download(List<Customer> customers) {
    return null;
  }
}
