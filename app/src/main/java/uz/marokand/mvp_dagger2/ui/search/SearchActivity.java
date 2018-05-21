package uz.marokand.mvp_dagger2.ui.search;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import uz.marokand.mvp_dagger2.R;
import uz.marokand.mvp_dagger2.model.Repo;
import uz.marokand.mvp_dagger2.model.User;
import uz.marokand.mvp_dagger2.ui.main.MainActivity;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mSearchView;
    private User mUser;

    public static ProgressDialog showProgress(Context context, @StringRes int messageId) {
        ProgressDialog dialog = null;
        try {
            dialog = new ProgressDialog(context, R.style.AlertDialog);
            dialog.setMessage(context.getString(messageId));
            dialog.setCancelable(false);
            return dialog;
        } finally {
            if (dialog != null) {
                dialog.show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mSearchView = findViewById(R.id.login);

        mSearchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    /*
                     * hide keyboard
                     */
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);
                    }

                    String login = mSearchView.getText().toString();

                    if (login.isEmpty()) {
                        showMessage(R.string.empty_login);
                        return false;
                    }

                    if (noConnection()) {
                        showMessage(R.string.no_internet);
                        return false;
                    }

                    mUser = new User(login);

                    new GetUserTask().execute("https://api.github.com/users/" + login);
                    return true;
                }
                return false;
            }
        });

        findViewById(R.id.btn_go).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String login = mSearchView.getText().toString();

        if (login.isEmpty()) {
            showMessage(R.string.empty_login);
            return;
        }

        if (noConnection()) {
            showMessage(R.string.no_internet);
            return;
        }

        mUser = new User(login);

        new GetUserTask().execute("https://api.github.com/users/" + login);
    }

    private void showMessage(String message) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage(message)
                .setPositiveButton(R.string.action_ok, null)
                .create().show();
    }

    private void showMessage(@StringRes int messageId) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage(messageId)
                .setPositiveButton(R.string.action_ok, null)
                .create().show();
    }

    public boolean noConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm != null ? cm.getActiveNetworkInfo() : null;
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return !activeNetwork.isConnected();
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return !activeNetwork.isConnected();
        }
        return true;
    }

    private void startMain() {
        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
        intent.putExtra(User.USER, mUser);
        startActivity(intent);
    }

    private String convertISToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringBuilder result = new StringBuilder();
        try {
            while ((line = bufferedReader.readLine()) != null)
                result.append(line);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }

    @SuppressLint("StaticFieldLeak")
    class GetUserTask extends AsyncTask<String, Void, Response> {

        private ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = showProgress(SearchActivity.this, R.string.msg_api_calling);
        }

        @Override
        protected Response doInBackground(String... urls) {
            int responseCode = HttpURLConnection.HTTP_OK;
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                responseCode = urlConnection.getResponseCode();
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    return new Response(convertISToString(in),
                            urlConnection.getResponseCode());
                } finally {
                    urlConnection.disconnect();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new Response("", responseCode);
        }

        @Override
        protected void onPostExecute(Response result) {
            super.onPostExecute(result);
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
            if (result == null) {
                showMessage(R.string.no_internet);
                return;
            }
            if (result.code == HttpURLConnection.HTTP_NOT_FOUND) {
                showMessage(R.string.user_not_found);
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(result.response);
                mUser.setAvatar(jsonObject.has("avatar_url")
                        && !jsonObject.isNull("avatar_url")
                        ? jsonObject.getString("avatar_url") : "");
                mUser.setName(jsonObject.has("name")
                        && !jsonObject.isNull("name")
                        ? jsonObject.getString("name") : "");
                mUser.setCompany(jsonObject.has("company")
                        && !jsonObject.isNull("company")
                        ? jsonObject.getString("company") : "");
                mUser.setAddress(jsonObject.has("location")
                        && !jsonObject.isNull("location")
                        ? jsonObject.getString("location") : "");
                new GetRepoTask().execute("https://api.github.com/users/" + mUser.getLogin() + "/repos");
            } catch (JSONException e) {
                e.printStackTrace();
                showMessage(e.getMessage());
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    class GetRepoTask extends AsyncTask<String, Void, Response> {

        private ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = showProgress(SearchActivity.this, R.string.msg_api_calling);
        }

        @Override
        protected Response doInBackground(String... strings) {
            int responseCode = HttpURLConnection.HTTP_OK;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    responseCode = urlConnection.getResponseCode();
                    return new Response(convertISToString(in),
                            urlConnection.getResponseCode());
                } finally {
                    urlConnection.disconnect();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new Response("", responseCode);
        }

        @Override
        protected void onPostExecute(Response result) {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
            if (result == null) {
                showMessage(R.string.no_internet);
                return;
            }
            if (result.code == HttpURLConnection.HTTP_NOT_FOUND) {
                showMessage(R.string.user_not_found);
                return;
            }
            try {
                JSONArray jsonArray = new JSONArray(result.response);
                int length = jsonArray.length();
                for (int i = 0; i < length; i++) {
                    JSONObject jsonObject = jsonArray.optJSONObject(i);
                    if (jsonObject == null) continue;
                    Repo repository = new Repo(jsonObject.has("name")
                            && !jsonObject.isNull("name")
                            ? jsonObject.getString("name") : "",
                            jsonObject.has("description")
                                    && !jsonObject.isNull("description")
                                    ? jsonObject.getString("description") : "",
                            jsonObject.has("html_url")
                                    && !jsonObject.isNull("html_url")
                                    ? jsonObject.getString("html_url") : "",
                            jsonObject.optInt("watchers_count"),
                            jsonObject.optInt("stargazers_count"),
                            jsonObject.optInt("forks_count"));
                    mUser.addRepository(repository);
                }
                startMain();
            } catch (JSONException e) {
                e.printStackTrace();
                showMessage(e.getMessage());
            }
        }
    }

    class Response {
        private String response;
        private int code;

        Response(String response, int code) {
            this.response = response;
            this.code = code;
            Log.i("Response", "Code: " + code);
            Log.i("Response", "Value: " + response);
        }

        public String getResponse() {
            return response;
        }

        public int getCode() {
            return code;
        }
    }
}
