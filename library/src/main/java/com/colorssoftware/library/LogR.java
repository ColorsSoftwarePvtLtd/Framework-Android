package com.colorssoftware.library;

import android.os.AsyncTask;
import android.util.Log;

import com.colorssoftware.library.model.Account;
import com.colorssoftware.library.model.Billing;
import com.colorssoftware.library.model.SignUpRequest;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Iterator;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by colors on 7/4/17.
 */

public class LogR implements X509TrustManager {

    private static TrustManager[] trustManagers;
    private static final X509Certificate[] _AcceptedIssuers = new X509Certificate[]{};
    private static final String TAG = "Logr";
    private Account mAccount;
    private SignUpRequest mSignUpRequest;
    private Billing mBilling;

    /**
     * Interface for server Response
     */
    public interface AsyncResponse {
        void onSuccess(String result);
        void onFailure(String error);
    }

    private AsyncResponse response=null;

    /**
     * Set login params
     * @param username
     * @param password
     */
    public void login(String username,String password,String url,AsyncResponse response){
        mAccount = new Account();
        mAccount.setName(username);
        mAccount.setPassword(password);
        mAccount.setUrl(url);
        new SendPostRequest().execute();
        this.response=response;
    }

    /**
     * Set register params
     * @param username
     * @param email
     * @param password
     */
    public void register(String username, String password, String first,String lastname, String email, String url, AsyncResponse response){
        this.response=response;
        mSignUpRequest = new SignUpRequest();
        mSignUpRequest.setName(username);
        mSignUpRequest.setPassword(password);
        mSignUpRequest.setFirstname(first);
        mSignUpRequest.setLastname(lastname);
        mSignUpRequest.setEmail(email);
        mSignUpRequest.setUrl(url);
        new RegisterRequest().execute();
    }

    /**
     *
     * @param id
     * @param fname
     * @param lname
     * @param company
     * @param address1
     * @param address2
     * @param city
     * @param state
     * @param zip
     * @param country
     * @param url
     * @param response
     */
    public void payBill(String id,String fname,String lname,String company,String address1,String address2,String city,String state,String zip,String country,
            String url,AsyncResponse response){
        this.response=response;
        mBilling =new Billing();
        mBilling.setId(id);
        mBilling.setFname(fname);
        mBilling.setLname(lname);
        mBilling.setCompany(company);
        mBilling.setAddress1(address1);
        mBilling.setAdress2(address2);
        mBilling.setCity(city);
        mBilling.setState(state);
        mBilling.setZipcode(zip);
        mBilling.setCountry(country);
        mBilling.setUrl(url);
        new BillingRequest().execute();
    }
    /**
     * Creating AsyncTask for POST method
     */
    private class SendPostRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){}

        //Background process
        protected String doInBackground(String... arg0) {

            HttpURLConnection conn = null;
            try {
                URL url;
                JSONObject postDataParams;
                /**
                 * Checking condition for methods like
                 * login()
                 */
                Log.d(TAG, "Login: " + mAccount.getUrl());
                url = new URL(mAccount.getUrl()); // here is your URL path
                postDataParams = new JSONObject();
                postDataParams.put("username", mAccount.getName());
                postDataParams.put("password", mAccount.getPassword());
                Log.e("params",postDataParams.toString());

                /**
                 * Making HTTP connection
                 */
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches (false);
                //OutputStream
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                /**
                 * Getting response code
                 */

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();
                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
            finally {
                if(conn!=null) conn.disconnect();
            }

        }
        /**
         * Getting response from Server
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result==null){
                response.onFailure("Result is blank");
            }
            else {
                response.onSuccess(result);
                Log.d(TAG, "onPostExecute: " +result);
            }

        }
    }

    /**
     * Register AsyncTask
     */
    private class RegisterRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){}

        //Background process
        protected String doInBackground(String... arg0) {

            try {
                URL url;
                JSONObject postDataParams;
                /**
                 * Checking condition for methods like
                 * register() etc
                 **/
                Log.d(TAG, "Register: " +mSignUpRequest.getUrl());
                url = new URL(mSignUpRequest.getUrl()); // here is your URL path
                postDataParams = new JSONObject();
                postDataParams.put("username", mSignUpRequest.getName());
                postDataParams.put("password", mSignUpRequest.getPassword());
                postDataParams.put("firstname",mSignUpRequest.getFirstname());
                postDataParams.put("lastname",mSignUpRequest.getLastname());
                postDataParams.put("email",mSignUpRequest.getEmail());

                Log.e("params",postDataParams.toString());

                /**
                 * Making HTTP connection
                 */
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches (false);
                //OutputStream
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                /**
                 * Getting response code
                 */
                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }

        }

        /**
         * Getting response from Server
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            if(result.isEmpty()){
                response.onFailure("No response");
            }
            else {
                response.onSuccess(result);
                Log.d(TAG, "onPostExecute: " +result);
            }

        }
    }

    /**
     * Billing AsyncTask
     */
    private class BillingRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){}

        //Background process
        protected String doInBackground(String... arg0) {

            HttpURLConnection conn;
            try {

                URL url;
                JSONObject postDataParams;
                /**
                 * Checking condition for methods like
                 * Billing details
                 */
                url = new URL(mBilling.getUrl()); // here is your URL path
                Log.d(TAG, "Billing: " +mBilling.getUrl());
                postDataParams = new JSONObject();
                postDataParams.put("customerId",mBilling.getId());
                postDataParams.put("firstname",mBilling.getFname());
                postDataParams.put("lastname",mBilling.getLname());
                postDataParams.put("company",mBilling.getCompany());
                postDataParams.put("address_1",mBilling.getAddress1());
                postDataParams.put("address_2",mBilling.getAdress2());
                postDataParams.put("city",mBilling.getCity());
                postDataParams.put("state",mBilling.getState());
                postDataParams.put("postcode",mBilling.getZipcode());
                postDataParams.put("country",mBilling.getCountry());
                Log.e("params",postDataParams.toString());

                /**
                 * Making HTTP connection
                 */

                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches (false);

                //OutputStream
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                /**
                 * Getting response code
                 */

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }

        }

        /**
         * Getting response from Server
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            if(result.isEmpty()){
                response.onFailure("No response");
            }
            else{
                response.onSuccess(result);
                Log.d(TAG, "onPostExecute: " +result);
            }
        }
    }

    /**
     *
     * @param params
     * @return
     * @throws Exception
     */
    private String getPostDataString(JSONObject params) throws Exception {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator<String> itr = params.keys();
        while(itr.hasNext()){
            String key= itr.next();
            Object value = params.get(key);
            if (first){
                first = false;
            }
            else{
                result.append("&");
            }
            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

    /**
     * Allowing all the certification
     */
    @Override
    public void checkClientTrusted(
            X509Certificate[] x509Certificates, String s)
            throws java.security.cert.CertificateException {
    }

    @Override
    public void checkServerTrusted(
            X509Certificate[] x509Certificates, String s)
            throws java.security.cert.CertificateException {
    }

    public boolean isClientTrusted(X509Certificate[] chain) {
        return true;
    }

    public boolean isServerTrusted(X509Certificate[] chain) {
        return true;
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return _AcceptedIssuers;
    }

    public static void allowAllSSL() {
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }

        });

        SSLContext context = null;
        if (trustManagers == null) {
            trustManagers = new TrustManager[]{new LogR()};
        }

        try {
            context = SSLContext.getInstance("TLS");
            context.init(null, trustManagers, new SecureRandom());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }

        HttpsURLConnection.setDefaultSSLSocketFactory(context != null ? context.getSocketFactory() : null);
    }
    //End
}