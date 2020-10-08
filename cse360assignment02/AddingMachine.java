package cse360assignment02;
import java.util.LinkedList;
import java.util.Queue;

public class AddingMachine {
    private int total;
    Queue< String> q = new LinkedList<>() ;
    public AddingMachine () {
        total = 0;  // not needed - included for clarity
        String str = total +"";
        q.add(str);
    }

    public int getTotal () {
        return this.total;
    }

    public void add (int value) {
        String str = " + "+value;
        q.offer(str);
        this.total = this.total + value;
    }

    public void subtract (int value) {
        String str = " - "+value;
        q.offer(str);
        this.total = this.total - value;
    }

    public String toString () {
        String str = "";
        for(String s : q){
            str += s;
        }
        return str;
    }

    public void clear() {
        this.total =0;
        q.clear();
        String str = 0+"";
        q.offer(str);

    }
}
