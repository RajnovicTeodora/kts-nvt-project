package com.ftn.restaurant.constants;

import java.util.ArrayList;
import java.util.List;

public class MenuConstants {
    public static final List<Long> ITEM_ID_LIST = new ArrayList<Long>(){
        {
            add(1L);
        }
    };
    public static final List<Long> INVALID_ITEM_ID_LIST = new ArrayList<Long>(){
        {
            add(16L);
        }
    };

    public static final List<Long> EMPTY_ITEM_ID_LIST = new ArrayList<>();
}
