package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import chodi.Match;
import chodi.com.betclientapp.R;
import chodi.com.betclientapp.UserData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FinishedMatchesFragment extends Fragment {

    ListView listViewFinishedMatches;
    ArrayList finishedMatchesView;

    public FinishedMatchesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_finished_matches, container, false);

            listViewFinishedMatches = (ListView)rootView.findViewById(R.id.listViewFinishedMatches);
            try{
                finishedMatchesView = convertFinishedMatchesIntoArray();
                ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, finishedMatchesView);
                listViewFinishedMatches.setAdapter(adapter);
            }
            catch(NullPointerException e)
            {
                Toast.makeText(getActivity(), "Results are not available.\nTry again later.", Toast.LENGTH_LONG).show();
            }

        return rootView;
    }

    ArrayList<String> convertFinishedMatchesIntoArray() throws NullPointerException
    {
        ArrayList<String> matches = new ArrayList<>();

        if(!finishedMatchesContainInfoAboutWinner())
        {
           throw new NullPointerException("No matches");
        }
        for (Match m : UserData.finishedMatches)
        {
            String winner = m.getWinner() == 1 ? m.getTeam1() : m.getTeam2();
            String yourType;
            if (m.getUserChoice() == 0)
                yourType = "You didn't bet this game";
            else
                yourType = m.getUserChoice() == 1 ? m.getTeam1() : m.getTeam2();
            int points = m.getUserChoice() == m.getWinner() ? 1 : 0;
            matches.add(m.getTeam1() + " vs " + m.getTeam2() + "\nWinner was: " + winner + "\nYour type was: " + yourType + "\nYou get: " + points + " points.");
        }
        return matches;

    }

    private boolean finishedMatchesContainInfoAboutWinner()
    {
        for(Match m : UserData.finishedMatches)
            if(m.getWinner()!=0)
                return true;
        return false;

    }

    public static FinishedMatchesFragment newInstance(int sectionNumber) {
        FinishedMatchesFragment fragment = new FinishedMatchesFragment();
        return fragment;
    }
}
