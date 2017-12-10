package com.three.sharecare.bootapi.dto;

import java.io.Serializable;
import java.util.List;

public class FindAllFavoriteDto implements Serializable {

    private List<ShareCareDto> favoriteShareCareList;

    private List<BabySittingDto> favoriteBabysittingList;

    private List<EventDto> favoriteEventList;

    public List<ShareCareDto> getFavoriteShareCareList() {
        return favoriteShareCareList;
    }

    public void setFavoriteShareCareList(List<ShareCareDto> favoriteShareCareList) {
        this.favoriteShareCareList = favoriteShareCareList;
    }

    public List<BabySittingDto> getFavoriteBabysittingList() {
        return favoriteBabysittingList;
    }

    public void setFavoriteBabysittingList(List<BabySittingDto> favoriteBabysittingList) {
        this.favoriteBabysittingList = favoriteBabysittingList;
    }

    public List<EventDto> getFavoriteEventList() {
        return favoriteEventList;
    }

    public void setFavoriteEventList(List<EventDto> favoriteEventList) {
        this.favoriteEventList = favoriteEventList;
    }
}
