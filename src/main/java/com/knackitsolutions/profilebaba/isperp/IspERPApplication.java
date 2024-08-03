package com.knackitsolutions.profilebaba.isperp;

import com.knackitsolutions.profilebaba.isperp.entity.main.IspPlan;
import com.knackitsolutions.profilebaba.isperp.entity.main.UserRoleFeature;
import com.knackitsolutions.profilebaba.isperp.enums.PlanType;
import com.knackitsolutions.profilebaba.isperp.enums.UserType;
import com.knackitsolutions.profilebaba.isperp.repository.main.IspPlanRepository;
import com.knackitsolutions.profilebaba.isperp.repository.main.PermissionRepository;
import com.knackitsolutions.profilebaba.isperp.repository.main.UserRoleFeatureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableTransactionManagement
@EnableAsync
@EnableCaching
@EnableScheduling
@RequiredArgsConstructor
public class IspERPApplication implements ApplicationRunner {

    private final UserRoleFeatureRepository userRoleFeatureRepository;
	private final PermissionRepository permissionRepository;
	@Value("${customer.permissions:5}")
	private List<Long> customerPermission = new ArrayList<>();
	@Value("${employee.permissions:1,4,10}")
	private List<Long> employeePermission = new ArrayList<>();

	public static void main(String[] args) {
		SpringApplication.run(IspERPApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		createUserRoleFeaturesIfNotExists();
	}

	private void createUserRoleFeaturesIfNotExists() {
		List<UserRoleFeature> userRoleFeatures = userRoleFeatureRepository.findAll();
		long customerFeatures = userRoleFeatures.stream().filter(userRoleFeature -> userRoleFeature.getUserType() == UserType.CUSTOMER).count();
		long employeeFeatures = userRoleFeatures.stream().filter(userRoleFeature -> userRoleFeature.getUserType() == UserType.EMPLOYEE).count();
		if(customerFeatures <= 0){
			customerPermission.stream()
					.map(permissionRepository::findById)
					.flatMap(Optional::stream)
					.map(permission -> new UserRoleFeature(UserType.CUSTOMER, permission))
					.forEach(userRoleFeatureRepository::save);
		}
		if (employeeFeatures <= 0) {
			employeePermission.stream()
					.map(permissionRepository::findById)
					.flatMap(Optional::stream)
					.map(permission -> new UserRoleFeature(UserType.EMPLOYEE, permission))
					.forEach(userRoleFeatureRepository::save);
		}
	}
}
