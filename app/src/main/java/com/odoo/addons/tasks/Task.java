package com.odoo.addons.tasks;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.odoo.R;
import com.odoo.addons.customers.CustomerDetails;
import com.odoo.addons.customers.Customers;
import com.odoo.addons.tasks.models.ProjectTask;
import com.odoo.core.orm.ODataRow;
import com.odoo.core.support.addons.fragment.BaseFragment;
import com.odoo.core.support.addons.fragment.ISyncStatusObserverListener;
import com.odoo.core.support.drawer.ODrawerItem;
import com.odoo.core.support.list.OCursorListAdapter;
import com.odoo.core.utils.IntentUtils;
import com.odoo.core.utils.OControls;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 12/10/16.
 */

public class Task extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor>,ISyncStatusObserverListener
        ,SwipeRefreshLayout.OnRefreshListener,OCursorListAdapter.OnViewBindListener, View.OnClickListener {

    private static final String TAG=Task.class.getSimpleName();


    private boolean syncRequested = false;
    //memangil view
    private View mView;
    private ListView listView;
    private OCursorListAdapter listAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.common_listview,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(_s(R.string.task));

        mView=view;
        listView=(ListView)mView.findViewById(R.id.listview);
        listAdapter=new OCursorListAdapter(getActivity(),null,android.R.layout.simple_list_item_1);
        listView.setAdapter(listAdapter);
        listAdapter.setOnViewBindListener(this);
        setHasSyncStatusObserver(TAG,this,db());

        setHasFloatingButton(view, R.id.fabButton, listView, this);

        getLoaderManager().initLoader(0,null,this);
    }

    @Override
    public List<ODrawerItem> drawerMenus(Context context) {
        List<ODrawerItem> menu=new ArrayList<>();
        menu.add(new ODrawerItem(TAG).setTitle("Tasks").setIcon(R.drawable.ic_task).setInstance(new Task()));
        return menu;
    }

    @Override
    public Class<ProjectTask> database() {
        return ProjectTask.class;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),db().uri(),null,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        listAdapter.changeCursor(data);
        if (data.getCount() > 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    OControls.setGone(mView, R.id.loadingProgress);
                    OControls.setVisible(mView, R.id.swipe_container);
                    OControls.setGone(mView, R.id.data_list_no_item);
                    setHasSwipeRefreshView(mView, R.id.swipe_container, Task.this);
                }
            }, 500);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    OControls.setGone(mView, R.id.loadingProgress);
                    OControls.setGone(mView, R.id.swipe_container);
                    OControls.setVisible(mView, R.id.data_list_no_item);
                    setHasSwipeRefreshView(mView, R.id.data_list_no_item, Task.this);
                    OControls.setImage(mView, R.id.icon, R.drawable.ic_action_customers);
                    OControls.setText(mView, R.id.title, _s(R.string.label_no_task_found));
                    OControls.setText(mView, R.id.subTitle, "");
                }
            }, 500);
            if (db().isEmptyTable() && !syncRequested) {
                syncRequested = true;
                onRefresh();
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
            listAdapter.changeCursor(null);
    }

    @Override
    public void onRefresh() {

        if(inNetwork()){
            parent().sync().requestSync(ProjectTask.AUTHORITY);
        }
    }

    @Override
    public void onStatusChange(Boolean refreshing) {

        if(refreshing){
            getLoaderManager().restartLoader(0,null,this);
        }
    }

    @Override
    public void onViewBind(View view, Cursor cursor, ODataRow row) {
        OControls.setText(view,android.R.id.text1,row.getString("name"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabButton:
                loadActivity(null);
                break;
        }
    }

    private void loadActivity(ODataRow row) {
        IntentUtils.startActivity(getActivity(), InputTask.class, null);
    }

}
