package com.example.stefan.dwr_neu;


import android.app.ListFragment;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.stefan.dwr_neu.Connectivity.Database;
import com.example.stefan.dwr_neu.Model.BoardPreview;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BoardList extends ListFragment implements AdapterView.OnItemClickListener {


    Database db=null;
    ListView lstBoards=null;

    public BoardList() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_board_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        System.out.println("######################################created");
        db=Database.newInstance();
        System.out.println("######################################gonna init");

        initGUIComponents();
        System.out.println("######################################done initing");

        fillBoardList();
        System.out.println("######################################filled");

    }

    private void initGUIComponents()
    {
        lstBoards=(ListView)getView().findViewById(R.id.lstBoards);
    }
    private void fillBoardList()
    {
        ArrayList<BoardPreview>x = null;
        try {
            x = db.getAllBoardNames();
        } catch (Exception e) {
            Toast.makeText(this.getActivity(),"Could not get Board List",Toast.LENGTH_LONG).show();
            System.out.println("Could not get Board List in fillBoardList in BoardList");
        }
        lstBoards.setAdapter(new ArrayAdapter<BoardPreview>(
                this.getActivity(),
                android.R.layout.simple_list_item_1,x));
    }
    /******************************************************************************/
    //Event Handler

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        listener.onItemSelectedListener(((BoardPreview)parent.getAdapter().getItem(position)).getId());
    }

    private onItemSelectedListener listener;

    public interface onItemSelectedListener {
        public void onItemSelectedListener(int boardid);
    }

    public void setOnItemSelectListener(onItemSelectedListener l)  {
        listener = l;
    }

    private void registrateEventHandler() {
        lstBoards.setOnItemClickListener(this);
    }

}
