package uz.marokand.mvp_dagger2.data;

import java.util.List;

import rx.Single;
import uz.marokand.mvp_dagger2.data.model.Repo;
import uz.marokand.mvp_dagger2.data.model.User;

/**
 * The service to call application api methods
 *
 * @author akbar
 * @since 2018, May 21
 */

public interface ApiService {

    byte CONNECT_TIMEOUT = 120;
    byte WRITE_TIMEOUT = 120;
    byte READ_TIMEOUT = 120;

    /**
     * Checking internet connection
     *
     * @return true if there is not internet connection, otherwise false
     */
    boolean noConnection();

    Single<User> getUser(String userName);

    Single<List<Repo>> getRepositories(String userName);
}
