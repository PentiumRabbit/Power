package com.android.base.common.SortList.HanZiSrot;

import android.os.Build;

public abstract class HanziToPinyin {
	public static HanziToPinyin getInstance() {
		int sdk = Build.VERSION.SDK_INT;
		if (sdk < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			return (OldHanziToPinyin.getInstance());
		}
		return (NewHanziToPinyin.getInstance());
	}

	public abstract String get(final String input);
}
