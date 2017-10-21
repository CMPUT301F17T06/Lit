package com.example.lit;

import java.util.Date;

/**
 * Created by weikailu on 2017-10-21.
 */

public class NormalHabit extends Habit {
    public NormalHabit(String title){super(title);}

    public NormalHabit(String title, Date date) {super(title, date);}

    @Override
    public String habitType(){return "Normal";}
}
