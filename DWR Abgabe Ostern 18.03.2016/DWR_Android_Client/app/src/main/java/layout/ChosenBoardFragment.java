package layout;

import com.example.stefan.dwr_neu.*;
import com.example.stefan.dwr_neu.Connectivity.*;
import com.example.stefan.dwr_neu.Model.Board;
import com.example.stefan.dwr_neu.Model.BoardPreview;
import com.example.stefan.dwr_neu.Model.Kommentar;

import android.app.Fragment;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Comment;

import java.util.ArrayList;

//import com.example.stefan.dwrclient.WorkingClasses.Database;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChosenBoardFragment extends Fragment implements View.OnClickListener {

    TextView lblBoardName = null;
    TextView lblOP=null;
    TextView lblDate=null;
    ListView lstComments=null;
    TextView txtInput=null;
    Button btnSend=null;
    Board currentBoard=new Board();
    Database db=null;
    TextView lblNoComments=null;
    TextView lblBoardText=null;
    //int currentBoardID=-1;

    public ChosenBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chosen_board, container, false);


    }

    /* @Override
     public void onViewCreated(View view, Bundle savedInstanceState)
     {
         db=Database.newInstance();

         initGUIComponents();

     }*/
    @Override
    public void onViewCreated(View v, Bundle savedInstanceState)
    {
        System.out.println("++++++++++++++++++++++++++++fragmeeent");
        db=Database.newInstance();
        System.out.println("++++++++++++++++++++++++++++fragmeeent");
        initGUIComponents();
        registrateEventHandlers();
        fillGUIComponents();
    }



    private void initGUIComponents()
    {
        System.out.println("++++++++++++++++++++++++++++ init fragmeeent");
        lblBoardName = (TextView)getView().findViewById(R.id.lblBoardName);
        lblOP=(TextView)getView().findViewById(R.id.lblOP);
        lblDate=(TextView)getView().findViewById(R.id.lblDate);
        lstComments=(ListView)getView().findViewById(R.id.lstComments);
        txtInput=(TextView)getView().findViewById(R.id.txtInput);
        btnSend=(Button)getView().findViewById(R.id.btnSend);
        lblNoComments =(TextView)getView().findViewById(R.id.lblNoComments);
        lblBoardText = (TextView)getView().findViewById(R.id.lblBoardText);
        System.out.println(lblBoardName + "  " + lblOP + "  " + btnSend);
        System.out.println("++++++++++++++++++++++++++++seting patn");

    }
    private void registrateEventHandlers()
    {
        btnSend.setOnClickListener(this);
    }

    private void fillGUIComponents()
    {
        if(currentBoard != null) {
            lblBoardName.setText(currentBoard.getName());
            lblDate.setText(currentBoard.getPostTime().toString());
            lblBoardText.setText(currentBoard.getText().toString());
            lblOP.setText(currentBoard.getOp());
            fillCommentList();

        }

    }

    private void fillCommentList()
    {
        ArrayList<Kommentar> x = new ArrayList<Kommentar>();
        try
        {
            currentBoard = db.getBoard(currentBoard.getId());

            x = currentBoard.getKommentar();
        } catch (Exception e)
        {
            Toast.makeText(this.getActivity(), "Could not get Comment List", Toast.LENGTH_LONG).show();
            System.out.println("Could not get Comment List");
            e.printStackTrace();
        }
        if(x.size()==0)
        {
            lblNoComments.setText("There are no Comments on this Board.");
        }
        else
        {
            lblNoComments.setText("");
        }

        lstComments.setAdapter(new ArrayAdapter<Kommentar>(
                this.getActivity(),
                android.R.layout.simple_list_item_1, x));
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==btnSend.getId())
        {
            db.postComment(txtInput.getText().toString(),currentBoard.getId());
            fillCommentList();
        }
    }

    public void setCurrentBoard(int boardID) {
        //currentBoardID=boardID;
        try {
            currentBoard = db.getBoard(boardID);
            fillGUIComponents();
        }catch(Exception exept)
        {
            System.out.println("Board accuiring error");
            exept.printStackTrace();
            currentBoard = new Board();
        }
    }
}



