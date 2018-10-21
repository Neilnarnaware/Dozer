package com.deeplin.dozer;

import android.app.ActivityManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<ActivityManager.RunningAppProcessInfo> processes;
    ListAdapter adapter;
    ActivityManager manager;
    int load = 0;
    ListView listv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listv=(ListView)findViewById(R.id.listv);
        listv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (load == 1) {
                    for (ActivityManager.RunningAppProcessInfo info : processes) {
                        if (info.processName.equalsIgnoreCase(
                                ((ActivityManager.RunningAppProcessInfo)parent.getItemAtPosition(position)).processName)) {
                            android.os.Process.killProcess(info.pid);
                            android.os.Process.sendSignal(info.pid, android.os.Process.SIGNAL_KILL);
                            manager.killBackgroundProcesses(info.processName);
                        }
                    }
                    load = 0;
                    skill_Load_Process();
                }
                return true;
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void skill_Load_Process() {
        if (load == 0) {
            manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            processes = manager.getRunningAppProcesses();
            adapter=new ListAdapter(processes,MainActivity.this);
            listv.setAdapter(adapter);
            load = 1;
        }}
}
