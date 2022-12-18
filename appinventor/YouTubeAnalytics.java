package com.gordonlu.youtubeanalytics;

import android.app.Activity;
import android.content.Context;
import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import java.io.BufferedReader;
import java.io.IOException;
import android.app.Activity;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.appinventor.components.runtime.util.AsynchUtil;

import java.lang.Integer;
import java.util.concurrent.TimeUnit;
import java.util.Calendar;
import com.google.appinventor.components.runtime.util.Dates;
import com.google.appinventor.components.runtime.util.YailList;
import com.google.appinventor.components.runtime.util.CsvUtil;
import com.google.appinventor.components.runtime.Web;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ArrayList;
import org.json.JSONArray;
import java.net.URLEncoder;

@DesignerComponent(
        version = 2,
        description = "A non-visible extension that allows you to analyze data of your YouTube channel over the dates, for example, comments, subscribers likes and dislike count gains.<br><br>Developed by Gordon Lu (AICODE), December 1 2022.",
        category = ComponentCategory.EXTENSION,
        nonVisible = true,
        iconName = "https://docs.google.com/drawings/d/e/2PACX-1vSY40yLJFgKf-y5xi60jF5u-PDOOqLbKhllr8G-pkmyOgDs30dwr1Z5oedg-RYY5xzPZA5X3R0pInm_/pub?w=16&h=16")

@SimpleObject(external = true)
//Libraries
@UsesLibraries(libraries = "")
//Permissions
@UsesPermissions(permissionNames = "")

public class YoutubeAnalytics extends AndroidNonvisibleComponent {

    //Activity and Context
    private Context context;
    private Activity activity;
    public ComponentContainer cont;

    String appsScript = "";

    public YoutubeAnalytics(ComponentContainer container){
        super(container.$form());
        this.activity = container.$context();
        this.context = container.$context();
    }

