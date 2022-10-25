package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.config.TenantContext;
import com.knackitsolutions.profilebaba.isperp.entity.main.Tenant;
import com.knackitsolutions.profilebaba.isperp.exception.TenantCreationException;
import com.knackitsolutions.profilebaba.isperp.repository.main.TenantRepository;
import com.knackitsolutions.profilebaba.isperp.service.EncryptionService;
import com.knackitsolutions.profilebaba.isperp.service.TenantManagementService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.stereotype.Service;

@Service
public class TenantManagementServiceImpl implements TenantManagementService {

  private static final String VALID_DATABASE_NAME_REGEXP = "[A-Za-z0-9_]*";
  private static final String TENANT_ID_PREFIX = "T-";
  private final EncryptionService encryptionService;
  private final DataSource dataSource;
  private final JdbcTemplate jdbcTemplate;
  private final ResourceLoader resourceLoader;
  private final TenantRepository tenantRepository;

  private final String urlPrefix;
  private final String secret;
  private final String salt;


  @Autowired
  public TenantManagementServiceImpl(EncryptionService encryptionService, DataSource dataSource,
      JdbcTemplate jdbcTemplate,
      ResourceLoader resourceLoader, TenantRepository tenantRepository,
      @Value("${multitenancy.tenant.datasource.url-prefix}") String urlPrefix,
      @Value("${encryption.secret}") String secret, @Value("${encryption.salt}") String salt) {
    this.encryptionService = encryptionService;
    this.dataSource = dataSource;
    this.jdbcTemplate = jdbcTemplate;
    this.resourceLoader = resourceLoader;
    this.tenantRepository = tenantRepository;
    this.urlPrefix = urlPrefix;
    this.secret = secret;
    this.salt = salt;
  }

  @Override
  public Tenant createTenant(String tenantId, String db, String password) {
    tenantId = TENANT_ID_PREFIX + tenantId;
    if (!db.matches(VALID_DATABASE_NAME_REGEXP)) {
      throw new TenantCreationException("Invalid db name: " + db);
    }

    String url = urlPrefix+db;
    String encryptedPassword = encryptionService.encrypt(password, secret, salt);
    try {
      createDatabase(db, password);
    } catch (DataAccessException e) {
      throw new TenantCreationException("Error when creating db: " + db, e);
    }
    try (Connection connection = DriverManager.getConnection(url, db, password)) {
      DataSource tenantDataSource = new SingleConnectionDataSource(connection, false);
      createTables(tenantDataSource);
    } catch (SQLException e) {
      throw new TenantCreationException("Error when populating db: ", e);
    }
    Tenant tenant = Tenant.builder()
        .tenantId(tenantId)
        .db(db)
        .url(url)
        .password(encryptedPassword)
        .build();
    return tenantRepository.save(tenant);
  }

  private void createDatabase(String db, String password) {
    jdbcTemplate.execute((StatementCallback<Boolean>) stmt ->
        stmt.execute("CREATE DATABASE " + db));
    jdbcTemplate.execute((StatementCallback<Boolean>) stmt ->
        stmt.execute("CREATE USER " + db + "@'localhost' IDENTIFIED BY '" + password + "'"));
    jdbcTemplate.execute((StatementCallback<Boolean>) stmt ->
        stmt.execute("GRANT ALL PRIVILEGES ON " + db + ".* TO " + db + "@'localhost'"));
  }

  protected void createTables(DataSource dataSource) {
    ResourceLoader loader = new DefaultResourceLoader();
    Resource resource = loader.getResource("isp_db.sql");
    ResourceDatabasePopulator populate = new ResourceDatabasePopulator(resource);
    populate.execute(dataSource);
  }

  @Override
  public List<Tenant> allTenants() {
    return tenantRepository.findAll();
  }
}
