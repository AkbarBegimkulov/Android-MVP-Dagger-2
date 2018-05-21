package uz.marokand.mvp_dagger2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author akbar
 * @since 2018, May 20
 */

public class Repo implements Parcelable {

    public static final Creator<Repo> CREATOR = new Creator<Repo>() {
        @Override
        public Repo createFromParcel(Parcel in) {
            return new Repo(in);
        }

        @Override
        public Repo[] newArray(int size) {
            return new Repo[size];
        }
    };

    @SerializedName("name")
    @Expose
    private String mName;
    @SerializedName("description")
    @Expose
    private String mDescription;
    @SerializedName("html_url")
    @Expose
    private String mHtmlUrl;
    @SerializedName("watchers_count")
    @Expose
    private int mWatchersCount;
    @SerializedName("stargazers_count")
    @Expose
    private int mStargazersCount;
    @SerializedName("forks_count")
    @Expose
    private int mForksCount;

    public Repo(String name, String description, String htmlUrl,
                int watchersCount, int stargazersCount, int forksCount) {
        mName = name;
        mDescription = description;
        mHtmlUrl = htmlUrl;
        mWatchersCount = watchersCount;
        mStargazersCount = stargazersCount;
        mForksCount = forksCount;
    }

    protected Repo(Parcel in) {
        mName = in.readString();
        mDescription = in.readString();
        mHtmlUrl = in.readString();
        mWatchersCount = in.readInt();
        mStargazersCount = in.readInt();
        mForksCount = in.readInt();
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getUrl() {
        return mHtmlUrl;
    }

    public String getWatchers() {
        return getFormattedNumber(mWatchersCount);
    }

    public String getStars() {
        return getFormattedNumber(mStargazersCount);
    }

    public String getForks() {
        return getFormattedNumber(mForksCount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mDescription);
        dest.writeString(mHtmlUrl);
        dest.writeInt(mWatchersCount);
        dest.writeInt(mStargazersCount);
        dest.writeInt(mForksCount);
    }

    private String getFormattedNumber(int number) {
        if (number < 1000) return String.valueOf(number);
        StringBuilder result = new StringBuilder();
        while (number > 0) {
            if (number < 1000) result.insert(0, number);
            else {
                result.insert(0, ", " + number % 1000);
            }
            number /= 1000;
        }
        return result.toString();
    }
}
