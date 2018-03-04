package api;

import java.util.List;

/**
 * Created by Thiqah on 2/21/2018.
 */

public interface DataSource {

    void passBookList(List list);

    void passAuthorList(List list);

    void passCoverPhotoList(List list);

}
