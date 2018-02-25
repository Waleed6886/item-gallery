package api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Thiqah on 2/18/2018.
 */

public class Author implements Parcelable{
    @SerializedName("ID")
    @Expose
    private int iD;
    @SerializedName("IDBook")
    @Expose
    private int iDBook;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;

    public Author(int iD, int iDBook, String firstName, String lastName) {
        this.iD = iD;
        this.iDBook = iDBook;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    protected Author(Parcel in) {
        iD = in.readInt();
        iDBook = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
    }

    public static final Creator<Author> CREATOR = new Creator<Author>() {
        @Override
        public Author createFromParcel(Parcel in) {
            return new Author(in);
        }

        @Override
        public Author[] newArray(int size) {
            return new Author[size];
        }
    };

    public int getID() {
        return iD;
    }

    public void setID(int iD) {
        this.iD = iD;
    }

    public int getIDBook() {
        return iDBook;
    }

    public void setIDBook(int iDBook) {
        this.iDBook = iDBook;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(iD);
        parcel.writeInt(iDBook);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
    }
}
