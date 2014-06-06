package com.truongtvd.pictureofwc.view;


import android.view.View;
import android.view.View.OnClickListener;

import com.truongtvd.pictureofwc.adapter.DetailAdapter;
import com.truongtvd.pictureofwc.util.AnimationUtil;


public class OnCloseClickListener implements OnClickListener {

	private DetailAdapter.ViewHolder viewHolder;

	public OnCloseClickListener(DetailAdapter.ViewHolder viewHolder) {
		this.viewHolder = viewHolder;

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		viewHolder.comment_detail.setAnimation(AnimationUtil
				.translateAnimation(0, 0, 0, -1500));
		viewHolder.comment_detail.setVisibility(View.GONE);
		OnCommentClickListener.deleteComment();
		OnCommentClickListener.isOpen = false;
	}

}
