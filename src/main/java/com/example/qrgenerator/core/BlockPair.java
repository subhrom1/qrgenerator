package com.example.qrgenerator.core;

/**
 * POJO class which saves data and error correction
 * bytes of QR code.
 */
final class BlockPair {

  private final byte[] dataBytes;
  private final byte[] errorCorrectionBytes;

  BlockPair(byte[] data, byte[] errorCorrection) {
    dataBytes = data;
    errorCorrectionBytes = errorCorrection;
  }

  public byte[] getDataBytes() {
    return dataBytes;
  }

  public byte[] getErrorCorrectionBytes() {
    return errorCorrectionBytes;
  }

}
