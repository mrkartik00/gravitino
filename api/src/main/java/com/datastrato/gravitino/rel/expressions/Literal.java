/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Referred from Apache Spark's connector/catalog implementation
// sql/catalyst/src/main/java/org/apache/spark/sql/connector/expressions/Literal.java

package com.datastrato.gravitino.rel.expressions;

import io.substrait.type.Type;
import io.substrait.type.TypeCreator;
import java.util.Objects;

/**
 * Represents a constant literal value in the public expression API.
 *
 * @param <T> the JVM type of value held by the literal
 */
public interface Literal<T> extends Expression {
  /** Returns the literal value. */
  T value();

  /** Returns the data type of the literal. */
  Type dataType();

  @Override
  default Expression[] children() {
    return EMPTY_EXPRESSION;
  }

  /**
   * Creates a literal with the given value and data type.
   *
   * @param value the literal value
   * @param dataType the data type of the literal
   * @return a new {@link Literal} instance
   * @param <T> the JVM type of value held by the literal
   */
  static <T> LiteralImpl<T> of(T value, Type dataType) {
    return new LiteralImpl<>(value, dataType);
  }

  /**
   * Creates an integer literal with the given value.
   *
   * @param value the integer literal value
   * @return a new {@link Literal} instance
   */
  static LiteralImpl<Integer> integer(Integer value) {
    return of(value, TypeCreator.REQUIRED.I32);
  }

  /**
   * Creates a string literal with the given value.
   *
   * @param value the string literal value
   * @return a new {@link Literal} instance
   */
  static LiteralImpl<String> string(String value) {
    return of(value, TypeCreator.REQUIRED.STRING);
  }

  final class LiteralImpl<T> implements Literal<T> {
    private final T value;
    private final Type dataType;

    private LiteralImpl(T value, Type dataType) {
      this.value = value;
      this.dataType = dataType;
    }

    @Override
    public T value() {
      return value;
    }

    @Override
    public Type dataType() {
      return dataType;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      LiteralImpl<?> literal = (LiteralImpl<?>) o;
      return Objects.equals(value, literal.value) && Objects.equals(dataType, literal.dataType);
    }

    @Override
    public int hashCode() {
      return Objects.hash(value, dataType);
    }
  }
}