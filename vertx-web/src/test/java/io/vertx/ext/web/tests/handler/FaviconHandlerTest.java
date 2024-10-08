/*
 * Copyright 2014 Red Hat, Inc.
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

package io.vertx.ext.web.tests.handler;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.handler.FaviconHandler;
import io.vertx.ext.web.tests.WebTestBase;
import org.junit.Test;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class FaviconHandlerTest extends WebTestBase {

  @Test
  public void testFaviconClasspath() throws Exception {
    testFaviconPath(FaviconHandler.create(vertx), FaviconHandler.DEFAULT_MAX_AGE_SECONDS);
  }

  @Test
  public void testFaviconPath() throws Exception {
    String path = "src/test/resources/favicon.ico";
    testFaviconPath(FaviconHandler.create(vertx, path), FaviconHandler.DEFAULT_MAX_AGE_SECONDS);
  }

  @Test
  public void testFaviconPathMaxAge() throws Exception {
    String path = "src/test/resources/favicon.ico";
    long maxAge = FaviconHandler.DEFAULT_MAX_AGE_SECONDS * 2;
    testFaviconPath(FaviconHandler.create(vertx, path, maxAge), maxAge);
  }

  @Test
  public void testFaviconMaxAge() throws Exception {
    long maxAge = FaviconHandler.DEFAULT_MAX_AGE_SECONDS * 2;
    testFaviconPath(FaviconHandler.create(vertx, maxAge), maxAge);
  }

  private void testFaviconPath(FaviconHandler favicon, long maxAgeSeconds) throws Exception {
    router.route().handler(favicon);
    router.route().handler(rc -> rc.response().end());
    Buffer icon = vertx.fileSystem().readFileBlocking("favicon.ico");
    testRequestBuffer(HttpMethod.GET, "/favicon.ico", null, resp -> {
      assertEquals("image/x-icon", resp.headers().get("content-type"));
      assertEquals(icon.length(), Integer.valueOf(resp.headers().get("content-length")).intValue());
      assertEquals("public, max-age=" + maxAgeSeconds, resp.headers().get("cache-control"));
    }, 200, "OK", icon);
  }

  @Test
  public void testUnresolvedFavicon() throws Exception {
    String path = "does/not/exist";
    router.route().handler(FaviconHandler.create(vertx, path));
    router.route().handler(rc -> rc.response().end());
    testRequestBuffer(HttpMethod.GET, "/favicon.ico", null, resp -> {
    }, 404, "Not Found", Buffer.buffer());
  }

  @Test
  public void testDefaultIcon() throws Exception {
    String path = "META-INF/vertx/web/favicon.ico";
    router.route().handler(FaviconHandler.create(vertx, path));
    router.route().handler(rc -> rc.response().end());
    testRequest(HttpMethod.GET, "/favicon.ico", 200, "OK");
  }
}
