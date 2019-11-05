package com.example.anis.ecommerce.adminpanel;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anis.ecommerce.R;
import com.example.anis.ecommerce.adapter.InternetUrl;
import com.example.anis.ecommerce.adapter.Product;
import com.example.anis.ecommerce.adminadapter.ViewUserAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewUsers.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewUsers#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewUsers extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView viewUsers;
    List<Product> productList;
    String lin = "adminpanel/getAlluser.php";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ViewUsers() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewUsers.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewUsers newInstance(String param1, String param2) {
        ViewUsers fragment = new ViewUsers();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_users, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewUsers = (RecyclerView) view.findViewById(R.id.viewUsersRecycler);

        productList = new ArrayList<>();
        viewUsers.hasFixedSize();
        viewUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        userDetails();

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

  /*  @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

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


    public void userDetails(){

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + lin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray array = new JSONArray(response);

                    for (int i = 0 ; i < array.length() ; i++){
                        JSONObject jsonObject = array.getJSONObject(i);
                        Product user= new Product();
                        user.setId(jsonObject.getInt("id"));
                        user.setFirstname(jsonObject.getString("firstname"));
                        user.setMidname(jsonObject.getString("middlename"));
                        user.setLastname(jsonObject.getString("lastname"));
                        user.setEmail(jsonObject.getString("email"));
                        user.setAllImage(jsonObject.getString("image"));
                        user.setContact(jsonObject.getString("contact"));
                        productList.add(user);
                    }

                    ViewUserAdapter adapter = new ViewUserAdapter(getContext() , productList);

                    viewUsers.setAdapter(adapter);



                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("volley" , error.toString());

            }
        });

        requestQueue.add(stringRequest);

    }
}
