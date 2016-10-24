package com.odoo.addons.tasks.models;

import android.content.Context;

import com.odoo.core.orm.OModel;
import com.odoo.core.orm.fields.OColumn;
import com.odoo.core.orm.fields.types.OVarchar;
import com.odoo.core.support.OUser;

/**
 * Created by root on 12/10/16.
 */

public class ProjectProject extends OModel {

    private static final String TAG=ProjectProject.class.getSimpleName();

    //buat kolom
    OColumn name=new OColumn("Name", OVarchar.class).setSize(100);


    public ProjectProject(Context context,OUser user) {
        super(context, "project.project", user);
    }


}
