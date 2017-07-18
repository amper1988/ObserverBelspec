import retrofit.model.CarDataShort;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NotificationManager {
    private static NotificationManager instance;
    private List<Integer> visibleNotification;

    private NotificationManager() {
        visibleNotification = new ArrayList<>();
    }

    public static NotificationManager getInstance() {
        if (instance == null) {
            instance = new NotificationManager();
        }
        return instance;
    }

    public void showNotification(List<CarDataShort> list) {
        if(list != null){
            for (CarDataShort item : list) {
                if (!visibleNotification.contains(item.getIdentifier())) {

                    FormNotification frm = new FormNotification();
                    frm.setData(item);
                    try {
                        frm.start(null);
                        visibleNotification.add(item.getIdentifier());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public void onCloseNotification(int identifier) {
        visibleNotification.remove((Integer)identifier);
    }

}
