package com.example.ntut.hw10;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchContact.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchContact#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchContact extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView mListContact;
    private View mView;
    private SearchView mSearch;
    private static final String DB_FILE = "contacts.db",
            DB_TABLE = "contacts";
    private SQLiteDatabase mContactDb;


    private OnFragmentInteractionListener mListener;

    public SearchContact() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchContact.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchContact newInstance(String param1, String param2) {
        SearchContact fragment = new SearchContact();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mView = getView();
        mListContact = mView.findViewById(R.id.listContact);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //Set DB
        ContactDbOpenHelper contactDbOpenHelper =
                new ContactDbOpenHelper(getContext(), DB_FILE, null, 1);
        mContactDb = contactDbOpenHelper.getWritableDatabase();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Refresh fragment here
            SearchContactByName("");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_contact, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        super.onCreateOptionsMenu(menu, inflater);

        mSearch = (SearchView) menu.findItem(R.id.search).getActionView();
        mSearch.setOnQueryTextListener(searchViewOnQueryTextList);
    }

    private void SearchContactByName(String name){
        String queryAll = "select _id, name||'-'||phoneType sname, phone from contacts";
        String queryByName = "select _id, name||'-'||phoneType sname, phone from contacts where name = '" + name +"'";
        String finQuery ="";
        if(TextUtils.isEmpty(name)){
            finQuery = queryAll;
        }
        else{
            finQuery = queryByName;
        }

        Cursor cursor = mContactDb.rawQuery(finQuery, null);
        if(cursor != null && cursor.getCount() > 0){
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(getContext(), android.R.layout.simple_list_item_2, cursor, new String[]{"sname", "phone"}, new int[]{android.R.id.text1, android.R.id.text2}, 0);
            mListContact.setAdapter(adapter);
        }
        else{
            mListContact.setAdapter(null);
            Toast.makeText(getActivity(), name + " not found!", Toast.LENGTH_LONG).show();
        }
    }

    private SearchView.OnQueryTextListener searchViewOnQueryTextList = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            SearchContactByName(query);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            if(TextUtils.isEmpty(newText)) {
                SearchContactByName(newText);
            }
            return false;
        }
    };

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //斷開魂結
        mContactDb.close();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
