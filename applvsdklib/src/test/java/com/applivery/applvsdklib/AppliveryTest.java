/*
 * Copyright (c) 2016 Applivery
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.applivery.applvsdklib;

import android.app.Application;
import android.content.Context;
import com.applivery.applvsdklib.domain.exceptions.AppliverySdkNotInitializedException;
import com.applivery.applvsdklib.domain.exceptions.NotForegroundActivityAvailable;
import java.util.concurrent.Executor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 24/1/16.
 * //TODO, redesign test suite, right now is not really useful
 */
@RunWith(MockitoJUnitRunner.class) public class AppliveryTest {

  @Mock Application app;
  @Mock Context context;
  @Mock Executor executor;

  private String appId = "kjkjfk9923ioiekj3qe9id";
  private String clientId = ".sfmkansdjnariqu3erijefncix";

  @Test public void shouldInitializeAllVariblesInSdkWhenContextIsNotNull() {
    when(app.getApplicationContext()).thenReturn(context);
    AppliverySdk.setExecutor(executor);

    Applivery.init(app, clientId, false);

    try {
      assertThat(AppliverySdk.getApplicationContext(), instanceOf(Context.class));
      assertThat(AppliverySdk.isStoreRelease(), is(false));
      assertThat(AppliverySdk.isInitialized(), is(true));
      assertNotNull(AppliverySdk.getPermissionRequestManager());

      AppliverySdk.cleanAllStatics();
    } catch (AppliverySdkNotInitializedException s) {
      assertNotNull(s);
    }
  }

  @Test(expected = AppliverySdkNotInitializedException.class)
  public void shouldBeAllStacticsNotInitializedAfterCallCleanAllStatics() {
    when(app.getApplicationContext()).thenReturn(context);
    AppliverySdk.setExecutor(executor);

    Applivery.init(app, clientId, false);
    AppliverySdk.cleanAllStatics();

    assertNull(AppliverySdk.getApplicationContext());
  }

  @Test public void shouldThrowExceptionWhenContextIsNull() {
    when(app.getApplicationContext()).thenReturn(null);
    Applivery.init(app, clientId, false);
  }

  @Test public void shouldNotInitializeSdkTwice() {
    Application mockApp = mock(Application.class);
    when(app.getApplicationContext()).thenReturn(context);
    AppliverySdk.setExecutor(executor);

    Applivery.init(app, clientId, false);
    Applivery.init(mockApp, clientId, false);
    verify(mockApp, never()).getApplicationContext();

    AppliverySdk.cleanAllStatics();
  }

  @Test public void shouldNotHaveForegroundActivityInjUnitContext() {
    when(app.getApplicationContext()).thenReturn(context);
    AppliverySdk.setExecutor(executor);

    try {
      Applivery.init(app, clientId, false);
      assertFalse(AppliverySdk.isContextAvailable());
    } catch (AppliverySdkNotInitializedException s) {
      assertNotNull(s);
    }

    try {
      AppliverySdk.getCurrentActivity();
    } catch (NotForegroundActivityAvailable na) {
      assertNotNull(na);
    } catch (AppliverySdkNotInitializedException s) {
      assertNotNull(s);
    }

    AppliverySdk.cleanAllStatics();
  }
}
