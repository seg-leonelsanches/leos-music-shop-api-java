package br.com.segment.leosmusicstoreapi.components;

import br.com.segment.leosmusicstoreapi.dtos.outputs.UserOutput;
import com.segment.analytics.Analytics;
import com.segment.analytics.messages.IdentifyMessage;
import com.segment.analytics.messages.TrackMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SegmentHelper {

    @Autowired
    private Analytics analytics;

    public void identifyEvent(UserOutput userDetails) {
        Map<String, String> map = new HashMap<>();
        map.put("firstName", userDetails.getFirstName());
        map.put("lastName", userDetails.getLastName());
        map.put("email", userDetails.getUsername());

        analytics.enqueue(IdentifyMessage.builder()
                .userId(userDetails.getId().toString())
                .traits(map));
    }

    public void trackEvent(String eventName, String userId, Map<String, Object> properties) {
        analytics.enqueue(TrackMessage.builder(eventName)
                .userId(userId)
                .properties(properties));
    }
}
