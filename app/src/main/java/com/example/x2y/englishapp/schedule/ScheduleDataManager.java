package com.example.x2y.englishapp.schedule;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * 用于管理英语学习计划设置的相关数据
 */
public class ScheduleDataManager {
    public static final String bookLibraryPageName = "bookLibraryPageName";
    public static final String bookLibraryPageURL = "http://word.iciba.com/?action=index&reselect=y";

    private static ScheduleDataManager scheduleDataManager = null;

    private Bookshelf bookshelf = null;
    private ScheduleModify scheduleModify = null;
    private BookLibrary bookLibrary = null;


    private ScheduleDataManager(){
    };

    //获得数据管理类的对象
    public static ScheduleDataManager instance(){
        if(scheduleDataManager == null){
            scheduleDataManager = new ScheduleDataManager();
        }
        return scheduleDataManager;
    }

    public Bookshelf getBookshelf(){
        return bookshelf;
    }

    public void setBookshelf(Bookshelf bookshelf){
        this.bookshelf = bookshelf;
    }

    public ScheduleModify getScheduleModify(){
        return scheduleModify;
    }

    public void setScheduleModify(ScheduleModify scheduleModify){
        this.scheduleModify = scheduleModify;
    }

    public void setBookLibrary(BookLibrary bookLibrary){
        this.bookLibrary = bookLibrary;
    }

    public BookLibrary getBookLibrary(){
        return bookLibrary;
    }


    public static void clearDate(){
        scheduleDataManager = null;
    }


    public void saveData(){
        bookshelf.saveBookSelected();
        scheduleModify.saveBookSchedule();
    }


    public static ArrayList<Book> initailBookshelfData(){
        ArrayList<Book> booksList = new ArrayList<Book>();
        Book book = null;

        book = new Book("四级词汇");
        book.setWorldCount(3486);
        book.setLearnedWorldCount(0);
        book.setIsLearnningState(true);
        book.setIsLearnedState(true);
        book.setStudyDeadline(null);
        booksList.add(book);

        return booksList;
    }

    public static ArrayList<Book>[] initailBookLibraryData(){
        ArrayList<Book>[] bookLibrarys = new ArrayList[BookLibrary.libraryCount];

        Book book = null;
        ArrayList<Book> booksList = null;

        //热词书库
        booksList = new ArrayList<Book>();
        bookLibrarys[BookLibrary.HOT] = booksList;

        book = new Book("中考词汇");
        book.setWorldCount(2364);
        book.setLearnedWorldCount(0);
        book.setIsLearnningState(false);
        book.setIsLearnedState(false);
        book.setStudyDeadline(null);
        booksList.add(book);

        book = new Book("高考英语");
        book.setWorldCount(4124);
        book.setLearnedWorldCount(0);
        book.setIsLearnningState(false);
        book.setIsLearnedState(false);
        book.setStudyDeadline(null);
        booksList.add(book);

        book = new Book("四级词汇");
        book.setWorldCount(3486);
        book.setLearnedWorldCount(0);
        book.setIsLearnningState(false);
        book.setIsLearnedState(false);
        book.setStudyDeadline(null);
        booksList.add(book);

        book = new Book("四级高频");
        book.setWorldCount(739);
        book.setLearnedWorldCount(0);
        book.setIsLearnningState(false);
        book.setIsLearnedState(false);
        book.setStudyDeadline(null);
        booksList.add(book);

        book = new Book("六级词汇");
        book.setWorldCount(3071);
        book.setLearnedWorldCount(0);
        book.setIsLearnningState(false);
        book.setIsLearnedState(false);
        book.setStudyDeadline(null);
        booksList.add(book);

        book = new Book("考研词汇");
        book.setWorldCount(6279);
        book.setLearnedWorldCount(0);
        book.setIsLearnningState(false);
        book.setIsLearnedState(false);
        book.setStudyDeadline(null);
        booksList.add(book);


        //小学书库
        booksList = new ArrayList<Book>();
        bookLibrarys[BookLibrary.PRIMARY_SCHOOL] = booksList;

        book = new Book("一年级");
        book.setWorldCount(73);
        book.setLearnedWorldCount(0);
        book.setIsLearnningState(false);
        book.setIsLearnedState(false);
        book.setStudyDeadline(null);
        booksList.add(book);

        book = new Book("二年级");
        book.setWorldCount(79);
        book.setLearnedWorldCount(0);
        book.setIsLearnningState(false);
        book.setIsLearnedState(false);
        book.setStudyDeadline(null);
        booksList.add(book);

        book = new Book("三年级");
        book.setWorldCount(77);
        book.setLearnedWorldCount(0);
        book.setIsLearnningState(false);
        book.setIsLearnedState(false);
        book.setStudyDeadline(null);
        booksList.add(book);

        book = new Book("四年级");
        book.setWorldCount(84);
        book.setLearnedWorldCount(0);
        book.setIsLearnningState(false);
        book.setIsLearnedState(false);
        book.setStudyDeadline(null);
        booksList.add(book);

        //中高考书库
        booksList = new ArrayList<Book>();
        bookLibrarys[BookLibrary.MIDDLE_SCHOOL] = booksList;

        book = new Book("中考");
        book.setWorldCount(2364);
        book.setLearnedWorldCount(0);
        book.setIsLearnningState(false);
        book.setIsLearnedState(false);
        book.setStudyDeadline(null);
        booksList.add(book);

        book = new Book("高考词汇");
        book.setWorldCount(4124);
        book.setLearnedWorldCount(0);
        book.setIsLearnningState(false);
        book.setIsLearnedState(false);
        book.setStudyDeadline(null);
        booksList.add(book);

        book = new Book("高考高分");
        book.setWorldCount(2396);
        book.setLearnedWorldCount(0);
        book.setIsLearnningState(false);
        book.setIsLearnedState(false);
        book.setStudyDeadline(null);
        booksList.add(book);

        book = new Book("高考高频");
        book.setWorldCount(489);
        book.setLearnedWorldCount(0);
        book.setIsLearnningState(false);
        book.setIsLearnedState(false);
        book.setStudyDeadline(null);
        booksList.add(book);


        //中高考书库
        booksList = new ArrayList<Book>();
        bookLibrarys[BookLibrary.COLLEGE] = booksList;

        book = new Book("四级词汇");
        book.setWorldCount(3486);
        book.setLearnedWorldCount(0);
        book.setIsLearnningState(false);
        book.setIsLearnedState(false);
        book.setStudyDeadline(null);
        booksList.add(book);

        book = new Book("六级词汇");
        book.setWorldCount(3071);
        book.setLearnedWorldCount(0);
        book.setIsLearnningState(false);
        book.setIsLearnedState(false);
        book.setStudyDeadline(null);
        booksList.add(book);

        book = new Book("考研词汇");
        book.setWorldCount(6279);
        book.setLearnedWorldCount(0);
        book.setIsLearnningState(false);
        book.setIsLearnedState(false);
        book.setStudyDeadline(null);
        booksList.add(book);

        //中高考书库
        booksList = new ArrayList<Book>();
        bookLibrarys[BookLibrary.OTHERS] = booksList;

        return bookLibrarys;
    }

}
