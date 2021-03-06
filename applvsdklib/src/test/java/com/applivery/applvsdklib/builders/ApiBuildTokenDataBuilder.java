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

package com.applivery.applvsdklib.builders;

import com.applivery.applvsdklib.network.api.model.ApiBuildTokenData;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 2/1/16.
 */
public class ApiBuildTokenDataBuilder {

    public static final String BUILD_TOKEN = "dflnasdjnaejw49u93ienfdmasnf32y8";
    public static final String BUILD_ID = "jfkef38jer328jfdke89";
    public static final long EXPIRATION_TIME = 230942347012l;

    public static ApiBuildTokenDataBuilder Builder() {
      return new ApiBuildTokenDataBuilder();
    }

    public ApiBuildTokenData build() {
        ApiBuildTokenData apiBuildTokenData = new ApiBuildTokenData();
        apiBuildTokenData.setBuild(BUILD_ID);
        apiBuildTokenData.setExp(EXPIRATION_TIME);
        apiBuildTokenData.setToken(BUILD_TOKEN);
        return apiBuildTokenData;
    }

    }
