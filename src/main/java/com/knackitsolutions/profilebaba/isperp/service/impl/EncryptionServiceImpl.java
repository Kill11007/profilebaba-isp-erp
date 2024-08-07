package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.service.EncryptionService;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class EncryptionServiceImpl implements EncryptionService {

  public static final String HASH_ALGORITHM = "PBKDF2WithHmacSHA256";
  public static final String CIPHER = "AES/CBC/PKCS5Padding";
  public static final String KEY_ALGORITHM = "AES";
  public static final int ITERATION_COUNT = 65536;
  public static final int KEY_LENGTH = 256;
  @Value("${encryption.secret}")
  private String secret;
  @Value("${encryption.salt}")
  private String salt;
  @Override
  public String encrypt(String strToEncrypt) {
    try {
      byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
      IvParameterSpec ivspec = new IvParameterSpec(iv);

      SecretKeyFactory factory = SecretKeyFactory.getInstance(HASH_ALGORITHM);
      KeySpec spec = new PBEKeySpec(secret.toCharArray(), salt.getBytes(), ITERATION_COUNT,
          KEY_LENGTH);
      SecretKey tmp = factory.generateSecret(spec);
      SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), KEY_ALGORITHM);

      Cipher cipher = Cipher.getInstance(CIPHER);
      cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
      return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
    } catch (Exception e) {
      log.error("Error while encrypting: ", e);
      return null;
    }
  }

  @Override
  public String decrypt(String strToDecrypt) {
    try {
      byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
      IvParameterSpec ivspec = new IvParameterSpec(iv);

      SecretKeyFactory factory = SecretKeyFactory.getInstance(HASH_ALGORITHM);
      KeySpec spec = new PBEKeySpec(secret.toCharArray(), salt.getBytes(), ITERATION_COUNT,
          KEY_LENGTH);
      SecretKey tmp = factory.generateSecret(spec);
      SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), KEY_ALGORITHM);

      Cipher cipher = Cipher.getInstance(CIPHER);
      cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
      return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
    } catch (Exception e) {
      log.error("Error while decrypting: ", e);
      return null;
    }
  }
}
