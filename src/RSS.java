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

public class RSS {

    public static void main(String[] args) throws Exception {

        Feed eteknix = new Feed();
        Feed bbc = new Feed();
        eteknix.printFeed(eteknix.getFeed("http://www.eteknix.com/feed/", true));
        bbc.printFeed(bbc.getFeed("http://feeds.bbci.co.uk/news/rss.xml?edition=uk", true));

    }
}
