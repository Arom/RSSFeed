/*
        RSSFeed  v .0.1 - Used to parse and output the RSS feed.
        Developed for my IRC Bot project, however can be implemented anywhere you like.
        Copyright (C) 2013 Christopher Ilkow

        This program is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        (at your option) any later version.

        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.
*/

//Rome is used to fetch the RSS feed and get various properties of it
//Bit.ly import for shortening the long links into more compact use
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import com.rosaloves.bitlyj.Url;

import static com.rosaloves.bitlyj.Bitly.*;

import java.util.Date;


public class Feed {

    private Url shortUrl;
    private URL url;
    private int today;
    private Date lastUpdated;
    private List<SyndEntry> entries;

    /*
        Obvious, checks for updates, compares the time of the last update to the current time and displays if finds
        something new.
     */
    public void checkUpdates() {
        System.out.println("Last updated on:  " + lastUpdated);
        Iterator<SyndEntry> newEntries = getFeed(url.toString(), false);

        while (newEntries.hasNext()) {
            SyndEntry entry = newEntries.next();
            if (entry.getPublishedDate().after(this.lastUpdated)) {
                lastUpdated = entry.getPublishedDate();
                System.out.println(entry.getTitle() + " " + shortUrl.getShortUrl());
                //You should enter your own details in the method below, register at bit.ly to get those.
                shortUrl = as("rssshort", "R_6d831dc36b5b3497feec2dc671bdec13").call(shorten(entry.getLink()));
                System.out.println();
            }
        }
    }
    /*
    Gets the feed from the link using Rome library. Also if true, will set the new update date, usually needed
    the first time the Feed is requested.
    //TODO replace deprecated getDay() method, used for simplicity for now.
     */
    public Iterator<SyndEntry> getFeed(String feedUrl, boolean updateDate)  {

        this.today = new Date().getDay();
        try{
            this.url = new URL(feedUrl);
        }catch(MalformedURLException ex){
            System.err.print("Incorrect URL has been entered, feed could not be fetched");
        }
        try{
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(httpConnection));
            entries = feed.getEntries();

        }catch(IOException ex){
            System.err.print("Error opening the connection to the URL" +ex.getStackTrace());
        }catch(FeedException ex){
            System.err.print("Error while fetching the feed" + ex.getStackTrace());
        }
        Iterator<SyndEntry> itEntries = entries.iterator();

        if (updateDate) {
            lastUpdated = entries.get(0).getPublishedDate();
        }
        return itEntries;
    }
   /*
   Prints the feed, requires the iterator from getFeed() method.
   Will print out <FeedTitle> <shortenedLink>
    */
    public void printFeed(Iterator<SyndEntry> itEntries) {

        while (itEntries.hasNext()) {
            SyndEntry entry = itEntries.next();
            if (entry.getPublishedDate().getDay() == today) {
                shortUrl = as("rssshort", "R_6d831dc36b5b3497feec2dc671bdec13").call(shorten(entry.getLink()));
                System.out.println(entry.getTitle() + " : " + shortUrl.getShortUrl());
                System.out.println("------------------------------------------");
            }
        }
    }
}
