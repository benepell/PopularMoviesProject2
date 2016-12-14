package eu.pellerito.popularmoviesproject2.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieReviewResult {


    @SerializedName("page")
    private int mPage;


    @SerializedName("results")
    private List<MovieReview> mResultList;


    @SerializedName("total_pages")
    private int mTotalPage;


    @SerializedName("total_results")
    private int mTotalResults;


    public int getPage() {
        return mPage;
    }

    public void setPage(int page) {
        mPage = page;
    }

    public List<MovieReview> getResultList() {
        return mResultList;
    }

    public void setResultList(List<MovieReview> resultList) {
        mResultList = resultList;
    }

    public int getTotalPage() {
        return mTotalPage;
    }

    public void setTotalPage(int totalPage) {
        mTotalPage = totalPage;
    }

    public int getTotalResults() {
        return mTotalResults;
    }

    public void setTotalResults(int totalResults) {
        mTotalResults = totalResults;
    }
}
