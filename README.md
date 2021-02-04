# BFAA-Final
This App can find github account users using Github API and is able to save favourite users using SQLLite.

#To Use this Project:
You need to change the GITHUB API token with your own Token in Build.Gradle(app):

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
        buildConfigField "String", "GITHUB_TOKEN", '"#####YourOwnToken#######"'
    }

