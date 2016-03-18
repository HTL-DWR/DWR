package com.example.stefan.dwr_neu;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.example.stefan.dwr_neu.Connectivity.Database;

import layout.ChosenBoardFragment;
import layout.BoardList;
//import layout.ChosenBoardFragment;

public class MainActivity extends AppCompatActivity implements BoardList.onItemSelectedListener{


    Database db=null;
    ChosenBoardFragment fragmentChosen=null;
    BoardList fragmentBoardList=null;
    boolean isBoardChosen=false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        System.out.println("--------------------------onCreated");
        db = Database.newInstance();
        initGUIComponents();
        showList();
    }

    private void initGUIComponents()
    {
        System.out.println("--------------------------init");
        fragmentChosen =
                (ChosenBoardFragment) getFragmentManager().findFragmentById(R.id.fragmentChosen);
        fragmentBoardList =
                (layout.BoardList) getFragmentManager().findFragmentById(R.id.fragmentBoardList);

        fragmentBoardList.setOnItemSelectListener(this);

        System.out.println("------------"+fragmentBoardList+"    "+fragmentChosen);
        System.out.println("--------------------------init end");
    }



    @Override
    public void onItemSelectedListener(int boardid) {
        try {

            System.out.println("------>>>>>>>>Item Selected");
            showDetail();
            isBoardChosen=true;
            fragmentChosen.setCurrentBoard(boardid);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // your code
            System.out.println("tobesure:"+isBoardChosen);
            if(isBoardChosen)
            {    showList();
                isBoardChosen=false;
                return false;
            }
            else
            {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    private void showDetail() {
        FragmentManager fm = this.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.hide(fragmentBoardList);
        ft.show(fragmentChosen);
        ft.commit();
    }

    private void showList()
    {
        FragmentManager fm = this.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.hide(fragmentChosen);
        ft.show(fragmentBoardList);
        ft.commit();


    }
}
