package com.kakaotechcampus.journey_planner.presentation.traveler;

import com.kakaotechcampus.journey_planner.application.memberplan.TravelerService;
import com.kakaotechcampus.journey_planner.application.plan.PlanService;
import com.kakaotechcampus.journey_planner.presentation.traveler.dto.response.TravelerResponse;
import com.kakaotechcampus.journey_planner.presentation.utils.MessagingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
@MessageMapping("/plan/{planId}/travelers")
public class TravelerController {

    private final PlanService planService;
    private final MessagingUtil messagingUtil;
    private static final String DESTINATION = "travelers";
    private final TravelerService travelerService;

    @MessageMapping("/init")
    public void initTravelers(@DestinationVariable Long planId) {
        List<TravelerResponse> travelerResponses = planService.getInvitedTravelers(planId);

        messagingUtil.sendResponse(planId, DESTINATION, "TRAVELER_INIT", "travelers", travelerResponses);
    }

    @MessageMapping("/{travelerId}/delete")
    public void deleteTraveler(
            @DestinationVariable Long planId,
            @DestinationVariable Long travelerId) {

        travelerService.deleteTraveler(travelerId);
        messagingUtil.sendResponse(planId, DESTINATION, "TRAVELER_DELETE", "travelerId", travelerId);
    }
}
