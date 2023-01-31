# BFAA-Final
This App can find github account users using Github API and is able to save favourite users using SQLLite.

### To Use this Project:
The Token on this project is currently invalid anymore
You need to change the GITHUB API token with your own token in Build.Gradle(app):

```
android {
    compileSdkVersion 29
    buildToolsVersion "30.0.1"
    
    defaultConfig {
        applicationId "com.dicoding.listviewparcel"
        minSdkVersion 21
        targetSdkVersion 29
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField "String", "GITHUB_TOKEN", '"<Your Own Token Here>"'
    }
}
```
    
    
### To Create a token, click profile on the right corner of your github account:
settings -> developer settings -> personal access token -> generate token

### User List Page (First page)
![Screenshot from 2022-03-23 18-41-40](https://user-images.githubusercontent.com/66354919/215674917-675bf7cc-6cfc-40da-83e8-b7d04adbff50.png)

### Save as favourite
![Screenshot from 2022-03-23 18-43-05](https://user-images.githubusercontent.com/66354919/215675057-55f53f0a-812c-4cb9-ab0b-0ce4b700465e.png)
![Screenshot from 2022-03-23 18-41-52](https://user-images.githubusercontent.com/66354919/215674982-5dcc1383-c20e-4e4c-89c2-e07e9ab8345b.png)

### Followers and Following list
![Screenshot from 2022-03-23 18-42-11](https://user-images.githubusercontent.com/66354919/215674996-a0e32739-c74f-4b59-8133-7a672a495683.png)
![Screenshot from 2022-03-23 18-42-19](https://user-images.githubusercontent.com/66354919/215675011-d11211f4-3d46-4ad9-a243-9eb659706e96.png)

### Search user
![Screenshot from 2022-03-23 18-42-50](https://user-images.githubusercontent.com/66354919/215675222-6fa337c7-9b21-4ac6-9781-c22779dc00d0.png)

### Favourite list data
![Screenshot from 2022-03-23 18-44-23](https://user-images.githubusercontent.com/66354919/215675273-c0331357-731b-4887-8fbe-0ce8a35d32dd.png)

### Alarm
![Screenshot from 2022-03-23 18-44-32](https://user-images.githubusercontent.com/66354919/215675302-40d8a19a-9d89-497f-9a76-5509bcbc4e86.png)





