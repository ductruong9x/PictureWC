package com.truongtvd.pictureofwc.view;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.truongtvd.pictureofwc.model.ItemNewFeed;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class OnDownloadClickListener implements OnClickListener {
	private Context context;
	private ItemNewFeed item;
	private NotificationHelper notificationHelper;
	private String path;
	private String name;
	private String path_image;

	public OnDownloadClickListener(Context context, ItemNewFeed item) {
		this.context = context;
		this.item = item;
		notificationHelper = new NotificationHelper(context);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		new DownloadImage().execute(item.getImage());
	}

	private class DownloadImage extends AsyncTask<String, Void, Void> {
		private ProgressDialog dialog;

		public DownloadImage() {

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			notificationHelper.onCreateNotificaion();
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			path_image = path + name;
			notificationHelper.downloadCopmplete(path_image);
			Toast.makeText(context, "Download to /sdcard/Download/",
					Toast.LENGTH_SHORT).show();
		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			int count;
			try {
				// folder
				path = Environment.getExternalStorageDirectory()
						.getAbsoluteFile() + "/Download/";
				File sdImageMainDirectory = new File(path);
				sdImageMainDirectory.mkdirs();

				// file dowload
				String imageNameFrUrl = "";
				imageNameFrUrl = params[0]
						.substring(params[0].lastIndexOf("/") + 1);
				name = imageNameFrUrl;
				File outputFile = new File(sdImageMainDirectory, name);

				// inset uri
				String imageType = name.substring(name.lastIndexOf(".") + 1);
				String imageMimetype = setMimeType(imageType);
				ContentValues values = new ContentValues();
				values.put(MediaStore.MediaColumns.DATA, outputFile.toString());
				values.put(MediaStore.MediaColumns.TITLE, name);
				values.put(MediaStore.MediaColumns.DATE_ADDED,
						System.currentTimeMillis());
				values.put(MediaStore.MediaColumns.MIME_TYPE, imageMimetype);
				Uri uri = context
						.getContentResolver()
						.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
								values);

				// download
				URL url = new URL(params[0]);
				HttpURLConnection urlConnection = (HttpURLConnection) url
						.openConnection();

				urlConnection.setRequestMethod("GET");
				urlConnection.connect();
				int lenghtOfFile = urlConnection.getContentLength();
				InputStream input = urlConnection.getInputStream();
				OutputStream output = new FileOutputStream(outputFile);
				byte data[] = new byte[1024];
				long total = 0;
				// int latestPercentDone;
				int percentDone = -1;

				while ((count = input.read(data)) != -1) {
					total += count;
					output.write(data, 0, count);
				}

				output.flush();
				output.close();
				input.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

	}

	// set mimetype
	protected String setMimeType(String type) {
		if (type.equals("jpg") || type.equals("jpeg"))
			return "image/jpeg";
		else if (type.equals("png"))
			return "image/png";
		return "";
	}
}
