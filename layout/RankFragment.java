package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import chodi.UserPointsPair;
import chodi.com.betclientapp.R;
import chodi.com.betclientapp.UserData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankFragment extends Fragment {
    ListView listView;
    ArrayList playerRankView;

    public RankFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_rank, container, false);

        listView = (ListView)rootView.findViewById(R.id.listView);
        playerRankView = convertPlayerRankToArrayOfStrings(UserData.rankOfPlayers);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, playerRankView);
        listView.setAdapter(adapter);

        return rootView;

    }


    private void initializeWidgets() {
        listView = (ListView)getActivity().findViewById(R.id.listView);
    }

    private ArrayList<String> convertPlayerRankToArrayOfStrings(ArrayList<UserPointsPair> pairs)
    {
        ArrayList<String> rankview = new ArrayList<>();
        for(UserPointsPair pair: pairs)
            rankview.add(new String(pair.getUsername() + " " + pair.getPoints() + " pts"));

        return rankview;
    }

    public static RankFragment newInstance(int sectionNumber) {
        RankFragment fragment = new RankFragment();
        return fragment;
    }
}
