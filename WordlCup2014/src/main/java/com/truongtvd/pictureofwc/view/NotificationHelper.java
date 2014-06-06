package com.truongtvd.pictureofwc.view;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.truongtvd.pictureofwc.R;

import java.io.File;


public class NotificationHelper {
	private Context context;
	private Notification mNotification;
	private NotificationManager mNotificationManager;
	private PendingIntent mContentIntent;
	private int mNOTIFICATION_ID = 1;

	public NotificationHelper(Context context) {
		this.context = context;

	}

	@SuppressWarnings("deprecation")
	public void onCreateNotificaion() {

		String contentText = context.getString(R.string.downloading);
		String title = context.getString(R.string.app_name);
		long when = System.currentTimeMillis();
		Intent intent = new Intent();
		PendingIntent pIntent = PendingIntent
				.getActivity(context, 0, intent, 0);
		mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotification = new Notification(R.drawable.ic_launcher, contentText,
				when);
		Intent notificationIntent = new Intent();
		mContentIntent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);
		mNotification.setLatestEventInfo(context, title, contentText,
				mContentIntent);
		mNotification.flags = Notification.FLAG_AUTO_CANCEL;
		mNotification.contentIntent = pIntent;
		mNotificationManager.notify(mNOTIFICATION_ID, mNotification);

	}

	@SuppressWarnings("deprecation")
	public void downloadCopmplete(String path) {

		String contentText = context.getString(R.string.complete);
		String title = context.getString(R.string.app_name);
		long when = System.currentTimeMillis();
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("file://"
				+ path));
		File file = new File(path);
		intent.setDataAndType(Uri.fromFile(file), "whatEverMIMEType");
		PendingIntent pIntent = PendingIntent
				.getActivity(context, 0, intent, 0);
		mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotification = new Notification(R.drawable.ic_launcher, contentText,
				when);
		Intent notificationIntent = new Intent();
		mContentIntent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);
		mNotification.setLatestEventInfo(context, title, contentText,
				mContentIntent);
		mNotification.flags = Notification.FLAG_AUTO_CANCEL;
		mNotification.contentIntent = pIntent;
		mNotificationManager.notify(mNOTIFICATION_ID, mNotification);

	}
}
