package com.odoo.addons.tasks.providers;

import com.odoo.addons.tasks.models.ProjectTask;
import com.odoo.core.orm.provider.BaseModelProvider;

/**
 * Created by root on 12/10/16.
 */

public class ProjectTaskProvider extends BaseModelProvider {

    private static final String TAG=ProjectTaskProvider.class.getSimpleName();

    @Override
    public String authority() {
        return ProjectTask.AUTHORITY;
    }
}
