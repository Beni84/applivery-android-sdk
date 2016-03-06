package com.applivery.applvsdklib.ui.views.update;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.applivery.applvsdklib.AppliverySdk;
import com.applivery.applvsdklib.R;
import com.applivery.applvsdklib.ui.model.UpdateInfo;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/1/16.
 */
public class SuggestedUpdateViewImpl implements UpdateView {

  private final Builder builder;
  private final AlertDialog alertDialog;
  private final UpdateListener updateListener;
  private final Context context;
  private ProgressDialog progress;

  public SuggestedUpdateViewImpl(UpdateInfo updateInfo, UpdateListener updateListener) {
    this.context = AppliverySdk.getCurrentActivity();
    this.builder = buildDialog(context, updateInfo);
    this.alertDialog = createAlertDialog(context, updateInfo);
    this.updateListener = updateListener;
  }

  private AlertDialog createAlertDialog(Context context, UpdateInfo updateInfo) {
    final FrameLayout frameView = new FrameLayout(context);
    builder.setView(frameView);

    final AlertDialog alertDialog = builder.create();
    LayoutInflater inflater = alertDialog.getLayoutInflater();
    inflater.inflate(R.layout.suggested_update, frameView);
    TextView textView = (TextView) frameView.findViewById(R.id.suggested_update_text);
    textView.setText(updateInfo.getAppUpdateMessage());
    return alertDialog;
  }

  private Builder buildDialog(Context context, UpdateInfo updateInfo) {
    Builder builder = new AlertDialog.Builder(context);
    builder.setTitle(updateInfo.getAppName())
        .setCancelable(true)
        .setPositiveButton(context.getString(R.string.update), onUpdateClick())
        .setNegativeButton(context.getString(R.string.cancel), onCancelClick());
    return builder;
  }

  private DialogInterface.OnClickListener onUpdateClick() {
    return new DialogInterface.OnClickListener() {
      @Override public void onClick(DialogInterface dialog, int id) {
        updateListener.onUpdateButtonClick();
        alertDialog.dismiss();
      }
    };
  }

  private DialogInterface.OnClickListener onCancelClick() {
    return new DialogInterface.OnClickListener() {
      @Override public void onClick(DialogInterface dialog, int id) {
        alertDialog.dismiss();
      }
    };
  }

  @Override public void showUpdateDialog() {
    alertDialog.show();
  }

  @Override public void hideDownloadInProgress() {
    if (progress != null && progress.isShowing()) {
      progress.dismiss();
    }
  }

  @Override public void showDownloadInProgress() {
    if (AppliverySdk.isContextAvailable()) {
      progress = ProgressDialog.show(context, context.getString(R.string.download_title),
          context.getString(R.string.download_message)+" 0%", true);
    }
  }

  @Override public void updateProgress(double percent) {
    updatProcessTextView(percent, new Handler(getLooper()));
  }

  private void updatProcessTextView(final double percent, Handler handler) {
    Runnable myRunnable = new Runnable() {
      @Override
      public void run() {
        progress.setMessage(context.getString(R.string.download_message)+" "+ Double.valueOf(percent).intValue() + "%");
      }
    };
    handler.post(myRunnable);
  }

  public Looper getLooper() {
    if (Looper.myLooper() == Looper.getMainLooper()){
      return Looper.myLooper();
    }else{
      return Looper.getMainLooper();
    }
  }
}