package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import chodi.Match;
import chodi.com.betclientapp.*;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingMatchesFragment extends Fragment {

    String login;

    ArrayList<String> upcomingMatchesView;
    ListView listHome;
    Button sendTypesBtn;
    final int MATCH_ID_COLUMN = 0;
    final int TYPE_COLUMN = 1;

    public UpcomingMatchesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_upcoming_matches, container, false);
        View footerView = inflater.inflate(R.layout.footer, null);


        listHome=(ListView)rootView.findViewById(R.id.listViewHome);
        sendTypesBtn = (Button)footerView.findViewById(R.id.sendTypesBtn);
        upcomingMatchesView = parseMatchesToStrings();

        listHome.addFooterView(footerView);
        listHome.setAdapter(new MyListAdapter(getActivity(),R.layout.list_element, upcomingMatchesView));



        setActionForSendingTypesButton();
        initializeArrayOfTypes();

        return rootView;
    }



    private void setActionForSendingTypesButton() {
        sendTypesBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ServerClient serverClient = new ServerClient();

                if(areAllTypesMade())
                    if(serverClient.sendTypesToServer(ServerConnector.clientSocket, UserData.types))
                        Toast.makeText(getActivity(), "Your types were successfully saved!", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getActivity(), "Could not connect to server and save your types.", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(), "You have to type all the matches!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initializeArrayOfTypes() {
        UserData.types = new int[UserData.upcomingMatches.size()][2];
        for(int i = 0; i<UserData.upcomingMatches.size(); i++)
            UserData.types[i][MATCH_ID_COLUMN] = UserData.upcomingMatches.get(i).getId();
        for(int i = 0; i<UserData.upcomingMatches.size(); i++)
            UserData.types[i][TYPE_COLUMN] = 0;
    }
    private boolean areAllTypesMade() {
        for (int i = 0; i < UserData.types.length; i++)
            if (UserData.types[i][TYPE_COLUMN] == 0)
                return false;
        return true;
    }




    private ArrayList<String> parseMatchesToStrings()
    {
        ArrayList<String> matches = new ArrayList<String>();
        for(Match m : UserData.upcomingMatches)
            matches.add(new String( m.getTeam1() + " vs " + m.getTeam2()));
        return matches;
    }


    public static UpcomingMatchesFragment newInstance(int sectionNumber) {
        UpcomingMatchesFragment fragment = new UpcomingMatchesFragment();
        return fragment;
    }
}
