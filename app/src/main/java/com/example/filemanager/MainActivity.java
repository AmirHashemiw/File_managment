package com.example.filemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButtonToggleGroup;

import java.io.File;

public class MainActivity extends AppCompatActivity implements AddNewFolderDialog.AddNewFolderCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (StorageHelper.isExternalStorageReadable()){
            File externalFilesDir =  getExternalFilesDir(null);
            listFiles(externalFilesDir.getPath(),false);
        }


        findViewById(R.id.iv_main_addNewFolder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddNewFolderDialog().show(getSupportFragmentManager(),null);
            }
        });

        EditText editText = findViewById(R.id.et_main_search);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_main_fragmentContainer);
                if (fragment instanceof FileListFragment){
                    ((FileListFragment) fragment).search(s.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        MaterialButtonToggleGroup toggleGroup = findViewById(R.id.toggle_group_main);
        toggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (checkedId==R.id.btn_main_list && isChecked){
                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_main_fragmentContainer);
                    if (fragment instanceof FileListFragment){
                        ((FileListFragment)fragment).setViewType(ViewType.ROW);
                    }
                        }else if (checkedId == R.id.btn_grid && isChecked){
                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_main_fragmentContainer);
                    if (fragment instanceof FileListFragment){
                        ((FileListFragment)fragment).setViewType(ViewType.GRID);
                    }
                }
            }
        });



    }

    public void listFiles(String path, boolean addToBackStack){

        FileListFragment fileListFragment = new FileListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("path",path);
        fileListFragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_main_fragmentContainer, fileListFragment);

        if(addToBackStack)
            transaction.addToBackStack(null);
        transaction.commit();

    }

    public void listFile (String path){
        this.listFiles(path,true);
    }

    @Override
    public void onCreatefolderButtonClick(String folderName) {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_main_fragmentContainer);
        if( fragment instanceof FileListFragment){
            ((FileListFragment) fragment).createNewFolder(folderName);
        }

    }
}