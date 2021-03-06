import java.util.ArrayList;
import java.util.Iterator;


public class CatTree implements Iterable<CatInfo>{
    public CatNode root;

    public CatTree(CatInfo c) {
        this.root = new CatNode(c);
    }

    private CatTree(CatNode c) {
        this.root = c;
    }


    public void addCat(CatInfo c)
    {
        this.root = root.addCat(new CatNode(c));
    }

    public void removeCat(CatInfo c)
    {
        this.root = root.removeCat(c);
    }

    public int mostSenior()
    {
        return root.mostSenior();
    }

    public int fluffiest() {
        return root.fluffiest();
    }

    public CatInfo fluffiestFromMonth(int month) {
        return root.fluffiestFromMonth(month);
    }

    public int hiredFromMonths(int monthMin, int monthMax) {
        return root.hiredFromMonths(monthMin, monthMax);
    }

    public int[] costPlanning(int nbMonths) {
        return root.costPlanning(nbMonths);
    }



    public Iterator<CatInfo> iterator()
    {
        return new CatTreeIterator();
    }


    class CatNode {

        CatInfo data;
        CatNode senior;
        CatNode same;
        CatNode junior;

        public CatNode(CatInfo data) {
            this.data = data;
            this.senior = null;
            this.same = null;
            this.junior = null;
        }

        public String toString() {
            String result = this.data.toString() + "\n";
            if (this.senior != null) {
                result += "more senior " + this.data.toString() + " :\n";
                result += this.senior.toString();
            }
            if (this.same != null) {
                result += "same seniority " + this.data.toString() + " :\n";
                result += this.same.toString();
            }
            if (this.junior != null) {
                result += "more junior " + this.data.toString() + " :\n";
                result += this.junior.toString();
            }
            return result;
        }


        public CatNode addCat(CatNode c) {
            // ADD YOUR CODE HERE
            if(c == null) {
                return this; //this is the current node
            }
            CatNode root = this;

            if(this.data.monthHired < c.data.monthHired) {
                if(junior == null) {
                    junior = c;
                }else {
                    junior = junior.addCat(c);
                }

            }else if(this.data.monthHired == c.data.monthHired) {

                if(this.data.furThickness > c.data.furThickness) {
                    if(this.same == null) {
                        this.same = c;
                    }
                    else {
                        if(this.same.data.furThickness > c.data.furThickness) {
                            this.same = this.same.addCat(c);
                        }
                        else {
                            c.same = this.same;
                            this.same = c;
                        }
                    }

                }else if(this.data.furThickness < c.data.furThickness){
                    CatNode preRoot = new CatNode(this.data);
                    this.data = c.data;
                    addCat(preRoot);
                }

            }else {
                if(senior == null) {
                    senior = c;
                }else {
                    senior = senior.addCat(c);
                }
            }
            return root; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }


        public CatNode removeCat(CatInfo c) {
            // ADD YOUR CODE HERE
            CatNode r = root;
            CatNode flag;
            CatNode supNode = null;
            CatNode flgSup = null;

            while(r.data.monthHired < c.monthHired) {
                supNode = r;
                r = r.junior;
                if(r == null) return root;
            }
            while(r.data.monthHired > c.monthHired) {
                supNode = r;
                r = r.senior;
                if(r == null) return root;
            }
            while(!r.data.equals(c)) {
                supNode = r;
                r = r.same;
                if(r == null) return root;
            }

            if(r.same!= null) {
                flag = r.same;
                flag.junior = r.junior;
                flag.senior = r.senior;

                r.same = null;
                r.junior = null;
                r.senior = null;

                if(flag.data.monthHired < supNode.data.monthHired) {
                    supNode.senior = flag;
                }
                if(flag.data.monthHired > supNode.data.monthHired){
                    supNode.junior = flag;
                }
                return root;
            }

            if(r.senior!=null) {
                flag = r.senior;

                while(flag.junior!=null) {
                    flgSup = flag;
                    flag = flag.junior;
                }
                root = r.senior;
                if(flgSup!=null) flgSup.junior = r.junior;
                r.junior = null;
                r.senior = null;
                return root;
            }else {
                flag = r.junior;

                while( flag.senior!=null) {
                    flgSup = flag;
                    flag = flag.senior;
                }
                root = r.junior;
                r.junior = null;
                r.senior = null;
                flgSup.senior = null;

                return root; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
            }

        }


