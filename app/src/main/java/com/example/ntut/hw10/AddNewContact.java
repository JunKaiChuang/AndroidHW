package com.example.ntut.hw10;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.support.v4.app.Fragment;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddNewContact.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddNewContact#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewContact extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText mTxtName, mTxtPhone;
    private Spinner mSpinTypeOfPhoneNum;
    private View mView;
    private static final String DB_FILE = "contacts.db",
            DB_TABLE = "contacts";
    private SQLiteDatabase mContactDb;

    private OnFragmentInteractionListener mListener;

    public AddNewContact() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddNewContact.
     */
    // TODO: Rename and change types and number of parameters
    public static AddNewContact newInstance(String param1, String param2) {
        AddNewContact fragment = new AddNewContact();
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
        //Set object
        mTxtName = mView.findViewById(R.id.txtName);
        mTxtPhone = mView.findViewById(R.id.txtPhoneNum);
        mSpinTypeOfPhoneNum = mView.findViewById(R.id.spinTypeOfPhoneNum);

        //Initial data
        ArrayAdapter<CharSequence> phoneTypeList = ArrayAdapter.createFromResource(getActivity(),
                R.array.typeOfPhoneNum,
                android.R.layout.simple_spinner_dropdown_item);
        mSpinTypeOfPhoneNum.setAdapter(phoneTypeList);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.add(0, 0, 0, "adding a new contact");
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AddNewContact();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_contact, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    private void AddNewContact(){
        String name, phoneNum, phoneType;
        name = mTxtName.getText().toString();
        phoneNum = mTxtPhone.getText().toString();
        phoneType = mSpinTypeOfPhoneNum.getSelectedItem().toString();

        if(IsInputEmpty(name, phoneNum)){
            Toast.makeText(getContext(), "Name or phone number must fill in !", Toast.LENGTH_LONG).show();
            return;
        }

        //Insert into DB
        ContentValues newRow = new ContentValues();
        newRow.put("name", name);
        newRow.put("phone", phoneNum);
        newRow.put("phoneType", phoneType);
        mContactDb.insert(DB_TABLE, null, newRow);

        ClearInput();
    }

    private void ClearInput(){
        mTxtName.setText("");
        mTxtPhone.setText("");
        mSpinTypeOfPhoneNum.setSelection(0);
    }

    private Boolean IsInputEmpty(String name, String phone)
    {
        Boolean result = false;
        result |= TextUtils.isEmpty(name);
        result |= TextUtils.isEmpty(phone);
        return result;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //斷開魂結
        mContactDb.close();
    }
}
