package com.truongtvd.pictureofwc.util;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class AnimationUtil {
	public static Animation translateAnimation(int fromX, int toX, int fromY,
			int toY) {
		Animation animation = new TranslateAnimation(fromX, toX, fromY, toY);
		animation.setDuration(400);
		return animation;
	}

	public static Animation alphaAnimation() {
		Animation animation = new AlphaAnimation(1.0f, 0.0f);
		animation.setDuration(500);

		return animation;
	}
}
