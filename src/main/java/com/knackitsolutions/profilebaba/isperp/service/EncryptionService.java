package com.knackitsolutions.profilebaba.isperp.service;

public interface EncryptionService {
  String encrypt(String strToEncrypt);
  String decrypt(String strToDecrypt);
}
