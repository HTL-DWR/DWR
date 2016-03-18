package layout;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.ListFragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stefan.dwr_neu.Connectivity.*;
import com.example.stefan.dwr_neu.Model.*;
import com.example.stefan.dwr_neu.R;
//import com.example.stefan.dwr_neu.Views.*;
import com.example.stefan.dwr_neu.Model.BoardPreview;
import com.example.stefan.dwr_neu.Connectivity.Database;
import layout.*;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BoardList extends ListFragment implements AdapterView.OnItemClickListener, View.OnClickListener, DialogInterface.OnClickListener {


    ListView lstBoards=null;
    TextView txtNoBoards=null;
    Button buttonAddBoard = null;

    Database db = Database.newInstance();


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

        registrateEventHandler();

    }

    private void initGUIComponents()
    {
        lstBoards=(ListView)getView().findViewById(R.id.lstBoards);
        txtNoBoards=(TextView)getView().findViewById(R.id.txtNoBoards);
        buttonAddBoard = (Button) getView().findViewById(R.id.buttonAddBoard);

        buttonAddBoard.setOnClickListener(this);
    }
    private void fillBoardList()
    {
        ArrayList<BoardPreview>x = new ArrayList<BoardPreview>();
        try
        {

            x = db.getAllBoardNames();
        } catch (Exception e)
        {
            Toast.makeText(this.getActivity(),"Could not get Board List",Toast.LENGTH_LONG).show();
            System.out.println("Could not get Board List");
            e.printStackTrace();
        }
        if(x.size()==0)
        {
            txtNoBoards.setText("There are no active Boards.");
        }

        lstBoards.setAdapter(new ArrayAdapter<BoardPreview>(
                this.getActivity(),
                android.R.layout.simple_list_item_1,x));
    }
    /******************************************************************************/
    //Event Handler

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        System.out.println("nearly at the station");
        listener.onItemSelectedListener(((BoardPreview)parent.getAdapter().getItem(position)).getId());
    }

    private onItemSelectedListener listener;

    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle("Add new Board");

        LayoutInflater inflater = getActivity().getLayoutInflater();


        builder.setView(inflater.inflate(R.layout.addboard_dialog,null));


            builder.setPositiveButton("OK", this);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });

        builder.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        EditText boardname = (EditText )((AlertDialog) dialog).findViewById(R.id.textBoardName);
        EditText boardtext = (EditText )((AlertDialog) dialog).findViewById(R.id.boardText);

        db.addBoard(boardname.getText().toString(), boardtext.getText().toString());

        fillBoardList();
    }

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
