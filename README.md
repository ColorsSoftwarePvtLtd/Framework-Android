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
	        compile 'com.colorssoftware.library:library:1.0.2'
	}
	
or maven

    <dependency>
    <groupId>com.colorssoftware.library</groupId>
    <artifactId>library</artifactId>
    <version>1.0.2</version>
    <type>pom</type>
    </dependency>


# Usage


    //for Login
    LogR logr=new LogR(); // Call LogR class
    LogR.allowAllSSL(); //Allowing all the certificates
    logr.login("username", "password","put login url", new LogR.AsyncResponse() {
                    /**
                     * Response coming from server
                     * Use it in your code
                     * @param out
                     */
                    @Override
                    public void onSuccess(String response) {
                       Toast.makeText(this,out,Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onFailure(String failure) {

                    }
                });
		
    //for registration	
    logr.register("username", "password", "firstname","lastname", "email","put registration url", new LogR.AsyncResponse() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(String failure) {
                        Toast.makeText(this, failure, Toast.LENGTH_SHORT).show();
                    }
                });
		
# LICENSE

   Copyright 2017 Colors Software Pvt Ltd
    
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
   
         http://www.apache.org/licenses/LICENSE-2.0
     

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
		
