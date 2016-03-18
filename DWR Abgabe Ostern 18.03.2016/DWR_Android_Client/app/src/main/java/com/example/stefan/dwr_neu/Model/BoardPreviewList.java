package com.example.stefan.dwr_neu.Model;



import com.example.stefan.dwr_neu.Model.BoardPreview;

import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

public class BoardPreviewList {
    Vector<BoardPreview> boardPreviews = new Vector<BoardPreview>();



    public BoardPreviewList(Vector<BoardPreview> boardPreviews) {
        super();
        this.boardPreviews = boardPreviews;
    }

    public BoardPreviewList() {
        super();
    }

    public Vector<BoardPreview> getBoardPreviews() {
        return boardPreviews;
    }



    public void setBoardPreviews(Vector<BoardPreview> boardPreviews) {
        this.boardPreviews = boardPreviews;
    }

    @Override
    public String toString() {
        return "BoardPreviewList [boardPreviews=" + boardPreviews + "]";
    }


}