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

package com.applivery.applvsdklib.tools.permissions.utils;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 18/1/16.
 */
public class StubString {
  public static final int FAKE_RES = 3;

  public int getPermissionDeniedFeedback() {
    return FAKE_RES;
  }

  public int getPermissionRationaleMessage() {
    return FAKE_RES;
  }

  public int getPermissionRationaleTitle() {
    return FAKE_RES;
  }
}
