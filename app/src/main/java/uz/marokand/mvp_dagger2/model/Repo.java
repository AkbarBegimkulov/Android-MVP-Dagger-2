package uz.marokand.mvp_dagger2.model;

import android.os.Parcel;
import android.os.Parcelable;

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
    private String mName;
    private String mDescription;
    private String mHtmlUrl;
    private int mWatchersCount;
    private int mStargazersCount;
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
        return String.valueOf(mWatchersCount);
    }

    public String getStars() {
        return String.valueOf(mStargazersCount);
    }

    public String getForks() {
        return String.valueOf(mForksCount);
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
}
