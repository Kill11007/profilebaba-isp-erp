package com.knackitsolutions.profilebaba.isperp.service;

public interface EncryptionService {
  String encrypt(String strToEncrypt, String secret, String salt);
  String decrypt(String strToDecrypt, String secret, String salt);
}