    @SimpleFunction(description = "Creates a report of your YouTube channel. A report includes the increment of subscribers, likes and views every day" + 
    " in the time frame that you have set.")
    public void CreateChannelReport(final Calendar startDate, final Calendar endDate){
        final String start = URLEncoder.encode(Dates.FormatDateTime(startDate, "MMM d, yyyy"));
        final String end = URLEncoder.encode(Dates.FormatDateTime(endDate, "MMM d, yyyy"));
        AsynchUtil.runAsynchronously(new Runnable() {
            @Override
            public void run() {
                try {
                    BufferedReader readStream = new BufferedReader(new InputStreamReader(new URL(appsScript + "?startTime=" + start + "&endTime=" + end + "&action=channelReport").openStream()));
                    String readLine;
                    StringBuilder data = new StringBuilder();
                    while ((readLine = readStream.readLine()) != null) data.append(readLine);
                    readStream.close();
                    final String finalData = data.toString();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String[] raw = finalData.split(",");
                            List<String> firstList= new ArrayList();
                            List<String> secondList = new ArrayList();
                            List<String> thirdList = new ArrayList();
                            List<String> fourthList = new ArrayList();
                            List<String> fifthList = new ArrayList();
                            List<String> sixthList = new ArrayList();
                            List<String> seventhList = new ArrayList();
                            List<String> eighthList = new ArrayList();
                            List<String> ninethList = new ArrayList();
                            List<String> tenthList = new ArrayList();
                                for (int i = 0; i < raw.length; i++) {
                                    if (i % 10 == 0) {
                                        firstList.add(raw[i]);
                                    } else if (i % 10 == 1) {
                                        secondList.add(raw[i]);
                                    } else if (i % 10 == 2) {
                                        thirdList.add(raw[i]);
                                    } else if (i % 10 == 3) {
                                        fourthList.add(raw[i]);
                                    } else if (i % 10 == 4) {
                                        fifthList.add(raw[i]);
                                    } else if (i % 10 == 5) {
                                        sixthList.add(raw[i]);
                                    } else if (i % 10 == 6) {
                                        seventhList.add(raw[i]);
                                    } else if (i % 10 == 7) {
                                        eighthList.add(raw[i]);
                                    } else if (i % 10 == 8) {
                                        ninethList.add(raw[i]);
                                    } else if (i % 10 == 9) {
                                        tenthList.add(raw[i]);
                                    }
                                }
                                ChannelReportCreated(firstList, secondList, thirdList, fourthList, fifthList, sixthList, seventhList, eighthList, ninethList, tenthList);
                        }
                    });
                } catch (IOException e) {
                    Error(e.getMessage());
                }
            }
        });
    }

    @SimpleFunction(description = "Creates a report of your specific YouTube videos. A report includes the increment of subscribers, likes and views every day" + 
    " in the time frame that you have set. Use the 'videos' parameter to limit the number of videos the data is from. For example," + 
    " you might want to create a report only about two of your videos. You can set a maximum of 500 videos. The videos parameter only" + 
    " accepts video IDs.")
    public void CreateVideoReport(final Calendar startDate, final Calendar endDate, final List videos){
        final String start = URLEncoder.encode(Dates.FormatDateTime(startDate, "MMM d, yyyy"));
        final String end = URLEncoder.encode(Dates.FormatDateTime(endDate, "MMM d, yyyy"));
        final String newList = URLEncoder.encode(String.join(",", videos));
        AsynchUtil.runAsynchronously(new Runnable() {
            @Override
            public void run() {
                try {
                    BufferedReader readStream = new BufferedReader(new InputStreamReader(new URL(appsScript + "?startTime=" + start + "&endTime=" + end + "&action=channelReport" + 
                    "&videoIds=" + newList).openStream()));
                    String readLine;
                    StringBuilder data = new StringBuilder();
                    while ((readLine = readStream.readLine()) != null) data.append(readLine);
                    readStream.close();
                    final String finalData = data.toString();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String[] raw = finalData.split(",");
                            List<String> firstList= new ArrayList();
                            List<String> secondList = new ArrayList();
                            List<String> thirdList = new ArrayList();
                            List<String> fourthList = new ArrayList();
                            List<String> fifthList = new ArrayList();
                            List<String> sixthList = new ArrayList();
                            List<String> seventhList = new ArrayList();
                            List<String> eighthList = new ArrayList();
                            List<String> ninethList = new ArrayList();
                            List<String> tenthList = new ArrayList();
                                for (int i = 0; i < raw.length; i++) {
                                    if (i % 10 == 0) {
                                        firstList.add(raw[i]);
                                    } else if (i % 10 == 1) {
                                        secondList.add(raw[i]);
                                    } else if (i % 10 == 2) {
                                        thirdList.add(raw[i]);
                                    } else if (i % 10 == 3) {
                                        fourthList.add(raw[i]);
                                    } else if (i % 10 == 4) {
                                        fifthList.add(raw[i]);
                                    } else if (i % 10 == 5) {
                                        sixthList.add(raw[i]);
                                    } else if (i % 10 == 6) {
                                        seventhList.add(raw[i]);
                                    } else if (i % 10 == 7) {
                                        eighthList.add(raw[i]);
                                    } else if (i % 10 == 8) {
                                        ninethList.add(raw[i]);
                                    } else if (i % 10 == 9) {
                                        tenthList.add(raw[i]);
                                    }
                                }
                                VideoReportCreated(firstList, secondList, thirdList, fourthList, fifthList, sixthList, seventhList, eighthList, ninethList, tenthList);
                        }
                    });
                } catch (IOException e) {
                    Error(e.getMessage());
                }
            }
        });
    }

    @SimpleEvent(description = "This event is fired when an error has occurred.")
    public void Error(String message){
        EventDispatcher.dispatchEvent(this, "Error", message);
    }

    @SimpleEvent(description = "This event is fired when the extension has retrieved a list of data from your YouTube channel.")
    public void ChannelReportCreated(List timestamps, List views, List estimatedMinutesWatched, List averageViewDuration, List averageViewPercentage, List likes, List dislikes,
    List subscribersGained, List subscribersLost, List commentsCount){
        EventDispatcher.dispatchEvent(this, "ChannelReportCreated", timestamps, views, estimatedMinutesWatched, averageViewDuration, averageViewPercentage, likes, dislikes, subscribersGained,
        subscribersLost, commentsCount);
    }

    @SimpleEvent(description = "This event is fired when the extension has retrieved a list of data from your YouTube videos.")
    public void VideoReportCreated(List timestamps, List views, List estimatedMinutesWatched, List averageViewDuration, List averageViewPercentage, List likes, List dislikes,
    List subscribersGained, List subscribersLost, List commentsCount){
        EventDispatcher.dispatchEvent(this, "VideoReportCreated", timestamps, views, estimatedMinutesWatched, averageViewDuration, averageViewPercentage, likes, dislikes, subscribersGained,
        subscribersLost, commentsCount);
    }

    @DesignerProperty(editorType = "string", defaultValue = "")
    @SimpleProperty(description = "Specifies the URL to your Google Apps Script.")
    public void ScriptUrl(String s) {
        appsScript = s;
    }

    @SimpleProperty(description = "Specifies the URL to your Google Apps Script.")
    public String ScriptUrl() {
        return appsScript;
    }
}
