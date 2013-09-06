package com.duowan.mobile.ixiaoshuo.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import com.duowan.mobile.ixiaoshuo.R;
import com.duowan.mobile.ixiaoshuo.view.finder.FinderView;
import com.duowan.mobile.ixiaoshuo.view.bookshelf.BookshelfView;
import com.duowan.mobile.ixiaoshuo.view.ViewBuilder;

public class MainMenuGridView extends SingleLineGridView {
	public MainMenuGridView(Context context) {
		super(context);
	}

	public MainMenuGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public static final int MENU_BOOKSHELF	= 10;
	public static final int MENU_FINDER		= 20;
	public static final int MENU_DETECTOR 	= 30;
	public static final int MENU_SEARCH		= 40;

	@Override
	protected void init() {
		mGridItems = new SparseArray<GridItem>(4);

		mGridItems.put(MENU_BOOKSHELF, new GridItem(R.drawable.menu_bookshelf_on, R.drawable.menu_bookshelf_off, new ClickEvent() {
			public void onClick() {
				getActivity().showView(buildViewBuilder(MENU_BOOKSHELF));
			}
		}));

		mGridItems.put(MENU_FINDER, new GridItem(R.drawable.menu_finder_on, R.drawable.menu_finder_off, new ClickEvent() {
			public void onClick() {
				getActivity().showView(buildViewBuilder(MENU_FINDER));
			}
		}));

		mGridItems.put(MENU_DETECTOR, new GridItem(R.drawable.menu_detector_on, R.drawable.menu_detector_off, new ClickEvent() {
			public void onClick() {
				getActivity().showToastMsg("点击了雷达菜单");
			}
		}));

		mGridItems.put(MENU_SEARCH, new GridItem(R.drawable.menu_search_on, R.drawable.menu_search_off, new ClickEvent() {
			public void onClick() {
				getActivity().showToastMsg("点击了搜索菜单");
			}
		}));

		mPaddingTop = getResources().getDimensionPixelSize(R.dimen.globalMenuPadding);

		mHighlightDrawable = getResources().getDrawable(R.drawable.menu_bg_pressed);
	}

	public ViewBuilder buildViewBuilder(int menuId) {
		if (menuId == MENU_BOOKSHELF) {
			return new BookshelfView(getActivity(), new ViewBuilder.OnShowListener() {
				@Override
				public void onShow() {
					selectItem(MENU_BOOKSHELF);
				}
			});
		}

		if (menuId == MENU_FINDER) {
			return new FinderView(getActivity(), new ViewBuilder.OnShowListener() {
				@Override
				public void onShow() {
					selectItem(MENU_FINDER);
				}
			});
		}
		return null;
	}

}