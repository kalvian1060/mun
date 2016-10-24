package com.odoo.addons.tasks;
import android.os.Bundle;

import com.odoo.R;
import com.odoo.core.support.OdooCompatActivity;

import odoo.controls.OForm;


public class InputTask extends OdooCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_task);

        OForm oForm=(OForm)findViewById(R.id.noteForm);
        oForm.initForm(null);

    }
}
