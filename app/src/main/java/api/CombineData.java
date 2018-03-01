package api;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Thiqah on 3/1/2018.
 */

public class CombineData implements Parcelable{

    //Author
    private int primaryAuthor;
    private int iDBookAuthor;
    private String firstName;
    private String lastName;

    //Book
    private int primaryBook;
    private String title;
    private String description;
    private int pageCount;
    private String excerpt;
    private String publishDate;

    //Cover Photo
    private int primaryCoverPhoto;
    private int iDBookCoverPhoto;
    private String url;

    public CombineData(int primaryAuthor, int iDBookAuthor, String firstName, String lastName,
                       int primaryBook, String title, String description, int pageCount, String excerpt,
                       String publishDate, int primaryCoverPhoto, int iDBookCoverPhoto, String url) {

        this.primaryAuthor = primaryAuthor;
        this.iDBookAuthor = iDBookAuthor;
        this.firstName = firstName;
        this.lastName = lastName;
        this.primaryBook = primaryBook;
        this.title = title;
        this.description = description;
        this.pageCount = pageCount;
        this.excerpt = excerpt;
        this.publishDate = publishDate;
        this.primaryCoverPhoto = primaryCoverPhoto;
        this.iDBookCoverPhoto = iDBookCoverPhoto;
        this.url = url;
    }

    protected CombineData(Parcel in) {
        primaryAuthor = in.readInt();
        iDBookAuthor = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        primaryBook = in.readInt();
        title = in.readString();
        description = in.readString();
        pageCount = in.readInt();
        excerpt = in.readString();
        publishDate = in.readString();
        primaryCoverPhoto = in.readInt();
        iDBookCoverPhoto = in.readInt();
        url = in.readString();
    }

    public static final Creator<CombineData> CREATOR = new Creator<CombineData>() {
        @Override
        public CombineData createFromParcel(Parcel in) {
            return new CombineData(in);
        }

        @Override
        public CombineData[] newArray(int size) {
            return new CombineData[size];
        }
    };

    public int getPrimaryAuthor() {
        return primaryAuthor;
    }

    public int getiDBookAuthor() {
        return iDBookAuthor;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getPrimaryBook() {
        return primaryBook;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPageCount() {
        return pageCount;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public int getPrimaryCoverPhoto() {
        return primaryCoverPhoto;
    }

    public int getiDBookCoverPhoto() {
        return iDBookCoverPhoto;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(primaryAuthor);
        dest.writeInt(iDBookAuthor);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeInt(primaryBook);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeInt(pageCount);
        dest.writeString(excerpt);
        dest.writeString(publishDate);
        dest.writeInt(primaryCoverPhoto);
        dest.writeInt(iDBookCoverPhoto);
        dest.writeString(url);
    }
}
