/*
 * Copyright 2023 Datastrato.
 * This software is licensed under the Apache License version 2.
 */

package com.datastrato.util;

import com.datastrato.graviton.util.ByteUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestByteUtils {

  @Test
  public void testIntToByte() {
    int v = 258;
    byte[] b = ByteUtils.intToByte(v);
    Assertions.assertArrayEquals(new byte[] {0x00, 0x00, 0x01, 0x02}, b);
    int v2 = ByteUtils.byteToInt(b);
    assert v == v2;
  }

  @Test
  public void testLongToByte() {
    long v = 259;
    byte[] b = ByteUtils.longToByte(v);
    Assertions.assertArrayEquals(new byte[] {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x03}, b);
    long v2 = ByteUtils.byteToLong(b);
    assert v == v2;
  }
}
