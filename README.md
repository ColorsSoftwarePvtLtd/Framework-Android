# LogR
LogR is used for Login and Registration.

[ ![Download](https://api.bintray.com/packages/braj24/maven/LogR/images/download.svg) ](https://bintray.com/braj24/maven/LogR/_latestVersion)

# Download

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

Add the dependencies

	dependencies {
	        compile 'com.colorssoftware.library:library:1.0.0'
	}

# Usage



    LogR l=new LogR();
                LogR.allowAllSSL();
                l.login(mUsername.getText().toString().trim(), mPassword.getText().toString().trim(),      "https://colourssoftware.com:5443/customer/login", new LogR.AsyncResponse() {
                    /**
                     * Response coming from server
                     * Use it in your code
                     * @param out
                     */
                    @Override
                    public void onSuccess(String out) {
                        try {
                            if(!out.isEmpty()){
                                JSONObject jsonObject=new JSONObject(out);
                                JSONObject jsonObject1=jsonObject.getJSONObject("customer");
                                id=jsonObject1.getString("_id");
                                String username=jsonObject1.getString("username");
                                String email=jsonObject1.getString("email");
                                mResponse.setText("Customer Id: " + id + "\n"+"Name: " +username +"\n"+"Email: " +email);
                            //Passing customer id to Billing
                            Intent intent=new Intent(LoginActivity.this,BillingActivity.class);
                            intent.putExtra("id",id);
                            startActivity(intent);
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Response is empty", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onFailure(String fail) {

                    }
                });
