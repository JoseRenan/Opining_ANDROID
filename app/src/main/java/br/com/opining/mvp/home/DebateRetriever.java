package br.com.opining.mvp.home;

import br.com.opining.domain.Room;

public interface DebateRetriever {

    interface DebateRetrieveListener {
        void onDebateReceived(Room room);
        void onDebateUpdated(String roomId, Room room);
        void onDebateRemoved(String roomId);
        void onCancelled(Exception ex);
    }

    void stopListening();

}
