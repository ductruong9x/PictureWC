package com.truongtvd.pictureofwc.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.NewPermissionsRequest;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.truongtvd.pictureofwc.R;
import com.truongtvd.pictureofwc.adapter.DetailAdapter;
import com.truongtvd.pictureofwc.model.ItemNewFeed;

import java.util.Arrays;

public class OnLikeClickListener implements OnClickListener {
	private DetailAdapter.ViewHolder viewHolder;
	private boolean isLike = false;
	private ItemNewFeed item;
	private Context context;

	public OnLikeClickListener(Context context, DetailAdapter.ViewHolder viewHolder,
			ItemNewFeed item) {
		this.viewHolder = viewHolder;
		this.item = item;
		this.context = context;

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int like = item.getLike_count();
		if (isLike == false) {
			int lik = like + 1;
			new LikeFacebook().execute(item.getPost_id());
			viewHolder.tvCountLike.setText(context.getString(R.string.like,lik));
			item.setLike_count(lik);
			isLike = true;
		} else {
			int unlike = item.getLike_count() - 1;
			viewHolder.tvCountLike.setText(context.getString(R.string.like,unlike));
			item.setLike_count(unlike);
			isLike = false;
		}

	}

	private class LikeFacebook extends AsyncTask<String, Void, Void> {
		private ProgressDialog dialog;

		public LikeFacebook() {
			dialog = new ProgressDialog(context);
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();
			Toast.makeText(context, "Like successfully", Toast.LENGTH_SHORT)
					.show();
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog.setMessage("Loading...");
			dialog.show();

		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				if (!Session.getActiveSession().getPermissions()
						.contains("publish_actions")) {
					NewPermissionsRequest request = new NewPermissionsRequest(
							(Activity) context, Arrays.asList("publish_actions"));
					request.setCallback(new StatusCallback() {

						@Override
						public void call(Session session, SessionState state,
								Exception exception) {
							// TODO Auto-generated method stub
							Log.e("PER", session.getPermissions().toString());
						}
					});
					Session.getActiveSession().requestNewPublishPermissions(
							request);

				}
			} catch (Exception e) {

			}
			Request likeRequest = new Request(Session.getActiveSession(),
					params[0] + "/likes", null, HttpMethod.POST,
					new Request.Callback() {

						@Override
						public void onCompleted(Response response) {
							Log.i("LIKE", response.toString());
						}
					});
			Request.executeBatchAndWait(likeRequest);
			return null;
		}

	}

}
