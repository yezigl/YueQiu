package com.yidongle.yueqiu.loader;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.support.v4.app.FragmentActivity;

/**
 * Created on 15/10/8.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public abstract class LoaderRequest<T> implements LoaderManager.LoaderCallbacks<T> {

    FragmentActivity context;

    public LoaderRequest(FragmentActivity context) {
        this.context = context;
    }

    @Override
    public void onLoaderReset(Loader<T> loader) {
        loader.stopLoading();
    }

    public void request() {
        context.getLoaderManager().restartLoader(hashCode(), null, this);
    }

    public Context getContext() {
        return this.context;
    }
}
