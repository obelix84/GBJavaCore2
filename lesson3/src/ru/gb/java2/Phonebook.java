package ru.gb.java2;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Phonebook {

    //ставим в соответсвие ИМЕНИ номер ТЕЛЕФОНА
    private List<String> names;
    private List<String> phones;

    public Phonebook() {
        names = new ArrayList<String>();
        phones = new ArrayList<String>();
    }

    public void add(String name, String phone) {
        this.names.add(name);
        this.phones.add(phone);
    }

    public List<String> get(String name) {
        List<String> phoneNumbers = new ArrayList<String>();
        Iterator<String> iter = this.names.iterator();
        int index = 0;
        while (iter.hasNext()) {
            String str = iter.next();
            if (str.equals(name)) {
                phoneNumbers.add(this.phones.get(index));
            }
            index++;
        }
        return phoneNumbers;
    }
}
