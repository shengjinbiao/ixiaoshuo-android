package com.duowan.mobile.ixiaoshuo.view.reader;

import android.widget.LinearLayout;
import com.duowan.mobile.ixiaoshuo.reader.ReaderActivity;
import com.duowan.mobile.ixiaoshuo.view.ViewBuilder;

public abstract class ReaderViewBuilder extends ViewBuilder {

	public ReaderViewBuilder(ReaderActivity activity, int viewId, OnShowListener showListener) {
		mShowListener = showListener;
		setActivity(activity);
		mViewId = viewId;
	}

	@Override
	protected void setLayout() {
		mView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
	}

	@Override
	public ReaderActivity getActivity() {
		return (ReaderActivity) super.getActivity();
	}

}