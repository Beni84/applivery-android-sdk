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

package com.applivery.applvsdklib.domain.download.token;

import com.applivery.applvsdklib.AppliverySdk;
import com.applivery.applvsdklib.domain.InteractorCallback;
import com.applivery.applvsdklib.domain.download.app.DownloadBuildInteractor;
import com.applivery.applvsdklib.domain.download.app.DownloadBuildInteractorCallback;
import com.applivery.applvsdklib.domain.download.app.ExternalStorageWriter;
import com.applivery.applvsdklib.domain.model.DownloadToken;
import com.applivery.applvsdklib.domain.model.ErrorObject;
import com.applivery.applvsdklib.network.api.DownloadApiService;
import com.applivery.applvsdklib.tools.androidimplementations.AndroidExternalStorageWriterImpl;
import com.applivery.applvsdklib.ui.views.ShowErrorAlert;
import com.applivery.applvsdklib.ui.views.update.UpdateView;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 10/1/16.
 */
public class ObtainBuildTokenInteractorCallback implements InteractorCallback<DownloadToken> {

  private final DownloadApiService apiService;
  private final String appName;
  private final UpdateView updateView;

  public ObtainBuildTokenInteractorCallback(DownloadApiService apiService, String appName,
      UpdateView updateView) {
    this.apiService = apiService;
    this.appName = appName;
    this.updateView = updateView;
  }

  @Override public void onSuccess(final DownloadToken downloadToken) {
    updateView.showDownloadInProgress();

    DownloadBuildInteractorCallback interactorCallback =
        new DownloadBuildInteractorCallback(updateView);
    ExternalStorageWriter externalStorageWriter = new AndroidExternalStorageWriterImpl();
    Runnable r =
        DownloadBuildInteractor.getInstance(apiService, appName, downloadToken, interactorCallback,
            externalStorageWriter);

    AppliverySdk.getExecutor().execute(r);
  }

  @Override public void onError(ErrorObject error) {
    ShowErrorAlert showErrorAlert = new ShowErrorAlert();
    showErrorAlert.showError(error);
  }
}
