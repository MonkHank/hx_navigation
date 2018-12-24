package com.seuic.hisense.utils;

import android.util.Base64;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class MD5UtilNew
{
  private static final String[] hexDigits = { "0", "1", "2", "3", "4", "5", 
    "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

  public static boolean checkSign(String data_digest, String logistics_interface, String key, String charset)
  {
//    if (StringUtils.isEmpty(data_digest))
	  if(data_digest == null)
      return false;
    try
    {
      String sign = new String(Base64.encode(code32(
              logistics_interface + key, charset).getBytes(charset),Base64.DEFAULT));
      if (sign.equals(data_digest)) {
        return true;
      }
      return false;
    }
    catch (UnsupportedEncodingException e)
    {
      e.printStackTrace();
    }return false;
  }

  public static void main(String[] args) {
    String json = "123456";
    String key = "test";
    System.out.println(doSign(json, key, "UTF-8"));
  }

  public static String doSign(String logistics_interface, String key, String charset)
  {
    try
    {   // MD5Util.doSign(this.contentvalue, this.key, "UTF-8")
      return new String(Base64.encode(code32(
        logistics_interface + key, charset).getBytes(charset),Base64.DEFAULT));
    }
    catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }return null;
  }

  public static String code32(String origin, String charset)
  {
    String resultString = null;
    try {
      resultString = new String(origin);
      MessageDigest md = MessageDigest.getInstance("MD5");
      if (charset == null)
        resultString = byteArrayToHexString(md.digest(resultString
          .getBytes()));
      else
        resultString = byteArrayToHexString(md.digest(resultString
          .getBytes(charset)));
    }
    catch (Exception localException) {
    }
    return resultString;
  }

  private static String byteArrayToHexString(byte[] b) {
    StringBuffer resultSb = new StringBuffer();
    for (int i = 0; i < b.length; i++) {
      resultSb.append(byteToHexString(b[i]));
    }

    return resultSb.toString();
  }

  private static String byteToHexString(byte b) {
    int n = b;
    if (n < 0)
      n += 256;
    int d1 = n / 16;
    int d2 = n % 16;
    return hexDigits[d1] + hexDigits[d2];
  }
}