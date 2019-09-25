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

package com.applivery.applvsdklib.tools.androidimplementations;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import com.applivery.applvsdklib.domain.download.app.AppInstaller;
import com.applivery.applvsdklib.domain.download.app.ExternalStorageReader;
import java.io.File;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/1/16.
 */
public class AndroidAppInstallerImpl implements AppInstaller, AppPathReceiver {

  private static final String FILE_URI_ID = "file:///";
  private static final String APP_TYPE_ID = "application/vnd.android.package-archive";

  private final Context context;
  private final String fileProviderAuthority;
  private final ExternalStorageReader externalStorageReader;

  public AndroidAppInstallerImpl(Context context, String fileProviderAuthority) {
    this.context = context;
    this.fileProviderAuthority = fileProviderAuthority;
    this.externalStorageReader = new AndroidExternalStorageReaderImpl();
  }

  private void install(String path) {
    Uri uri = composeUri(path, fileProviderAuthority);

    //Intent promptInstall = new Intent(Intent.ACTION_VIEW);
    Intent promptInstall = new Intent(Intent.ACTION_INSTALL_PACKAGE);
    promptInstall.setDataAndType(uri, APP_TYPE_ID);
    promptInstall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    promptInstall.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    context.grantUriPermission(context.getPackageName(), uri,
        Intent.FLAG_GRANT_READ_URI_PERMISSION);

    context.startActivity(promptInstall);
  }

  @Override public void installApp(final String apkFileName) {
    externalStorageReader.getPath(apkFileName, this);
  }

  @Override public void onPathReady(String path) {
    install(path);
  }

  private Uri composeUri(String path, String fileProviderAuthority) {
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
      return Uri.parse(FILE_URI_ID + path);
    } else {
      File file = new File(path);
      return AppliveryFileProviderLeg.getUriForFile(context, fileProviderAuthority, file);
    }
  }
}
