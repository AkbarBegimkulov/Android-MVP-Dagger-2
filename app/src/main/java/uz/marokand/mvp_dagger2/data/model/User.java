package uz.marokand.mvp_dagger2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akbar
 * @since 2018, May 20
 */

public class User implements Parcelable {

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @SerializedName("login")
    @Expose
    private String mLogin;
    @SerializedName("avatar_url")
    @Expose
    private String mAvatar;
    @SerializedName("name")
    @Expose
    private String mName;
    @SerializedName("company")
    @Expose
    private String mCompany;
    @SerializedName("location")
    @Expose
    private String mAddress;
    private List<Repo> mRepositories;

    protected User(Parcel in) {
        mLogin = in.readString();
        mAvatar = in.readString();
        mName = in.readString();
        mCompany = in.readString();
        mAddress = in.readString();
        mRepositories = in.createTypedArrayList(Repo.CREATOR);
    }

    public String getLogin() {
        return mLogin;
    }

    public void setLogin(String login) {
        mLogin = login;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getCompany() {
        return mCompany;
    }

    public void setCompany(String company) {
        mCompany = company;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getSecondaryText() {
        String secondaryText = mLogin;
        if (mCompany != null && !mCompany.equals("")) {
            secondaryText += " / " + mCompany;
        }
        if (mAddress != null && !mAddress.equals("")) {
            secondaryText += " / " + mAddress;
        }
        return secondaryText;
    }

    public List<Repo> getRepositories() {
        return mRepositories == null ? new ArrayList<>() : mRepositories;
    }

    public void addRepository(Repo repo) {
        if (mRepositories == null) mRepositories = new ArrayList<>();
        mRepositories.add(repo);
    }

    public void addRepositories(List<Repo> repositories) {
        if (repositories == null) return;
        if (mRepositories == null) mRepositories = new ArrayList<>();
        mRepositories.addAll(repositories);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mLogin);
        dest.writeString(mAvatar);
        dest.writeString(mName);
        dest.writeString(mCompany);
        dest.writeString(mAddress);
        dest.writeTypedList(mRepositories);
    }
}
