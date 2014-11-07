package com.vincestyling.ixiaoshuo.net.request;

import com.duowan.mobile.netroid.Listener;
import com.duowan.mobile.netroid.NetroidError;
import com.duowan.mobile.netroid.NetworkResponse;
import com.duowan.mobile.netroid.Response;
import com.vincestyling.ixiaoshuo.db.AppDAO;
import com.vincestyling.ixiaoshuo.pojo.Book;

public class DeleteBookDBRequest extends BasicRequest<Boolean> {
    private boolean result;
    private Book book;

    public DeleteBookDBRequest(Book book, Listener<Boolean> listener) {
        super(null, listener);
        this.book = book;
    }

    public DeleteBookDBRequest(Book book) {
        this(book, null);
    }

    @Override
    public NetworkResponse perform() {
        result = AppDAO.get().deleteBook(book);
        return new NetworkResponse(null, null);
    }

    @Override
    protected Response<Boolean> parseNetworkResponse(NetworkResponse response) {
        if (result) return Response.success(true, response);
        return Response.error(new NetroidError());
    }
}
