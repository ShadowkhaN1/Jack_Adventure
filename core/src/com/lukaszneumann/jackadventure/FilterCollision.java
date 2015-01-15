package com.lukaszneumann.jackadventure;


/**
 * Created by Lukasz on 2015-01-10.
 */
public class FilterCollision {


    public static enum filterCategory {
        Player, Witch, SmallCandy, StarWitch, Cloud, Tombstone
    }

    public short getFilterCategory(filterCategory filterCategory) {

        switch (filterCategory) {
            case Player:
                return 0x0001;
            case Witch:
                return 0x0002;
            case SmallCandy:
                return 0x0004;
            case StarWitch:
                return 0x0008;
            case Tombstone:
                return 0x0010;
            case Cloud:
                return 0x0000;
            default:
                return 1;

        }
    }
}
