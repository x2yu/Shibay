package com.example.x2y.englishapp.ReviewPage;

import com.example.x2y.englishapp.bean.Word;

import java.util.ArrayList;
import java.util.List;

public class initWord {
    public int max=20;
    public List<Word> wordList = new ArrayList<>();
    //用于标识每一个单词是否已经记得
    public boolean[] rightFlag = new boolean[max];
    //用于标识每一个单词的选择失败次数
    public int[] failCount = new int[max];
    //private MydatabaseHelper dbHelper; //数据库连接操作
    public initWord(){
        //dbHelper = new MydatabaseHelper(this,"数据库名",null,1);
        //        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        //其中的表名应为已背单词列表
//        Cursor cursor = db.query("表名", null, null, null, null, null, null);
//        Random random = new Random();
//        if (cursor.moveToFirst()) {
//            do {
//                //遍历取出对象
//                String name = cursor.getString(cursor.getColumnIndex("name"));
//                String mean = cursor.getString(cursor.getColumnIndex("mean"));
//                Word word = new Word(name, mean);
//                wordList.add(word);
//                //初始化单词拼写flag列表为false
//                rightFlag[count] = false;
//                failCount[count]=0;
//                count++;
//                if (count == max - 1) break;
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
        Word apple = new Word("apple", "n.苹果");
        Word orange = new Word("orange", "n.橙子");
        Word banana = new Word("banana", "n.香蕉");
        Word peer = new Word("peer", "n.梨子");
        Word android = new Word("android", "n.机器人；");
        Word review = new Word("review", "n.复习；回顾；");
        Word chinese = new Word("chinese", "n.中文；汉语；");
        Word test = new Word("test", "n.测试；考验；");
        Word equip=new Word("equip","v.装备；具备；");
        Word cliff=new Word("cliff","n.悬崖；峭壁；");
        Word composer=new Word("composer","n.作曲家；设计者；");
        Word tunnel=new Word("tunnel","n.隧道；通道；");
        Word grief=new Word("grief","n.悲伤；悲痛；");
        Word charater=new Word("charater","n.人物；角色；性格；");
        Word upgrade=new Word("upgrade","vt.升级；改善；提高；");
        Word factor=new Word("factor","n.因素；因子；");
        Word membership=new Word("membership","n.会员资格；成员资格；");
        Word faith=new Word("faith","n.信任；信心 ；");
        Word enormous=new Word("enormous","adj.极大的；巨大的");
        Word helmet=new Word("helmet","n.钢盔；头盔；");
        wordList.add(apple);
        wordList.add(orange);
        wordList.add(banana);
        wordList.add(peer);
        wordList.add(android);
        wordList.add(review);
        wordList.add(chinese);
        wordList.add(test);
        wordList.add(equip);
        wordList.add(cliff);
        wordList.add(composer);
        wordList.add(tunnel);
        wordList.add(grief);
        wordList.add(charater);
        wordList.add(upgrade);
        wordList.add(factor);
        wordList.add(membership);
        wordList.add(faith);
        wordList.add(enormous);
        wordList.add(helmet);
        for (int i = 0; i < max; i++) {
            rightFlag[i] = false;
            failCount[i] = 0;
        }
    }
}
