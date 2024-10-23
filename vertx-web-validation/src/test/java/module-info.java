/*
 * Copyright 2024 Red Hat, Inc.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  and Apache License v2.0 which accompanies this distribution.
 *
 *  The Eclipse Public License is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  The Apache License v2.0 is available at
 *  http://www.opensource.org/licenses/apache2.0.php
 *
 *  You may elect to redistribute this code under either of these licenses.
 */
open module io.vertx.web.validation.tests {
  requires static io.vertx.codegen.api;
  requires io.netty.codec.http;
  requires io.vertx.testing.junit5;
  requires io.vertx.core;
  requires io.vertx.jsonschema;
  requires io.vertx.web;
  requires io.vertx.web.client;
  requires io.vertx.web.common;
  requires io.vertx.web.validation;
  requires org.assertj.core;
  requires org.junit.jupiter.api;
  requires org.mockito;
  requires org.mockito.junit.jupiter;
  exports io.vertx.ext.web.validation.tests.testutils;
  exports io.vertx.ext.web.validation.tests;
}