        public int mostSenior() {
            // ADD YOUR CODE HERE
            CatNode curNode = root;

            while(curNode.senior!=null) {
                curNode = curNode.senior;
            }

            return curNode.data.monthHired; //CHANGE THIS
        }


        public int fluffiest() {
            // ADD YOUR CODE HERE
            int furMax = data.furThickness;
//            if((this.same == null) && (this.senior == null) && (this.junior == null)) {
//                return data.furThickness;
//            }
//            else{
//                if(!(this.same == null) && (this.same.fluffiest() > furMax)) {
//                    furMax = same.fluffiest();
//                }
//                if(!(this.senior == null) && (this.senior.fluffiest() > furMax)) {
//                    furMax = senior.fluffiest();
//                }
//                if(!(this.junior == null) && (this.junior.fluffiest() > furMax)) {
//                    furMax = junior.fluffiest();
//                }
//            }
            if(junior!= null) furMax = Math.max (furMax, junior.fluffiest());
            if(senior!= null) furMax = Math.max (furMax, senior.fluffiest());
            return furMax; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }


        public int hiredFromMonths(int monthMin, int monthMax) {

            int catNum = 0;
            if (monthMin > monthMax) return 0;

            if (this.junior!=null){
                catNum += junior.hiredFromMonths(monthMin,monthMax);
            }
            if ( (this.data.monthHired <= monthMax) && (this.data.monthHired >= monthMin)){
                catNum ++;
            }
            if (this.senior!=null){
                catNum += senior.hiredFromMonths(monthMin,monthMax);
            }
            if (this.same!=null){
                catNum += same.hiredFromMonths(monthMin,monthMax);
            }
            return catNum;
        }

        public CatInfo fluffiestFromMonth(int month) {
            // ADD YOUR CODE HERE

            CatInfo flag = null;
            if (this.data.monthHired > month) {
                flag = senior.fluffiestFromMonth(month);
            }
            else if(this.data.monthHired == month){
                return this.data;
            }
            else if(this.data.monthHired < month){
                flag = junior.fluffiestFromMonth(month);
            }
            return flag;
        }

        public int[] costPlanning(int nbMonths) {
            // ADD YOUR CODE HERE
            Iterator<CatInfo>  catInfoIterator = new CatTreeIterator();
            int [] array = new int[nbMonths];

            while(catInfoIterator.hasNext()) {
                CatInfo a = catInfoIterator.next();
                array[a.nextGroomingAppointment-243] += a.expectedGroomingCost;
            }
            return array; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }

    }


    private class CatTreeIterator implements Iterator<CatInfo> {
        // HERE YOU CAN ADD THE FIELDS YOU NEED
//        LinkedList<CatNode> stack = new LinkedList<CatNode>();
        private ArrayList<CatNode> list = new ArrayList<>();
        private int i = 0;

        public CatTreeIterator() {
            //YOUR CODE GOES HERE
            listInorder(root);
        }

        private void listInorder(CatNode root) {
            if(root.senior!=null){
                listInorder(root.senior);
            }
            list.add(root);
            if(root.same!=null){
                listInorder(root.same);
            }
            if (root.junior!=null){
                listInorder(root.junior);
            }
        }

        public CatInfo next(){
            //YOUR CODE GOES HERE
            CatNode p = list.get(i);
            i ++;
            return p.data;
        }

        public boolean hasNext() {
            //YOUR CODE GOES HERE
            return (i < list.size());
        }
    }

}
