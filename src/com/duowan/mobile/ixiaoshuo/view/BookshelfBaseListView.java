package com.duowan.mobile.ixiaoshuo.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.duowan.mobile.ixiaoshuo.R;
import com.duowan.mobile.ixiaoshuo.db.AppDAO;
import com.duowan.mobile.ixiaoshuo.pojo.Book;
import com.duowan.mobile.ixiaoshuo.reader.MainActivity;
import com.duowan.mobile.ixiaoshuo.reader.ReaderActivity;
import com.duowan.mobile.ixiaoshuo.ui.CommonMenuDialog;

import java.util.List;

public abstract class BookshelfBaseListView extends ViewBuilder implements OnItemLongClickListener, OnItemClickListener {
	protected BaseAdapter mAdapter;
	protected List<Book> mBookList;

	protected View mLotWithoutBooks;

	public BookshelfBaseListView(MainActivity activity, int viewId) {
		mActivity = activity;
		mViewId = viewId;
	}

	@Override
	public void init() {
		mLotWithoutBooks = mActivity.findViewById(R.id.lotWithoutBooks);
		resume();
	}

	@Override
	public void resume() {
		mBookList = AppDAO.get().getBookList();
		if (mBookList.size() > 0) {
			initListView();
			mAdapter.notifyDataSetChanged();
			mLotWithoutBooks.setVisibility(View.GONE);
			getListView().setVisibility(View.VISIBLE);
		} else {
			initWithoutBookLayout();
			getListView().setVisibility(View.GONE);
			mLotWithoutBooks.setVisibility(View.VISIBLE);
		}
	}

	private void initListView() {
		if (getListView().getAdapter() != null) return;
		mAdapter = new BaseAdapter() {
			@Override
			public int getCount() {
				return mBookList != null ? mBookList.size() : 0;
			}

			@Override
			public Book getItem(int position) {
				return mBookList.get(position);
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				return getAdapterView(position, convertView);
			}
		};
		getListView().setAdapter(mAdapter);
		getListView().setOnItemClickListener(this);
		getListView().setOnItemLongClickListener(this);
	}

	protected abstract View getAdapterView(int position, View convertView);

	private boolean isInitWithoutBook;
	private void initWithoutBookLayout() {
		if (isInitWithoutBook) return;

		mLotWithoutBooks.findViewById(R.id.lotGoFinder).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().showToastMsg("正在前往发现模块");
			}
		});

		mLotWithoutBooks.findViewById(R.id.lotGoDetector).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().showToastMsg("正在前往雷达界面");
			}
		});

		isInitWithoutBook = true;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		final Book book = (Book) parent.getItemAtPosition(position);
		if (book == null) return true;

		final CommonMenuDialog menuDialog = new CommonMenuDialog(getActivity(), '《' + book.getName() + '》');
		menuDialog.initContentView(new CommonMenuDialog.MenuItem[] {
				new CommonMenuDialog.MenuItem("查看详情", new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						getActivity().showToastMsg("点击了查看详情");
					}
				}),
				new CommonMenuDialog.MenuItem("删除书籍", new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
						builder.setMessage("是否确认删除该书籍？");
						builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								AppDAO.get().deleteBook(book);
								menuDialog.cancel();
								dialog.cancel();
								resume();
							}
						});
						builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();
							}
						});
						builder.show();
					}
				}),
				new CommonMenuDialog.MenuItem("更换站点", new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						getActivity().showToastMsg("点击了更换站点");
					}
				}),
		});
		menuDialog.show();

		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Book book = (Book) parent.getItemAtPosition(position);
		if (book != null) {
			Intent intent = new Intent(getActivity(), ReaderActivity.class);
			intent.setAction(String.valueOf(book.getBid()));
			getActivity().startActivity(intent);
		}
	}

	private ListView getListView() {
		return (ListView) mView;
	}

}