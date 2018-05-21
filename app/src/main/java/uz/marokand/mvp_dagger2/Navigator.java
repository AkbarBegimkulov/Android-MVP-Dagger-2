package uz.marokand.mvp_dagger2;

import android.content.Context;
import android.content.Intent;

import javax.inject.Inject;
import javax.inject.Singleton;

import uz.marokand.mvp_dagger2.data.model.User;
import uz.marokand.mvp_dagger2.ui.main.MainActivity;

/**
 * Provides methods to navigate to the different activities in the application.
 *
 * @author akbar
 * @since 2018, May 21
 */

@Singleton
public final class Navigator {

    public static final String USER = "user";

    @Inject
    Navigator() {
    }

    public void toMain(Context context, User user) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(USER, user);
        context.startActivity(intent);
    }

}
