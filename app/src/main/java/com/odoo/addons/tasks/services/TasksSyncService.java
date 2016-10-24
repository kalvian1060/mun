package com.odoo.addons.tasks.services;

import android.content.Context;
import android.os.Bundle;

import com.odoo.addons.tasks.models.ProjectProject;
import com.odoo.addons.tasks.models.ProjectTask;
import com.odoo.core.service.OSyncAdapter;
import com.odoo.core.service.OSyncService;
import com.odoo.core.support.OUser;

/**
 * Created by root on 12/10/16.
 */

public class TasksSyncService extends OSyncService {

    private static  final String TAG=TasksSyncService.class.getSimpleName();


    @Override
    public OSyncAdapter getSyncAdapter(OSyncService service, Context context){
        return new OSyncAdapter(getApplicationContext(), ProjectTask.class,this,true);
    }

    @Override
    public void performDataSync(OSyncAdapter adapter, Bundle extras, OUser user) {
            adapter.syncDataLimit(80);
    }
}
