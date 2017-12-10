package com.three.sharecare.bootapi.dto;

import java.io.Serializable;
import java.util.List;

public class FindAllShareDto implements Serializable{

    private List<ShareCareDto> shareCareList;

    private List<BabySittingDto> babySittingList;

    private List<EventDto> eventList;

    public List<ShareCareDto> getShareCareList() {
        return shareCareList;
    }

    public void setShareCareList(List<ShareCareDto> shareCareList) {
        this.shareCareList = shareCareList;
    }

    public List<BabySittingDto> getBabySittingList() {
        return babySittingList;
    }

    public void setBabySittingList(List<BabySittingDto> babySittingList) {
        this.babySittingList = babySittingList;
    }

    public List<EventDto> getEventList() {
        return eventList;
    }

    public void setEventList(List<EventDto> eventList) {
        this.eventList = eventList;
    }
}
